package gears.sidescroller.start;

import gears.sidescroller.gui.GameFrame;
import gears.sidescroller.gui.PageController;
import gears.sidescroller.loader.FileSystemLevelLoader;
import gears.sidescroller.loader.LevelLoader;
import gears.sidescroller.world.entities.Player;

/**
 *
 * @author Matt
 */
public class GameMain {

    public static void main(String[] args) {
        GameFrame window = new GameFrame();
        Player player = new Player();
        LevelLoader levelLoader = new FileSystemLevelLoader();
        
        PageController ctrl = new PageController(window, player, levelLoader);
    }
}
