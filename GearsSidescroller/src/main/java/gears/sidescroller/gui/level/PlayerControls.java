package gears.sidescroller.gui.level;

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
        
        /*
        This current system doesn't allow the likes of KeyEvent.VK_SHIFT, as
        shift and its ilk are modifier keys, and so the keystrokes are a bit
        different.
        
        To allow this to work, I'd need to rework the LevelPage::registerKey to
        use KeyListeners instead of action maps
        */
        p.registerKey(KeyEvent.VK_X, true, ()->{
            ((Player)getControlledEntity()).setSneaking(true);
        });
        p.registerKey(KeyEvent.VK_X, false, ()->{
            ((Player)getControlledEntity()).setSneaking(false);
        });        
    }
}
