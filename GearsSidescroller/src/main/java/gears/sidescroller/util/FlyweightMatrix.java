package gears.sidescroller.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * The FlyweightMatrix class is used to store a 2D array of values.
 * It is extended by TileMap and Level, as they share similar functionality.
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
        int thisCurrMax = this.keyToValue.keySet().stream().reduce(0, Math::max);
        otherMatrix.forEachKeyToValue((otherMatrixKey, otherMatrixValue)->{
            this.setKeyToVal(thisCurrMax + otherMatrixKey, otherMatrixValue);
        });
        
        // next, fit as much of the new matrix as possible into this one
        for(int dx = 0; dx < otherMatrix.getWidthInCells(); dx++){
            for(int dy = 0; dy < otherMatrix.getHeightInCells(); dy++){
                if(isValidIdx(xIdx + dx, yIdx + dy)){
                    this.set(xIdx + dx, yIdx + dy, otherMatrix.get(dx, dy) + thisCurrMax);
                }
            }
        }
    }
    
    public final void forEachKeyToValue(BiConsumer<Integer, T> doThis){
        this.keyToValue.forEach(doThis);
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
