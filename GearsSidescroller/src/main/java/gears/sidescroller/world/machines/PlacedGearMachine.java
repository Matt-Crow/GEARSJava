package gears.sidescroller.world.machines;

import gears.sidescroller.world.core.Interactable;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.GearItem;

/**
 * A PlacedGearMachine is simply a GearMachine a Player has placed via a GearItem.
 * I don't particularly like how I'm using an entirely different class for this.
 * The Player can pick this back up by interacting with it.
 * 
 * @author Matt Crow
 */
public class PlacedGearMachine extends GearMachine implements Interactable {
    private final GearItem asItem;
    
    /**
     * 
     * @param x the x-coordinate of this machine, measured in pixel-space
     * @param y the y-coordinate of this machine, measured in pixel-space
     * @param asItem the GearItem a Player used to create this PlacedGearMachine
     */
    public PlacedGearMachine(int x, int y, GearItem asItem) {
        super(x, y);
        this.asItem = asItem;
    }

    /**
     * Checks to see if the given Player should
     * interact with this, and lets them pick this
     * up as a GearItem if so.
     * 
     * @param p the Player to check.
     * 
     * @return whether or not the given Player interacts with this.
     */
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
