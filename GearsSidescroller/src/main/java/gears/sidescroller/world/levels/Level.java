package gears.sidescroller.world.levels;

import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.util.Direction;
import gears.sidescroller.util.Matrix;
import gears.sidescroller.world.core.MobileWorldObject;
import gears.sidescroller.world.tileMaps.MapBoundsReachedListener;
import gears.sidescroller.world.tileMaps.OutOfBoundsEvent;
import java.awt.Graphics;

/**
 * A Level represents a grid of Areas where the Player can move around. Upon
 * reaching the edge of an Area, an AbstractEntity will be moved to the appropriate
 * next Area, if such an Area exists.
 * 
 * @author Matt Crow
 */
public class Level extends Matrix<Area> implements MapBoundsReachedListener {
    private final int defaultAreaX;
    private final int defaultAreaY;
    private int currentAreaX;
    private int currentAreaY;
    private Player player; //maybe move this to a Game class
    
    public Level(Area[][] areaList, int startAreaX, int startAreaY){
        super(areaList[0].length, areaList.length);
        this.setContents(areaList);
        defaultAreaX = startAreaX;
        defaultAreaY = startAreaY;
        currentAreaX = defaultAreaX;
        currentAreaY = defaultAreaY;
        this.forEachCell((area, xIdx, yIdx)->{
            area.getTileMap().addMapBoundsReachListener(this);
        });
        player = null;
    }
    
    /**
     * Loads a Player into this Level,
     * adding them to the current Area.
     * 
     * @param p the Player to load.
     * @return this, for chaining purposes
     */
    public final Level loadPlayer(Player p){
        player = p;
        p.init();
        Area currArea = getCurrentArea();
        currArea.addToWorld(p); //don't forget to remove the player when changing areas
        currArea.getTileMap().spawnEntityCenter(p);
        return this;
    }
    
    /**
     * Initializes the Level and all its subcomponents.
     * 
     * @return this, for chaining purposes. 
     */
    public final Level init(){
        this.forEachCell((area, xIdx, yIdx)->{
            if(area != null){
                area.init();
            }
        });
        
        if(player != null){
            player.init();
        }
        
        return this;
    }
    
    /**
     * 
     * @return the Area the Player is currently occupying.
     */
    public final Area getCurrentArea(){
        return this.get(currentAreaX, currentAreaY);
    }
    
    /**
     * Updates the current Area. Note that 
     * this means all other Areas are suspended,
     * and are not updated.
     * 
     * @return this, for chaining purposes. 
     */
    public final Level update(){
        getCurrentArea().update();
        return this;
    }
    
    /**
     * Renders this Level on the current Graphics context.
     * 
     * @param g the Graphics to render on.
     * @return this, for chaining purposes.
     */
    public final Level draw(Graphics g){
        getCurrentArea().draw(g);
        if(player != null){
            player.draw(g);
        }
        return this;
    }
    
    /**
     * This is fired by an instance of TileMap whenever an AbstractEntity passes beyond its borders.
     * If the offending AbstractEntity is the Player, this will transition them to the appropriate new
     * Area (passing the lefternmost bounds of an Area attempts to move the Player one Area to the left, 
     * for example). Note that this method assumes the entity is passing the bounds of the current Area.
     * Therefore, if this method is invoked for an Area besides the current one, this method will behave 
     * erroneously. However, this shouldn't be a problem, as only the current Area is updated, and therefore
     * is the only one which can invoke this method.
     * 
     * @param event an event detailing how something went out of bounds.
     */
    @Override
    public void boundReached(OutOfBoundsEvent event) {
        int newXIdx = this.currentAreaX + event.getDirection().getXMod();
        int newYIdx = this.currentAreaY + event.getDirection().getYMod();
        if(event.getMap().equals(this.getCurrentArea().getTileMap()) && this.isValidIdx(newXIdx, newYIdx)){
            // is valid index
            if(this.get(newXIdx, newYIdx) == null){
                throw new UnsupportedOperationException("TODO: generate new area");
            } else {
                boolean canSpawn = get(newXIdx, newYIdx).getTileMap().spawnEntityFromDir(event.getOutOfBoundsEntity(), Direction.rotateCounterClockWise(Direction.rotateCounterClockWise(event.getDirection())));
                if(canSpawn){
                    MobileWorldObject e = event.getOutOfBoundsEntity();
                    e.getArea().removeFromWorld(e);
                    get(newXIdx, newYIdx).addToWorld(e);
                    if(e.equals(player)){
                        currentAreaX = newXIdx;
                        currentAreaY = newYIdx;
                        System.out.printf("Moved player to area (%d, %d)\n", newXIdx, newYIdx);
                    }
                } else {
                    System.err.printf("Cannot spawn entity in area:\n %s \n", get(newXIdx, newYIdx).toString());
                }
            }
        }
    }
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TODO: better logging in Level::toString\n");
        sb.append("AREAS:\n");
        this.forEachCell((area, xIdx, yIdx)->{
            sb.append(String.format("Area #%d,%d:\n", xIdx, yIdx));
            sb.append((area == null) ? "NULL" : area.toString());
        });
        return sb.toString();
    }
}
