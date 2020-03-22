package gears.sidescroller.world;

import gears.sidescroller.entities.AbstractEntity;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * this will be used to store a tileMap,
 * list of Entities,
 * and Machines.
 * 
 * @author Matt Crow
 */
public class Area {
    private final TileMap tileMap;
    private final ArrayList<AbstractEntity> entities;
    
    public Area(TileMap t){
        tileMap = t;
        entities = new ArrayList<>();
    }
    
    public Area addEntity(AbstractEntity e){
        entities.add(e);
        return this;
    }
    public boolean removeEntity(AbstractEntity e){
        return entities.remove(e);
    }
    
    public Area update(){
        entities.forEach((e)->{
            e.update();
            tileMap.checkForCollisions(e);
        });
        return this;
    }
    
    public Area draw(Graphics g){
        tileMap.draw(g);
        return this;
    }
}
