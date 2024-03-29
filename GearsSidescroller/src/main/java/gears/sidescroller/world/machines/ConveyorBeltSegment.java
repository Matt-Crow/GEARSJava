package gears.sidescroller.world.machines;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.core.MobileWorldObject;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import javax.json.JsonObjectBuilder;

/**
 * A ConveryorBeltSegment is a machine which will move AbstractEntities
 * in a given direction, assuming it is powered.
 * 
 * @author Matt Crow
 */
public class ConveyorBeltSegment extends AbstractMachine {
    private final Direction movesStuffInThisDirection;
    private final int speed;
    private int lastFps; // needed for collideWith
    
    /*
    These 6 fields are used in rendering
    the animated line that zooms down this
    ConveryorBeltSegment.
    */
    private double lineXOffset;
    private double lineYOffset;
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
        lastFps = 1;
        int x0;
        int y0;
        switch(movesStuffInThisDirection){
            case UP:
               x0 = getXAsInt();
               y0 = getYAsInt() + getHeight();
               break;
            case DOWN:
                x0 = getXAsInt();
                y0 = getYAsInt();
                break;
            case LEFT:
                x0 = getXAsInt() + getWidth();
                y0 = getYAsInt();
                break;
            case RIGHT:
                x0 = getXAsInt();
                y0 = getYAsInt();
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
    public void machineUpdate(int fps) {
        lastFps = fps;
        lineXOffset = (lineXOffset + movesStuffInThisDirection.getXMod() * speed / (double)fps) % getWidth();
        lineYOffset = (lineYOffset + movesStuffInThisDirection.getYMod() * speed / (double)fps) % getHeight();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        //                         LEFT or RIGHT will have no xOffset
        int xOffset = Math.abs(TILE_SIZE * movesStuffInThisDirection.getYMod()) / 10;
        int yOffset = Math.abs(TILE_SIZE * movesStuffInThisDirection.getXMod()) / 10;
        g.fillRect(getXAsInt() + xOffset, getYAsInt() + yOffset, getWidth() - xOffset * 2, getHeight() - yOffset * 2);
    
        g.setColor(Color.GRAY);
        g.fillRect(
                (int)(lineStartX + xOffset + lineXOffset), 
                (int)(lineStartY + yOffset + lineYOffset), 
                lineWidth, 
                lineHeight
        );
    }

    /**
     * Moves the given AbstractEntity if appropriate.
     * 
     * @param e the AbstractEntity this collided with
     */
    @Override
    public void collideWith(MobileWorldObject e) {
        if(isPowered()){
            double fps = (double)lastFps;
            e.setX(e.getX() + this.movesStuffInThisDirection.getXMod() * this.speed / fps);
            e.setY(e.getY() + this.movesStuffInThisDirection.getYMod() * this.speed / fps);
        }
    }

    @Override
    protected void attachJsonProperties(JsonObjectBuilder builder) {
        super.attachJsonProperties(builder);
        builder.add("speed", speed);
        builder.add("movesStuffInThisDirection", movesStuffInThisDirection.getName());
    }

    @Override
    public String getJsonType() {
        return "ConveyorBeltSegment";
    }
}
