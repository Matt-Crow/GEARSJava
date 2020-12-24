package gears.sidescroller.world.levels;

import gears.sidescroller.world.areas.Area;
import gears.sidescroller.entities.Player;
import java.awt.Graphics;

/**
 *
 * @author Matt Crow
 */
public class Level {
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
        player = null;
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
}
