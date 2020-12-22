package gears.sidescroller.world.levels;

import gears.sidescroller.world.areas.Area;
import gears.sidescroller.entities.Player;
import java.awt.Graphics;

/**
 *
 * @author Matt Crow
 */
public class Level {
    private final int defaultArea;
    private int currentArea;
    private final Area[] areas;
    private Player player; //maybe move this to a Game class
    
    public Level(Area[] areaList, int startArea){
        defaultArea = startArea;
        currentArea = defaultArea;
        areas = areaList;
        player = null;
    }
    
    public Level loadPlayer(Player p){
        player = p;
        areas[currentArea].addEntity(p); //don't forget to remove the player when changing areas
        return this;
    }
    
    public Level init(){
        for(Area area : areas){
            area.init();
        }
        if(player != null){
            //player.init();
        }
        return this;
    }
    
    public Area getCurrentArea(){
        return (currentArea >= 0 && currentArea < areas.length) ? areas[currentArea] : null;
    }
    
    public Level update(){
        areas[currentArea].update();
        return this;
    }
    
    public Level draw(Graphics g){
        areas[currentArea].draw(g);
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
            sb.append(String.format("Area #%d:\n", i));
            sb.append(areas[i].toString());
        }
        return sb.toString();
    }
}
