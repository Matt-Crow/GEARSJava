package gears.sidescroller.world.entities;

/**
 * The InventoryListener is used to listen for changes to a Player's inventory,
 * firing whenever they pick up or lost an AbstractItem.
 * 
 * Use {@code player.addInventoryListener(...)} to register a listener to receive
 * updates about inventory changes.
 * 
 * @author Matt Crow
 */
public interface InventoryListener {
    /**
     * Fired whenever the inventory of a Player this is listening to changes.
     * 
     * @param whoseInventory the Player whose inventory changed. 
     */
    public void inventoryChanged(Player whoseInventory);
}
