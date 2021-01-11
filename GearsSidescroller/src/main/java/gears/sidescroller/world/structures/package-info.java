/**
 * Structures are matrices of tiles which can be inserted into a TileMap. These are
 * analogous to naturally generated structures in games such as Minecraft. 
 * 
 * Currently, these don't do much, just providing patterns to copy-paste into the TileMap,
 * but future versions will see Structures become more complex, having their own
 * sets of Machines and Items, becoming mini Areas in a way. In addition, once serialization
 * is complete, I will add the ability to read Structure data from a file, so the
 * user can have a folder of Structure templates in their local file system, which
 * the program will scour for ideas of what to build when performing random Area generation.
 */
package gears.sidescroller.world.structures;
