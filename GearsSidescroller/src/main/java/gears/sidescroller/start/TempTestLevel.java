package gears.sidescroller.start;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.items.GearItem;
import gears.sidescroller.world.levels.Level;
import gears.sidescroller.world.machines.ConveyorBeltSegment;
import gears.sidescroller.world.machines.GearMachine;
import gears.sidescroller.world.machines.PowerPlant;
import gears.sidescroller.world.tileMaps.TileMap;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import gears.sidescroller.world.tiles.TileGenerator;
import java.awt.Color;

/**
 * Previously used in HomePage to test out game features
 * 
 * @author Matt Crow
 */
public class TempTestLevel {
    public static final Level getTestLevel(){
        TileMap t = new TileMap((byte)10, (byte)10);
        t.addToTileSet((byte)0, new TileGenerator().generateRandom(false));
        t.addToTileSet((byte)1, new TileGenerator().generateRandom(true));
        t.setTile((byte)5, (byte)5, (byte)1);
        PowerPlant gen = new PowerPlant(300, 300);
        ConveyorBeltSegment belt = new ConveyorBeltSegment(600, 300, false, TILE_SIZE / 10, Direction.LEFT);
        GearMachine gear = new GearMachine(700, 400);
        
        Area testArea = new Area(t);
        testArea.addToWorld(gen);
        testArea.addToWorld(belt);
        testArea.addToWorld(gear);
        
        GearItem[] items = new GearItem[]{
            new GearItem(100, 100, Color.CYAN),
            new GearItem(200, 100, Color.CYAN),
            new GearItem(100, 200, Color.CYAN)
        };
        for(int i = 0; i < items.length; i++){
            testArea.addToWorld(items[i]);
        }
        
        Level l = new Level(new Area[][]{{testArea}}, 0, 0);
        return l;
    }
}
