package gears.sidescroller.util.dataStructures;

import java.util.function.Consumer;

/**
 *
 * @author Matt
 */
public interface Removable<T> {
    public void addRemovalListener(RemovalListener<T> listener);
    public void forEachRemovalListener(Consumer<RemovalListener<T>> doThis);
    public default void remove(){
        forEachRemovalListener((l)->l.itemRemoved(this));
    }
}
