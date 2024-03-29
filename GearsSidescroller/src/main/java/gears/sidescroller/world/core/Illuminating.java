package gears.sidescroller.world.core;

/**
 * denotes that an object can produce light
 * @author Matt Crow 
 */
public interface Illuminating {
    
    /**
     * @return the x-index of the center of the square of light this produces
     */
    public int getLightCenterXIdx();
    
    /**
     * @return the y-index of the center of the square of light this produces
     */
    public int getLightCenterYIdx();
    
    /**
     * @return the radius of the square of light this object provides 
     */
    public int getIlluminationRange();
    
    /**
     * @param d the number of tiles between this object and a tile
     * @return the illumination this provides to a tile at the given number of
     *  tiles away
     */
    public LightLevel getIlluminationAtDistance(int d);
}
