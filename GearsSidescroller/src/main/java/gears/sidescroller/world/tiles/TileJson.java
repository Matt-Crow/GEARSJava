package gears.sidescroller.world.tiles;

import gears.sidescroller.loader.JsonResourceType;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import javax.imageio.ImageIO;
import javax.json.*;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class TileJson implements JsonResourceType<AbstractTileTemplate> {

    @Override
    public AbstractTileTemplate deserialize(JsonObject json) {
        AbstractTileTemplate tile = null;
        String type = json.getString("type");
        switch(type){
            case "BasicColorTile": {
                tile = new BasicColorTile(
                        json.getBoolean("isTangible"),
                        new Color(json.getInt("outlineColor"), true), // has alpha
                        new Color(json.getInt("bodyColor"), true) // has alpha
                );
                break;
            }
            case "ImageTile": {
                tile = deserializeImageTile(json);
                break;
            }
            default: {
                throw new JsonException("Invalid tile JSON type: " + type);
            }
        }
        return tile;
    }

    private AbstractTileTemplate deserializeImageTile(JsonObject json) {
        String base64Image = json.getString("img");
        Decoder base64Decoder = Base64.getDecoder();
        ByteArrayInputStream bin = new ByteArrayInputStream(base64Decoder.decode(base64Image));
        BufferedImage img = null;
        try {
            img = ImageIO.read(bin);
        } catch (IOException ex) {
            throw new JsonException("failed to decode ImageTile", ex);
        }
        return new ImageTile(json.getBoolean("isTangible"), img);
    }
}
