package gears.sidescroller.gui;

import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.AbstractItem;
import java.awt.Color;
import javax.swing.JMenuItem;

/**
 *
 * @author Matt
 */
class PlayerInventoryItemComponent extends JMenuItem {
    private final Player forPlayer;
    private final AbstractItem forItem;
    
    PlayerInventoryItemComponent(Player forPlayer, AbstractItem forItem){
        super(forItem.toString());
        this.forPlayer = forPlayer;
        this.forItem = forItem;
        this.addActionListener((e)->{
            
        });
        this.setBackground(Color.red);
    }
}
