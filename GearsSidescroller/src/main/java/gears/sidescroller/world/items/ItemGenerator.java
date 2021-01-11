package gears.sidescroller.world.items;

import java.awt.Color;
import java.util.Random;

/**
 * The ItemGenerator is used to produce random items for randomly generated Areas.
 * @author Matt Crow
 */
public class ItemGenerator {
    /**
     * Creates a new instance of a random subclass of AbstractItem at the
     * given coordinates <b>in pixel-space</b>.
     * 
     * @param xCoord the x-coordinate of the new item
     * @param yCoord the y-coordinate of the new item
     * 
     * @return the newly created item 
     */
    public final AbstractItem generateRandomAt(int xCoord, int yCoord){
        // todo: add ability to randomly choose item type once there are multiple different item types
        Random rng = new Random();
        return new GearItem(
            xCoord,
            yCoord,
            new Color(
                rng.nextInt(256),
                rng.nextInt(256),
                rng.nextInt(256)
            )
        );
    }
}
