package gears.sidescroller.world.tiles;

import gears.sidescroller.loader.LevelLoadingException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;
import javax.imageio.ImageIO;
import javax.json.JsonObjectBuilder;

/**
 * The ImageTile is a tile which renders an image,
 * trimmed to fit on the tile.
 * 
 * @author Matt Crow
 */
public class ImageTile extends AbstractTileTemplate{
    private final BufferedImage image;
    
    public ImageTile(boolean tangible, BufferedImage img) {
        super(tangible);
        
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
    public AbstractTileTemplate drawAt(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
        return this;
    }

    @Override
    public void attachJsonProperties(JsonObjectBuilder builder) { // untested
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", bout);
        } catch (IOException ex) {
            throw new LevelLoadingException(ex);
        }
        Encoder base64Encoder = Base64.getEncoder();
        builder.add("img", base64Encoder.encodeToString(bout.toByteArray()));
    }
    
    @Override
    public String getJsonType(){
        return "ImageTile";
    }
}
