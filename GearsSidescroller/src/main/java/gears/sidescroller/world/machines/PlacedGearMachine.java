package gears.sidescroller.world.machines;

import gears.sidescroller.world.Interactable;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.GearItem;

/**
 *
 * @author Matt
 */
public class PlacedGearMachine extends GearMachine implements Interactable {
    private final GearItem asItem;
    public PlacedGearMachine(int x, int y, GearItem asItem) {
        super(x, y);
        this.asItem = asItem;
    }

    @Override
    public boolean interactWith(Player p) {
        boolean playerInteracts = getCollisionBox().isCollidingWith(p);
        if(playerInteracts){
            p.pickupItem(asItem);
            getArea().removeMachine(this);
            getArea().removeInteractable(this);
        }
        return playerInteracts;
     }
}
