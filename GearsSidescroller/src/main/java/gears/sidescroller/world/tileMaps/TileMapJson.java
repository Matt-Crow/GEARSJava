package gears.sidescroller.world.tileMaps;

import gears.sidescroller.loader.JsonResourceType;
import gears.sidescroller.world.tiles.TileJson;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author Matt Crow 
 */
public class TileMapJson implements JsonResourceType<TileMap> {

    @Override
    public TileMap deserialize(JsonObject json) {
        JsonArray keys = json.getJsonArray("keys");
        
        JsonArray map = json.getJsonArray("map");
        JsonArray row = map.getJsonArray(0);
        int h = map.size();
        int w = row.size();
        
        TileMap tileMap = new TileMap(w, h);
        
        TileJson tileDeser = new TileJson();
        int numKeys = keys.size();
        JsonObject kv;
        for(int i = 0; i < numKeys; ++i){
            kv = keys.getJsonObject(i);
            tileMap.addToTileSet(kv.getInt("key"), 
                    tileDeser.deserialize(kv.getJsonObject("value"))
            );
        }
        
        for(int y = 0; y < h; ++y){
            row = map.getJsonArray(y);
            for(int x = 0; x < w; ++x){
                tileMap.setTile(x, y, row.getInt(x));
            }
        }
        
        return tileMap;
    }
}
