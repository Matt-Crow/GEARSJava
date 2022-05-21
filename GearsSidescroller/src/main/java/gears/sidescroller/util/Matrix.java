package gears.sidescroller.util;

import gears.io.StreamWriterUtil;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 *
 * @author Matt Crow
 * @param <ElementType> the type of element this will store
 */
public class Matrix<ElementType> {
    private final int width;
    private final int height;
    private final Object[][] map; // can't do generic array
    
    public Matrix(int width, int height){
        this.width = width;
        this.height = height;
        this.map = new Object[height][width];
    }
    
    public final boolean isValidIdx(int xIdx, int yIdx){
        return xIdx >= 0 && xIdx < this.width && yIdx >= 0 && yIdx < this.height;
    }
    
    public final Matrix<ElementType> setContents(Object[][] contents){
        if(!isValidIdx(contents[0].length - 1, contents.length - 1)){
            throw new IndexOutOfBoundsException("Contents are too large");
        }
        for(int yIdx = 0; yIdx < contents.length; yIdx++){
            for(int xIdx = 0; xIdx < contents[yIdx].length; xIdx++){
                this.map[yIdx][xIdx] = contents[yIdx][xIdx];
            }
        }
        return this;
    }
    
    public final Matrix<ElementType> set(int xIdx, int yIdx, ElementType val){
        if(!isValidIdx(xIdx, yIdx)){
            throw new IndexOutOfBoundsException();
        }
        this.map[yIdx][xIdx] = val;
        return this;
    }
    
    public final Matrix<ElementType> setAllTo(ElementType value){
        this.forEachCell((key, xIdx, yIdx)->{
            this.set(xIdx, yIdx, value);
        });
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public final ElementType get(int xIdx, int yIdx){
        if(!isValidIdx(xIdx, yIdx)){
            throw new IndexOutOfBoundsException();
        }
        return (ElementType)map[yIdx][xIdx];
    }
    
    public final int getWidthInCells(){
        return width;
    }
    
    public final int getHeightInCells(){
        return height;
    }
    
    public final void forEachCell(TriConsumer<ElementType, Integer, Integer> doThis){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                doThis.accept(get(x, y), x, y);
            }
        }
    }
    
    public final void forEachRow(BiConsumer<Object[], Integer> doThis){
        for(int y = 0; y < height; y++){
            doThis.accept(map[y], y);
        }
    }
    
    /**
     * note: the returned object is of the form [y][x], not [x][y]!
     * 
     * @param mapper applied to each element of this matrix
     * @return the result of applying the given function to each element in this
     */
    public final Object[][] mapToArray(Function<ElementType, Object> mapper){
        // cannot use generic array :(
        Object[][] result = new Object[width][height];
        for(int y = 0; y < height; ++y){
            for(int x = 0; x < width; ++x){
                result[y][x] = mapper.apply(get(x, y));
            }
        }
        return result;
    }
    
    /**
     * Gets this as CSV
     * 
     * @return this in CSV format, ready to write to a file. 
     */
    public final String getAsCsv(){
        StringBuilder b = new StringBuilder();
        String[] row;
        for(byte y = 0; y < height; y++){
            row = new String[width];
            for(byte x = 0; x < width; x++){
                row[x] = map[y][x].toString();
            }
            b.append(String.join(", ", row)).append(StreamWriterUtil.NEWLINE);
        }
        return b.toString();
    }
}
