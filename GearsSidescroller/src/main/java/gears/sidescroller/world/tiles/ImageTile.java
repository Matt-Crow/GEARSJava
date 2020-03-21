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
    
    public ImageTile(int xIndex, int yIndex, BufferedImage img) {
        super(xIndex, yIndex);
        
        //create a new, transparent image to guarantee the image is the correct size
        image = new BufferedImage(TILE_SIZE, TILE_SIZE, img.getType());
        int transparent = new Color(0, 0, 0, 0).getRGB();
        for(int i = 0; i < TILE_SIZE; i++){
            for(int j = 0; j < TILE_SIZE; j++){
                image.setRGB(i, j, transparent);
            }
        }
        
        //copy the img argument over the new image
        BufferedImage subImage = img.getSubimage(0, 0, (img.getWidth() < TILE_SIZE) ? img.getWidth() : TILE_SIZE, (img.getHeight() < TILE_SIZE) ? img.getHeight() : TILE_SIZE);
        //https://stackoverflow.com/questions/10408994/how-to-add-one-image-onto-another-in-java
        image.getGraphics().drawImage(subImage, 0, 0, null);
    }

    @Override
    public AbstractTile draw(Graphics g) {
        g.drawImage(image, getXCoord(), getYCoord(), null);
        return this;
    }

    @Override
    public AbstractTile copyTo(int xIndex, int yIndex) {
        return new ImageTile(xIndex, yIndex, image);
    }
}
