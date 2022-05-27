package gears.sidescroller.world.entities;

import gears.sidescroller.world.core.Illuminating;
import gears.sidescroller.world.core.LightLevel;
import gears.sidescroller.world.core.MobileWorldObject;
import gears.sidescroller.world.items.AbstractItem;
import gears.sidescroller.world.tiles.AbstractTileTemplate;
import java.awt.*;
import java.util.*;
import java.util.function.Consumer;
import javax.json.*;

/**
 * A Player is an AbstractEntity which can pick up and use AbstractItems.
 * 
 * @author Matt Crow
 */
public class Player extends AbstractEntity implements Illuminating {
    private final ArrayList<AbstractItem> inventory;
    private final ArrayList<InventoryListener> inventoryListeners;
    private boolean lightEnabled;
    private boolean isSneaking;
    
    public Player(){
        super();
        this.setSpeed(4 * AbstractTileTemplate.TILE_SIZE); // seems like it's going slower than this
        inventory = new ArrayList<>();
        inventoryListeners = new ArrayList<>();
        lightEnabled = false;
        isSneaking = false;
    }
    
    
    @Override
    public int getSpeed(){
        double mod = (isSneaking) ? 0.5 : 1.0;
        return (int)(super.getSpeed() * mod);
    }    
    
    public void setSneaking(boolean isSneaking){
        this.isSneaking = isSneaking;
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
        lightEnabled = false;
        isSneaking = false;
    }
    
    public void toggleLightEnabled(){
        lightEnabled = !lightEnabled;
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
        g.fillRect(getXAsInt(), getYAsInt(), t, t);
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
    
    @Override
    public int getLightCenterXIdx() {
        return getCenterXIdx();
    }

    @Override
    public int getLightCenterYIdx() {
        return getCenterYIdx();
    }

    @Override
    public int getIlluminationRange() {
        return (lightEnabled) ? 3 : 0;
    }

    @Override
    public LightLevel getIlluminationAtDistance(int d) {
        return (lightEnabled)
                ? new LightLevel(100 - 15 * d)
                : LightLevel.NO_LIGHT;
    }
}
