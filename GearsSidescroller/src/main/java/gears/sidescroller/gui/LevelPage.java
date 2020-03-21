package gears.sidescroller.gui;

import gears.sidescroller.world.TileMap;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The LevelPage class is used to render TileMaps
 * (later it will render Levels), and allow
 * the user to play the game.
 * 
 * @author Matt Crow
 */
public final class LevelPage extends Page{
    /*
    Once I implement Levels, this
    will have its class changed
    */
    private TileMap currentLevel;
    
    public LevelPage(GamePane pane) {
        super(pane);
        currentLevel = null;
        setBackground(Color.black);
        setFocusable(true); //I might need this for controls
    }
    
    /**
     * This method will be changed a bit once Level is
     * implemented.
     * 
     * @param t the TileMap to set as the current level
     * @return this, for chaining purposes
     */
    public final LevelPage setCurrentLevel(TileMap t){
        currentLevel = t;
        return this;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(currentLevel == null){
            g.setColor(Color.yellow);
            g.drawString("current level not set", getWidth() / 2, getHeight() / 2);
        } else {
            currentLevel.draw(g);
        }
    }
}
