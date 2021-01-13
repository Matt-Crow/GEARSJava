package gears.sidescroller.world.tileMaps;

/**
 * The MapBoundsReachedListener is used to listen for when a MobileWorldObject
 * passes the bounds of a TileMap. This is currently only used by Level for Area
 * transitions, so I may change this in the future.
 * 
 * Use {@code area.getTileMap().addMapBoundsReachListener(listener)} to register
 * your listeners.
 * 
 * @author Matt Crow
 */

public interface MapBoundsReachedListener {
    /**
     * Fired by the TileMap this is listening to whenever
     * a MobileWorldObject passes the bounds of a TileMap.
     * 
     * @param event an event detailing who went out of bounds, and how
     */
    public void boundReached(OutOfBoundsEvent event);
}
