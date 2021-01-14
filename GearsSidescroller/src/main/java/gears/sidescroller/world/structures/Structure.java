package gears.sidescroller.world.structures;

import gears.sidescroller.util.FlyweightMatrix;
import gears.sidescroller.world.tiles.AbstractTileTemplate;

/**
 * A Structure is currently simply a Matrix which gets inserted
 * into a TileMap. Later versions may see these become more akin
 * to miniature Areas, with their own machines and items.
 * 
 * @author Matt Crow
 */
public class Structure extends FlyweightMatrix<AbstractTileTemplate> {
    /**
     * Creates a new Structure of the given size.
     * @param width the width of the Structure, measured in tiles
     * @param height the height of the Structure, measured in tiles
     */
    public Structure(int width, int height) {
        super(width, height);
    }
}
