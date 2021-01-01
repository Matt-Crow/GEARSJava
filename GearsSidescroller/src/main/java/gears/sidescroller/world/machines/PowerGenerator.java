package gears.sidescroller.world.machines;

import gears.sidescroller.entities.AbstractEntity;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public class PowerGenerator extends AbstractMachine implements PowerProvidingMachine {

    public PowerGenerator(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void update() {
        
    }

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
