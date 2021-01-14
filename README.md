# GEARS
Currently just a toolkit for allowing me to create games.

## Summary
Since I currently work on a semester-by-semester basis, not being employed in between, I find it helpful to create game projects to maintain my skills over the break.
Seeing as I have my Associate of Science in Java, I find it helpful to practice this language, as I have done to a great extent in [Orpheus](https://github.com/Matt-Crow/Orpheus).
Unfortunately, Orpheus has gotten rather large and difficult to work with, so I wanted to practice Java using something a bit simpler (preferably without socket programming...).
GEARS is mostly a collection of game mechanics I've had for some time. I made an early version of this game several years ago in my [other GEARS repository](https://github.com/Matt-Crow/GEARS),
though these versions have diverged significantly in their designs and general ideas. 


Current Plan:
* Make all core classes:
    * tile
    * tile map
    * entities
    * machines
    * structures
    * item (can pick up)
* add factories for each
* add serialization for each

Need sprite loader
Make most classes have a "sprite" attribute that is 1x1 tile

WASD to move,
R to regenerate randomly generated world
L to log the world to System.out
Q to interact with your current tile (such as picking up placed gears)

## Some Screenshots
Here are just a few examples of the kind of worlds this program can generate:
![A square of conveyor belts all leading into each other](./readmeResources/gears1.png)
![A very long train of conveyor belts](./readmeResources/gears2.png)
![A very pink house](./readmeResources/gears3.png)

In case I can't find it: Remember Matt, the TempTestLevel is in the "start" package,
and you set the Level to play in HomePage!
