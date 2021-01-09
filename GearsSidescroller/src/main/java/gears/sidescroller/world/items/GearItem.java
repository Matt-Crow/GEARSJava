package gears.sidescroller.world.items;

import gears.sidescroller.world.entities.Player;
import gears.sidescroller.util.UnrotatedGearSprite;
import gears.sidescroller.world.machines.PlacedGearMachine;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The GearItem is an item which the Player can
 * pick up, then place in their Area as a PlacedGearMachine.
 * 
 * @author Matt Crow
 */
public class GearItem extends AbstractItem {
    private final UnrotatedGearSprite sprite;
    
    /**
     * Creates a new GearItem at the given coordinates <b>in pixel-space</b>
     * @param x the x-coordinate of the item
     * @param y the y-coordinate of the item
     * @param c the color of this item as it appears in the Area.
     */
    public GearItem(int x, int y, Color c) {
        super(x, y);
        sprite = new UnrotatedGearSprite(c, TILE_SIZE / 2);
    }

    @Override
    public void draw(Graphics g) {
        sprite.draw(g, getX() + TILE_SIZE / 4, getY() + TILE_SIZE / 4);
    }
    
    /**
     * Places this item as a PlacedGearMachine at the player's coordinates.
     * 
     * @param whoUsedItem the Player who used this item.
     * @return whether or not the gear was placed.
     */
    @Override
    public boolean doAction(Player whoUsedItem) {
        PlacedGearMachine thisAsMachine = new PlacedGearMachine(whoUsedItem.getXIdx() * TILE_SIZE, whoUsedItem.getYIdx() * TILE_SIZE, this);
        whoUsedItem.getArea().addMachine(thisAsMachine);
        whoUsedItem.getArea().addInteractable(thisAsMachine);
        whoUsedItem.removeItem(this);
        return true; // gear was placed
    }

}
