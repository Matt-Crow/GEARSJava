/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gears.sidescroller.world.core;

import gears.sidescroller.world.entities.Player;

/**
 *
 * @author Matt
 */
@FunctionalInterface
public interface Interactable {
    public boolean interactWith(Player p);
}
