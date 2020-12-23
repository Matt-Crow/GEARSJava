package gears.sidescroller.world.tileMaps;

import gears.io.StreamWriterUtil;
import gears.sidescroller.entities.AbstractEntity;
import gears.sidescroller.entities.Player;
import gears.sidescroller.world.tiles.AbstractTile;
import java.awt.Graphics;
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
    /*
    The tileSet serves as a mapping of bytes to tiles.
    Given the mapping b -> t, each instance of b in the
    tile map represents an instance of t.
    
    This field allows the class to use the Flyweight design
    pattern to drastically minimize the amount of memory used
    to store and retrieve tile map information.
    */
    private final HashMap<Byte, AbstractTile> tileSet;
    private final byte width;
    private final byte height;
    private final byte[][] map;
    
    /*
    The coordinates players will
    spawn at upon entering this tile map.
    
    This feature will need to be changed
    in future versions, given the change
    to overhead view.
    */
    private int leftPlayerSpawnX;
    private int leftPlayerSpawnY;
    private int rightPlayerSpawnX;
    private int rightPlayerSpawnY;
    
    /**
     * Creates a new TileMap of the given size.
     * 
     * @param w the width of this TileMap, measured in tiles
     * @param h the height of this TileMap, measured in tiles
     */
    public TileMap(byte w, byte h){
        tileSet = new HashMap<>();
        width = w;
        height = h;
        map = new byte[height][width];
        for(byte y = 0; y < height; y++){
            for(byte x = 0; x < width; x++){
                map[y][x] = 0;
            }
        }
        leftPlayerSpawnX = 0;
        rightPlayerSpawnX = 0;
        leftPlayerSpawnY = 0;
        rightPlayerSpawnY = 0;
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
    public TileMap addToTileSet(byte key, AbstractTile tile){
        if(tile == null){
            throw new NullPointerException("tile cannot be null");
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
    private boolean isValidIdx(byte xIdx, byte yIdx) {
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
    public TileMap setTile(byte xIndex, byte yIndex, byte key){
        if(!isValidIdx(xIndex, yIndex)){
            throw new IndexOutOfBoundsException(String.format("Map indeces must range from (0 <= x < %d, 0 <= y < %d), so the point (%d, %d) is invalid.", width, height, xIndex, yIndex));
        }
        
        map[yIndex][xIndex] = key;
        
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
        for(byte y = 0; y < height; y++){
            row = new String[width];
            for(byte x = 0; x < width; x++){
                row[x] = Byte.toString(map[y][x]);
            }
            b.append(String.join(", ", row)).append(StreamWriterUtil.NEWLINE);
        }
        return b.toString();
    }
    
    private void keepInBounds(AbstractEntity e){
        if(e.getX() < 0){
            e.setX(0);
        } else if(e.getX() + e.getWidth() >= this.width * TILE_SIZE){
            e.setX(this.width * TILE_SIZE - e.getWidth());
        }
        
        if(e.getY() < 0){
            e.setY(0);
        } else if(e.getY() + e.getHeight() >= this.height * TILE_SIZE){
            e.setY(this.height * TILE_SIZE - e.getHeight());
        }
    }
    
    private boolean handleCollisions(AbstractEntity e, byte tileXIdx, byte tileYIdx){
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
        keepInBounds(e);
        
        /*
        This gets the tile index for the UPPER LEFT corner of e
        becuase of how integer division rounds down.
        
        Therefore, need to check for collisions with four tiles
        in a 2x2 tile square to catch all the tiles they could
        possibly be colliding with.
        */
        
        byte yIdx = (byte)(e.getY() / AbstractTile.TILE_SIZE);
        byte xIdx = (byte)(e.getX() / AbstractTile.TILE_SIZE);
        
        boolean collUpperLeft = handleCollisions(e, xIdx, yIdx);
        boolean collUpperRight = handleCollisions(e, (byte)(xIdx+1), yIdx);
        boolean collLowerLeft = handleCollisions(e, xIdx, (byte)(yIdx+1));
        boolean collLowerRight = handleCollisions(e, (byte)(xIdx+1), (byte)(yIdx+1));
        
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
        for(byte x = 0; x < width; x++){
            for(byte y = 0; y < height; y++){
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
    
    
    
    /*
    Need to change these later
    */
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
}
