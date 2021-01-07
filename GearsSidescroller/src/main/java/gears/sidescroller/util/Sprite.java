package gears.sidescroller.util;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public class Sprite extends FlyweightMatrix<Color> {
    private final int spriteWidth;
    private final int spriteHeight;
    public Sprite(int widthInCells, int heightInCells, int widthInPixels, int heightInPixels) {
        super(widthInCells, heightInCells);
        this.spriteWidth = widthInPixels;
        this.spriteHeight = heightInPixels;
    }
    
    public final void draw(Graphics g, int atX, int atY){
        int cellWidth = spriteWidth / this.getWidthInCells();
        int cellHeight = spriteHeight / this.getHeightInCells();
        this.forEachCell((color, xIdx, yIdx)->{
            g.setColor(color);
            g.fillRect(atX + xIdx * cellWidth, atY + yIdx * cellHeight, cellWidth, cellHeight);
        });
    }
}
