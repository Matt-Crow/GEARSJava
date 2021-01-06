package gears.sidescroller.world.levels;

import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.util.Direction;
import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.world.tileMaps.MapBoundsReachedListener;
import gears.sidescroller.world.tileMaps.OutOfBoundsEvent;
import java.awt.Graphics;
import java.util.function.Consumer;

/**
 * May make this extend Matrix later
 * @author Matt Crow
 */
public class Level implements MapBoundsReachedListener {
    private final int defaultAreaX;
    private final int defaultAreaY;
    private int currentAreaX;
    private int currentAreaY;
    private final Area[][] areas;
    private Player player; //maybe move this to a Game class
    
    public Level(Area[][] areaList, int startAreaX, int startAreaY){
        defaultAreaX = startAreaX;
        defaultAreaY = startAreaY;
        currentAreaX = defaultAreaX;
        currentAreaY = defaultAreaY;
        areas = areaList;
        forEachArea((Area a)->{
            a.getTileMap().addMapBoundsReachListener(this);
        });
        player = null;
    }
    
    private void forEachArea(Consumer<Area> action){
        for(int i = 0; i < areas.length; i++){
            for(int j = 0; j < areas[i].length; j++){
                if(areas[i][j] != null){
                    action.accept(areas[i][j]);
                }
            }
        }
    }
    
    public Level loadPlayer(Player p){
        player = p;
        areas[currentAreaY][currentAreaX].addEntity(p); //don't forget to remove the player when changing areas
        areas[currentAreaY][currentAreaX].getTileMap().spawnEntityCenter(p);
        return this;
    }
    
    public Level init(){
        for(int y = 0; y < areas.length; y++){
            for(int x = 0; x < areas[y].length; x++){
                if(areas[y][x] != null){
                    areas[y][x].init();
                }
            }
        }
        if(player != null){
            //player.init();
        }
        return this;
    }
    
    public Area getCurrentArea(){
        return areas[currentAreaY][currentAreaX];
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
        for(int i = 0; i < areas.length; i++){
            for(int j = 0; j < areas[i].length; j++){
                sb.append(String.format("Area #%d,%d:\n", i, j));
                sb.append((areas[i][j] == null) ? "NULL" : areas[i][j].toString());
            }
        }
        return sb.toString();
    }

    @Override
    public void boundReached(OutOfBoundsEvent event) {
        int newXIdx = this.currentAreaX + event.getDirection().getXMod();
        int newYIdx = this.currentAreaY + event.getDirection().getYMod();
        if(event.getMap().equals(this.getCurrentArea().getTileMap()) && newYIdx >= 0 && newYIdx < this.areas.length && newXIdx >= 0 && newXIdx < this.areas[0].length){
            // is valid index
            if(areas[newYIdx][newXIdx] == null){
                throw new UnsupportedOperationException("TODO: generate new area");
            } else {
                boolean canSpawn = areas[newYIdx][newXIdx].getTileMap().spawnEntityFromDir(event.getOutOfBoundsEntity(), Direction.rotateCounterClockWise(Direction.rotateCounterClockWise(event.getDirection())));
                if(canSpawn){
                    AbstractEntity e = event.getOutOfBoundsEntity();
                    e.getArea().removeEntity(e);
                    areas[newYIdx][newXIdx].addEntity(e);
                    if(e.equals(player)){
                        currentAreaX = newXIdx;
                        currentAreaY = newYIdx;
                        System.out.printf("Moved player to area (%d, %d)\n", newXIdx, newYIdx);
                    }
                } else {
                    System.out.printf("Cannot spawn entity in area:\n %s \n", areas[newYIdx][newXIdx].toString());
                }
            }
        }
    }
}
