package gears.sidescroller.world.entities;

import gears.sidescroller.gui.LevelPage;
import gears.sidescroller.util.dataStructures.VolatileLinkedList;
import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.items.AbstractItem;
import gears.sidescroller.world.tiles.AbstractTile;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 *
 * @author Matt
 */
public class Player extends AbstractEntity {
    private final ArrayList<AbstractItem> inventory;
    private final ArrayList<InventoryListener> inventoryListeners;
    
    public Player(){
        super();
        this.setSpeed(3 * AbstractTile.TILE_SIZE / LevelPage.FPS);
        inventory = new ArrayList<>();
        inventoryListeners = new ArrayList<>();
    }
    
    public final void addInventoryListener(InventoryListener listener){
        this.inventoryListeners.add(listener);
    }
    
    public final void pickupItem(AbstractItem item){
        inventory.add(item);
        fireInventoryChanged();
    }
    
    public final boolean useItem(int itemNum, Area inArea){
        boolean usedItem = inventory.size() > itemNum && inventory.get(itemNum).doAction(this, inArea);
        if(usedItem){
            inventory.remove(itemNum); // may cause ConcurrentModificationException
            fireInventoryChanged();
        }
        return usedItem;
    }
    
    public final void forEachInventoryItem(Consumer<AbstractItem> doThis){
        inventory.forEach(doThis);
    }
    
    private void fireInventoryChanged(){
        this.inventoryListeners.forEach((listener)->listener.inventoryChanged(this));
    }
    
    public void init(){
        inventory.clear();
        fireInventoryChanged();
    }
    
    @Override
    public AbstractEntity doUpdate() {
        return this;
    }

    @Override
    public void draw(Graphics g) {
        //temporary until I have player sprites
        int t = AbstractTile.TILE_SIZE;
        g.setColor(Color.gray);
        g.fillRect(getX(), getY(), t, t);
    }
}
