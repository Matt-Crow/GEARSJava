package gears.sidescroller.world.areas;

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

/**
 * this will be used to store a tileMap,
 * list of Entities,
 * and Machines.
 * 
 * @author Matt Crow
 */
public class Area {
    private final TileMap tileMap;
    private final PowerGrid powerGrid;
    private final VolatileLinkedList<MobileWorldObject> stuffThatCanBumpIntoOtherStuff;
    private final VolatileLinkedList<Collidable> stuffThatOtherStuffCanBumpInto;
    
    private final VolatileLinkedList<AbstractMachine> machines;
    private final VolatileLinkedList<AbstractItem> items;
    private final VolatileLinkedList<Interactable> interactables;
    
    public Area(TileMap t){
        tileMap = t;
        powerGrid = new PowerGrid(t.getWidthInCells(), t.getHeightInCells());
        stuffThatCanBumpIntoOtherStuff = new VolatileLinkedList<>();
        stuffThatOtherStuffCanBumpInto = new VolatileLinkedList<>();
        
        machines = new VolatileLinkedList<>();
        items = new VolatileLinkedList<>();
        interactables = new VolatileLinkedList<>();
    }
    
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
        obj.setArea(this);
        
        return this;
    }
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
        return this;
    }
    
    public final void playerInteracted(Player p){
        interactables.forEach((i)->i.interactWith(p));
    }
    
    public Area init(){
        //entities.forEach((e)->e.init());
        return this;
    }
    
    public TileMap getTileMap(){
        return tileMap;
    }
    
    private void updatePowerGrid(){
        powerGrid.setAllTo(false);
        boolean powerWasProvided = false;
        AbstractMachine asMachine = null;
        do {
            powerWasProvided = false;
            for(Object machine : machines.toArray()){
                asMachine = (AbstractMachine)machine;
                asMachine.setExternallyPowered(powerGrid.get(asMachine.getXIdx(), asMachine.getYIdx()));
                if(asMachine.isPowered()){
                    powerWasProvided |= powerGrid.applyPowerFrom(asMachine); // "Or equals" prevents true from being replaced with false
                }
            }
        } while(powerWasProvided);
        /*
        keep updating until no new PowerProvidingMachines are powered
        This helps catch cases where a machine earlier in the list is originally unpowered,
        but gets power by a later machine in the list.
        */
        
        // lastly, set all Machine's powered status
        machines.forEach((machine)->{
            machine.setExternallyPowered(powerGrid.get(machine.getXIdx(), machine.getYIdx()));
        });
    }
    
    public Area update(){
        updatePowerGrid();
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
            /*
            machines.forEach((machine)->{
                machine.checkForCollisions(e);
            });
            items.forEach((item)->{
                item.checkForCollisions(e);
            });*/
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
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TODO: better Area::toString\n");
        sb.append("Tile Map:\n");
        sb.append(tileMap.toString());
        return sb.toString();
    }
}
