package gears.sidescroller.gui;

import gears.sidescroller.entities.AbstractEntity;
import gears.sidescroller.world.Level;
import gears.sidescroller.world.TileMap;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * The LevelPage class is used to render TileMaps
 * (later it will render Levels), and allow
 * the user to play the game.
 * 
 * @author Matt Crow
 */
public final class LevelPage extends Page{
    private Level currentLevel;
    private final Timer timer;
    private AbstractEntity focusedEntity;
    
    public static final int FPS = 20;
    
    public LevelPage(GamePane pane) {
        super(pane);
        currentLevel = null;
        setBackground(Color.black);
        setFocusable(true); //I might need this for controls
        timer = new Timer(1000 / FPS, (e)->{
            update();
        });
        timer.setRepeats(true);
        timer.start();
        setFocusable(true);
        focusedEntity = null;
    }
    
    public final LevelPage focusOnEntity(AbstractEntity e){
        focusedEntity = e;
        return this;
    }
    /**
     * Registers a key control to the Canvas.
     * For example,
     * <br>
     * {@code
     *  registerKey(KeyEvent.VK_X, true, ()->foo());
     * }
     * <br>
     * will cause foo to run whenever the user presses the 'x' key.
     * 
     * @param key the KeyEvent code for the key to react to
     * @param pressed whether to react when the key is pressed or released
     * @param r the runnable to run when the key is pressed/released
     */
    public void registerKey(int key, boolean pressed, Runnable r){
        String text = key + ((pressed) ? " pressed" : " released");
        getInputMap().put(KeyStroke.getKeyStroke(key, 0, !pressed), text);
        getActionMap().put(text, new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                r.run();
            }
        });
    }
    
    /**
     * This method will be changed a bit once Level is
     * implemented.
     * 
     * @param l the Level to set as the current level
     * @return this, for chaining purposes
     */
    public final LevelPage setCurrentLevel(Level l){
        currentLevel = l;
        return this;
    }
    
    private void update(){
        if(currentLevel != null){
            currentLevel.update();
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(focusedEntity != null){
            g.translate(
                -(int)(focusedEntity.getX() - getWidth() / 2),
                -(int)(focusedEntity.getY() - getHeight() / 2)
            );
        }
        if(currentLevel == null){
            g.setColor(Color.yellow);
            g.drawString("current level not set", getWidth() / 2, getHeight() / 2);
        } else {
            currentLevel.draw(g);
        }
    }
}
