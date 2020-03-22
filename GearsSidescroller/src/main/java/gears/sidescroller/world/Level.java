package gears.sidescroller.world;

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
    
    public Level draw(Graphics g){
        areas[currentArea].draw(g);
        if(player != null){
            player.draw(g);
        }
        return this;
    }
}
