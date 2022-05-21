package gears.sidescroller.gui;

import javax.swing.JPanel;

/**
 * Page serves as the base class for the different views of the application. 
 * Pages are managed by a PageController
 * 
 * @author Matt Crow
 */
public class Page extends JPanel{
    private final PageController controller;
    
    
    public Page(PageController controller){
        super();
        this.controller = controller;
    }
    
    
    public final PageController getController(){
        return controller;
    }
    
    /**
     * called whenever the controller is leaving this page
     */
    public void switchingFrom(){
        
    }
    
    /**
     * called whenever the controller is entering this page
     */
    public void switchingTo(){
        
    }
}
