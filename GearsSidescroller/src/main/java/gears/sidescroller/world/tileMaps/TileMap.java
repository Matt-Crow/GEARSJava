package gears.sidescroller.world.tileMaps;

import gears.io.StreamWriterUtil;
import gears.sidescroller.entities.AbstractEntity;
import gears.sidescroller.util.Direction;
import gears.sidescroller.world.tiles.AbstractTile;
import java.awt.Graphics;
import java.util.HashMap;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Point;
import java.util.LinkedList;

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
    
    private final LinkedList<MapBoundsReachedListener> boundsReachedListeners;
    
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
        boundsReachedListeners = new LinkedList<>();
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
    
    private boolean isTileOpen(byte xIdx, byte yIdx){
        return isValidIdx(xIdx, yIdx) && !tileSet.get(map[yIdx][xIdx]).getIsTangible();
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
            fireMapBoundsReached(Direction.LEFT);
        } else if(e.getX() + e.getWidth() >= this.width * TILE_SIZE){
            e.setX(this.width * TILE_SIZE - e.getWidth() - 1);
            fireMapBoundsReached(Direction.RIGHT);
        }
        
        if(e.getY() < 0){
            e.setY(0);
            fireMapBoundsReached(Direction.UP);
        } else if(e.getY() + e.getHeight() >= this.height * TILE_SIZE){
            e.setY(this.height * TILE_SIZE - e.getHeight() - 1);
            fireMapBoundsReached(Direction.DOWN);
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
    
    /**
     * Searches for an intangible tile index around the given tile map coordinates.
     * 
     * @param initialXIdx the tile x index to check for intangible tiles around
     * @param initialYIdx the tile y index to check fro intangible tiles around
     * 
     * @return the indeces of an intangible tile around the given tile, or null if no open tiles exist
     */
    private Point searchForValidSpawnTile(byte initialXIdx, byte initialYIdx){
        Point ret = null;
        byte xIdx = initialXIdx;
        byte yIdx = initialYIdx;
        if(isTileOpen(xIdx, yIdx)){
            ret = new Point(xIdx, yIdx);
        }        
        Direction spiralDir = Direction.UP;
        byte spiralLength = 1;
        byte spiralLengthThusFar = 0;
        int numTilesChecked = 0;
        boolean justTurned = true;
        while(ret == null && numTilesChecked < this.height * this.width){
            if(this.isValidIdx(xIdx, yIdx)){
                numTilesChecked++; // doesn't run if checking a point outside the map
            }
            // search in a spiralling pattern
            xIdx += spiralDir.getXMod();
            yIdx += spiralDir.getYMod();
            spiralLengthThusFar++;
            if(spiralLengthThusFar >= spiralLength){ // time to turn
                spiralDir = Direction.rotateCounterClockWise(spiralDir);
                spiralLengthThusFar = 0;
                if(justTurned){
                    justTurned = false;
                } else { // need to increase spiral length every other turn
                    // completed one loop
                    spiralLength += 1; // search in a wider spiral
                    justTurned = true;
                }
            }
            //System.out.println(spiralDir.getName());
            if(isTileOpen(xIdx, yIdx)){
                ret = new Point(xIdx, yIdx);
            }
        }
        return ret;
    }
    
    /**
     * Sets an entity's coordinates to those of an open tile around the given tile.
     * 
     * @param e the entity to set coordinates for
     * @param xIdx the tile x index to check around
     * @param yIdx the tile y index to check around
     * @return this, for chaining purposes
     */
    private TileMap spawnEntityFromPoint(AbstractEntity e, byte xIdx, byte yIdx){
        Point spawnTile = searchForValidSpawnTile(xIdx, yIdx);
        if(spawnTile == null){
            throw new RuntimeException("No valid spawn tiles");
        } else {
            e.setX((int)(spawnTile.getX()*TILE_SIZE));
            e.setY((int)(spawnTile.getY()*TILE_SIZE));
        }
        return this;
    }    
    
    /**
     * Attempts to set an Entity's coordinates around the center
     * of this TileMap.
     * 
     * @param e the Entity to set coordinates for
     * @return this, for chaining purposes
     */
    public final TileMap spawnEntityCenter(AbstractEntity e){
        return spawnEntityFromPoint(e, (byte) (width / 2), (byte)(height / 2));
    }
    
    public final TileMap spawnEntityFromDir(AbstractEntity e, Direction dir){
        linearOpenSpawnTileSearch(e, dir);
        
        /*
        table  | x   | y
        left   | 0     e.y
        right  | w     e.y
        top    | e.x   0
        bottom | e.x   h
        */
        /*
        switch(dir){
            case LEFT:
                spawnEntityFromLeft(e);
                break;
            case RIGHT:
                spawnEntityFromRight(e);
                break;
            case UP:
                spawnEntityFromTop(e);
                break;
            case DOWN:
                spawnEntityFromBottom(e);
                break;
            default:
                throw new UnsupportedOperationException("Cannot spawn Entity from Direction " + dir.getName());
        }
        */
        /*
        Math doesn't work
        spawnEntityFromPoint(e, 
            (byte)((e.getX()/TILE_SIZE) + (1 - Math.abs(dir.getXMod())) * this.width),
            (byte)((e.getY()/TILE_SIZE) + (1 - Math.abs(dir.getYMod())) * this.height)
        );
        */
        return this;
    }
    
    private boolean linearOpenSpawnTileSearch(AbstractEntity e, Direction fromDir){
        boolean isValidSpawn = false;
        Direction lineDirection = Direction.rotateCounterClockWise(fromDir);
        byte initialXIdx = 0;
        byte initialYIdx = 0;
        switch(fromDir){
            case UP:
                initialXIdx = (byte)e.getXIdx();
                initialYIdx = 0;
                break;
            case DOWN:
                initialXIdx = (byte)e.getXIdx();
                initialYIdx = this.height;
                break;
            case LEFT:
                initialXIdx = 0;
                initialYIdx = (byte)e.getYIdx();
                break;
            case RIGHT:
                initialXIdx = this.width;
                initialYIdx = (byte)e.getYIdx();
                break;
            default:
                throw new UnsupportedOperationException(String.format("Cannot do linear tile search with direction \"%s\"", fromDir.getName()));
        }
        
        boolean posRadValid = false;
        boolean negRadValid = false;
        byte currXIdx = initialXIdx;
        byte currYIdx = initialYIdx;
        for(byte searchRadius = 0; !isValidSpawn && posRadValid && negRadValid; searchRadius++){
            // check positive radius direction
            currXIdx = (byte) (initialXIdx + lineDirection.getXMod() * searchRadius);
            currYIdx = (byte) (initialYIdx + lineDirection.getYMod() * searchRadius);
            posRadValid = this.isValidIdx(currXIdx, currYIdx);
            if(posRadValid){
                // check if the positive tile is open
                isValidSpawn = this.isTileOpen(currXIdx, currYIdx);
            }
            
            // check negative radius direction
            if(!isValidSpawn){
                currXIdx = (byte) (initialXIdx - lineDirection.getXMod() * searchRadius);
                currYIdx = (byte) (initialYIdx - lineDirection.getYMod() * searchRadius);
                negRadValid = this.isValidIdx(currXIdx, currYIdx);
                if(negRadValid){
                    // check if the negative tile is open
                    isValidSpawn = this.isTileOpen(currXIdx, currYIdx);
                }
            }
            
            // set entity coordinates if either spawn is valid
            if(isValidSpawn){
                e.setXIdx(currXIdx);
                e.setYIdx(currYIdx);
            }
        }
        
        
        return isValidSpawn;
    }
    
    
    /**
     * Attempts to set an Entity's coordinates around the left side
     * of this TileMap.
     * 
     * @param e the Entity to set coordinates for
     * @return this, for chaining purposes
     */
    public final TileMap spawnEntityFromLeft(AbstractEntity e){
        byte xIdx = 0;
        byte origYIdx = (byte)(e.getY()/TILE_SIZE);
        boolean isValidSpawn = false;
        for(byte searchRadius = 0; !isValidSpawn && searchRadius < this.height; searchRadius++){
            // check downward
            isValidSpawn = this.isTileOpen(xIdx, (byte) (origYIdx + searchRadius));
            if(isValidSpawn){
                e.setX(xIdx * TILE_SIZE);
                e.setY((origYIdx + searchRadius) * TILE_SIZE);
            } else {
                // check upward
                isValidSpawn = this.isTileOpen(xIdx, (byte) (origYIdx - searchRadius));
                if(isValidSpawn){
                    e.setX(xIdx * TILE_SIZE);
                    e.setY((origYIdx - searchRadius) * TILE_SIZE);
                }
            }
        }
        
        return this; //spawnEntityFromPoint(e, (byte)0, (byte)(e.getY()/TILE_SIZE));
    }
    
    /**
     * Attempts to set an Entity's coordinates around the right side
     * of this TileMap.
     * 
     * @param e the Entity to set coordinates for
     * @return this, for chaining purposes
     */
    public final TileMap spawnEntityFromRight(AbstractEntity e){
        return spawnEntityFromPoint(e, (byte)(this.width - 1), (byte)(e.getY()/TILE_SIZE));
    }
    
    /**
     * Attempts to set an Entity's coordinates around the top
     * of this TileMap.
     * 
     * @param e the Entity to set coordinates for
     * @return this, for chaining purposes
     */
    public final TileMap spawnEntityFromTop(AbstractEntity e){
        return spawnEntityFromPoint(e, (byte)(e.getX()/TILE_SIZE), (byte)0);
    }
    
    /**
     * Attempts to set an Entity's coordinates around the bottom
     * of this TileMap.
     * 
     * @param e the Entity to set coordinates for
     * @return this, for chaining purposes
     */
    public final TileMap spawnEntityFromBottom(AbstractEntity e){
        return spawnEntityFromPoint(e, (byte)(e.getX()/TILE_SIZE), (byte)(this.height - 1));
    }
    
    public final TileMap addMapBoundsReachListener(MapBoundsReachedListener listener){
        this.boundsReachedListeners.add(listener);
        return this;
    }
    private TileMap fireMapBoundsReached(Direction fromDir){
        this.boundsReachedListeners.forEach((listener)->listener.boundReached(fromDir));
        return this;
    }
}
