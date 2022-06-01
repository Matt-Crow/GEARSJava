package gears.sidescroller.gui.level;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.Direction;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
@SuppressWarnings("serial")
public class EntityControls extends JPanel {
    private final AbstractEntity controlledEntity;
    
    public EntityControls(AbstractEntity controlledEntity){
        super();
        this.setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));
        setForeground(new Color(0, 0, 0, 0));
        this.setOpaque(false); // allows components beneath this to render
        add(new JLabel(String.format("Controlling Entity %s", controlledEntity.toString())), BorderLayout.PAGE_START);
        
        this.controlledEntity = controlledEntity;
    }
    
    protected final AbstractEntity getControlledEntity(){
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
