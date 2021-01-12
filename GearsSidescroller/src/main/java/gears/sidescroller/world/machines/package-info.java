/**
 * Machines are interactive components of the game world.
 * 
 * This package handles various machine-y things, including machine definitions,
 * common behavior, random generation, and serialization.
 * 
 * Machines may share some similar functionality, so I'll implement more interfaces
 * as it becomes clearer what sort of things many different machines may do.
 * 
 * Should you decide to implement a new AbstractMachine subclass, you'll need to
 * change MachineGenerator accordingly.
 */
package gears.sidescroller.world.machines;
