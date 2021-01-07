package gears.sidescroller.world.levels;

import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.util.Direction;
import gears.sidescroller.util.Matrix;
import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.world.tileMaps.MapBoundsReachedListener;
import gears.sidescroller.world.tileMaps.OutOfBoundsEvent;
import java.awt.Graphics;

/**
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
    
    public Level loadPlayer(Player p){
        player = p;
        Area currArea = getCurrentArea();
        currArea.addEntity(p); //don't forget to remove the player when changing areas
        currArea.getTileMap().spawnEntityCenter(p);
        return this;
    }
    
    public Level init(){
        this.forEachCell((area, xIdx, yIdx)->{
            if(area != null){
                area.init();
            }
        });
        
        if(player != null){
            //player.init();
        }
        
        return this;
    }
    
    public Area getCurrentArea(){
        return this.get(currentAreaX, currentAreaY);
    }
    
    public Level update(){
        getCurrentArea().update();
        return this;
    }
    
    public Level draw(Graphics g){
        getCurrentArea().draw(g);
        if(player != null){
            player.draw(g);
        }
        return this;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TODO: better loggin in Level::toString\n");
        sb.append("AREAS:\n");
        this.forEachCell((area, xIdx, yIdx)->{
            sb.append(String.format("Area #%d,%d:\n", xIdx, yIdx));
            sb.append((area == null) ? "NULL" : area.toString());
        });
        return sb.toString();
    }

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
                    AbstractEntity e = event.getOutOfBoundsEntity();
                    e.getArea().removeEntity(e);
                    get(newXIdx, newYIdx).addEntity(e);
                    if(e.equals(player)){
                        currentAreaX = newXIdx;
                        currentAreaY = newYIdx;
                        System.out.printf("Moved player to area (%d, %d)\n", newXIdx, newYIdx);
                    }
                } else {
                    System.out.printf("Cannot spawn entity in area:\n %s \n", get(newXIdx, newYIdx).toString());
                }
            }
        }
    }
}
