package gears.sidescroller.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * The FlyweightMatrix class is used to store a 2D array of values while conserving
 * space in memory. It uses the Flyweight design pattern by mapping Integer keys
 * to single instances of each type of Object it can hold. Literally speaking,
 * this is a matrix of integers, but functionally speaking, you can use it as though
 * it were a matrix of T.
 * 
 * <b>Important note: the get(x, y) method gets the INTEGER key at the given coordinate,
 * while getValueAt(x, y) returns the VALUE at that point. Additionally, forEachCell(...) 
 * passes the INTEGER key, whereas forEachValueInCell(...) passes the VALUE</b>
 * 
 * @author Matt Crow
 * @param <T> the type of value each FlyweightMatrix cell encodes
 * @see gears.sidescroller.world.tileMaps.TileMap
 * @see gears.sidescroller.world.levels.Level
 */
public class FlyweightMatrix<T> extends Matrix<Integer> {
    private final HashMap<Integer, T> keyToValue;
    
    public FlyweightMatrix(int width, int height){
        super(width, height);
        keyToValue = new HashMap<>();
        clear();
    }
    
    public final void clear(){
        this.setAllTo(0);
    }
    
    public final void setKeyToVal(int key, T val){
        if(key < 0){
            throw new IllegalArgumentException(String.format("Key must be non-negative, so %d isn't allowed", key));
        }
        keyToValue.put(key, val);
    }
    public final boolean isKeySet(int key){
        return keyToValue.containsKey(key);
    }
    
    public final T getValueAt(int xIdx, int yIdx){
        int key = get(xIdx, yIdx);
        if(!keyToValue.containsKey(key)){
            throw new NullPointerException(String.format("Matrix does not contain the key %d", key));
        }
        return keyToValue.get(key);
    }
    
    public final void forEachValueInCell(TriConsumer<T, Integer, Integer> doThis){
        this.forEachCell((key, xIdx, yIdx)->{
            doThis.accept(keyToValue.get(key), xIdx, yIdx);
        });
    }
    
    public final void insertMatrix(int xIdx, int yIdx, FlyweightMatrix<T> otherMatrix){
        // first, add new keys
        int nextAvailableKey = this.keyToValue.keySet().stream().reduce(0, Math::max) + 1;
        otherMatrix.forEachKeyToValue((otherMatrixKey, otherMatrixValue)->{
            this.setKeyToVal(nextAvailableKey + otherMatrixKey, otherMatrixValue);
            //                                  starts at 0
        });
        
        // next, fit as much of the new matrix as possible into this one
        for(int dx = 0; dx < otherMatrix.getWidthInCells(); dx++){
            for(int dy = 0; dy < otherMatrix.getHeightInCells(); dy++){
                if(isValidIdx(xIdx + dx, yIdx + dy)){
                    this.set(xIdx + dx, yIdx + dy, otherMatrix.get(dx, dy) + nextAvailableKey);
                }
            }
        }
    }
    
    public final void forEachKeyToValue(BiConsumer<Integer, T> doThis){
        this.keyToValue.forEach(doThis);
    }
    
    /**
     * @return a shallow copy of this object's key-to-value map 
     */
    public HashMap<Integer, T> copyKeyToValue(){
        HashMap<Integer, T> kToV = new HashMap<>();
        forEachKeyToValue((k, v)->kToV.put(k, v));
        return kToV;
    }
    
    @Override
    public String toString(){
        int widestKey = keyToValue.keySet().stream().reduce(0, (oldVal, newVal)->{
            return Math.max(oldVal, (int)Math.ceil(Math.log10(newVal + 1)));
        });
        
        String keyFormat = "%0" + Integer.toString(widestKey) + "d";
        StringBuilder sb = new StringBuilder();
        sb.append("Key to Value:\n");
        
        String format = keyFormat + " => %s\n";
        keyToValue.forEach((k, v)->{
            sb.append(String.format(format, k, v.toString()));
        });
        sb.append("Map:\n");
        forEachRow((Object[] row, Integer y)->{
            sb.append(String.join(", ", Arrays.stream(row).filter((obj)->obj instanceof Integer).map((obj)->{
                return String.format(keyFormat, (Integer)obj);
            }).toArray((size)->new String[size]))).append("\n");
        });
        return sb.toString();
    }
    
    public static void main(String[] args){
        FlyweightMatrix<String> m = new FlyweightMatrix<>(5, 5);
        m.setKeyToVal(0, "_");
        m.setKeyToVal(1, "hello");
        m.setKeyToVal(2, "world!");
        //m.setKeyToVal(999, "unused");
        m
            .set(0, 0, 1)
            .set(1, 1, 1)
            .set(1, 0, 2)
            .set(0, 1, 2);
        
        System.out.println(m);
    }
}
