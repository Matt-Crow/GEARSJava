package gears.sidescroller.util.dataStructures;

import java.util.function.Consumer;

/**
 * A Removable is an object which is added to a VolatileLinkedList, but may be removed at a later time.
 * To implement this interface, simply give the implementor a way of storing RemovalListeners, then implement this' methods accordingly 
 * @author Matt Crow
 */
public interface Removable {
    public void addRemovalListener(RemovalListener listener);
    public void forEachRemovalListener(Consumer<RemovalListener> doThis);
    
    /**
     * You shouldn't need to override this method, as VolatileNode handles the actual removal.
     */
    public default void remove(){
        forEachRemovalListener((l)->l.itemRemoved(this));
    }
}
