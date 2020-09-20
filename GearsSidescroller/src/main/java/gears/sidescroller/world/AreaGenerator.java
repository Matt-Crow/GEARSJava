package gears.sidescroller.world;

/**
 *
 * @author Matt
 */
public class AreaGenerator {
    public final Area generateRandom(){
        Area ret = new Area(new TileMapGenerator(5, 5, 5, 5).generateTileMap(20, 20));
        
        return ret;
    }
}
