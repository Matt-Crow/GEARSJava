package gears.sidescroller.world.tileMaps;

import gears.sidescroller.world.tiles.TileGenerator;
import java.util.function.BiFunction;

/**
 * Use this class to randomly generate tile maps.
 * 
 * Currently, subsequent invocations of generateRandom will produce the same
 * shape of TileMap, though the colors will be different
 * 
 * @author Matt Crow
 */
public class TileMapGenerator {
    private final TileGenerator tileGenerator;
    private final BiFunction<Integer, Integer, Integer> function;
    
    /**
     * @param tileGenerator the TileGenerator to use in randomly generating tile designs
     * @param function a function which maps x- and y-coordinates in index-space to keys in the FlyweightMatrix
     * of tile designs in the Areas this generates. Note that the output of this function is automatically 
     * capped at 0 at the minimum. Level curves produced by this function will each contain a single tile type.
     */
    public TileMapGenerator(TileGenerator tileGenerator, BiFunction<Integer, Integer, Integer> function){
        this.tileGenerator = tileGenerator;
        this.function = function;
    }
    
    public TileMapGenerator(TileGenerator tileGenerator, int cosAmplitude, int sinAmplitude, int cosPeriod, int sinPeriod){
        this.tileGenerator = tileGenerator;
        this.function = (x, y)->{
            return (int)(cosAmplitude * Math.cos(x / cosPeriod) + sinAmplitude * Math.sin(y / sinPeriod));
        };
    }
    
    public TileMapGenerator(int cosAmplitude, int sinAmplitude, int cosPeriod, int sinPeriod){
        this(new TileGenerator(), cosAmplitude, sinAmplitude, cosPeriod, sinPeriod);
    }
    
    public TileMapGenerator(){
        this(5, 5, 5, 5);
    }
    
    public TileMap generateTileMap(int w, int h){
        TileMap ret = new TileMap(w, h);
        
        ret.addToTileSet(0, tileGenerator.generateRandom(false)); 
        // should be able to walk on 0
        // since 0 will be the most common tile
        
        /*
        need to add a random value to the inputs of function, as otherwise the
        shape of the tilemap generated is not random.
        */
        int r;
        
        int newKey = -1;
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                r = (int) Math.floor(Math.random() * 10);
                newKey = Math.max(0, function.apply(i + r, j + r));
                if(!ret.isKeySet(newKey)){
                    // only add to the tile set if a new key appears
                    ret.addToTileSet(newKey, tileGenerator.generateRandom(true));
                }
                ret.setTile(i, j, newKey);
            }
        }
        
        return ret;
    }
    
    public static void main(String[] args){
        TileMapGenerator gen = new TileMapGenerator(5, 5, 1, 1);
        
        TileMap map;
        for(int i = 0; i < 20; i++){
            map = gen.generateTileMap(20, 20);
            System.out.println(map.getAsCsv());
        }
    }
}
