package gears.sidescroller.world.machines;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.Direction;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;

/**
 * A ConveryorBeltSegment is a machine which will move AbstractEntities
 * in a given direction, assuming it is powered.
 * 
 * @author Matt Crow
 */
public class ConveyorBeltSegment extends AbstractMachine {
    private final Direction movesStuffInThisDirection;
    private final int speed;
    
    /*
    These 6 fields are used in rendering
    the animated line that zooms down this
    ConveryorBeltSegment.
    */
    private int lineXOffset;
    private int lineYOffset;
    private final int lineStartX;
    private final int lineStartY;
    private final int lineWidth;
    private final int lineHeight;
    
    /**
     * @param x the x-coordinate of this conveyor belt, measured in pixel-space
     * @param y the y-coordinate of this conveyor belt, measured in pixel-space
     * @param selfPowering whether or not this should automatically provide power to itself
     * @param speed how fast this moves AbstractEntities that collide with it, measured in pixel-space
     * @param movesStuffInThisDirection what direction this moves AbstractEntities in
     */
    public ConveyorBeltSegment(int x, int y, boolean selfPowering, int speed, Direction movesStuffInThisDirection) {
        super(x, y, selfPowering);
        this.speed = speed;
        this.movesStuffInThisDirection = movesStuffInThisDirection;
        this.lineXOffset = 0;
        this.lineYOffset = 0;
        int x0;
        int y0;
        switch(movesStuffInThisDirection){
            case UP:
               x0 = getX();
               y0 = getY() + getHeight();
               break;
            case DOWN:
                x0 = getX();
                y0 = getY();
                break;
            case LEFT:
                x0 = getX() + getWidth();
                y0 = getY();
                break;
            case RIGHT:
                x0 = getX();
                y0 = getY();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        lineStartX = x0;
        lineStartY = y0;
        lineWidth = (movesStuffInThisDirection.getXMod() == 0) ? (TILE_SIZE * 4) / 5 : TILE_SIZE / 10;
        lineHeight = (movesStuffInThisDirection.getYMod() == 0) ? (TILE_SIZE * 4) / 5 : TILE_SIZE / 10;
    }

    @Override
    public void update() {
        lineXOffset = (lineXOffset + movesStuffInThisDirection.getXMod() * speed) % getWidth();
        lineYOffset = (lineYOffset + movesStuffInThisDirection.getYMod() * speed) % getHeight();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        //                         LEFT or RIGHT will have no xOffset
        int xOffset = Math.abs(TILE_SIZE * movesStuffInThisDirection.getYMod()) / 10;
        int yOffset = Math.abs(TILE_SIZE * movesStuffInThisDirection.getXMod()) / 10;
        g.fillRect(getX() + xOffset, getY() + yOffset, getWidth() - xOffset * 2, getHeight() - yOffset * 2);
    
        g.setColor(Color.GRAY);
        g.fillRect(lineStartX + xOffset + lineXOffset, lineStartY + yOffset + lineYOffset, lineWidth, lineHeight);
    }

    /**
     * Checks for collisions with the given AbstractEntity,
     * and moves them if appropriate.
     * 
     * @param e the AbstractEntity to check for collisions with.
     * 
     * @return the boolean 
     */
    @Override
    public boolean checkForCollisions(gears.sidescroller.world.core.MobileWorldObject e) {
        boolean collided = this.getCollisionBox().isCollidingWith(e);
        if(collided && isPowered()){
            e.setX(e.getX() + this.movesStuffInThisDirection.getXMod() * this.speed);
            e.setY(e.getY() + this.movesStuffInThisDirection.getYMod() * this.speed);
        }
        return collided;
    }
}
