package gears.sidescroller.entities;

/**
 *
 * @author Matt
 */
public abstract class AbstractEntity {
    private int x;
    private int y;
    
    public AbstractEntity(){
        x = 0;
        y = 0;
    }
    
    public final int getX(){
        return x;
    }
    public final int getY(){
        return y;
    }
    
    public final AbstractEntity setX(int newX){
        x = newX;
        return this;
    }
    public final AbstractEntity setY(int newY){
        y = newY;
        return this;
    }
    
}
