package gears.sidescroller.gui;

import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.levels.Level;
import gears.sidescroller.world.levels.LevelGenerator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * The LevelPage class is used to render Levels, 
 * and allows the user to play the game.
 * 
 * @author Matt Crow
 */
public final class LevelPage extends Page{
    private final Level currentLevel;
    private final Timer timer;
    private final Player focusedEntity;
    private final PlayerControls controls;
    public static final int FPS = 20;
    
    public LevelPage(GamePane pane, Level forLevel, Player thePlayer) {
        super(pane);
        setLayout(new BorderLayout());
        
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
        setFocusable(true);
        
        registerKey(KeyEvent.VK_R, true, ()->{
            //regenerate random. Debugging tool
            this.getInputMap().clear(); // un-register controls
            this.getActionMap().clear();
            focusedEntity.remove();
            Level newLevel = new LevelGenerator().generateRandom(3);
            this.getParentGamePane().switchToPage(new LevelPage(
                getParentGamePane(),
                newLevel,
                focusedEntity
            ));
        });
        registerKey(KeyEvent.VK_L, true, ()->{
            // save world. placeholder
            if(currentLevel != null){
                System.out.println(currentLevel.toString()); //todo actually save it somehow
            }
        });
        
        add(controls, BorderLayout.CENTER);
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
    
    public final Level getCurrentLevel(){
        return currentLevel;
    }
    
    private void update(){
        currentLevel.update();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform priorToTranslate = g2d.getTransform();
        
        if(this.currentLevel.getCurrentArea().getTileMap().checkForCollisions(focusedEntity)){
            //g2d.setColor(new Color(255, 0, 0, 127));
            //g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        
        g2d.translate(
            -(int)(focusedEntity.getX() - getWidth() / 2),
            -(int)(focusedEntity.getY() - getHeight() / 2)
        );
        currentLevel.draw(g2d);
        g2d.setTransform(priorToTranslate);
    }
}
