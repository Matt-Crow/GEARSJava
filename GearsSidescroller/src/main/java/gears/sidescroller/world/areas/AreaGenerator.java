package gears.sidescroller.world.areas;

import gears.sidescroller.world.structures.Structure;
import gears.sidescroller.world.structures.StructureGenerator;
import gears.sidescroller.world.tileMaps.TileMap;
import gears.sidescroller.world.tileMaps.TileMapGenerator;
import java.util.Random;

/**
 *
 * @author Matt
 */
public class AreaGenerator {
    public final Area generateRandom(){
        Random rng = new Random();
        int max = 10;
        
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
            //System.out.println(newStruct.getAsCsv());
        }
        
        Area ret = new Area(map);
        
        return ret;
    }
}
