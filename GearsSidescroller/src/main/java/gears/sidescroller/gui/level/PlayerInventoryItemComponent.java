package gears.sidescroller.gui.level;

import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.AbstractItem;
import javax.swing.JMenuItem;

/**
 *
 * @author Matt
 */
@SuppressWarnings("serial")
class PlayerInventoryItemComponent extends JMenuItem {
    private final Player forPlayer;
    private final AbstractItem forItem;
    
    PlayerInventoryItemComponent(Player forPlayer, AbstractItem forItem){
        super(forItem.toString());
        this.forPlayer = forPlayer;
        this.forItem = forItem;
        this.addActionListener((e)->{
            forPlayer.useItem(forItem);
        });
    }
}
