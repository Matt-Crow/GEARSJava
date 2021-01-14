# GEARS
Currently just a toolkit for allowing me to create games.

## Summary
Since I currently work on a semester-by-semester basis, not being employed in between, I find it helpful to create game projects to maintain my skills over the break.
Seeing as I have my Associate of Science in Java, I find it helpful to practice this language, as I have done to a great extent in [Orpheus](https://github.com/Matt-Crow/Orpheus).
Unfortunately, Orpheus has gotten rather large and difficult to work with, so I wanted to practice Java using something a bit simpler (preferably without socket programming...).
GEARS is mostly a collection of game mechanics I've had for some time. I made an early version of this game several years ago in my [other GEARS repository](https://github.com/Matt-Crow/GEARS),
though these versions have diverged significantly in their designs and general ideas. 

## How to Run the Program
Currently, this project contains 2 sub-projects: GearsSidescroller and ImageEditor. ImageEditor isn't complete, so you'll want to head on over to GearsSidescroller, 
which contains a JAR file under Gears/GearsSidescroller/build/libs. Simply double-click the JAR file to run it, or run from the command line:
```
java -jar GearsSidescroller/build/libs/GearsSidescroller-0.9.jar
```
when run from the repository root.

## Matt's To Do List
* everything in the package info for world
* sprite loader
* Renderable interface which gives access to a 1x1 tile sprite

## Controls
* WASD to move
* R to regenerate randomly generated world
* L to log the world to System.out
* Q to interact with your current tile (such as picking up placed gears)
* select items from the "inventory" menu on the bottom of the screen to use them

## Some Screenshots
Here are just a few examples of the kind of worlds this program can generate:
![A square of conveyor belts all leading into each other](./readmeResources/gears1.png)
![A very long train of conveyor belts](./readmeResources/gears2.png)
![A very pink house](./readmeResources/gears3.png)

## Because Matt is Forgetfull
In case I can't find it: Remember Matt, the TempTestLevel is in the "start" package,
and you set the Level to play in HomePage!
