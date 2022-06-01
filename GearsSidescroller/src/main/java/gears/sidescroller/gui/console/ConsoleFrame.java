package gears.sidescroller.gui.console;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * @author Matt Crow 
 */
@SuppressWarnings("serial")
public class ConsoleFrame extends JFrame {
    public ConsoleFrame(ConsoleComponent console){
        setTitle("console");
        
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        setContentPane(content);
        
        content.add(console, BorderLayout.CENTER);
        
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
