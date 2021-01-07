package gears.sidescroller.world.items;

import gears.sidescroller.world.entities.Player;
import gears.sidescroller.util.UnrotatedGearSprite;
import gears.sidescroller.world.core.Interactable;
import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.machines.GearMachine;
import gears.sidescroller.world.machines.PlacedGearMachine;
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
        PlacedGearMachine thisAsMachine = new PlacedGearMachine(whoUsedItem.getXIdx() * TILE_SIZE, whoUsedItem.getYIdx() * TILE_SIZE, this);
        inArea.addMachine(thisAsMachine);
        inArea.addInteractable(thisAsMachine);
        whoUsedItem.removeItem(this);
        return true; // gear was placed
    }

}
