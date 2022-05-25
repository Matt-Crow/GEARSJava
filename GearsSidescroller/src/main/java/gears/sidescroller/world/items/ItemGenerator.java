package gears.sidescroller.world.items;

import gears.sidescroller.world.core.LightLevel;
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
        AbstractItem rando;
        Random rng = new Random();
        if(rng.nextBoolean()){
            rando = randomGear(xCoord, yCoord, rng);
        } else {
            rando = randomLantern(xCoord, yCoord, rng);
        }
        return rando;
    }
    
    private GearItem randomGear(int xCoord, int yCoord, Random rng){
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
    
    private LanternItem randomLantern(int xCoord, int yCoord, Random rng){
        return new LanternItem(xCoord, yCoord, new LightLevel(128 + rng.nextInt(128)));
    }
}
