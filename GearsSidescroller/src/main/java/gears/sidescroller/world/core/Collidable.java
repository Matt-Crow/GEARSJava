package gears.sidescroller.world.core;

/**
 * The Collidable interface should be implemented
 * by classes which should support collision detection
 * and reaction.
 * 
 * @author Matt Crow
 */
public interface Collidable {
    /**
     * called whenever this collides with the given object
     * @param obj the object to handle collisions with
     */
    public void collideWith(MobileWorldObject obj);
    
    /**
     * @return a CollisionBox that can detect collisions with this
     */
    public CollisionBox getCollisionBox();
    
    /**
     * @param obj another object
     * @return whether this is colliding with the given object
     */
    public default boolean isCollidingWith(MobileWorldObject obj){
        return getCollisionBox().isCollidingWith(obj);
    }
}
