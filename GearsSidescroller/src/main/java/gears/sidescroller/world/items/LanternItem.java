package gears.sidescroller.world.items;

import gears.sidescroller.world.core.Illuminating;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.machines.PlacedLanternMachine;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import javax.json.JsonObjectBuilder;

/**
 * a LanternItem can be picked up and provides light
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class LanternItem extends AbstractItem implements Illuminating {
    private final byte lightLevel;
    
    public LanternItem(int x, int y, byte lightLevel) {
        super(x, y);
        this.lightLevel = lightLevel;
    }

    @Override
    public boolean doAction(Player whoUsedItem) {
        PlacedLanternMachine thisAsItem = new PlacedLanternMachine(
                whoUsedItem.getXIdx() * TILE_SIZE,
                whoUsedItem.getYIdx() * TILE_SIZE,
                true,
                lightLevel,
                this
        );
        whoUsedItem.getArea().addToWorld(thisAsItem);
        whoUsedItem.removeItem(this);
        return true; // this was placed
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
        g.fillRect(x + w / 3, y + h / 3, w / 3, h / 3);
    }

    @Override
    protected void attachJsonProperties(JsonObjectBuilder builder) {
        builder.add("lightLevel", lightLevel);
    }

    @Override
    public String getJsonType() {
        return "LanternItem";
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
        return 0; // only light up this' tile
    }

    @Override
    public byte getIlluminationAtDistance(int d) {
        return lightLevel;
    }
    
    @Override
    public String toString(){
        return "Lantern";
    }
}
