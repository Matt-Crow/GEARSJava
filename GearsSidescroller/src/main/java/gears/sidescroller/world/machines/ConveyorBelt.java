package gears.sidescroller.world.machines;

import gears.sidescroller.util.Direction;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public class ConveyorBelt extends AbstractMachine {
    private final Direction movesStuffInThisDirection;
    private final int speed;
    private int lineXOffset;
    private int lineYOffset;
    private final int lineStartX;
    private final int lineStartY;
    private final int lineWidth;
    private final int lineHeight;
    
    public ConveyorBelt(int x, int y, boolean selfPowering, int speed, Direction movesStuffInThisDirection) {
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
}
