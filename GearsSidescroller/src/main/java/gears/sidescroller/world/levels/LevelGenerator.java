package gears.sidescroller.world.levels;

import gears.sidescroller.world.levels.Level;
import gears.sidescroller.world.areas.AreaGenerator;
import gears.sidescroller.world.areas.Area;

/**
 *
 * @author Matt
 */
public class LevelGenerator {
    public final Level generateRandom(int numAreas){
        Area[] areaList = new Area[numAreas];
        for(int i = 0; i < numAreas; i++){
            areaList[i] = new AreaGenerator().generateRandom();
        }
        return new Level(areaList, 0);
    }
}
