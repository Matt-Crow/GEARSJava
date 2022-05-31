package gears.sidescroller.world.tiles;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * a TileSet is used to store tile designs that can be shared among various game
 * world components
 * 
 * @author Matt Crow
 */
public class TileSet {
    private final HashMap<Integer, AbstractTileTemplate> designs;
    private int nextId;
    
    /**
     * creates an empty TileSet
     */
    public TileSet(){
        designs = new HashMap<>();
        nextId = 0;
    }
    
    
    /**
     * adds the given design to this tile set, then returns the ID assigned to 
     * it.
     * 
     * @param design the design to include in this TileSet
     * @return the design's ID in this TileSet
     */
    public int add(AbstractTileTemplate design){
        int designId = nextId;
        designs.put(designId, design);
        ++nextId;
        return designId;
    }
    
    /**
     * gets the tile design with the given ID in this TileSet. This will throw 
     * an exception if no such tile exists.
     * 
     * @param id the ID associated with the given tile design, such as by the
     *  add() method.
     * @return the tile design associated with the given ID
     */
    public AbstractTileTemplate get(int id){
        if(!designs.containsKey(id)){
            throw new IllegalArgumentException(String.format("no tile designs exist with ID %d", id));
        }
        return designs.get(id);
    }
    
    /**
     * chooses an intangible tile design at random. If no intangible tile 
     * designs are in this TileSet, throws an exception
     * 
     * @param rng the random number generator to use for choosing a design
     * @return an intangible tile in this TileSet
     */
    public AbstractTileTemplate chooseRandomIntangibleTile(Random rng){
        return chooseRandomTileWhere(rng, (e)->!e.getIsTangible(), "TileSet has no intangible tiles");
    }
    
    /**
     * chooses an tangible tile design at random. If no tangible tile designs 
     * are in this TileSet, throws an exception
     * 
     * @param rng the random number generator to use for choosing a design
     * @return a tangible tile in this TileSet
     */
    public AbstractTileTemplate chooseRandomTangibleTile(Random rng){
        return chooseRandomTileWhere(rng, (e)->e.getIsTangible(), "TileSet has no tangible tiles");
    }
    
    private AbstractTileTemplate chooseRandomTileWhere(Random rng, Predicate<AbstractTileTemplate> filter, String errorMessage){
        int id = chooseRandomTileIdWhere(rng, filter, errorMessage);
        return designs.get(id);
    }
    
    private int chooseRandomTileIdWhere(Random rng, Predicate<AbstractTileTemplate> filter, String errorMessage){
        List<Integer> filtered = designs.entrySet().stream()
                .filter((e)->filter.test(e.getValue()))
                .map((e)->e.getKey())
                .collect(Collectors.toList());
        
        if(filtered.isEmpty()){
            throw new RuntimeException(errorMessage);
        }
        
        int id = filtered.get(rng.nextInt(filtered.size()));
        
        return id;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Tile Set:\n");
        for(int i = 0; i < nextId; ++i){
            sb.append(String.format("* %2d: %s\n", i, designs.get(i).toString()));
        } 
        return sb.toString();
    }
}
