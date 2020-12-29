# GEARS

TODO:
* Matrix2D class for both TileMap and Level
* having issues with collision checking when moving to a new area
    - Caused by old area checking for collisions after moving player to new area

Current Plan:
* Make all core classes
    * tile
    * tile map
    * entities
    * machines
    * structures
* add factories for each
* add serialization for each

WASD to move,
R to regenerate randomly generated world
L to log the world to System.out