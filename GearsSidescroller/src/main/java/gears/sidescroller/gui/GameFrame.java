package gears.sidescroller.gui;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Matt Crow
 */
public class GameFrame extends JFrame{
    public GameFrame(){
        super();
        setTitle("GEARS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // fullscreen
        setSize(
            (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom
        );
        setContentPane(new GamePane());
        setVisible(true);
        revalidate();
        repaint();
    }
}
