package gears.sidescroller.gui.level;

import gears.sidescroller.gui.*;
import gears.sidescroller.gui.console.ConsoleFactory;
import gears.sidescroller.gui.console.ConsoleFrame;
import gears.sidescroller.loader.LevelLoader;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.levels.Level;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

/**
 * The LevelPage class is used to render Levels, 
 * and allows the user to play the game.
 * 
 * @author Matt Crow
 */
public class LevelPage extends Page{
    private final Level currentLevel;
    private final Timer timer;
    private final Player focusedEntity;
    private final PlayerControls controls;
    private double zoom;
    private boolean isConsoleShowing;
    
    private static final int FPS = 60;
    private static final boolean DRAW_COLLISION_OVERLAY = false;
    
    public LevelPage(PageController controller, Level forLevel, Player thePlayer, LevelLoader loader) {
        super(controller);
        setLayout(new BorderLayout());
        add(new LevelMenuBar(this, loader), BorderLayout.PAGE_START);
        
        currentLevel = forLevel;
        currentLevel.loadPlayer(thePlayer);
        currentLevel.init();
        controls = new PlayerControls(thePlayer);
        controls.registerTo(this);
        focusedEntity = thePlayer;
        thePlayer.init(); // reset inventory
        
        setBackground(Color.black);
        setFocusable(true); //I might need this for controls
        timer = new Timer(1000 / FPS, (e)->{
            update();
        });
        timer.setRepeats(true);
        timer.start();
        
        zoom = 1.0;
        isConsoleShowing = false;
        
        setFocusable(true);
        add(controls, BorderLayout.CENTER);
        
        registerKey(KeyEvent.VK_EQUALS, true, this::zoomIn);
        registerKey(KeyEvent.VK_MINUS, true, this::zoomOut);
        registerKey(KeyEvent.VK_C, true, this::showConsole);
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
        //                                            V no modifiers (shift, alt, etc)
        getInputMap().put(KeyStroke.getKeyStroke(key, 0, !pressed), text);
        getActionMap().put(text, new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                r.run();
            }
        });
    }
    
    public Level getCurrentLevel(){
        return currentLevel;
    }
    
    @Override
    public void switchingFrom(){
        getInputMap().clear(); // un-register controls
        getActionMap().clear();
        currentLevel.getCurrentArea().removeFromWorld(focusedEntity);
    }
    
    public void zoomIn(){
        zoom += 0.1;
        if(zoom > 2.0){
            zoom = 2.0;
        }
    }
    
    public void zoomOut(){
        zoom -= 0.1;
        if(zoom < 0.5){
            zoom = 0.5;
        }
    }
    
    private void update(){
        currentLevel.update(FPS);
        repaint();
    }
    
    private void showConsole(){
        if(!isConsoleShowing){
            ConsoleFrame f = new ConsoleFactory().makeDefaultConsole(currentLevel);
            isConsoleShowing = true;
            f.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e){
                    isConsoleShowing = false;
                }
            });
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform priorToTranslate = g2d.getTransform();
        g2d.scale(zoom, zoom);
        g2d.translate( // center on the focusedEntity
            -(int)(focusedEntity.getXAsInt() - getWidth() / (2 * zoom)),
            -(int)(focusedEntity.getYAsInt() - getHeight() / (2 * zoom))
        );
        currentLevel.draw(g2d);
        g2d.setTransform(priorToTranslate);
        
        if(DRAW_COLLISION_OVERLAY && currentLevel.getCurrentArea().getTileMap().checkForCollisions(focusedEntity)){
            g2d.setColor(new Color(255, 0, 0, 127));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
