package gears.sidescroller.world.areas;

import gears.sidescroller.world.core.WorldObject;
import gears.sidescroller.world.items.*;
import gears.sidescroller.world.machines.MachineGenerator;
import gears.sidescroller.world.structures.*;
import gears.sidescroller.world.tileMaps.*;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The AreaGenerator is used to randomly create new Areas. If you have your own
 * custom subclasses of AbstractItem and AbstractMachine, you may have to pass
 * your custom generators to the constructor.
 * 
 * @author Matt Crow
 */
public class AreaGenerator {
    private final TileMapGenerator tileMapGenerator;
    private final StructureGenerator structureGenerator;
    private final ItemGenerator itemGenerator;
    private final MachineGenerator machineGenerator;
    
    /**
     * @param tileMapGenerator the TileMapGenerator which will provide TileMaps
     * for the Areas this produces.
     * @param structureGenerator the StructureGenerator which will provide Structures
     * for the Areas this produces.
     * @param itemGenerator the ItemGenerator which will provide AbstractItems 
     * for the Areas this produces. 
     * @param machineGenerator the MachineGenerator which will provide 
     * AbstractMachines for the Areas this produces.
     */
    public AreaGenerator(TileMapGenerator tileMapGenerator, StructureGenerator structureGenerator, ItemGenerator itemGenerator, MachineGenerator machineGenerator){
        this.tileMapGenerator = tileMapGenerator;
        this.structureGenerator = structureGenerator;
        this.itemGenerator = itemGenerator;
        this.machineGenerator = machineGenerator;
    }
    
    /**
     * Creates a new, randomly generated Area. Subsequent invocations of this
     * method will produce distinct Areas.
     * 
     * @return a new Area. 
     */
    public Area generateRandom(){
        TileMap map = tileMapGenerator.generateTileMap(20, 20);//generateTileMap();
        
        List<Structure> structs = generateStructures(20);
        
        applyStructuresIn(structs, map); // need to modify both map...
        
        Area area = new Area(map);
        
        applyStructuresIn(structs, area); // ... and Area
        generateItemsIn(area);
        generateMachinesIn(area);
        
        return area;
    }
    
    private List<Structure> generateStructures(int areaSize){
        List<Structure> structs = new ArrayList<>();
        Random rng = new Random();
        // choose number of structures
        int numStructs = rng.nextInt(4) + 4;
        
        for(int i = 0; i < numStructs; ++i){
            /*
            this means upper left corner of struct cannot be within 5 tiles of
            the max x & y of the area, so larger structures may get cut off
            */
            structs.add(structureGenerator.generateRandom(
                    rng.nextInt(areaSize - 5) * TILE_SIZE,
                    rng.nextInt(areaSize - 5) * TILE_SIZE
            ));
        }
        
        return structs;
    }
    
    private void applyStructuresIn(List<Structure> structs, TileMap here){
        TileMap map;
        for(Structure newStruct : structs){
            map = newStruct.getTileMap();
            here.insertMatrix(
                    newStruct.getX() / TILE_SIZE,
                    newStruct.getY() / TILE_SIZE,
                    map
            );
        }
    }
    
    private void applyStructuresIn(List<Structure> structs, Area area){
        structs.forEach((struct)->applyStructureIn(struct, area));
    }
    
    private void applyStructureIn(Structure struct, Area area){
        // this ensures the objects don't spawn out of bounds
        Point openTile;
        OpenTileSearch search = new OpenTileSearch();
        TileMap map = area.getTileMap();
        for(WorldObject obj : struct.getWorldObjects()){ 
            openTile = search.searchForOpenTileAround(map, obj.getXIdx(), obj.getYIdx());
            if(openTile != null){
                obj.setXIdx((int) openTile.getX());
                obj.setYIdx((int) openTile.getY());
                area.addToWorld(obj);
            }
        }
    }
    
    private void generateItemsIn(Area a){
        Random rng = new Random();
        Point openTile;
        OpenTileSearch search = new OpenTileSearch();
        TileMap map = a.getTileMap();
        
        // choose number of items
        int numItems = rng.nextInt(5);
        AbstractItem newItem;
        for(int i = 0; i < numItems; i++){
            openTile = search.searchForOpenTileAround(
                map,
                rng.nextInt(map.getWidthInCells()), 
                rng.nextInt(map.getHeightInCells())
            );
            if(openTile != null){
                newItem = itemGenerator.generateRandomAt(
                    (int)(openTile.getX()*TILE_SIZE), 
                    (int)(openTile.getY()*TILE_SIZE)
                );
                a.addToWorld(newItem);
            }
        }
    }
    
    private void generateMachinesIn(Area a){
        Random rng = new Random();
        OpenTileSearch search = new OpenTileSearch();
        Point openTile;
        TileMap map = a.getTileMap();
        
        // generate machines
        int numMachines = rng.nextInt(6) + 4;
        for(int i = 0; i < numMachines; i++){
            openTile = search.searchForOpenTileAround(
                map,
                rng.nextInt(map.getWidthInCells()), 
                rng.nextInt(map.getHeightInCells())
            );
            if(openTile != null){
                machineGenerator.createRandomMachineAt(a, (int) openTile.getX(), (int) openTile.getY()).forEach(a::addToWorld);
            }
        }
    }
}
