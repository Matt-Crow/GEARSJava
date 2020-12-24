package gears.sidescroller.util;

/**
 * might not need this
 * @author Matt Crow
 */
public enum Direction {
    LEFT("left", -1, 0),
    RIGHT("right", 1, 0),
    UP("up", 0, -1),
    DOWN("down", 0, 1);
    
    private final String name;
    private final int xMod;
    private final int yMod;
    
    Direction(String n, int x, int y){
        name = n;
        xMod = x;
        yMod = y;
    }
    
    public final String getName(){
        return name;
    }
    
    public final int getXMod(){
        return xMod;
    }
    
    public final int getYMod(){
        return yMod;
    }
    
    public static final Direction rotateCounterClockWise(Direction d){
        Direction[] dirs = new Direction[]{RIGHT, UP, LEFT, DOWN};
        int currIdx = -1;
        for(int i = 0; currIdx == -1 && i < dirs.length; i++){
            if(dirs[i].equals(d)){
                currIdx = i;
            }
        }
        return dirs[(currIdx + 1) % dirs.length];
    }
}
