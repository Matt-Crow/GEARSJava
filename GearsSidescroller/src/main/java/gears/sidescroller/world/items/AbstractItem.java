package gears.sidescroller.world.items;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.ObjectInWorld;
import gears.sidescroller.world.areas.Area;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public abstract class AbstractItem extends ObjectInWorld {
    
    public AbstractItem(int x, int y) {
        super(x, y, TILE_SIZE, TILE_SIZE);
    }
    
    public final boolean checkForCollisions(AbstractEntity e){
        boolean collided = this.getCollisionBox().isCollidingWith(e);
        if(collided && e instanceof Player){
            getArea().removeItem(this);
            ((Player)e).pickupItem(this);
        }
        return collided;
    }

    public abstract boolean doAction(Player whoUsedItem, Area inArea);
    
    public abstract void draw(Graphics g);
}
