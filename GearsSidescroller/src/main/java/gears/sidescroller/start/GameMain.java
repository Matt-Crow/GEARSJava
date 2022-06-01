package gears.sidescroller.start;

import gears.sidescroller.world.structures.*;
import java.util.*;

/**
 *
 * @author Matt Crow
 */
public class GameMain {

    public static void main(String[] args) {
        List<GeneratesStructures> genStructs = new ArrayList<>();
        genStructs.add(new RoomGenerator(10, 10));
        genStructs.add(new PassageGenerator(10));
        genStructs.add(new GearRoomGenerator());
        
        ApplicationBuilder builder = new ApplicationBuilder();
        builder.useStructureGenerators(genStructs);
        builder.build();
    }
}
