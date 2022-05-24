package gears.sidescroller.world.areas;

import gears.sidescroller.loader.JsonSerializable;
import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.world.core.*;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.AbstractItem;
import gears.sidescroller.world.machines.AbstractMachine;
import gears.sidescroller.world.tileMaps.TileMap;
import java.awt.Graphics;
import java.util.*;
import javax.json.*;

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
    Once I have more behavioral interfaces (Renderable, Interactable, 
    Initializable, etc)  I'll want to categorize by behavior instead of type.
    Using each of these is marginally faster than iterating & filtering the
    objects field, but has little benefit otherwise
    */
    private final Set<MobileWorldObject> stuffThatCanBumpIntoOtherStuff;
    private final Set<Collidable> stuffThatOtherStuffCanBumpInto;
    private final Set<Interactable> interactables;
    private final Set<AbstractMachine> machines;
    private final Set<AbstractItem> items;
    
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
        stuffThatCanBumpIntoOtherStuff = new HashSet<>();
        stuffThatOtherStuffCanBumpInto = new HashSet<>();
        interactables = new HashSet<>();
        machines = new HashSet<>();
        items = new HashSet<>();
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
            stuffThatCanBumpIntoOtherStuff.add((MobileWorldObject)obj);
        }
        if(obj instanceof Collidable){
            stuffThatOtherStuffCanBumpInto.add((Collidable)obj);
        }
        if(obj instanceof AbstractMachine){
            machines.add((AbstractMachine) obj);
        }
        if(obj instanceof AbstractItem){
            items.add((AbstractItem) obj);
        }
        if(obj instanceof Interactable){
            interactables.add((Interactable) obj);
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
            stuffThatCanBumpIntoOtherStuff.remove((MobileWorldObject)obj);
        }
        if(obj instanceof Collidable){
            stuffThatOtherStuffCanBumpInto.remove((Collidable)obj);
        }
        if(obj instanceof AbstractMachine){
            machines.remove((AbstractMachine) obj);
        }
        if(obj instanceof AbstractItem){
            items.remove((AbstractItem) obj);
        }
        if(obj instanceof Interactable){
            interactables.remove((Interactable) obj);
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
        new HashSet<>(stuffThatCanBumpIntoOtherStuff).forEach((e)->{
            if(e instanceof AbstractEntity){
                ((AbstractEntity)e).update();
            }
            tileMap.checkForCollisions(e);
            
            // make a copy of collidables to avoid ConcurrentModificationException
            new HashSet<>(stuffThatOtherStuffCanBumpInto).forEach((c)->{
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
}
