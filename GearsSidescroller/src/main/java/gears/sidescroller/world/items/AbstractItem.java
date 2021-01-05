package gears.sidescroller.world.items;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.util.dataStructures.Removable;
import gears.sidescroller.util.dataStructures.RemovalListener;
import gears.sidescroller.world.ObjectInWorld;
import gears.sidescroller.world.areas.Area;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 *
 * @author Matt
 */
public abstract class AbstractItem extends ObjectInWorld implements Removable {
    private final LinkedList<RemovalListener> removalListeners;
    
    public AbstractItem(int x, int y) {
        super(x, y, TILE_SIZE, TILE_SIZE);
        removalListeners = new LinkedList<>();
    }
    
    public final boolean checkForCollisions(AbstractEntity e){
        boolean collided = this.getCollisionBox().isCollidingWith(e);
        if(collided && e instanceof Player){
            this.remove();
            ((Player)e).pickupItem(this);
        }
        return collided;
    }

    @Override
    public void addRemovalListener(RemovalListener listener) {
        removalListeners.add(listener);
    }

    @Override
    public void forEachRemovalListener(Consumer<RemovalListener> doThis) {
        removalListeners.forEach(doThis);
    }
    
    public abstract boolean doAction(Player whoUsedItem, Area inArea);
    
    public abstract void draw(Graphics g);
}
