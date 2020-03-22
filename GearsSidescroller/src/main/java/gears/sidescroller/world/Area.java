package gears.sidescroller.world;

import java.awt.Graphics;

/**
 * this will be used to store a tileMap,
 * list of Entities,
 * and Machines.
 * 
 * @author Matt Crow
 */
public class Area {
    private final TileMap tileMap;
    
    public Area(TileMap t){
        tileMap = t;
    }
    
    public Area draw(Graphics g){
        tileMap.draw(g);
        return this;
    }
}
