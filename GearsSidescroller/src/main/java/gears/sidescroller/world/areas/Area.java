package gears.sidescroller.world.areas;

import gears.sidescroller.entities.AbstractEntity;
import gears.sidescroller.world.tileMaps.TileMap;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this will be used to store a tileMap,
 * list of Entities,
 * and Machines.
 * 
 * @author Matt Crow
 */
public class Area {
    private final TileMap tileMap;
    private final HashMap<Long, AbstractEntity> entities;
    
    public Area(TileMap t){
        tileMap = t;
        entities = new HashMap<>();
    }
    
    public Area addEntity(AbstractEntity e){
        entities.put(e.getId(), e);
        return this;
    }
    public boolean removeEntity(AbstractEntity e){
        return entities.remove(e.getId(), e);
    }
    
    public Area init(){
        tileMap.buildMap();
        //entities.values().forEach((e)->e.init());
        return this;
    }
    
    public TileMap getTileMap(){
        return tileMap;
    }
    
    public Area update(){
        entities.values().forEach((e)->{
            e.update();
            tileMap.checkForCollisions(e);
        });
        return this;
    }
    
    public Area draw(Graphics g){
        tileMap.draw(g);
        return this;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TODO: better Area::toString\n");
        sb.append("Tile Map:\n");
        sb.append(tileMap.toString());
        return sb.toString();
    }
}
