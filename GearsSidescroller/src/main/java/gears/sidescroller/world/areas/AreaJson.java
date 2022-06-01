package gears.sidescroller.world.areas;

import gears.sidescroller.loader.JsonResourceType;
import gears.sidescroller.world.core.ObjectInWorldJson;
import gears.sidescroller.world.tileMaps.TileMapJson;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author Matt Crow 
 */
public class AreaJson implements JsonResourceType<Area> {

    @Override
    public Area deserialize(JsonObject json) {
        Area area = new Area(new TileMapJson().deserialize(json.getJsonObject("tileMap")));
        
        JsonArray gameObjJson = json.getJsonArray("objects");
        ObjectInWorldJson objDeser = new ObjectInWorldJson();
        int s = gameObjJson.size();
        for(int i = 0; i < s; ++i){
            area.addToWorld(objDeser.deserialize(gameObjJson.getJsonObject(i)));
        }
        
        return area;
    }
}
