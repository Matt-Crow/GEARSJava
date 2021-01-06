package gears.sidescroller.world.areas;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.dataStructures.VolatileLinkedList;
import gears.sidescroller.world.Interactable;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.AbstractItem;
import gears.sidescroller.world.machines.AbstractMachine;
import gears.sidescroller.world.tileMaps.TileMap;
import java.awt.Graphics;
import java.util.Arrays;

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
    private final VolatileLinkedList<AbstractEntity> entities;
    private final VolatileLinkedList<AbstractMachine> machines;
    private final VolatileLinkedList<AbstractItem> items;
    private final VolatileLinkedList<Interactable> interactables;
    
    public Area(TileMap t){
        tileMap = t;
        powerGrid = new PowerGrid(t.getWidthInCells(), t.getHeightInCells());
        entities = new VolatileLinkedList<>();
        machines = new VolatileLinkedList<>();
        items = new VolatileLinkedList<>();
        interactables = new VolatileLinkedList<>();
    }
    
    public Area addEntity(AbstractEntity e){
        entities.add(e);
        e.setArea(this);
        return this;
    }
    
    public Area addMachine(AbstractMachine m){
        machines.add(m);
        m.setArea(this);
        return this;
    }
    
    public Area addItem(AbstractItem i){
        items.add(i);
        i.setArea(this);
        return this;
    }
    
    public Area addInteractable(Interactable i){
        interactables.add(i);
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
        powerGrid.clear();
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
        entities.forEach((e)->{
            e.update();
            tileMap.checkForCollisions(e); 
            machines.forEach((machine)->{
                machine.checkForCollisions(e);
            });
            items.forEach((item)->{
                item.checkForCollisions(e);
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
