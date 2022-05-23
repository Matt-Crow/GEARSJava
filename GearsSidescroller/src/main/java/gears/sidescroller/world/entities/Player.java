package gears.sidescroller.world.entities;

import gears.sidescroller.gui.level.LevelPage;
import gears.sidescroller.world.core.MobileWorldObject;
import gears.sidescroller.world.items.AbstractItem;
import gears.sidescroller.world.tiles.AbstractTileTemplate;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * A Player is an AbstractEntity which can pick up and use AbstractItems.
 * 
 * @author Matt Crow
 */
public class Player extends AbstractEntity {
    private final ArrayList<AbstractItem> inventory;
    private final ArrayList<InventoryListener> inventoryListeners;
    
    public Player(){
        super();
        this.setSpeed(3 * AbstractTileTemplate.TILE_SIZE / LevelPage.FPS);
        inventory = new ArrayList<>();
        inventoryListeners = new ArrayList<>();
    }
    
    public Player(Collection<AbstractItem> inventory){
        this();
        inventory.forEach(this::pickupItem);
    }
    
    /**
     * Registers and InventoryListener to receive notifications when this'
     * inventory changes.
     * 
     * @param listener the listener to register. 
     */
    public final void addInventoryListener(InventoryListener listener){
        this.inventoryListeners.add(listener);
    }
    
    /**
     * Causes this Player to interact with its current Area, which handles
     * interactions appropriately.
     */
    public final void grab(){
        getArea().playerInteracted(this);
    }
    
    /**
     * Gives this Player the given item. Note that this does not remove the item
     * from its Area.
     * 
     * @param item the item to pick up 
     */
    public final void pickupItem(AbstractItem item){
        inventory.add(item);
        fireInventoryChanged();
    }
    
    /**
     * Removes the given item from this' inventory, notifying listeners if 
     * successful
     * .
     * @param item the item to remove from this' inventory 
     */
    public final void removeItem(AbstractItem item){
        if(inventory.remove(item)){
            fireInventoryChanged();
        }
    }
    
    /**
     * Attempts to use the given AbstractItem in this' Area. If this successfully
     * uses the item, removes it from this' inventory.
     * 
     * @param item the item to use.
     * @return 
     */
    public final boolean useItem(AbstractItem item){
        return inventory.contains(item) && item.doAction(this);
    }
    
    /**
     * Performs the given action on each item in this' inventory
     * 
     * @param doThis what to do with each item
     */
    public final void forEachInventoryItem(Consumer<AbstractItem> doThis){
        inventory.forEach(doThis);
    }
    
    /**
     * Notifies all attached listeners that this' inventory has changed
     */
    private void fireInventoryChanged(){
        this.inventoryListeners.forEach((listener)->listener.inventoryChanged(this));
    }
    
    /**
     * Prepares this Player for a new Level
     */
    public void init(){
        inventory.clear();
        fireInventoryChanged();
    }
    
    /**
     * GNDN
     * @return this, for chaining purposes 
     */
    @Override
    public AbstractEntity doUpdate() {
        return this;
    }

    /**
     * Renders this Player beautifully as a masterpiece of modern art, the likes
     * of which even the most esteemed artists of the yesteryear yearn to achieve.
     * 
     * @param g the canvas which this method should grace the surface with a
     * magnificent rendition of this Player.
     */
    @Override
    public void draw(Graphics g) {
        //temporary until I have player sprites
        int t = AbstractTileTemplate.TILE_SIZE;
        g.setColor(Color.gray);
        g.fillRect(getX(), getY(), t, t);
    }

    @Override
    public boolean checkForCollisions(MobileWorldObject obj) {
        return this.getCollisionBox().isCollidingWith(obj);
    }

    @Override
    protected void attachJsonProperties(JsonObjectBuilder builder) {
        JsonArrayBuilder inventoryBuilder = Json.createArrayBuilder();
        inventory.forEach((i)->inventoryBuilder.add(i.toJson()));
        builder.add("inventory", inventoryBuilder.build());
    }

    @Override
    public String getJsonType() {
        return "Player";
    }
}
