package gears.sidescroller.world.structures;

import gears.sidescroller.world.core.WorldObject;
import gears.sidescroller.world.tileMaps.TileMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A Structure is currently simply a Matrix which gets inserted
 * into a TileMap. Later versions may see these become more akin
 * to miniature Areas, with their own machines and items.
 * 
 * @author Matt Crow
 */
public class Structure {
    private final int x;
    private final int y;
    private final TileMap tileMap;
    private final Set<WorldObject> objects;
    
    public Structure(int x, int y, TileMap tileMap, Set<WorldObject> objects) {
        this.x = x;
        this.y = y;
        this.tileMap = tileMap;
        this.objects = objects;
    }
    
    /**
     * @return the x-coordinate of this Structure within an Area, measured in
     *  pixel-space
     */
    public int getX(){
        return x;
    }
    
    /**
     * @return the y-coordinate of this Structure within an Area, measured in
     *  pixel-space
     */
    public int getY(){
        return y;
    }
    
    /**
     * @return this structure's tile map. Changes to this map will be reflected
     *  in this structure.
     */
    public TileMap getTileMap(){
        return tileMap;
    }
    
    /**
     * @return a shallow copy of this structure's world objects
     */
    public Set<WorldObject> getWorldObjects(){
        return new HashSet<>(objects);
    }
}
