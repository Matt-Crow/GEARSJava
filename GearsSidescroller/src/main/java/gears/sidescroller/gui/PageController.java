package gears.sidescroller.gui;

import gears.sidescroller.gui.level.LevelPage;
import gears.sidescroller.loader.LevelLoader;
import gears.sidescroller.start.TempTestLevel;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.levels.*;



/**
 * this class is responsible for switching between the different pages in the
 * application
 * 
 * @author Matt Crow 
 */
public class PageController {
    private final GameFrame window;
    private Player player;
    private final LevelLoader levelLoader;
    private final LevelGenerator levelGenerator;    
    
    public PageController(GameFrame window, Player player, LevelLoader levelLoader, LevelGenerator levelGenerator){
        this.window = window;
        this.player = player;
        this.levelLoader = levelLoader;
        this.levelGenerator = levelGenerator;
        switchToPage(new HomePage(this));
    }
    
    public void setPlayer(Player player){
        this.player = player;
    }
    
    public void levelSelect(){
        //change this to direct to a level selector
        playRandomLevel();
    }
    
    public void playRandomLevel(){
        playLevel(createRandomLevel());
    }
    
    private Level createRandomLevel(){
        return levelGenerator.generateRandom(3);
    }
    
    public void playTestLevel(){
        playLevel(TempTestLevel.getTestLevel());
    }
    
    public void playLevel(Level level){
        LevelPage newPage = new LevelPage(this, level, player, levelLoader);
        switchToPage(newPage);
    }
    
    public void switchToPage(Page p){
        window.setPage(p);
    }
}
