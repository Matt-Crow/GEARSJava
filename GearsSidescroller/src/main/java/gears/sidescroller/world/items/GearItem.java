package gears.sidescroller.world.items;

import gears.sidescroller.entities.Player;
import gears.sidescroller.util.UnrotatedGearSprite;
import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.machines.GearMachine;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public class GearItem extends AbstractItem {
    private final UnrotatedGearSprite sprite;
    
    public GearItem(int x, int y, Color c) {
        super(x, y);
        sprite = new UnrotatedGearSprite(c, TILE_SIZE / 2);
    }

    @Override
    public void draw(Graphics g) {
        sprite.draw(g, getX() + TILE_SIZE / 4, getY() + TILE_SIZE / 4);
    }

    @Override
    public boolean doAction(Player whoUsedItem, Area inArea) {
        GearMachine thisAsMachine = new GearMachine(whoUsedItem.getXIdx() * TILE_SIZE, whoUsedItem.getYIdx() * TILE_SIZE);
        inArea.addMachine(thisAsMachine);
        this.remove(); // ... from whoUsedItem's inventory (currently does nothing, as this is stored in an ArrayList)
        return true; // gear was placed
    }

}
