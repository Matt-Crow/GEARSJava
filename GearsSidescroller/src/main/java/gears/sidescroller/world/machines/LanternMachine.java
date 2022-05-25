package gears.sidescroller.world.machines;

import static gears.sidescroller.gui.level.LevelPage.FPS;
import gears.sidescroller.world.core.Illuminating;
import gears.sidescroller.world.core.MobileWorldObject;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class LanternMachine extends AbstractMachine implements Illuminating {
    private final byte lightLevel;
    private int flicker;
    
    public LanternMachine(int x, int y, boolean selfPowering, byte lightLevel) {
        super(x, y, TILE_SIZE, TILE_SIZE, selfPowering);
        this.lightLevel = lightLevel;
        flicker = 0;
    }

    @Override
    public void update() {
        ++flicker;
        if(flicker >= FPS){
            flicker = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();
        
        g.setColor(Color.GRAY);
        g.fillRect(x + w / 4, y + h / 4, w / 2, h / 2);
        
        g.setColor(new Color(
                Byte.toUnsignedInt(lightLevel),
                Byte.toUnsignedInt(lightLevel),
                0
        ));
        g.fillRect(x + w / 3, y + h / 3, w / 3, h / 3);}

    @Override
    public boolean checkForCollisions(MobileWorldObject e) {
        return getCollisionBox().isCollidingWith(e); // don't react
    }
    
    @Override
    protected void attachJsonProperties(JsonObjectBuilder builder) {
        super.attachJsonProperties(builder);
        builder.add("lightLevel", lightLevel);    
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
        return Byte.toUnsignedInt(lightLevel) / 15;
    }

    @Override
    public byte getIlluminationAtDistance(int d) {
        //                            prevents byte rollover in some cases
        return (flicker > FPS / 20 && d <= getIlluminationRange()) 
                ? (byte) (Byte.toUnsignedInt(lightLevel) - d * 15)
                : 0;
    }
}
