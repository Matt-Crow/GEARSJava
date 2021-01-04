package gears.sidescroller.util;

import java.awt.Color;

/**
 * @author Matt
 */
public class UnrotatedGearSprite extends Sprite {

    public UnrotatedGearSprite(Color color, int sizeInPixels) {
        super(5, 5, sizeInPixels, sizeInPixels);
        this.setKeyToVal(0, new Color(0, 0, 0, 0)); // clear
        this.setKeyToVal(1, color);
        // todo: add sprite loader to load from files
        this.setContents(new int[][]{
            {1, 0, 1, 0, 1},
            {0, 1, 1, 1, 0},
            {1, 1, 0, 1, 1},
            {0, 1, 1, 1, 0},
            {1, 0, 1, 0, 1}
        });
    }

}
