package gears.sidescroller.entities;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.tiles.AbstractTile;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public class Player extends AbstractEntity{
    
    public Player(){
        super();
        this.setSpeed(5);
    }
    
    @Override
    public AbstractEntity doUpdate() {
        return this;
    }

    @Override
    public void draw(Graphics g) {
        //temporary until I have player sprites
        int t = AbstractTile.TILE_SIZE;
        g.setColor(Color.gray);
        g.fillRect(getX() + (int)(t * 0.1), getY(), (int)(t * 0.8), t);
        
        int eyeSize = t / 5;
        int pupilSize = t / 10;
        int pupilOffset = (eyeSize - pupilSize) / 2;
        int firstEyeXOffset = (getFacing() == Direction.LEFT) ? (int)(t * 0.1) : t / 2;
        g.setColor(Color.white);
        g.fillRect(getX() + firstEyeXOffset, getY(), eyeSize, eyeSize);
        g.fillRect(getX() + firstEyeXOffset + eyeSize * 2, getY(), eyeSize, eyeSize);
        g.setColor(Color.black);
        g.fillRect(getX() + firstEyeXOffset + pupilOffset, getY() + pupilOffset, pupilSize, pupilSize);
        g.fillRect(getX() + firstEyeXOffset + eyeSize * 2 + pupilOffset, getY() + pupilOffset, pupilSize, pupilSize);
    }
}
