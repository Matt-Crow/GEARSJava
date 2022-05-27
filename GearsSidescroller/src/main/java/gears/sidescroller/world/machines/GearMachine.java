package gears.sidescroller.world.machines;

import gears.sidescroller.gui.level.LevelPage;
import gears.sidescroller.util.RotatedGearSprite;
import gears.sidescroller.util.Sprite;
import gears.sidescroller.util.UnrotatedGearSprite;
import gears.sidescroller.world.core.MobileWorldObject;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;

/**
 * A GearMachine is a machine which can serve as a repeater for power provided to it.
 * One can create some pretty fun puzzles with these, if one is so inclined.
 * 
 * Note that this class currently needn't override 
 * AbstractMachine::attachJsonProperties, as it has no unique constructor 
 * arguments
 * 
 * @author Matt Crow
 */
public class GearMachine extends AbstractMachine implements PowerProvidingMachine {
    /*
    All of these are used for animation
    */
    private int currentState;
    private int frameCount;
    private static final Sprite[] SPRITES = new Sprite[]{
        new UnrotatedGearSprite(Color.GRAY, TILE_SIZE),
        new RotatedGearSprite(Color.GRAY, TILE_SIZE)
    };
    
    /**
     * 
     * @param x the x-coordinate of this gear, measured in pixel-space
     * @param y the y-coordinate of this gear, measured in pixel-space
     */
    public GearMachine(int x, int y) {
        super(x, y, TILE_SIZE, TILE_SIZE, false);
        currentState = 0;
        frameCount = 0;
    }

    @Override
    public void machineUpdate(int fps) {
        frameCount++;
        if(frameCount >= fps){
            frameCount = 0;
            currentState = (currentState + 1) % SPRITES.length;
        }
    }

    @Override
    public void draw(Graphics g) {
        SPRITES[this.currentState].draw(g, getXAsInt(), getYAsInt());
    }

    @Override
    public void collideWith(MobileWorldObject e) {
        // don't shove out
    }

    @Override
    public int getPowerAreaRadiusInTiles() {
        return 1;
    }

    @Override
    public String getJsonType() {
        return "GearMachine";
    }
}
