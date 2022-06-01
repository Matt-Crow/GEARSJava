package gears.sidescroller.gui.level;

import gears.sidescroller.loader.LevelLoader;
import gears.sidescroller.world.levels.Level;
import javax.swing.*;

/**
 *
 * @author Matt Crow 
 */
@SuppressWarnings("serial")
public class LevelMenuBar extends JMenuBar { 
    private final LevelPage inPage;
    private final LevelLoader loader;
    
    public LevelMenuBar(LevelPage inPage, LevelLoader loader){
        this.inPage = inPage;
        this.loader = loader;
        
        JMenu file = new JMenu("File");
        file.setMnemonic('f');
        
        JMenuItem random = new JMenuItem("play a random level");
        random.addActionListener((e)->inPage.getController().playRandomLevel());
        file.add(random);
        
        JMenuItem open = new JMenuItem("open");
        open.addActionListener((e)->open());
        file.add(open);
        
        JMenuItem save = new JMenuItem("save");
        save.addActionListener((e)->save());
        file.add(save);
        
        add(file);
    }
    
    private void open(){
        String name = JOptionPane.showInputDialog(this, "enter level name: ");
        if(name != null){
            Level level = loader.load(name);
            inPage.getController().playLevel(level);
        }
    }
    
    private void save(){
        String name = JOptionPane.showInputDialog(this, "enter level name: ");
        if(name != null){
            loader.save(name, inPage.getCurrentLevel());
        }
    }
}
