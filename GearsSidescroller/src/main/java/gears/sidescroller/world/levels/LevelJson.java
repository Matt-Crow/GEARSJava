package gears.sidescroller.world.levels;

import gears.sidescroller.loader.JsonResourceType;
import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.areas.AreaJson;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class LevelJson implements JsonResourceType<Level> {

    @Override
    public Level deserialize(JsonObject json) {
        JsonArray areaJsonMatrix = json.getJsonArray("areas");
        JsonArray row = areaJsonMatrix.getJsonArray(0);
        int rows = areaJsonMatrix.size();
        int cols = row.size();
        Area[][] areas = new Area[rows][cols];
        AreaJson areaDeser = new AreaJson();
        
        for(int y = 0; y < rows; ++y){
            row = areaJsonMatrix.getJsonArray(y);
            for(int x = 0; x < cols; ++x){
                areas[y][x] = areaDeser.deserialize(row.getJsonObject(x));
            }
        }
        
        Level level = new Level(areas, json.getInt("startAreaX"), json.getInt("startAreaY"));
        return level;
    }
}
