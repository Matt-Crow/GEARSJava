package gears.sidescroller.world.tileMaps;

import gears.sidescroller.loader.JsonSerializable;
import gears.sidescroller.world.core.CollisionBox;
import gears.sidescroller.util.Direction;
import gears.sidescroller.util.FlyweightMatrix;
import gears.sidescroller.world.core.MobileWorldObject;
import gears.sidescroller.world.tiles.AbstractTileTemplate;
import java.awt.Graphics;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Point;
import java.util.LinkedList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * A TileMap is used to store tiles in a two-dimensional matrix.
 * This allows for O(1) lookup for collisions, as well as fast
 * reading and writing.
 * 
 * This class uses the Flyweight design
 * pattern to drastically minimize the amount of memory used
 * to store and retrieve tile map information.
 * 
 * @author Matt Crow
 */
public class TileMap extends FlyweightMatrix<AbstractTileTemplate> implements JsonSerializable {
    private final LinkedList<MapBoundsReachedListener> boundsReachedListeners;
    
    /**
     * Creates a new TileMap of the given size.
     * 
     * @param w the width of this TileMap, measured in tiles
     * @param h the height of this TileMap, measured in tiles
     */
    public TileMap(int w, int h){
        super(w, h);
        boundsReachedListeners = new LinkedList<>();
    }
    
    /**
     * Adds a MapBoundsReachedListener to the TileMap. The listener will be notified
     * whenever a MobileWorldObject passes the bounds of this TileMap.
     * 
     * @param listener the listener to register.
     * 
     * @return this, for chaining purposes
     */
    public final TileMap addMapBoundsReachListener(MapBoundsReachedListener listener){
        this.boundsReachedListeners.add(listener);
        return this;
    }
    
    /**
     * Notifies each attached MapBoundsReachedListener that a MobileWorldObject
     * has passed this TileMap's bounds.
     * 
     * @param event an event detailing who went out of bounds, and how.
     * 
     * @return this, for chaining purposes.
     */
    private TileMap fireMapBoundsReached(OutOfBoundsEvent event){
        this.boundsReachedListeners.forEach((listener)->listener.boundReached(event));
        return this;
    }
    
    public final int getWidthInPixels(){
        return getWidthInCells() * TILE_SIZE;
    }
    
    public final int getHeightInPixels(){
        return getHeightInCells() * TILE_SIZE;
    }
    
    /**
     * Adds a tile to this map's tile set.
     * When constructing tiles from this'
     * tile map, each cell in the map with
     * the given key will represent a copy
     * of the given tile.
     * 
     * @param key the integer key to map to the given tile
     * @param tile the tile the given integer key represents
     * @return this, for chaining purposes
     */
    public TileMap addToTileSet(int key, AbstractTileTemplate tile){
        setKeyToVal(key, tile);
        return this;
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
        set(xIndex, yIndex, key);
        return this;
    }
    
    /**
     * Checks to see if the tile at the given indeces is open for MobileWorldObjects
     * to occupy.
     * 
     * @param xIdx the x-coordinate of the tile to check, measured in index-space
     * @param yIdx the y-coordinate of the tile to check, measured in index-space
     * @return whether or not a valid open tile exists at the given coordinates
     */
    public final boolean isTileOpen(int xIdx, int yIdx){
        return isValidIdx(xIdx, yIdx) && !getValueAt(xIdx, yIdx).getIsTangible();
    }
    
    /**
     * Sets a MobileWorldObject's coordinates to those of an open tile around the given tile.
     * <b>Remember: this doesn't set the MobileWorldObject's Area, so don't forget to call {@code area.addToWorld()}!</b>
     * 
     * @param e the MobileWorldObject to set coordinates for
     * @param xIdx the tile x index to check around
     * @param yIdx the tile y index to check around
     * @return this, for chaining purposes
     */
    private TileMap spawnEntityFromPoint(MobileWorldObject e, int xIdx, int yIdx){
        Point spawnTile = new OpenTileSearch().searchForOpenTileAround(this, xIdx, yIdx);
        if(spawnTile == null){
            throw new RuntimeException("No valid spawn tiles");
        } else {
            e.setX((int)(spawnTile.getX()*TILE_SIZE));
            e.setY((int)(spawnTile.getY()*TILE_SIZE));
        }
        return this;
    }    
    
    /**
     * Attempts to set an MobileWorldObject's coordinates around the center
     * of this TileMap.
     * 
     * @param e the MobileWorldObject to set coordinates for
     * @return this, for chaining purposes
     */
    public final TileMap spawnEntityCenter(MobileWorldObject e){
        return spawnEntityFromPoint(e, (int) (getWidthInCells() / 2), (int)(getHeightInCells() / 2));
    }
    
    /**
     * Attempts to set a MobileWorldObject's coordinates along the given side
     * of this TileMap.
     * 
     * @param e the MobileWorldObject to attempt to set coordinates for
     * @param fromDir the side of this to set the MobileWorldObject's coordinates
     * along.
     * 
     * @return whether or not an open tile exists along the given side. 
     */
    public final boolean spawnEntityFromDir(MobileWorldObject e, Direction fromDir){
        int initialXIdx = 0;
        int initialYIdx = 0;
        // I'd love it if I could move this to Direction, but how?
        switch(fromDir){
            case UP:
                initialXIdx = e.getXIdx();
                initialYIdx = 0;
                break;
            case DOWN:
                initialXIdx = e.getXIdx();
                initialYIdx = this.getHeightInCells() - 1;
                break;
            case LEFT:
                initialXIdx = 0;
                initialYIdx = e.getYIdx();
                break;
            case RIGHT:
                initialXIdx = this.getWidthInCells() - 1;
                initialYIdx = e.getYIdx();
                break;
            default:
                throw new UnsupportedOperationException(String.format("Cannot do linear tile search with direction \"%s\"", fromDir.getName()));
        }
        
        Point spawnHere = new OpenTileSearch().searchForOpenTileAlongSide(this, initialXIdx, initialYIdx, fromDir);
        
        if(spawnHere != null){
            e.setXIdx(spawnHere.x);
            e.setYIdx(spawnHere.y);
        }
        
        return spawnHere != null;
    }
    
    /**
     * Checks to see if the given MobileWorldObject is outside the bounds
     * of this TileMap. If so, returns how it is outside the TileMap.
     * 
     * @param e the MobileWorldObject to check if it is out of bounds.
     * 
     * @return how the given MobileWorldObject is out of bounds, <b>or null if 
     * it is within the TileMap bounds</b>. 
     */
    private OutOfBoundsEvent checkIfOutsideBounds(MobileWorldObject e){
        OutOfBoundsEvent lrEvent = null; // left-right
        OutOfBoundsEvent udEvent = null; // up-down
        if(e.getXAsInt() < 0){
            e.setX(0);
            lrEvent = new OutOfBoundsEvent(this, e, Direction.LEFT);
        } else if(e.getXAsInt() > getWidthInPixels() - e.getWidth()){
            e.setX(getWidthInPixels() - e.getWidth());
            lrEvent = new OutOfBoundsEvent(this, e, Direction.RIGHT);
        }
        
        if(e.getYAsInt() < 0){
            e.setY(0);
            udEvent = new OutOfBoundsEvent(this, e, Direction.UP);
        } else if(e.getYAsInt() > getHeightInPixels() - e.getHeight()){
            e.setY(getHeightInPixels() - e.getHeight());
            udEvent = new OutOfBoundsEvent(this, e, Direction.DOWN);
        }
        
        OutOfBoundsEvent event = null;
        if(lrEvent == null){
            event = udEvent;
        } else if(udEvent == null){
            event = lrEvent;
        } else {
            // neither is null, so favor the direction more out of bounds
            double dx = Math.abs(e.getXAsInt() - getWidthInPixels() / 2); // distance from center
            double dy = Math.abs(e.getYAsInt() - getHeightInPixels() / 2);
            event = (dx < dy) ? udEvent : lrEvent;
        }
        
        return event;
    }
    
    /**
     * Handles collisions between the given MobileWorldObject and the tile at the given index.
     * 
     * @param e the MobileWorldObject to handle collisions with
     * @param tileXIdx the x-coordinate of the tile to handle collisions with, measured in index-space
     * @param tileYIdx the y-coordinate of the tile to handle collisions with, measured in index-space
     * 
     * @return whether or not collisions were handles 
     */
    private boolean handleCollisions(MobileWorldObject e, int tileXIdx, int tileYIdx){
        boolean collided = false;
        if(isValidIdx(tileXIdx, tileYIdx) && getValueAt(tileXIdx, tileYIdx).getIsTangible()){
            collided = true;
            new CollisionBox(
                tileXIdx * TILE_SIZE,
                tileYIdx * TILE_SIZE,
                TILE_SIZE,
                TILE_SIZE
            ).shoveOut(e);
        }
        return collided;
    }
    
    /**
     * Checks to see if the given MobileWorldObject is inside a tile,
     * and shoves them out if that tile is tangible.
     * 
     * This collision checking is O(1) thanks to integer division,
     * which is pretty cool.
     * 
     * @param e the MobileWorldObject to check for collisions with
     * 
     * @return whether or not a collision was detected
     */
    public final boolean checkForCollisions(MobileWorldObject e){
        OutOfBoundsEvent boundChecking = checkIfOutsideBounds(e);
        
        /*
        This gets the tile index for the UPPER LEFT corner of e
        becuase of how integer division rounds down.
        
        Therefore, need to check for collisions with four tiles
        in a 2x2 tile square to catch all the tiles they could
        possibly be colliding with.
        */
        
        int yIdx = (int)(e.getYAsInt() / AbstractTileTemplate.TILE_SIZE);
        int xIdx = (int)(e.getXAsInt() / AbstractTileTemplate.TILE_SIZE);
        
        boolean collUpperLeft  = handleCollisions(e, xIdx  , yIdx  );
        boolean collUpperRight = handleCollisions(e, xIdx+1, yIdx  );
        boolean collLowerLeft  = handleCollisions(e, xIdx  , yIdx+1);
        boolean collLowerRight = handleCollisions(e, xIdx+1, yIdx+1);
        
        // AFTER handling collisions with this map, notify listeners if the entity left the area
        if(boundChecking != null){
            this.fireMapBoundsReached(boundChecking);
        }        
        
        // do this to avoid short-circuit evaluation of the handleCollisions method
        return collUpperLeft
            || collUpperRight
            || collLowerLeft
            || collLowerRight;
    }
    
    /**
     * Renders each tile in this TileMap
     * on the given graphics.
     * 
     * @param g the Graphics to render tiles on.
     * @return this, for chaining purposes
     */
    public final TileMap draw(Graphics g){
        forEachValueInCell((tile, xIdx, yIdx)->{
            tile.drawAt(g, xIdx * TILE_SIZE, yIdx * TILE_SIZE);
        });
        return this;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TODO: better TileMap::toString\n");
        sb.append("TILE MAP\n");
        forEachKeyToValue((i, tile)->{
            sb.append(String.format("%d : %s\n", i, tile.toString()));
        });
        sb.append(getAsCsv());
        return sb.toString();
    }

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        
        JsonArrayBuilder keyBuilder = Json.createArrayBuilder();
        copyKeyToValue().forEach((k, v)->{
            JsonObjectBuilder tempBuilder = Json.createObjectBuilder();
            tempBuilder.add("key", k);
            tempBuilder.add("value", v.toJson());
            keyBuilder.add(tempBuilder.build());
        });
        builder.add("keys", keyBuilder.build());
        
        JsonArrayBuilder mapBuilder = Json.createArrayBuilder();
        JsonArrayBuilder rowBuilder;
        Object[][] objMap = mapToArray((key)->key);
        for(Object[] row : objMap){
            rowBuilder = Json.createArrayBuilder();
            for(Object item : row){
                if(!(item instanceof Integer)){
                    throw new UnsupportedOperationException();
                }
                rowBuilder.add((Integer)item);
            }
            mapBuilder.add(rowBuilder.build());
        }
        builder.add("map", mapBuilder.build());
        
        return builder.build();
    }
}
