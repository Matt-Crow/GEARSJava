package gears.sidescroller.util;

import java.awt.Point;
import java.util.function.Predicate;

/**
 *
 * @author Matt
 */
public class SpiralSearchAlgorithm<T> {
    private boolean isValidIdx(T[][] array, int xIdx, int yIdx){
        return xIdx >= 0 && xIdx < array[0].length && yIdx >= 0 && yIdx < array.length;
    }
    public final Point search(T[][] array, int initialXIdx, int initialYIdx, Predicate<T> isValid, TriConsumer<T[][], Integer, Integer> doEachStep){
        Point ret = null;
        int xIdx = initialXIdx;
        int yIdx = initialYIdx;
        if(isValidIdx(array, xIdx, yIdx) && isValid.test(array[yIdx][xIdx])){
            ret = new Point(xIdx, yIdx);
        }        
        Direction spiralDir = Direction.UP;
        byte spiralLength = 1;
        byte spiralLengthThusFar = 0;
        int numTilesChecked = 0;
        boolean justTurned = true;
        while(ret == null && numTilesChecked < array.length * array[0].length){
            if(this.isValidIdx(array, xIdx, yIdx)){
                numTilesChecked++; // doesn't run if checking a point outside the map
                doEachStep.accept(array, xIdx, yIdx);
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
            if(isValidIdx(array, xIdx, yIdx) && isValid.test(array[xIdx][yIdx])){
                ret = new Point(xIdx, yIdx);
            }
        }
        return ret;
    }
    
    public static void main(String[] args){
        Integer[][] a = new Integer[5][5];
        new SpiralSearchAlgorithm<Integer>().search(a, 2, 2, (Integer i)->false, new TriConsumer<Integer[][], Integer, Integer>() {
            int numSteps = 0;
            @Override
            public void accept(Integer[][] m, Integer x, Integer y) {
                m[y][x] = numSteps;
                numSteps++;
            }
        });
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a[i].length; j++){
                System.out.printf("%03d ", a[i][j]);
            }
            System.out.println();
        }
    }
}
