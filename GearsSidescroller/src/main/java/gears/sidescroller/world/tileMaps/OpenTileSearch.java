package gears.sidescroller.world.tileMaps;

import gears.sidescroller.util.Direction;
import java.awt.Point;

/**
 * The OpenTileSearch class is used to store algorithms used to find valid 
 * coordinates in a TileMap. Note that you must instantiate this to use these
 * methods, as they are not static.
 * 
 * @author Matt Crow
 */
public class OpenTileSearch {
    
    /**
     * Searches for an intangible tile index around the given tile map coordinates.
     * 
     * @param map the TileMap to search for an open tile in
     * @param initialXIdx the tile x index to check for intangible tiles around
     * @param initialYIdx the tile y index to check fro intangible tiles around
     * 
     * @return the indeces of an intangible tile around the given tile, or null if no open tiles exist
     */
    public final Point searchForOpenTileAround(TileMap map, int initialXIdx, int initialYIdx){
        Point ret = null;
        int xIdx = initialXIdx;
        int yIdx = initialYIdx;
        if(map.isTileOpen(xIdx, yIdx)){
            ret = new Point(xIdx, yIdx);
        }        
        Direction spiralDir = Direction.UP;
        int spiralLength = 1;
        int spiralLengthThusFar = 0;
        int numTilesChecked = 0;
        boolean justTurned = true;
        int totalCellsToCheck = map.getHeightInCells() * map.getWidthInCells();
        while(ret == null && numTilesChecked < totalCellsToCheck){
            if(map.isValidIdx(xIdx, yIdx)){
                numTilesChecked++; // doesn't run if checking a point outside the map
            }
            // search in a spiralling pattern
            xIdx += spiralDir.getXMod();
            yIdx += spiralDir.getYMod();
            spiralLengthThusFar++;
            if(spiralLengthThusFar >= spiralLength){ // time to turn
                spiralDir = Direction.rotateCounterClockWise(spiralDir);
                spiralLengthThusFar = 0;
                if(justTurned){
                    justTurned = false;
                } else { // need to increase spiral length every other turn
                    // completed one loop
                    spiralLength += 1; // search in a wider spiral
                    justTurned = true;
                }
            }
            //System.out.println(spiralDir.getName());
            if(map.isTileOpen(xIdx, yIdx)){
                ret = new Point(xIdx, yIdx);
            }
        }
        return ret;
    }
    
    /**
     * Searches for an open tile along one side of the given map. It attempts to
     * find a point as close the the given initial indeces as possible.
     * 
     * @param map the map to search for an open tile in 
     * @param initialXIdx the x-coordinate this should find a point close to,
     * measured in index-space.
     * @param initialYIdx the y-coordinate this should find a point close to,
     * measured in index-space.
     * @param whatSide the side of the TileMap to check for open tiles along.
     * 
     * @return the index of an open tile, or null if no such tile exists 
     */
    public final Point searchForOpenTileAlongSide(TileMap map, int initialXIdx, int initialYIdx, Direction whatSide){
        Point ret = null;
        
        Direction lineDirection = Direction.rotateCounterClockWise(whatSide);
        
        boolean posRadValid = true;
        boolean negRadValid = true;
        int currXIdx = initialXIdx;
        int currYIdx = initialYIdx;
        for(int searchRadius = 0; ret == null && (posRadValid || negRadValid); searchRadius++){
            // check positive radius direction
            currXIdx = (int) (initialXIdx + lineDirection.getXMod() * searchRadius);
            currYIdx = (int) (initialYIdx + lineDirection.getYMod() * searchRadius);
            posRadValid = map.isValidIdx(currXIdx, currYIdx);
            if(posRadValid){
                // check if the positive tile is open
                if(map.isTileOpen(currXIdx, currYIdx)){
                    ret = new Point(currXIdx, currYIdx);
                }
            }
            
            // check negative radius direction
            if(ret == null){
                currXIdx = (int) (initialXIdx - lineDirection.getXMod() * searchRadius);
                currYIdx = (int) (initialYIdx - lineDirection.getYMod() * searchRadius);
                negRadValid = map.isValidIdx(currXIdx, currYIdx);
                if(negRadValid && map.isTileOpen(currXIdx, currYIdx)){
                    // check if the negative tile is open
                    ret = new Point(currXIdx, currYIdx);
                }
            }
        }
        
        return ret;
    }
}
