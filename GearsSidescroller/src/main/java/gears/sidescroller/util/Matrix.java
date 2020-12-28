package gears.sidescroller.util;

import java.util.Arrays;
import java.util.HashMap;

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
    
    public final void setKeyToVal(int key, T val){
        if(key < 0){
            throw new IllegalArgumentException(String.format("Key must be non-negative, so %d isn't allowed", key));
        }
        keyToValue.put(key, val);
    }
    
    public final Matrix set(int x, int y, int val){
        this.map[y][x] = val;
        return this;
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
