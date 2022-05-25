package gears.sidescroller.world.items;

import gears.sidescroller.world.entities.Player;
import gears.sidescroller.util.UnrotatedGearSprite;
import gears.sidescroller.world.machines.PlacedGearMachine;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import javax.json.JsonObjectBuilder;

/**
 * The GearItem is an item which the Player can
 * pick up, then place in their Area as a PlacedGearMachine.
 * 
 * @author Matt Crow
 */
public class GearItem extends AbstractItem {
    private final Color color;
    private final UnrotatedGearSprite sprite;
    
    /**
     * Creates a new GearItem at the given coordinates <b>in pixel-space</b>
     * @param x the x-coordinate of the item
     * @param y the y-coordinate of the item
     * @param color the color of this item as it appears in the Area.
     */
    public GearItem(int x, int y, Color color) {
        super(x, y);
        this.color = color;
        sprite = new UnrotatedGearSprite(color, TILE_SIZE / 2);
    }

    @Override
    public void draw(Graphics g) {
        sprite.draw(g, getX() + TILE_SIZE / 4, getY() + TILE_SIZE / 4);
    }
    
    /**
     * Places this item as a PlacedGearMachine at the player's coordinates.
     * 
     * @param whoUsedItem the Player who used this item.
     * @return whether or not the gear was placed.
     */
    @Override
    public boolean doAction(Player whoUsedItem) {
        PlacedGearMachine thisAsMachine = new PlacedGearMachine(whoUsedItem.getXIdx() * TILE_SIZE, whoUsedItem.getYIdx() * TILE_SIZE, this);
        whoUsedItem.getArea().addToWorld(thisAsMachine);
        whoUsedItem.removeItem(this);
        return true; // gear was placed
    }

    @Override
    protected void attachJsonProperties(JsonObjectBuilder builder) {
        builder.add("color", color.getRGB());
    }

    @Override
    public String getJsonType() {
        return "GearItem";
    }
    
    @Override
    public String toString(){
        return "Gear";
    }
}
