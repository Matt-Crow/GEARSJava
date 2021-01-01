package gears.sidescroller.world.machines;

/**
 * Machines implementing the PowerProvidingMachine
 * interface will provide power for other Machines.
 * 
 * @author Matt Crow
 */
public interface PowerProvidingMachine {
    /**
     * 
     * @return the radius of the square of power this provides,
     * measured in tiles
     */
    public int getPowerAreaRadiusInTiles();
}
