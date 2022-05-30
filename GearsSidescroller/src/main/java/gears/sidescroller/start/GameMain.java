package gears.sidescroller.start;

import gears.sidescroller.gui.*;
import gears.sidescroller.loader.*;
import gears.sidescroller.world.areas.AreaGenerator;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.ItemGenerator;
import gears.sidescroller.world.levels.LevelGenerator;
import gears.sidescroller.world.machines.MachineGenerator;
import gears.sidescroller.world.structures.*;
import gears.sidescroller.world.tileMaps.TileMapGenerator;
import gears.sidescroller.world.tiles.TileGenerator;
import java.util.*;

/**
 *
 * @author Matt
 */
public class GameMain {

    public static void main(String[] args) {
        GameFrame window = new GameFrame();
        Player player = new Player();
        LevelLoader levelLoader = new FileSystemLevelLoader();
        
        Random rng = new Random();
        TileMapGenerator tileMapGen = new TileMapGenerator(
                rng.nextInt(9) + 1,
                rng.nextInt(9) + 1,
                rng.nextInt(9) + 1,
                rng.nextInt(9) + 1
        );
        TileGenerator tileGen = new TileGenerator();
        List<GeneratesStructures> genStructs = new ArrayList<>();
        genStructs.add(new RoomGenerator(10, 10, tileGen));
        genStructs.add(new PassageGenerator(10, tileGen));
        genStructs.add(new GearRoomGenerator(tileGen));
        StructureGenerator structGen = new StructureGenerator(rng, genStructs);
        AreaGenerator areaGen = new AreaGenerator(
                tileMapGen, 
                structGen, 
                new ItemGenerator(), 
                new MachineGenerator()
        );
        LevelGenerator levelGen = new LevelGenerator(areaGen);
        
        PageController ctrl = new PageController(window, player, levelLoader, levelGen);
    }
}
