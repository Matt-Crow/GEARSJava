package gears.sidescroller.gui;

import gears.sidescroller.world.entities.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class PlayerControls extends EntityControls {

    public PlayerControls(Player controlledEntity) {
        super(controlledEntity);
        
        JPanel leftSide = new JPanel();
        leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
        JButton inventoryButton = new JButton("Inventory");
        
        leftSide.add(inventoryButton);
        add(leftSide, BorderLayout.LINE_START);
    }
    
    @Override
    public void registerTo(LevelPage p){
        super.registerTo(p);
        p.registerKey(KeyEvent.VK_I, true, ()->{
            if(!((Player)this.getContolledEntity()).useItem(0, p.getCurrentLevel().getCurrentArea())){
                System.out.println("From PlayerControls: Cannot use item here");
            }
        });
    }
}
