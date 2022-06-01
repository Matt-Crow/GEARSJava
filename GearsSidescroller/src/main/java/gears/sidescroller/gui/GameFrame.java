package gears.sidescroller.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.*;

/**
 * This serves as the window for the application, and is managed by a
 * PageController
 * 
 * @author Matt Crow
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame{
    private final JPanel content;
    private Page currentPage;
    
    public GameFrame(){
        super();
        setTitle("GEARS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // fullscreen
        setSize(
            (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom
        );
        
        currentPage = null;
        content = new JPanel();
        content.setLayout(new GridLayout(1, 1)); // fill available space
        content.setBackground(Color.blue);        
        setContentPane(content);
        setVisible(true);
        revalidate();
        repaint();
    }
    
    public void setPage(Page p){
        if(currentPage != null){
            currentPage.switchingFrom();
            content.removeAll();
        }
        
        currentPage = p;
        content.add(p);
        p.switchingTo();
        
        revalidate();
        repaint();
        p.requestFocus();
    }
}
