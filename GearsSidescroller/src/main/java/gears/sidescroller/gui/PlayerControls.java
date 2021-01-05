package gears.sidescroller.gui;

import gears.sidescroller.world.entities.Player;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
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
}
