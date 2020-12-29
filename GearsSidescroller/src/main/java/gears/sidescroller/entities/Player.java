package gears.sidescroller.entities;

import gears.sidescroller.gui.LevelPage;
import gears.sidescroller.world.tiles.AbstractTile;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public class Player extends AbstractEntity {
    
    public Player(){
        super();
        this.setSpeed(3 * AbstractTile.TILE_SIZE / LevelPage.FPS);
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
        g.fillRect(getX(), getY(), t, t);
    }
}
