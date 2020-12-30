package gears.sidescroller.world.tiles;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The BasicColorTile is my classic lazy tile design:
 * a square with an outline.
 * 
 * @author Matt Crow
 */
public class BasicColorTile extends AbstractTile{
    private final Color outlineColor;
    private final Color bodyColor;
    
    private static final int OUTLINE_OFFSET = TILE_SIZE / 10;
    
    public BasicColorTile(int xIndex, int yIndex, boolean tangible, Color outline, Color body) {
        super(xIndex, yIndex, tangible);
        outlineColor = outline;
        bodyColor = body;
    }

    @Override
    public AbstractTile drawAt(Graphics g, int x, int y) {
        //outline
        g.setColor(outlineColor);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        
        //body
        if(this.getIsTangible()){
            g.setColor(bodyColor);
            g.fillRect(x + OUTLINE_OFFSET, y + OUTLINE_OFFSET, TILE_SIZE - OUTLINE_OFFSET * 2, TILE_SIZE - OUTLINE_OFFSET * 2);
        }
        return this;
    }

    @Override
    public AbstractTile copyTo(int xIndex, int yIndex) {
        return new BasicColorTile(xIndex, yIndex, getIsTangible(), outlineColor, bodyColor);
    }
}
