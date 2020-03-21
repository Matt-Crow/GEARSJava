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
    
    public BasicColorTile(int xIndex, int yIndex, Color outline, Color body) {
        super(xIndex, yIndex);
        outlineColor = outline;
        bodyColor = body;
    }

    @Override
    public AbstractTile draw(Graphics g) {
        //outline
        g.setColor(outlineColor);
        g.fillRect(getXCoord(), getYCoord(), TILE_SIZE, TILE_SIZE);
        
        //body
        g.setColor(bodyColor);
        g.fillRect(getXCoord() + OUTLINE_OFFSET, getYCoord() + OUTLINE_OFFSET, TILE_SIZE - OUTLINE_OFFSET * 2, TILE_SIZE - OUTLINE_OFFSET * 2);
        
        return this;
    }

    @Override
    public AbstractTile copyTo(int xIndex, int yIndex) {
        return new BasicColorTile(xIndex, yIndex, outlineColor, bodyColor);
    }
}
