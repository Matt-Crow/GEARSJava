package gears.sidescroller.world.core;

/**
 * The Collidable interface should be implemented
 * by classes which should support collision detection
 * and reaction.
 * 
 * @author Matt Crow
 */
public interface Collidable {
    public boolean checkForCollisions(MobileWorldObject obj);
    public CollisionBox getCollisionBox();
}
