package gears.sidescroller.gui;

import gears.sidescroller.gui.level.PlayerInventoryMenu;
import gears.sidescroller.gui.level.LevelPage;
import gears.sidescroller.world.entities.Player;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JMenuBar;

/**
 *
 * @author Matt
 */
public class PlayerControls extends EntityControls {

    public PlayerControls(Player controlledEntity) {
        super(controlledEntity);
        
        JMenuBar bar = new JMenuBar();
        bar.add(new PlayerInventoryMenu(controlledEntity));
        add(bar, BorderLayout.PAGE_END);
    }
    
    @Override
    public void registerTo(LevelPage p){
        super.registerTo(p);
        p.registerKey(KeyEvent.VK_Q, true, ()->{
            ((Player)getControlledEntity()).grab();
        });
        p.registerKey(KeyEvent.VK_L, true, ()->{
            ((Player)getControlledEntity()).toggleLightEnabled();
        });
        
        // for some reason, this never runs
        p.registerKey(KeyEvent.VK_SHIFT, true, ()->{
            ((Player)getControlledEntity()).setSneaking(true);
        });
        
        // this runs on key down?!?
        p.registerKey(KeyEvent.VK_SHIFT, false, ()->{
            ((Player)getControlledEntity()).setSneaking(false);
        });
        
        /*
        this works
        p.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SHIFT){
                    ((Player)getControlledEntity()).setSneaking(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SHIFT){
                    ((Player)getControlledEntity()).setSneaking(false);
                }
            }
        });
        */
    }
}
