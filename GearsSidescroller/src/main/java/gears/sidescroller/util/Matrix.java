package gears.sidescroller.util;

import gears.io.StreamWriterUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * The Matrix class is used to store a 2D array of values.
 * It is extended by TileMap and Level, as they share similar functionality.
 * 
 * @author Matt Crow
 * @param <T> the type of value each Matrix cell encodes
 * @see gears.sidescroller.world.tileMaps.TileMap
 * @see gears.sidescroller.world.levels.Level
 */
public class Matrix<T> {
    private final HashMap<Integer, T> keyToValue;
    private final int width;
    private final int height;
    private final int[][] map;
    
    public Matrix(int width, int height){
        keyToValue = new HashMap<>();
        this.width = width;
        this.height = height;
        this.map = new int[height][width];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                this.map[y][x] = 0;
            }
        }
    }
    
    public final int getWidthInCells(){
        return width;
    }
    
    public final int getHeightInCells(){
        return height;
    }
    
    public final void setKeyToVal(int key, T val){
        if(key < 0){
            throw new IllegalArgumentException(String.format("Key must be non-negative, so %d isn't allowed", key));
        }
        keyToValue.put(key, val);
    }
    
    public final Matrix set(int xIdx, int yIdx, int val){
        if(!isValidIdx(xIdx, yIdx)){
            throw new IndexOutOfBoundsException();
        }
        this.map[yIdx][xIdx] = val;
        return this;
    }
    
    public final T get(int xIdx, int yIdx){
        if(!isValidIdx(xIdx, yIdx)){
            throw new IndexOutOfBoundsException();
        }
        if(!keyToValue.containsKey(map[yIdx][xIdx])){
            throw new NullPointerException(String.format("Matrix does not contain the key %d", map[yIdx][xIdx]));
        }
        return keyToValue.get(map[yIdx][xIdx]);
    }
        
    public final boolean isValidIdx(int xIdx, int yIdx){
        return xIdx >= 0 && xIdx < this.width && yIdx >= 0 && yIdx < this.height;
    }
    
    public final void forEachKeyToValue(BiConsumer<Integer, T> doThis){
        this.keyToValue.forEach(doThis);
    }
    
    public final void forEachCell(TriConsumer<T, Integer, Integer> doThis){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                doThis.accept(get(x, y), x, y);
            }
        }
    }
    
    /**
     * Gets this as CSV. Note that this does not include
     * this' key to value mapping.
     * 
     * @return this in CSV format, ready to write to a file. 
     */
    public final String getAsCsv(){
        StringBuilder b = new StringBuilder();
        String[] row;
        for(byte y = 0; y < height; y++){
            row = new String[width];
            for(byte x = 0; x < width; x++){
                row[x] = Integer.toString(map[y][x]);
            }
            b.append(String.join(", ", row)).append(StreamWriterUtil.NEWLINE);
        }
        return b.toString();
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
        for(int y = 0; y < height; y++){
            sb.append(String.join(", ", Arrays.stream(map[y]).mapToObj((intVal)->{
                return String.format(keyFormat, intVal);
            }).toArray((size)->new String[size]))).append("\n");
        }
        return sb.toString();
    }
    
    public static void main(String[] args){
        Matrix<String> m = new Matrix<>(5, 5);
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
