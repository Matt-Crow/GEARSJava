package gears.sidescroller.world.core;

/**
 * The MobileWorldObject class adds support for changing
 * the coordinates of an ObjectInWorld. I may need to change
 * this class in the future to support classes such as mobile 
 * machines.
 * 
 * @author Matt Crow
 */
public abstract class MobileWorldObject extends WorldObject {

    public MobileWorldObject(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
    
    /**
     * Moves this object. Subclasses should override this method and call
     * super.update if they need special behavior.
     * 
     * @param fps the frame rate 
     */
    @Override
    public void update(int fps){
        move(fps);
    }
    
    /**
     * updates the coordinates of this object
     * @param fps the approximate number of frames since the last call to move.
     *  Subclasses should scale their movement based upon this parameter.
     */
    public abstract void move(int fps);
}
