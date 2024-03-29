package gears.sidescroller.world.tiles;

import java.awt.Color;
import java.awt.Graphics;
import javax.json.JsonObjectBuilder;

/**
 * The BasicColorTile is my classic lazy tile design:
 * a square with an outline.
 * 
 * @author Matt Crow
 */
public class BasicColorTile extends AbstractTileTemplate{
    private final Color outlineColor;
    private final Color bodyColor;
    
    private static final int OUTLINE_OFFSET = TILE_SIZE / 10;
    
    public BasicColorTile(boolean tangible, Color outline, Color body) {
        super(tangible);
        outlineColor = outline;
        bodyColor = body;
    }
    
    @Override
    public String toString(){
        return String.format("BasicColorTile %s %s %s", 
                colorString(outlineColor),
                colorString(bodyColor),
                (getIsTangible()) ? "tangible" : "intangible"
        );
    }
    
    private static String colorString(Color color){
        return String.format("Color(%d, %d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public AbstractTileTemplate drawAt(Graphics g, int x, int y) {
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
    public void attachJsonProperties(JsonObjectBuilder builder) {
        builder.add("outlineColor", outlineColor.getRGB());
        builder.add("bodyColor", bodyColor.getRGB());
    }
    
    @Override
    public String getJsonType(){
        return "BasicColorTile";
    }
}
