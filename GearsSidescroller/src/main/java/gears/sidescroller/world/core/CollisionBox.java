package gears.sidescroller.world.core;

import java.awt.Graphics;
import javax.json.JsonObjectBuilder;

/**
 * A CollisionBox is used to share collision detection and reaction between
 * rectangular things. Objects wishing to use a CollisionBox should implement
 * the Collidable interface.
 * 
 * Note that the implementing object needn't be an ObjectInWorld:
 * ObjectInWorlds need only invoke the CollisionBox constructor
 * parametrized with an ObjectInWorld, whereas other classes can
 * call the 4 integer parametrized constructor.
 * 
 * @author Matt Crow
 */
public class CollisionBox {
    private final WorldObject forObject;
    
    // need this for tiles
    public CollisionBox(int x, int y, int w, int h){
        forObject = new WorldObject(x, y, w, h) {
            @Override
            public void collideWith(MobileWorldObject obj) {}

            @Override
            protected void attachJsonProperties(JsonObjectBuilder builder) {}

            @Override
            public String getJsonType() {
                return "CollisionBox";
            }

            @Override
            public void draw(Graphics g) {}

            @Override
            public void update(int fps) {}
        };
    }
    
    public CollisionBox(WorldObject forObject){
        this.forObject = forObject;
    }
    
    public final WorldObject getWorldObject(){
        return forObject;
    }
    
    public final boolean isOtherObjectOutside(WorldObject otherObject){
        return forObject.getXAsInt() > otherObject.getXAsInt() + otherObject.getWidth() //other object is to the left
            || forObject.getXAsInt() + forObject.getWidth() < otherObject.getXAsInt() // other object is to the right
            || forObject.getYAsInt() > otherObject.getYAsInt() + otherObject.getHeight() // other object is above
            || forObject.getYAsInt() + forObject.getHeight() < otherObject.getYAsInt() // other object is below
            ;
    }
    
    public final boolean isCollidingWith(WorldObject otherObject){        
        return otherObject instanceof Collidable && !isOtherObjectOutside(otherObject);
    }
    
    public final boolean shoveOut(MobileWorldObject otherObject){
        boolean willShoveOut = this.isCollidingWith(otherObject);
        if(willShoveOut){
            // figure out which way to shove them out
            // find difference between their centers
            int dx = forObject.getXAsInt() + forObject.getWidth() / 2 - 
                (otherObject.getXAsInt() + otherObject.getWidth() / 2);
            int dy = forObject.getYAsInt() + forObject.getHeight() / 2 -
                (otherObject.getYAsInt() + otherObject.getHeight() / 2);
            if(Math.abs(dx) > Math.abs(dy)){
                // shove them left or right
                if(dx < forObject.getWidth() / 2){
                    // more than half way through, so shove to the right
                    otherObject.setX(forObject.getXAsInt() + forObject.getWidth());
                } else if(dx < forObject.getWidth()){
                    // not yet half way through
                    otherObject.setX(forObject.getXAsInt() - otherObject.getWidth());
                } else {
                    // dx is too big, so the object isn't really colliding
                }
            } else {
                // shove up or down
                if(dy < forObject.getHeight() / 2){
                    otherObject.setY(forObject.getYAsInt() + forObject.getHeight());
                } else if(dy < forObject.getHeight()){
                    otherObject.setY(forObject.getYAsInt() - otherObject.getHeight());
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
