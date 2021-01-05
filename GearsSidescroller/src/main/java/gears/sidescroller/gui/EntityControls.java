package gears.sidescroller.gui;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.Direction;
import java.awt.event.KeyEvent;

/**
 *
 * @author Matt
 */
public class EntityControls {
    private final AbstractEntity controlledEntity;
    
    public EntityControls(AbstractEntity controlledEntity){
        this.controlledEntity = controlledEntity;
    }
    
    protected final AbstractEntity getContolledEntity(){
        return controlledEntity;
    }
    
    private Runnable setMovingInDir(Direction d, boolean shouldMove){
        return ()->controlledEntity.setMovingInDir(d, shouldMove);
    }
    private Runnable moveInDir(Direction d){
        return setMovingInDir(d, true);
    }
    private Runnable noMoveInDir(Direction d){
        return setMovingInDir(d, false);
    }
    private void keyToDir(LevelPage p, int keyEvent, Direction d){
        p.registerKey(keyEvent, true, moveInDir(d));
        p.registerKey(keyEvent, false, noMoveInDir(d));
    }
    
    public void registerTo(LevelPage p){
        keyToDir(p, KeyEvent.VK_W, Direction.UP);
        keyToDir(p, KeyEvent.VK_A, Direction.LEFT);
        keyToDir(p, KeyEvent.VK_S, Direction.DOWN);
        keyToDir(p, KeyEvent.VK_D, Direction.RIGHT);
    }
}
