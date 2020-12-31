package gears.sidescroller.world.Machines;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public class PowerGenerator extends AbstractMachine implements PowerProvidingMachine {

    public PowerGenerator(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getWidthInPixels();
        int h = getHeightInPixels();
        int offset = w / 10;
        g.setColor(Color.GRAY);
        g.fillOval(x, y, w, h);
        g.setColor(new Color(255, 255, 0, 100));
        g.fillOval(x + offset, y + offset, w - 2 * offset, h - 2 * offset);
    }

    @Override
    public boolean isProvidingPower() {
        return true;
    }

    @Override
    public int getPowerAreaRadiusInTiles() {
        return 3;
    }

}
