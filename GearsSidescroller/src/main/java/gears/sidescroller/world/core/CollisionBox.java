package gears.sidescroller.world.core;

import gears.sidescroller.world.core.Collidable;

/**
 *
 * @author Matt
 */
public class CollisionBox {
    private final ObjectInWorld forObject;
    
    // need this for tiles
    public CollisionBox(int x, int y, int w, int h){
        forObject = new ObjectInWorld(x, y, w, h);
    }
    
    public CollisionBox(ObjectInWorld forObject){
        this.forObject = forObject;
    }
    
    public final ObjectInWorld getWorldObject(){
        return forObject;
    }
    
    public final boolean isOtherObjectOutside(ObjectInWorld otherObject){
        return forObject.getX() > otherObject.getX() + otherObject.getWidth() //other object is to the left
            || forObject.getX() + forObject.getWidth() < otherObject.getX() // other object is to the right
            || forObject.getY() > otherObject.getY() + otherObject.getHeight() // other object is above
            || forObject.getY() + forObject.getHeight() < otherObject.getY() // other object is below
            ;
    }
    
    public final boolean isCollidingWith(ObjectInWorld otherObject){        
        return otherObject instanceof Collidable && !isOtherObjectOutside(otherObject);
    }
    
    public final boolean shoveOut(ObjectInWorld otherObject){
        boolean willShoveOut = this.isCollidingWith(otherObject);
        if(willShoveOut){
            // figure out which way to shove them out
            // find difference between their centers
            int dx = forObject.getX() + forObject.getWidth() / 2 - 
                (otherObject.getX() + otherObject.getWidth() / 2);
            int dy = forObject.getY() + forObject.getHeight() / 2 -
                (otherObject.getY() + otherObject.getHeight() / 2);
            if(Math.abs(dx) > Math.abs(dy)){
                // shove them left or right
                if(dx < forObject.getWidth() / 2){
                    // more than half way through, so shove to the right
                    otherObject.setX(forObject.getX() + forObject.getWidth());
                } else if(dx < forObject.getWidth()){
                    // not yet half way through
                    otherObject.setX(forObject.getX() - otherObject.getWidth());
                } else {
                    // dx is too big, so the object isn't really colliding
                }
            } else {
                // shove up or down
                if(dy < forObject.getHeight() / 2){
                    otherObject.setY(forObject.getY() + forObject.getHeight());
                } else if(dy < forObject.getHeight()){
                    otherObject.setY(forObject.getY() - otherObject.getHeight());
                } else {
                    // dy too big
                }
            }
        }
        return willShoveOut;
    }
    
    public static void main(String[] args){
        CollisionBox box1 = new CollisionBox(0, 0, 100, 100);
        CollisionBox box2 = new CollisionBox(0, 0, 100, 100);
        System.out.printf("Is box2 outside of box1? %b\n", box1.isOtherObjectOutside(box2.getWorldObject()));
        System.out.printf("Is box2 colliding with box1? %b\n", box1.isCollidingWith(box2.getWorldObject()));
    }
}
