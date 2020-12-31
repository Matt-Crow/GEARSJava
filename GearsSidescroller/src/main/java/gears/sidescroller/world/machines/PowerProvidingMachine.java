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
     * @return whether or not this machine is currently providing power.
     * Some implementations may automatically provide power, while others
     * may require external power to provide their own.
     */
    public boolean isProvidingPower();
    
    /**
     * 
     * @return the radius of the square of power this provides,
     * measured in tiles
     */
    public int getPowerAreaRadiusInTiles();
}
