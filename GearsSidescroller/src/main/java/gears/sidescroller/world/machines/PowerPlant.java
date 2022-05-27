package gears.sidescroller.world.machines;

import gears.sidescroller.world.core.Illuminating;
import gears.sidescroller.world.core.LightLevel;
import gears.sidescroller.world.core.MobileWorldObject;
import java.awt.Color;
import java.awt.Graphics;

/**
 * A PowerPlant is simply a machine which provides power.
 * 
 * Note that this class needn't override AbstractMachine::attachJsonProperties,
 * as it requires no unique constructor arguments
 * 
 * @author Matt Crow
 */
public class PowerPlant extends AbstractMachine implements PowerProvidingMachine, Illuminating {

    /**
     * 
     * @param x the x-coordinate of this PowerPlant, measured in pixel-space
     * @param y the y-coordinate of this PowerPlant, measured in pixel-space
     */
    public PowerPlant(int x, int y) {
        super(x, y, true);
    }

    /**
     * GNDN (that's a Star Trek reference)
     */
    @Override
    public void machineUpdate(int fps) {}

    @Override
    public void draw(Graphics g) {
        int x = getXAsInt();
        int y = getYAsInt();
        int w = getWidth();
        int h = getHeight();
        int offset = w / 10;
        g.setColor(Color.GRAY);
        g.fillOval(x, y, w, h);
        g.setColor(new Color(255, 255, 0, 100));
        g.fillOval(x + offset, y + offset, w - 2 * offset, h - 2 * offset);
    }

    @Override
    public int getPowerAreaRadiusInTiles() {
        return 3;
    }

    @Override
    public void collideWith(MobileWorldObject e) {
        getCollisionBox().shoveOut(e);
    }

    @Override
    public String getJsonType() {
        return "PowerPlant";
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
        return 3; // same as power radius
    }

    @Override
    public LightLevel getIlluminationAtDistance(int d) {
        return LightLevel.capped(125 - d * 15);
    }
}
