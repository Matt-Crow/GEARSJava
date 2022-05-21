package gears.sidescroller.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This serves as the starting page for the GEARS sidescroller application.
 * 
 * @author Matt Crow
 */
public class HomePage extends Page{
    public HomePage(PageController controller) {
        super(controller);
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
        play.addActionListener((e)->getController().levelSelect());
        bottom.add(play);
        
        JButton button3 = new JButton("Button 3");
        bottom.add(button3);
        
        add(bottom, BorderLayout.PAGE_END);
    }
}
