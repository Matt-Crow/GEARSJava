package gears.sidescroller.world.tileMaps;

import gears.sidescroller.world.tileMaps.TileMap;
import gears.sidescroller.world.tiles.TileGenerator;

/**
 * Use this class to randomly
 * generate tile maps.
 * 
 * @author Matt Crow
 */
public class TileMapGenerator {
    private final int cosAmplitude;
    private final int sinAmplitude;
    private final int cosPeriod;
    private final int sinPeriod;
    
    public TileMapGenerator(int cosAmplitude, int sinAmplitude, int cosPeriod, int sinPeriod){
        this.cosAmplitude = cosAmplitude;
        this.sinAmplitude = sinAmplitude;
        this.cosPeriod = cosPeriod;
        this.sinPeriod = sinPeriod;
    }
    
    private byte f(byte x, byte y){
        return (byte)(cosAmplitude * Math.cos(x / cosPeriod) + sinAmplitude * Math.sin(y / sinPeriod));
    }
    
    public TileMap generateTileMap(byte w, byte h){
        TileMap ret = new TileMap(w, h);
        
        // first, generate the tile set
        TileGenerator tileGen = new TileGenerator();
        ret.addToTileSet((byte)0, tileGen.generateRandom(false)); // should be able to walk on 0
        // since 0 will be the most common tile
        int maxZ = cosAmplitude + sinAmplitude; // highest tile index we can have
        for(byte i = 1; i < maxZ + 1; i++){ // skip index 0, as it is already set, but make sure to account for maxZ
            ret.addToTileSet(i, tileGen.generateRandom(true));
        }
        
        // second, set the tile map
        for(byte i = 0; i < w; i++){
            for(byte j = 0; j < h; j++){
                ret.setTile(i, j, (byte)Math.max(0, f(i, j)));
            }
        }
        
        return ret;
    }
    
    public static void main(String[] args){
        TileMapGenerator gen = new TileMapGenerator(5, 5, 1, 1);
        
        TileMap map;
        for(int i = 0; i < 20; i++){
            map = gen.generateTileMap((byte)20, (byte)20);
            System.out.println(map.getAsCsv());
        }
    }
}
