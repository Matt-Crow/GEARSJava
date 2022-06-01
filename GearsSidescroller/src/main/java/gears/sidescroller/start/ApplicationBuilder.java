package gears.sidescroller.start;

import gears.sidescroller.gui.GameFrame;
import gears.sidescroller.gui.PageController;
import gears.sidescroller.loader.FileSystemLevelLoader;
import gears.sidescroller.loader.LevelLoader;
import gears.sidescroller.world.areas.AreaGenerator;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.ItemGenerator;
import gears.sidescroller.world.levels.LevelGenerator;
import gears.sidescroller.world.machines.MachineGenerator;
import gears.sidescroller.world.structures.GeneratesStructures;
import gears.sidescroller.world.structures.StructureGenerator;
import gears.sidescroller.world.tileMaps.TileMapGenerator;
import gears.sidescroller.world.tiles.TileGenerator;
import gears.sidescroller.world.tiles.TileSetGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Matt Crow
 */
public class ApplicationBuilder {

    private final List<GeneratesStructures> structureGenerators = new ArrayList<>();

    public ApplicationBuilder useStructureGenerators(List<GeneratesStructures> structureGenerators) {
        this.structureGenerators.clear();
        this.structureGenerators.addAll(structureGenerators);
        return this;
    }

    public PageController build() {
        if (structureGenerators.isEmpty()) {
            throw new UnsupportedOperationException("must have at least 1 structure generator");
        }

        GameFrame window = new GameFrame();
        Player player = new Player();
        LevelLoader levelLoader = new FileSystemLevelLoader();

        Random rng = new Random();

        // tile services
        TileGenerator tileGen = new TileGenerator();
        TileSetGenerator tileSetGen = new TileSetGenerator(tileGen);
        TileMapGenerator tileMapGen = new TileMapGenerator(
                rng,
                rng.nextInt(9) + 1,
                rng.nextInt(9) + 1,
                rng.nextInt(9) + 1,
                rng.nextInt(9) + 1
        );

        // structure services
        StructureGenerator structGen = new StructureGenerator(rng, structureGenerators);

        AreaGenerator areaGen = new AreaGenerator(
                tileSetGen,
                tileMapGen,
                structGen,
                new ItemGenerator(),
                new MachineGenerator()
        );
        LevelGenerator levelGen = new LevelGenerator(areaGen);

        PageController ctrl = new PageController(window, player, levelLoader, levelGen);

        return ctrl;
    }
}
