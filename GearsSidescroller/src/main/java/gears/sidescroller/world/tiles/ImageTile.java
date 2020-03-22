package gears.sidescroller.world.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * The ImageTile is a tile which renders an image,
 * trimmed to fit on the tile.
 * 
 * @author Matt Crow
 */
public class ImageTile extends AbstractTile{
    private final BufferedImage image;
    
    public ImageTile(int xIndex, int yIndex, boolean tangible, BufferedImage img) {
        super(xIndex, yIndex, tangible);
        
        //create a new, transparent image to guarantee the image is the correct size
        //and copy the image over it
        image = new BufferedImage(TILE_SIZE, TILE_SIZE, img.getType());
        int transparent = new Color(0, 0, 0, 0).getRGB();
        int pixelColor = 0;
        for(int i = 0; i < TILE_SIZE; i++){
            for(int j = 0; j < TILE_SIZE; j++){
                if(img.getWidth() <= i || img.getHeight() <= j){
                    // out of bounds, so just transparent
                    pixelColor = transparent;
                } else {
                    pixelColor = img.getRGB(i, j);
                }
                image.setRGB(i, j, pixelColor);
            }
        }
    }

    @Override
    public AbstractTile draw(Graphics g) {
        g.drawImage(image, getXCoord(), getYCoord(), null);
        return this;
    }

    @Override
    public AbstractTile copyTo(int xIndex, int yIndex) {
        return new ImageTile(xIndex, yIndex, getIsTangible(), image);
    }
}
