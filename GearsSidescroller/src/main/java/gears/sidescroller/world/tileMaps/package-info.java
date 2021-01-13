/**
 * The tileMaps package is responsible for the TileMap background of Areas. These
 * provide collision checking with the game environment and rendering Tiles. Note
 * the use of the Flyweight design pattern in TileMap to drastically reduce the
 * amount of memory the TileMap consumes. Additionally, collision checking in the
 * TileMap is O(1), which is pretty cool.
 * 
 * @see gears.sidescroller.world.tileMaps.OpenTileSearch for searching for open
 * tiles in a TileMap.
 */
package gears.sidescroller.world.tileMaps;
