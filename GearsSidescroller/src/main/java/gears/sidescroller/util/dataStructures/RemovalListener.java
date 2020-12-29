package gears.sidescroller.util.dataStructures;

/**
 * This interface is currently only used by VolatileNode to listen for an item being removed.
 * @author Matt Crow
 */
public interface RemovalListener {
    public void itemRemoved(Removable item);
}
