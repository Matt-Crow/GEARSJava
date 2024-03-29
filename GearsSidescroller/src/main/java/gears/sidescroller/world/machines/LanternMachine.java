package gears.sidescroller.world.machines;

import gears.sidescroller.world.core.Illuminating;
import gears.sidescroller.world.core.LightLevel;
import gears.sidescroller.world.core.MobileWorldObject;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Matt Crow 
 */
public class LanternMachine extends AbstractMachine implements Illuminating {
    private final LightLevel lightLevel;
    private boolean isFlickering;
    private int flicker;
    
    public LanternMachine(int x, int y, boolean selfPowering, LightLevel lightLevel) {
        super(x, y, TILE_SIZE, TILE_SIZE, selfPowering);
        this.lightLevel = lightLevel;
        flicker = 0;
        isFlickering = false;
    }

    @Override
    public void machineUpdate(int fps) {
        ++flicker;
        if(flicker >= fps){
            flicker = 0;
        }
        
        isFlickering = flicker > fps / 20; // flicker 5% of the time
    }

    @Override
    public void draw(Graphics g) {
        int x = getXAsInt();
        int y = getYAsInt();
        int w = getWidth();
        int h = getHeight();
        
        g.setColor(Color.GRAY);
        g.fillRect(x + w / 4, y + h / 4, w / 2, h / 2);
        
        g.setColor(new Color(
                lightLevel.getIntValue(),
                lightLevel.getIntValue(),
                0
        ));
        g.fillRect(x + w / 3, y + h / 3, w / 3, h / 3);}

    @Override
    public void collideWith(MobileWorldObject e) {
        // don't react
    }
    
    @Override
    protected void attachJsonProperties(JsonObjectBuilder builder) {
        super.attachJsonProperties(builder);
        builder.add("lightLevel", lightLevel.getIntValue());    
    }
    
    @Override
    public String getJsonType() {
        return "LanternMachine";
    }

    @Override
    public int getLightCenterXIdx() {
        return getCenterXIdx();
    }

    @Override
    public int getLightCenterYIdx() {
        return getCenterYIdx();
    }

    @Override
    public int getIlluminationRange() {
        return lightLevel.getIntValue() / 15;
    }

    @Override
    public LightLevel getIlluminationAtDistance(int d) {
        return (isFlickering)
                ? LightLevel.capped(lightLevel.getIntValue() - d * 15)
                : LightLevel.NO_LIGHT;
    }
}
