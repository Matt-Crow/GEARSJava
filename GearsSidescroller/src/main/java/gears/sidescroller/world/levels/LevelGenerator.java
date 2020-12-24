package gears.sidescroller.world.levels;

import gears.sidescroller.world.areas.AreaGenerator;
import gears.sidescroller.world.areas.Area;

/**
 *
 * @author Matt
 */
public class LevelGenerator {
    public final Level generateRandom(int levelSize){
        Area[][] areaList = new Area[levelSize][levelSize];
        AreaGenerator gen = new AreaGenerator();
        for(int i = 0; i < levelSize; i++){
            for(int j = 0; j < levelSize; j++){
                areaList[i][j] = gen.generateRandom();
            }
        }
        return new Level(areaList, levelSize / 2, levelSize / 2);
    }
}
