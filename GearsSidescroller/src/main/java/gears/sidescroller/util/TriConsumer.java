/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gears.sidescroller.util;

/**
 *
 * @author Matt
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {
    public void accept(T t, U u, V v);
}
