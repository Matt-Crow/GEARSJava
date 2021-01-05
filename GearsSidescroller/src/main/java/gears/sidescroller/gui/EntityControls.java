package gears.sidescroller.gui;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.Direction;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class EntityControls extends JPanel {
    private final AbstractEntity controlledEntity;
    
    public EntityControls(AbstractEntity controlledEntity){
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JLabel(String.format("Controlling Entity %s", controlledEntity.toString())), gbc.clone());
        
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
