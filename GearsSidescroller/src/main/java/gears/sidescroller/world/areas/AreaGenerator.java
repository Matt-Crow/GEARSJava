package gears.sidescroller.world.areas;

import gears.sidescroller.world.items.AbstractItem;
import gears.sidescroller.world.items.ItemGenerator;
import gears.sidescroller.world.machines.MachineGenerator;
import gears.sidescroller.world.structures.Structure;
import gears.sidescroller.world.structures.StructureGenerator;
import gears.sidescroller.world.tileMaps.TileMap;
import gears.sidescroller.world.tileMaps.TileMapGenerator;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Point;
import java.util.Random;

/**
 *
 * @author Matt
 */
public class AreaGenerator {
    private final ItemGenerator itemGenerator;
    private final MachineGenerator machineGenerator;
    
    public AreaGenerator(ItemGenerator itemGenerator, MachineGenerator machineGenerator){
        this.itemGenerator = itemGenerator;
        this.machineGenerator = machineGenerator;
    }
    public AreaGenerator(){
        this(new ItemGenerator(), new MachineGenerator());
    }
    
    public final Area generateRandom(){
        Random rng = new Random();
        int max = 10;
        Point openTile = null;
        
        TileMap map = new TileMapGenerator(
            rng.nextInt(max - 1) + 1, // from 1-max 
            rng.nextInt(max - 1) + 1, 
            rng.nextInt(max - 1) + 1, 
            rng.nextInt(max - 1) + 1
        ).generateTileMap((byte)20, (byte)20);
        
        // choose number of structures
        int numStructs = rng.nextInt(3);
        for(int i = 0; i < numStructs; i++){
            Structure newStruct = new StructureGenerator(10, 10).generateRoom();
            map.insertMatrix(
                rng.nextInt(map.getWidthInCells() - newStruct.getWidthInCells()), 
                rng.nextInt(map.getHeightInCells() - newStruct.getHeightInCells()), 
                newStruct
            );
        }
        
        Area ret = new Area(map);
        
        // choose number of items
        int numItems = rng.nextInt(5);
        AbstractItem newItem = null;
        for(int i = 0; i < numItems; i++){
            openTile = map.searchForOpenTileAround(
                rng.nextInt(map.getWidthInCells()), 
                rng.nextInt(map.getHeightInCells())
            );
            if(openTile != null){
                newItem = itemGenerator.generateRandomAt(
                    (int)(openTile.getX()*TILE_SIZE), 
                    (int)(openTile.getY()*TILE_SIZE)
                );
                ret.addToWorld(newItem);
            }
        }
        
        // generate machines
        int numMachines = rng.nextInt(5);
        for(int i = 0; i < numMachines; i++){
            openTile = map.searchForOpenTileAround(
                rng.nextInt(map.getWidthInCells()), 
                rng.nextInt(map.getHeightInCells())
            );
            if(openTile != null){
                machineGenerator.createRandomMachineAt(ret, (int) openTile.getX(), (int) openTile.getY()).forEach(ret::addToWorld);
            }
        }
        
        return ret;
    }
}
