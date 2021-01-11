package gears.sidescroller.world.machines;

import gears.sidescroller.world.entities.AbstractEntity;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class ConveyorBeltComposite extends AbstractMachine {
    private final LinkedList<ConveyorBeltSegment> segments;
    
    public ConveyorBeltComposite(int x, int y) {
        super(x, y);
        segments = new LinkedList<>();
    }
    
    protected final void addSegment(ConveyorBeltSegment segment){
        segments.add(segment);
    }

    @Override
    public void update() {
        segments.forEach((segment)->segment.update());
    }

    @Override
    public void draw(Graphics g) {
        segments.forEach((segment)->segment.draw(g));
    }

    @Override
    public boolean checkForCollisions(AbstractEntity e) {
        return segments.stream().map((segment)->segment.checkForCollisions(e)).anyMatch((b)->b);
    }
}