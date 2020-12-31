package gears.sidescroller.world.areas;

import gears.sidescroller.entities.AbstractEntity;
import gears.sidescroller.util.dataStructures.VolatileLinkedList;
import gears.sidescroller.world.Machines.AbstractMachine;
import gears.sidescroller.world.Machines.PowerProvidingMachine;
import gears.sidescroller.world.tileMaps.TileMap;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.LinkedList;

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
    
    public Area(TileMap t){
        tileMap = t;
        powerGrid = new PowerGrid(t.getWidthInCells(), t.getHeightInCells());
        entities = new VolatileLinkedList<>();
        machines = new VolatileLinkedList<>();
    }
    
    public Area addEntity(AbstractEntity e){
        entities.add(e);
        return this;
    }
    
    public Area addMachine(AbstractMachine m){
        machines.add(m);
        return this;
    }
    
    public Area init(){
        //entities.values().forEach((e)->e.init());
        return this;
    }
    
    public TileMap getTileMap(){
        return tileMap;
    }
    
    private void updatePowerGrid(){
        powerGrid.clear();
        
        LinkedList<PowerProvidingMachine> firstSet = new LinkedList<>();
        machines.forEach((machine)->{
            if(machine instanceof PowerProvidingMachine){
                firstSet.add((PowerProvidingMachine)machine);
            }
        });
        boolean powerWasProvided = false;
        LinkedList<PowerProvidingMachine> currentSet = firstSet;
        LinkedList<PowerProvidingMachine> nextSet;
        do {
            powerWasProvided = false;
            nextSet = new LinkedList<>();
            for(PowerProvidingMachine machine : currentSet){
                if(machine.isProvidingPower()){
                    powerWasProvided = powerGrid.applyPowerFrom(machine);
                } else {
                    nextSet.add(machine);
                }
            }
            currentSet = nextSet;
        } while(powerWasProvided);
        /*
        keep updating until no new PowerProvidingMachines are powered
        This helps catch cases where a machine earlier in the list is originally unpowered,
        but gets power by a later machine in the list.
        */
    }
    
    public Area update(){
        updatePowerGrid();
        entities.forEach((e)->{
            e.update();
            tileMap.checkForCollisions(e); // need to add checking for machines
        });
        machines.forEach((m)->{
            m.update();
        });
        return this;
    }
    
    public Area draw(Graphics g){
        boolean debug = true; // move this
        tileMap.draw(g);
        machines.forEach((m)->{
            m.draw(g);
        });
        if(debug){
            powerGrid.draw(g);
        }
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
