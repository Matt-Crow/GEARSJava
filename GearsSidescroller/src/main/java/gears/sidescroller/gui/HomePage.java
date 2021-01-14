package gears.sidescroller.gui;

import gears.sidescroller.start.TempTestLevel;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.levels.Level;
import gears.sidescroller.world.levels.LevelGenerator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This serves as the starting
 * page for the GEARS sidescroller
 * application.
 * 
 * @author Matt Crow
 */
public class HomePage extends Page{
    public HomePage(GamePane pane) {
        super(pane);
        setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Gear Engine And Robot Sidescroller");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.PAGE_START);
        
        JPanel center = new JPanel();
        center.setBackground(Color.gray);
        add(center, BorderLayout.CENTER);
        
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton button1 = new JButton("Button 1");
        bottom.add(button1);
        JButton play = new JButton("Play a level");
        play.addActionListener((e)->{
            //change this to direct to a level selector
            LevelPage newPage = new LevelPage(
                getParentGamePane(),
                getDefaultWorld(),
                new Player()
            );
            getParentGamePane().switchToPage(newPage);
        });
        bottom.add(play);
        JButton button3 = new JButton("Button 3");
        bottom.add(button3);
        add(bottom, BorderLayout.PAGE_END);
    }
    
    /**
     * This method is temporary until
     * I implement the level creator so
     * I can use real levels
     * @return 
     */
    private Level getDefaultWorld(){
        Level l = new LevelGenerator().generateRandom(3);
        //Level l = TempTestLevel.getTestLevel();
        l.init();
        
        return l;
    }
}
