package gears.sidescroller.world.machines;

import gears.sidescroller.world.entities.AbstractEntity;
import java.awt.Color;
import java.awt.Graphics;

/**
 * A PowerPlant is simply a machine which provides power.
 * 
 * @author Matt Crow
 */
public class PowerPlant extends AbstractMachine implements PowerProvidingMachine {

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
    public void update() {}

    @Override
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
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
    public boolean checkForCollisions(AbstractEntity e) {
        return this.getCollisionBox().shoveOut(e);
    }

}
