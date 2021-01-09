package gears.sidescroller.world.levels;

import gears.sidescroller.world.areas.AreaGenerator;
import gears.sidescroller.world.areas.Area;

/**
 * The LevelGenerator class is used to create random Levels.
 * 
 * @author Matt Crow
 */
public class LevelGenerator {
    private final AreaGenerator areaGenerator;
    
    /**
     * 
     * @param areaGenerator the AreaGenerator which will
     * generate random Areas for the Levels this creates.
     */
    public LevelGenerator(AreaGenerator areaGenerator){
        this.areaGenerator = areaGenerator;
    }
    
    /**
     * Creates a new LevelGenerator 
     * with its own built-in AreaGenerator.
     */
    public LevelGenerator(){
        this(new AreaGenerator());
    }
    
    /**
     * Generates a new Level, producing Areas for it
     * using this LevelGenerator's AreaGenerator.
     * 
     * @param levelSize the width and height of the Levels
     * this will generate, measured in Areas.
     * 
     * @return the newly generated Level. 
     */
    public final Level generateRandom(int levelSize){
        Area[][] areaList = new Area[levelSize][levelSize];
        for(int i = 0; i < levelSize; i++){
            for(int j = 0; j < levelSize; j++){
                areaList[i][j] = areaGenerator.generateRandom();
            }
        }
        return new Level(areaList, levelSize / 2, levelSize / 2);
    }
}
