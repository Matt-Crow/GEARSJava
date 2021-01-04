package gears.sidescroller.world.items;

import gears.sidescroller.util.UnrotatedGearSprite;
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

}
