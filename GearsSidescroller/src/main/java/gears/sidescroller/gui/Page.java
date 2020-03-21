package gears.sidescroller.gui;

import javax.swing.JPanel;

/**
 * Page serves as the base class
 * for the different views of the
 * application. Pages are hosted by
 * GamePane, which flips between its
 * Pages using a CardLayout.
 * 
 * @author Matt Crow
 * @see gears.sidescroller.gui.GamePane
 */
public class Page extends JPanel{
    private final GamePane parent;
    
    /**
     * 
     * @param pane the GamePane hosting this Page
     */
    public Page(GamePane pane){
        super();
        parent = pane;
    }
    
    /**
     * 
     * @return the GamePane hosting this page
     */
    public final GamePane getParentGamePane(){
        return parent;
    }
    
    /**
     * Called whenever the hosting
     * GamePane switches between Pages.
     * Subclasses which render
     * data should use this method
     * to refresh their data.
     */
    public void refresh(){
        
    }
}
