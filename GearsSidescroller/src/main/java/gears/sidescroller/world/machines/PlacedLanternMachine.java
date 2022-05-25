package gears.sidescroller.world.machines;

import gears.sidescroller.world.core.Interactable;
import gears.sidescroller.world.core.LightLevel;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.LanternItem;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class PlacedLanternMachine extends LanternMachine implements Interactable {
    private final LanternItem asItem;
    
    public PlacedLanternMachine(int x, int y, boolean selfPowering, LightLevel lightLevel, LanternItem asItem) {
        super(x, y, selfPowering, lightLevel);
        this.asItem = asItem;
    }

    @Override
    public boolean interactWith(Player p) {
        boolean playerInteracts = getCollisionBox().isCollidingWith(p);
        if(playerInteracts){
            p.pickupItem(asItem);
            getArea().removeFromWorld(this);
        }
        return playerInteracts;
    }
    
}
