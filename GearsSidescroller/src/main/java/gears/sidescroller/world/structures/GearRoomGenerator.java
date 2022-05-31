package gears.sidescroller.world.structures;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.core.WorldObject;
import gears.sidescroller.world.items.GearItem;
import gears.sidescroller.world.machines.GearMachine;
import gears.sidescroller.world.machines.PowerPlant;
import gears.sidescroller.world.tileMaps.TileMap;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import gears.sidescroller.world.tiles.TileSet;
import java.awt.Color;
import java.util.*;

/**
 * generates structures that can teach the player how gears and power work
 * 
 * @author Matt Crow 
 */
public class GearRoomGenerator implements GeneratesStructures { 
    @Override
    public Structure generate(Random rng, int x, int y, TileSet tiles) {
        int size = 7;
        int halfAcrossRoom = TILE_SIZE * (size / 2);
        int centerX = x + halfAcrossRoom;
        int centerY = y + halfAcrossRoom;
        
        TileMap map = new TileMap(size, size);
        map.addToTileSet(0, tiles.chooseRandomIntangibleTile(rng));
        map.addToTileSet(1, tiles.chooseRandomTangibleTile(rng));
        map.setAllTo(0);
        for(int i = 0; i < size; ++i){
            if(i != size / 2){
                map.set(i, 0, 1);
                map.set(i, size - 1, 1);
                map.set(0, i, 1);
                map.set(size - 1, i, 1);
            }
        }
        
        Direction[] dirs = Direction.getCardinalDirections();
        Direction dir = dirs[rng.nextInt(dirs.length)];
        Set<WorldObject> objs = new HashSet<>();
        
        // a power plant on one side of the room...
        PowerPlant power = new PowerPlant(
                centerX - halfAcrossRoom * dir.getXMod(),
                centerY - halfAcrossRoom * dir.getYMod()
        );
        objs.add(power);
        
        // and a gear on the other...
        objs.add(new GearMachine(
                centerX + halfAcrossRoom * dir.getXMod(),
                centerY + halfAcrossRoom * dir.getYMod()
        ));
        
        objs.add(new GearMachine(
                power.getXAsInt() + dir.getXMod() * power.getPowerAreaRadiusInTiles() * TILE_SIZE,
                power.getYAsInt() + dir.getYMod() * power.getPowerAreaRadiusInTiles() * TILE_SIZE
        ));
        objs.add(new GearMachine(
                power.getXAsInt() + dir.getXMod() * (power.getPowerAreaRadiusInTiles() + 1) * TILE_SIZE,
                power.getYAsInt() + dir.getYMod() * (power.getPowerAreaRadiusInTiles() + 1) * TILE_SIZE
        ));
        
        objs.add(new GearItem( // purposely swapping x & y mods
                centerX + halfAcrossRoom * dir.getYMod(),
                centerY + halfAcrossRoom * dir.getXMod(),
                new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256))
        ));
        // hmm... maybe they can use this gear to power the machine
        
        return new Structure(x, y, map, objs);
    }
}
