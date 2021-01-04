/**
 * This package currently only contains classes relevant to the VolatileLinkedList.
 * 
 * Given the dynamic nature of AbstractEntities in an Area, an Area may add or remove entities while iterating over its list of entities.
 * As such, this list must support those operations without throwing ConcurrentModificationExceptions.
 * 
 * <h2>How to use this Package</h2>
 * <ul>
 * <li>Have a root data type, T</li>
 * <li>Store a {@code VolatileLinkedList<T>}, henceforth referred to as "the list"</li>
 * <li>Items of type T which should be removed at some point must implement the {@code Removable<T>} interface</li>
 * <li>non-Removable items are allowed in the list</li>
 * <li>The Removable items should invoke their remove() method when they should be deleted from the list</li>
 * </ul>
 */
package gears.sidescroller.util.dataStructures;
