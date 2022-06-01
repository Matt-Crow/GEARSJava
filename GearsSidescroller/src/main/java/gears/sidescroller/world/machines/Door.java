package gears.sidescroller.world.machines;

import gears.sidescroller.world.core.MobileWorldObject;
import gears.sidescroller.world.tiles.AbstractTileTemplate;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import gears.sidescroller.world.tiles.BasicColorTile;
import java.awt.Color;
import java.awt.Graphics;

/**
 * a Door is a machine that is tangible only when it is not powered
 * @author Matt Crow
 */
public class Door extends AbstractMachine {
    private final AbstractTileTemplate tangibleDesign;
    private final AbstractTileTemplate intangibleDesign;
    
    /**
     * creates a Door at the given coordinates, measured in pixel-space
     * @param x the x-coordinate of the upper-left corner of this Door
     * @param y the y-coordinate of the upper-left corner of this Door
     */
    public Door(int x, int y) {
        super(x, y, TILE_SIZE, TILE_SIZE, false);
        tangibleDesign = new BasicColorTile(true, Color.BLACK, Color.GRAY);
        intangibleDesign = new BasicColorTile(false, Color.GRAY, Color.GRAY);
    }

    @Override
    public void machineUpdate(int fps) {
        
    }

    @Override
    public void draw(Graphics g) {
        AbstractTileTemplate drawMe = (isPowered())
                ? intangibleDesign
                : tangibleDesign;
        drawMe.drawAt(g, getXAsInt(), getYAsInt());
    }

    @Override
    public void collideWith(MobileWorldObject e) {
        if(!isPowered()){
            getCollisionBox().shoveOut(e);
        }
    }

    @Override
    public String getJsonType() {
        return "Door";
    }
}
