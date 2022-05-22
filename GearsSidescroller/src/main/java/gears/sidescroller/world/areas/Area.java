package gears.sidescroller.world.areas;

import gears.sidescroller.loader.JsonSerializable;
import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.dataStructures.VolatileLinkedList;
import gears.sidescroller.world.core.Collidable;
import gears.sidescroller.world.core.Interactable;
import gears.sidescroller.world.core.MobileWorldObject;
import gears.sidescroller.world.core.ObjectInWorld;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.AbstractItem;
import gears.sidescroller.world.machines.AbstractMachine;
import gears.sidescroller.world.tileMaps.TileMap;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * An Area is a complete, playable world where a Player can explore. Areas are
 * contained within a Level, and contain many things to interact with.
 * 
 * @author Matt Crow
 */
public class Area implements JsonSerializable {
    private final TileMap tileMap;
    private final PowerGrid powerGrid;
    
    /*
    Once I have more behavioral interfaces (Renderable, Interactable, Initializable, etc) 
    I'll want to categorize by behavior instead of type.
    */
    private final VolatileLinkedList<MobileWorldObject> stuffThatCanBumpIntoOtherStuff;
    private final VolatileLinkedList<Collidable> stuffThatOtherStuffCanBumpInto;
    private final VolatileLinkedList<Interactable> interactables;
    private final VolatileLinkedList<AbstractMachine> machines;
    private final VolatileLinkedList<AbstractItem> items;
    
    private final Set<ObjectInWorld> objects;
    
    /**
     * Creates a new Area with the given TileMap
     * 
     * @param t the TileMap for this Area 
     */
    public Area(TileMap t){
        tileMap = t;
        powerGrid = new PowerGrid(t.getWidthInCells(), t.getHeightInCells());
        objects = new HashSet<>();
        stuffThatCanBumpIntoOtherStuff = new VolatileLinkedList<>();
        stuffThatOtherStuffCanBumpInto = new VolatileLinkedList<>();
        interactables = new VolatileLinkedList<>();
        machines = new VolatileLinkedList<>();
        items = new VolatileLinkedList<>();
    }
    
    /**
     * Adds the given ObjectInWorld to this Area. Note that this method does not
     * alter the object's coordinates. This automatically invokes 
     * {@code obj.setArea(this)}, so you needn't worry about that.
     * 
     * @param obj the ObjectInWorld to add to this Area.
     * 
     * @return this, for chaining purposes 
     */
    public Area addToWorld(ObjectInWorld obj){
        if(obj instanceof MobileWorldObject){
            this.stuffThatCanBumpIntoOtherStuff.add((MobileWorldObject)obj);
        }
        if(obj instanceof Collidable){
            this.stuffThatOtherStuffCanBumpInto.add((Collidable)obj);
        }
        if(obj instanceof AbstractMachine){
            this.machines.add((AbstractMachine) obj);
        }
        if(obj instanceof AbstractItem){
            this.items.add((AbstractItem) obj);
        }
        if(obj instanceof Interactable){
            this.interactables.add((Interactable) obj);
        }
        objects.add(obj);
        obj.setArea(this);
        
        return this;
    }
    
    /**
     * Removes the given ObjectInWorld from this Area, if it is present.
     * 
     * @param obj the ObjectInWorld to remove from this Area.
     * 
     * @return this, for chaining purposes. 
     */
    public Area removeFromWorld(ObjectInWorld obj){
        if(obj instanceof MobileWorldObject){
            this.stuffThatCanBumpIntoOtherStuff.delete((MobileWorldObject)obj);
        }
        if(obj instanceof Collidable){
            this.stuffThatOtherStuffCanBumpInto.delete((Collidable)obj);
        }
        if(obj instanceof AbstractMachine){
            this.machines.delete((AbstractMachine) obj);
        }
        if(obj instanceof AbstractItem){
            this.items.delete((AbstractItem) obj);
        }
        if(obj instanceof Interactable){
            this.interactables.delete((Interactable) obj);
        }
        objects.remove(obj);
        return this;
    }
    
    /**
     * Use this method if you need to check for open tiles or other static
     * environment information.
     * 
     * @return this Area's TileMap. 
     */
    public final TileMap getTileMap(){
        return tileMap;
    }
    
    /**
     * DNGN
     * 
     * @return this, for chaining purposes
     */
    public Area init(){
        //entities.forEach((e)->e.init());
        return this;
    }
    
    /**
     * Notifies all the interactable objects in the Area that a Player has
     * interacted, causing them to act as appropriate.
     * 
     * @param p the Player who interacted with this Area
     */
    public final void playerInteracted(Player p){
        interactables.forEach((i)->i.interactWith(p));
    }
    
    /**
     * Updates this Area and everything within it. This method
     * is invoked once per frame by Level.
     * 
     * @return this, for chaining purposes. 
     */
    public Area update(){
        powerGrid.update(machines);
        this.stuffThatCanBumpIntoOtherStuff.forEach((e)->{
            if(e instanceof AbstractEntity){
                ((AbstractEntity)e).update();
            }
            tileMap.checkForCollisions(e); 
            this.stuffThatOtherStuffCanBumpInto.forEach((c)->{
                if(!c.equals(e)){ // don't collide with self
                    c.checkForCollisions(e);
                }
            });
        });
        machines.forEach((m)->{
            if(m.isPowered()){
                m.update();
            }
        });
        items.forEach((i)->{
            //i.update(); Don't currently have an update method
        });
        return this;
    }
    
    /**
     * Renders this Area and everything within it.
     * 
     * @param g the Graphics to render this on.
     * 
     * @return this, for chaining purposes.
     */
    public Area draw(Graphics g){
        tileMap.draw(g);
        machines.forEach((m)->{
            m.draw(g);
        });
        items.forEach((item)->{
            item.draw(g);
        });
        powerGrid.draw(g);
        
        return this;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("tileMap", tileMap.toJson());
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        objects.forEach((e)->arrayBuilder.add(e.toJson()));
        builder.add("objects", arrayBuilder.build());
        
        return builder.build();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TODO: better Area::toString\n");
        sb.append("Tile Map:\n");
        sb.append(tileMap.toString());
        return sb.toString();
    }
}
