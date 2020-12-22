package gears.sidescroller.world.tileMaps;

import gears.io.StreamWriterUtil;
import gears.sidescroller.entities.AbstractEntity;
import gears.sidescroller.entities.Player;
import gears.sidescroller.world.tiles.AbstractTile;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;

/**
 * A TileMap is used to store tiles in a two-dimensional matrix.
 * This allows for O(1) lookup for collisions, as well as fast
 * reading and writing.
 * 
 * @author Matt Crow
 */
public class TileMap {
    private final HashMap<Integer, AbstractTile> tileSet;
    private final int width;
    private final int height;
    private final int[][] map;
    private final ArrayList<AbstractTile> builtMap;
    
    private int leftPlayerSpawnX;
    private int leftPlayerSpawnY;
    private int rightPlayerSpawnX;
    private int rightPlayerSpawnY;
    
    /**
     * 
     * @param w the width of this TileMap, measured in tiles
     * @param h the height of this TileMap, measured in tiles
     */
    public TileMap(int w, int h){
        tileSet = new HashMap<>();
        builtMap = new ArrayList<>();
        width = w;
        height = h;
        map = new int[height][width];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                map[y][x] = 0;
            }
        }
        leftPlayerSpawnX = 0;
        rightPlayerSpawnX = 0;
        leftPlayerSpawnY = 0;
        rightPlayerSpawnY = 0;
    }
    
    public final TileMap setLeftPlayerSpawnTile(int xIndex, int yIndex){
        leftPlayerSpawnX = xIndex * AbstractTile.TILE_SIZE;
        leftPlayerSpawnY = yIndex * AbstractTile.TILE_SIZE;
        return this;
    }
    public final TileMap setRightPlayerSpawnTile(int xIndex, int yIndex){
        rightPlayerSpawnX = xIndex * AbstractTile.TILE_SIZE;
        rightPlayerSpawnY = yIndex * AbstractTile.TILE_SIZE;
        return this;
    }
    
    public final TileMap spawnPlayerLeft(Player p){
        p.setX(leftPlayerSpawnX);
        p.setY(leftPlayerSpawnY);
        return this;
    }
    public final TileMap spawnPlayerRight(Player p){
        p.setX(rightPlayerSpawnX);
        p.setY(rightPlayerSpawnY);
        return this;
    }
    
    /**
     * Adds a tile to this map's tile set.
     * When constructing tiles from this'
     * tile map, each cell in the map with
     * the given key will represent a copy
     * of the given tile.
     * 
     * @param key
     * @param tile
     * @return this, for chaining purposes
     */
    public TileMap addToTileSet(int key, AbstractTile tile){
        if(tile == null){
            throw new NullPointerException("tile cannot be null");
        }
        if(!builtMap.isEmpty()){
            throw new RuntimeException("Cannot edit the tile map while this is alread built: call clearBuiltMap, addToTileSet, then buildMap");
        }
        tileSet.put(key, tile);
        return this;
    }
    
    /**
     * Checks if the given values are valid indeces
     * in the tile map array. That is to say,
     * <code>tileMap[yIdx][xIdx]</code> will not throw an IndexOutOfBoundsException.
     * 
     * @param xIdx the x index into the array
     * @param yIdx the y index into the array
     * 
     * @return whether or not the given indeces are valid. 
     */
    private boolean isValidIdx(int xIdx, int yIdx) {
        return xIdx >= 0 && xIdx < width && yIdx >= 0 && yIdx < height;
    }
    
    /**
     * Sets the tile key for the given x- and y-indeces.
     * 
     * @param xIndex the x-index for the tile
     * @param yIndex the y-index for the tile
     * @param key the key for a tile type in this' tile set.
     * 
     * @return this, for chaining purposes
     */
    public TileMap setTile(int xIndex, int yIndex, int key){
        if(xIndex < 0 || xIndex >= width){
            throw new IndexOutOfBoundsException(String.format("xIndex parameter must be withing the range 0 <= xIndex < %d, so %d is out of bounds", width, xIndex));
        }
        if(yIndex < 0 || yIndex >= height){
            throw new IndexOutOfBoundsException(String.format("yIndex parameter must be withing the range 0 <= yIndex < %d, so %d is out of bounds", height, yIndex));
        }
        if(!builtMap.isEmpty()){
            throw new RuntimeException("Cannot edit the tile map while this is alread built: call clearBuiltMap, setTile, then buildMap");
        }
        
        map[yIndex][xIndex] = key;
        
        return this;
    }
    
    /**
     * Constructs the map based
     * on the current tile map.
     * 
     * @return this, for chaining purposes 
     */
    public TileMap buildMap(){
        builtMap.clear();
        int key;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                key = map[y][x];
                if(tileSet.containsKey(key)){
                    builtMap.add(tileSet.get(key).copyTo(x, y));
                } else {
                    // not sure if I want this
                    throw new RuntimeException(String.format("Tile set does not contain key '%d'", key));
                }
            }
        }
        return this;
    }
    
    /**
     * Clears the built map cache, allowing
     * the tile map to be changed.
     * 
     * @return this, for chaining purposes.
     */
    public final TileMap clearBuiltMap(){
        builtMap.clear();
        return this;
    }
    
    /**
     * Gets this' tile map as CSV. Note that this does not include
     * this' tile set.
     * 
     * @return this' tile map in CSV format, ready to write to a file. 
     */
    public final String getTileMapCsv(){
        StringBuilder b = new StringBuilder();
        String[] row;
        for(int y = 0; y < height; y++){
            row = Arrays.stream(map[y]).mapToObj((int cell)->{
                return Integer.toString(cell);
            }).toArray((int size)->{
                return new String[size];
            });
            b.append(String.join(", ", row)).append(StreamWriterUtil.NEWLINE);
        }
        return b.toString();
    }
    
    private boolean handleCollisions(AbstractEntity e, int tileXIdx, int tileYIdx){
        boolean collided = false;
        if(isValidIdx(tileXIdx, tileYIdx) && tileSet.get(map[tileYIdx][tileXIdx]).getIsTangible()){
            collided = true;
            // now know the entity has collided, so now figure out how to shove them out
            int tileLeft = tileXIdx * AbstractTile.TILE_SIZE;
            int tileTop = tileYIdx * AbstractTile.TILE_SIZE;
            // these are the coordinates of the tile they are colliding with
            int diffX = tileLeft - e.getX();
            int diffY = tileTop - e.getY();
            if(Math.abs(diffX) > Math.abs(diffY)){
                // shove them to the side
                if(diffX < AbstractTile.TILE_SIZE / 2){
                    // entity is more than half way through the tile, so shove them right
                    e.setX(tileLeft + AbstractTile.TILE_SIZE + 1);
                } else {
                    // not half way though, so shove left
                    e.setX(tileLeft - e.getWidth() - 1);
                }
            } else {
                // shove up or down
                if(diffY < AbstractTile.TILE_SIZE / 2){
                    // more than half way down, so shove down
                    e.setY(tileTop + AbstractTile.TILE_SIZE + 1);
                } else {
                    // less, so pull up
                    e.setY(tileTop - e.getHeight() - 1);
                }
            }
        }
        return collided;
    }
    
    /**
     * Checks to see if the given entity is inside a block,
     * and shoves them out if that block is tangible.
     * 
     * @param e the entitiy to check for collisions with
     * @return whether or not a collision was detected
     */
    public final boolean checkForCollisions(AbstractEntity e){
        /*
        This gets the tile index for the UPPER LEFT corner of e
        becuase of how integer division rounds down.
        
        Therefore, need to check for collisions with four tiles
        in a 2x2 tile square to catch all the tiles they could
        possibly be colliding with.
        */
        int yIdx = e.getY() / AbstractTile.TILE_SIZE;
        int xIdx = e.getX() / AbstractTile.TILE_SIZE;
        
        boolean collUpperLeft = handleCollisions(e, xIdx, yIdx);
        boolean collUpperRight = handleCollisions(e, xIdx + 1, yIdx);
        boolean collLowerLeft = handleCollisions(e, xIdx, yIdx + 1);
        boolean collLowerRight = handleCollisions(e, xIdx + 1, yIdx + 1);
        
        // do this to avoid short-circuit evaluation
        return collUpperLeft
            || collUpperRight
            || collLowerLeft
            || collLowerRight;
    }
    
    /**
     * Renders each tile in this' builtMap
     * on the given graphics.
     * 
     * @param g the Graphics to render tiles on.
     * @return this, for chaining purposes
     */
    public final TileMap draw(Graphics g){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                tileSet.get(map[y][x]).drawAt(g, x * TILE_SIZE, y * TILE_SIZE);
            }
        }
        return this;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TODO: better TileMap::toString\n");
        sb.append("TILE MAP\n");
        tileSet.forEach((i, tile)->{
            sb.append(String.format("%d : %s\n", i, tile.toString()));
        });
        sb.append(getTileMapCsv());
        return sb.toString();
    }
}
