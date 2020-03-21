package gears.sidescroller.world;

import gears.io.StreamReaderUtil;
import gears.io.StreamWriterUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Matt
 */
public class TileMapIO {
    /**
     * Writes the tile map of the given TileMap to a csv file. Note that this does not
     * include the TileMap's tile set.
     * 
     * @param csvFile
     * @param tileMap
     * @throws IOException 
     */
    public static void writeTileMapCsv(OutputStream csvFile, TileMap tileMap) throws IOException{
        StreamWriterUtil.writeStream(csvFile, tileMap.getTileMapCsv());
    }
    
    /**
     * Reads an input stream from a CSV file, and converts it to a TileMap.
     * Note that the returned TileMap's tile set has not been set.
     * 
     * @param csvFile the inputstream from a CSV file to read.
     * @return the TileMap encoded in the given CSV file, without its tile set.
     * @throws IOException if any errors occur when reading the given stream.
     */
    public static TileMap readTileMapCsv(InputStream csvFile) throws IOException{
        String csvContent = StreamReaderUtil.readStream(csvFile);
        String[] rows = csvContent.split(StreamReaderUtil.NEWLINE);
        
        //find how large the tile map should be
        int widestRow = 0;
        int currWidth;
        for(String row : rows){
            currWidth = row.split(",").length;
            if(currWidth > widestRow){
                widestRow = currWidth;
            }
        }
        TileMap ret = new TileMap(widestRow, rows.length);
        
        //start setting tiles
        String[] currRow;
        int tileKey;
        for(int y = 0; y < rows.length; y++){
            currRow = rows[y].split(",");
            for(int x = 0; x < currRow.length; x++){
                tileKey = Integer.parseInt(currRow[x]);
                ret.setTile(x, y, tileKey);
            }
        }
        
        return ret;
    }
}
