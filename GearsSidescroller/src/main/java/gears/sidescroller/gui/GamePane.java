package gears.sidescroller.gui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Matt Crow
 */
public class GamePane extends JPanel{
    private final JPanel body;
    private Page currentPage;
    
    public GamePane(){
        super();
        setLayout(new GridLayout(1, 1)); //content should fill the screen
        
        body = new JPanel();
        body.setLayout(new GridLayout(1, 1));
        body.setBackground(Color.blue);
        add(body);
        
        currentPage = null;
        switchToPage(new HomePage(this));        
    }
    
    public final GamePane switchToPage(Page p){
        currentPage = p;
        body.removeAll();
        body.add(p);
        p.refresh();
        revalidate();
        repaint();
        p.requestFocus();
        return this;
    }
    
    public final Page getCurrentPage(){
        return currentPage;
    }
}
