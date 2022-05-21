package gears.sidescroller.gui.level;

import gears.sidescroller.world.entities.InventoryListener;
import gears.sidescroller.world.entities.Player;
import javax.swing.JLabel;
import javax.swing.JMenu;

/**
 *
 * @author Matt
 */
public class PlayerInventoryMenu extends JMenu implements InventoryListener {
    private final Player forPlayer;
    
    public PlayerInventoryMenu(Player forPlayer){
        super("Inventory");
        this.forPlayer = forPlayer;
        update();
        forPlayer.addInventoryListener(this);
    }
    
    private void update(){
        this.removeAll();
        forPlayer.forEachInventoryItem((item)->{
            this.add(new PlayerInventoryItemComponent(forPlayer, item));
        });
        if(this.getItemCount() == 0){ // note: component count and item count are different
            this.add(new JLabel("There's nothing in your inventory"));
        }
    }

    @Override
    public void inventoryChanged(Player whoseInventory) {
        update();
    }
}
