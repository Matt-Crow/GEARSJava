package gears.sidescroller.world.tileMaps;

import gears.sidescroller.world.tileMaps.TileMap;
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
        StreamWriterUtil.writeStream(csvFile, tileMap.getAsCsv());
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
        byte widestRow = 0;
        byte currWidth;
        for(String row : rows){
            currWidth = (byte)row.split(",").length;
            if(currWidth > widestRow){
                widestRow = currWidth;
            }
        }
        TileMap ret = new TileMap(widestRow, (byte)rows.length);
        
        //start setting tiles
        String[] currRow;
        byte tileKey;
        for(byte y = 0; y < rows.length; y++){
            currRow = rows[y].split(",");
            for(byte x = 0; x < currRow.length; x++){
                tileKey = Byte.parseByte(currRow[x]);
                ret.setTile(x, y, tileKey);
            }
        }
        
        return ret;
    }
}
