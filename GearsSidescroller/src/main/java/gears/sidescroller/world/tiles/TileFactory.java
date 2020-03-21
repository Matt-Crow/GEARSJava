package gears.sidescroller.world.tiles;

/**
 *
 * @author Matt
 */
public class TileFactory {
    public AbstractTile createIntangibleTile(AbstractTile copyMe, int xIndex, int yIndex){
        return copyMe.copyTo(xIndex, yIndex);
    }
    
    public AbstractTile createTangibleTile(AbstractTile copyMe, int xIndex, int yIndex){
        //I have no idea if this will work
        return (AbstractTile & Collideable)copyMe;
    }
}
