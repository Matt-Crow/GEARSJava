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
    
    // in case I ever add more, non-cardinal directions, so I don't need to use Direction.values()
    public static final Direction[] getCardinalDirections(){
        return new Direction[]{
            UP,
            DOWN,
            LEFT,
            RIGHT
        };
    }
    
    private static Direction rotate(Direction d, int rotation){
        Direction[] dirs = new Direction[]{RIGHT, UP, LEFT, DOWN};
        int currIdx = -1;
        for(int i = 0; currIdx == -1 && i < dirs.length; i++){
            if(dirs[i].equals(d)){
                currIdx = i;
            }
        }
        int newIdx = (currIdx + rotation >= 0) ? (currIdx + rotation) % dirs.length : dirs.length + rotation + currIdx; 
        return dirs[newIdx];
    }
    
    public static final Direction rotateCounterClockWise(Direction d){
        return rotate(d, 1);
    }
    
    public static final Direction rotateClockWise(Direction d){
        return rotate(d, -1);
    }
    
    public static void main(String[] args){
        Direction.rotateClockWise(RIGHT);
    }
}
