package gears.sidescroller.world.items;

import gears.sidescroller.world.core.MobileWorldObject;
import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.core.ObjectInWorld;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Graphics;

/**
 * An AbstractItem represents any pick-up-able item in the 
 * game world which a Player can obtain. An AbstractItem is
 * collected by the Player upon colliding, which removes the
 * AbstractItem from its Area. A Player may then use the AbstractItem
 * through their inventory.
 * 
 * @author Matt Crow
 */
public abstract class AbstractItem extends ObjectInWorld {
    /**
     * Creates a new AbstarctItem at the given coordinates,
     * <b>measured in pixel-space</b>
     * @param x the x-coordinate of the item
     * @param y the y-coordinate of the item
     */
    public AbstractItem(int x, int y) {
        super(x, y, TILE_SIZE, TILE_SIZE);
    }
    
    /**
     * Checks to see if a Player collects this.
     * 
     * @param e the MobileWorldObject to check for collisions with.
     * @return whether or not the given entity collided with this.
     */
    public final boolean checkForCollisions(MobileWorldObject e){
        boolean collided = this.getCollisionBox().isCollidingWith(e);
        if(collided && e instanceof Player){
            getArea().removeFromWorld(this);
            ((Player)e).pickupItem(this);
        }
        return collided;
    }

    /**
     * This method is invoked whenever the user selects this AbstractItem
     * from their inventory. Subclasses should override this method to implement
     * behavior which should happen when the Player uses this AbstractItem.
     * Note that an AbstractItem may be used in an Area different from its own,
     * so use {@code whoUsedItem.getArea()} to reference the Area it is used in,
     * not {@code this.getArea()}.
     * 
     * Note that the AbstractItem is responsible for removing itself from the
     * Player's inventory, if appropriate.
     * 
     * @param whoUsedItem the Player who used this AbstractItem
     * @return whether or not this could perform its action
     */
    public abstract boolean doAction(Player whoUsedItem);
    
    /**
     * Subclasses should override this method
     * to render this AbstractItem on the given
     * Graphics context.
     * 
     * @param g the Graphics context to render on. 
     */
    public abstract void draw(Graphics g);
}
