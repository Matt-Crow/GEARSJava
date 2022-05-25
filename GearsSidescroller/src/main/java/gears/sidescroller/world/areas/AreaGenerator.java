package gears.sidescroller.world.areas;

import gears.sidescroller.world.items.AbstractItem;
import gears.sidescroller.world.items.ItemGenerator;
import gears.sidescroller.world.machines.MachineGenerator;
import gears.sidescroller.world.structures.Structure;
import gears.sidescroller.world.structures.StructureGenerator;
import gears.sidescroller.world.tileMaps.OpenTileSearch;
import gears.sidescroller.world.tileMaps.TileMap;
import gears.sidescroller.world.tileMaps.TileMapGenerator;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Point;
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
     * Creates an AreaGenerator with the default generators.
     */
    public AreaGenerator(){
        this(new TileMapGenerator(
            new Random().nextInt(9) + 1, // don't like this, but only way
            new Random().nextInt(9) + 1, // to fit in constructor with "this(...)"
            new Random().nextInt(9) + 1,
            new Random().nextInt(9) + 1
        ), new StructureGenerator(10, 10), new ItemGenerator(), new MachineGenerator());
    }
    
    /**
     * Creates a new, randomly generated Area. Subsequent invocations of this
     * method will produce distinct Areas.
     * 
     * @return a new Area. 
     */
    public Area generateRandom(){
        TileMap map = tileMapGenerator.generateTileMap(20, 20);//generateTileMap();
        
        generateStructuresIn(map);
        
        Area ret = new Area(map);
        
        generateItemsIn(ret);
        generateMachinesIn(ret);
        
        return ret;
    }
    
    private void generateStructuresIn(TileMap here){
        Random rng = new Random();
        // choose number of structures
        int numStructs = rng.nextInt(3) + 1;
        for(int i = 0; i < numStructs; i++){
            Structure newStruct = structureGenerator.generateRandom();
            here.insertMatrix(
                rng.nextInt(here.getWidthInCells() - newStruct.getWidthInCells()), 
                rng.nextInt(here.getHeightInCells() - newStruct.getHeightInCells()), 
                newStruct
            );
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
        int numMachines = rng.nextInt(5);
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
