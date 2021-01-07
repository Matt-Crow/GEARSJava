package gears.sidescroller.world.core;

import gears.sidescroller.world.entities.Player;

/**
 * Implement this interface in a class to specify that something should happen
 * when a Player interacts with the object.
 * 
 * @author Matt Crow
 */
@FunctionalInterface
public interface Interactable {
    /**
     * Use this method to specify how this object checks if a Player
     * is interacting with it, as well as what it should do when an
     * interaction occurs.
     * 
     * @param p the player to potentially interact with.
     * 
     * @return whether or not the Player is interacting with this. 
     * (Pro-tip: check if they are colliding with this)
     */
    public boolean interactWith(Player p);
}
