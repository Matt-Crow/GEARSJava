package gears.sidescroller.world.tileMaps;

/**
 * more or less derived from https://en.wikipedia.org/wiki/Perlin_noise
 * this works as a function for the AreaGenerator, but is too claustrophobic to
 * use as a tile map
 * 
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class PerlinNoiseGenerator {
    private final int max;
    
    
    public PerlinNoiseGenerator(int max){
        this.max = max;
    }
    
    
    /**
     * use this method to determine which type of tile to use at the given tile
     * map index.
     * 
     * @param xIdx the x-index in the tile map
     * @param yIdx the y-index in the tile map
     * @return a value within [0, max)
     */
    public int computeNoiseAt(int xIdx, int yIdx){
        int nextX = xIdx + 1;
        int nextY = yIdx + 1;
        
        double weight = 0.5;
        
        double[] cornerDotGradients = {
            computeDotGradientAt(xIdx, yIdx),
            computeDotGradientAt(nextX, yIdx),
            computeDotGradientAt(xIdx, nextY),
            computeDotGradientAt(nextX, nextY)
        };
        
        double xInt = interpolate(
                cornerDotGradients[0],
                cornerDotGradients[1],
                weight
        );
        double yInt = interpolate(
                cornerDotGradients[2],
                cornerDotGradients[3],
                weight
        );
        
        double result = interpolate(xInt, yInt, weight);
        int tileSetIdx = (int) Math.floor(max * Math.abs(result));
        
        return tileSetIdx;
    }
    
    private double computeDotGradientAt(int xIdx, int yIdx){
        double[] rv = randomUnitVector();
        return rv[0] * 0.5 + rv[1] * 0.5;
    }
    
    private double[] randomUnitVector(){
        double angle = Math.random() * Math.PI * 2;
        return new double[]{
            Math.cos(angle),
            Math.sin(angle)
        };
    }
    
    private double interpolate(double a, double b, double weight){
        double min = Math.min(a, b);
        double max = Math.max(a, b);
        return (max - min) * weight + min;
    }
    
    
    public static void main(String[] args){
        PerlinNoiseGenerator png = new PerlinNoiseGenerator(5);
        int[][] grid = new int[10][10];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                grid[i][j] = png.computeNoiseAt(i, j);
            }
        }
        
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                System.out.printf("%3d ", grid[i][j]);
            }
            System.out.println();
        }
    }
}
