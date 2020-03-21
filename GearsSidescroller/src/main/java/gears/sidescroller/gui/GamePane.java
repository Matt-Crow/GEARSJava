package gears.sidescroller.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author Matt Crow
 */
public class GamePane extends JPanel{
    public static final String HOME = "Home";
    public static final String PLAY = "Play";
    
    private final JPanel body;
    private final HashMap<String, Page> pages; 
    
    public GamePane(){
        super();
        setLayout(new GridLayout(1, 1)); //content should fill the screen
        pages = new HashMap<>();
        
        CardLayout l = new CardLayout();
        body = new JPanel();
        body.setLayout(l);
        body.setBackground(Color.blue);
        add(body);
        
        //create pages
        addPage(HOME, new HomePage(this));
        addPage(PLAY, new LevelPage(this));
        
        switchToPage(HOME);
    }
    
    private void addPage(String pageName, Page p){
        body.add(pageName, p);
        pages.put(pageName, p);
    }
    
    public final GamePane switchToPage(String pageName){
        if(pages.containsKey(pageName)){
            ((CardLayout)body.getLayout()).show(body, pageName);
            pages.get(pageName).refresh();
        } else {
            throw new IllegalArgumentException(String.format("Page with name of '%s' does not exist.", pageName));
        }
        return this;
    }
}
