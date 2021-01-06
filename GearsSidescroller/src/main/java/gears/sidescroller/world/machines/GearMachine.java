package gears.sidescroller.world.machines;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.RotatedGearSprite;
import gears.sidescroller.util.Sprite;
import gears.sidescroller.util.UnrotatedGearSprite;
import gears.sidescroller.util.dataStructures.Removable;
import gears.sidescroller.util.dataStructures.RemovalListener;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Matt
 */
public class GearMachine extends AbstractMachine implements PowerProvidingMachine, Removable {
    private int currentState;
    private int frameCount;
    private final ArrayList<RemovalListener> removalListeners;
    
    private static final int TIME_UNTIL_UPDATE = 20; // where am I storing frame rate?
    private static final Sprite[] SPRITES = new Sprite[]{
        new UnrotatedGearSprite(Color.GRAY, TILE_SIZE),
        new RotatedGearSprite(Color.GRAY, TILE_SIZE)
    };
    public GearMachine(int x, int y) {
        super(x, y, TILE_SIZE, TILE_SIZE, false);
        currentState = 0;
        frameCount = 0;
        removalListeners = new ArrayList<>();
    }

    @Override
    public void update() {
        frameCount++;
        if(frameCount >= TIME_UNTIL_UPDATE){
            frameCount = 0;
            currentState = (currentState + 1) % SPRITES.length;
        }
    }

    @Override
    public void draw(Graphics g) {
        SPRITES[this.currentState].draw(g, getX(), getY());
    }

    @Override
    public boolean checkForCollisions(AbstractEntity e) {
        // don't shove out
        return this.getCollisionBox().isCollidingWith(e);
    }

    @Override
    public int getPowerAreaRadiusInTiles() {
        return 1;
    }

    @Override
    public void addRemovalListener(RemovalListener listener) {
        removalListeners.add(listener);
    }

    @Override
    public void forEachRemovalListener(Consumer<RemovalListener> doThis) {
        removalListeners.forEach(doThis);
    }

}
