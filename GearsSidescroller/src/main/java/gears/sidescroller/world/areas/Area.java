package gears.sidescroller.world.areas;

import gears.sidescroller.entities.AbstractEntity;
import gears.sidescroller.util.dataStructures.VolatileLinkedList;
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
    private final VolatileLinkedList<AbstractEntity> entities;
    
    public Area(TileMap t){
        tileMap = t;
        powerGrid = new PowerGrid(t.getWidthInCells(), t.getHeightInCells());
        entities = new VolatileLinkedList<>();        
    }
    
    public Area addEntity(AbstractEntity e){
        entities.add(e);
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
        // TODO: set tiles in power grid based on power-supplying machines
        powerGrid.clear();
        
        // temporary, for testing purposes
        powerGrid.forEachCell((b, xIdx, yIdx)->{
            if((xIdx + yIdx) % 2 == 0){
                powerGrid.set(xIdx, yIdx, 1);
            }
        });
    }
    
    public Area update(){
        updatePowerGrid();
        entities.forEach((e)->{
            e.update();
            tileMap.checkForCollisions(e);
        });
        return this;
    }
    
    public Area draw(Graphics g){
        boolean debug = true; // move this
        tileMap.draw(g);
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
