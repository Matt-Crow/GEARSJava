package gears.sidescroller.gui.level;

import gears.sidescroller.world.levels.Level;
import javax.swing.*;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class LevelMenuBar extends JMenuBar {
    private final LevelPage inPage;
    
    
    public LevelMenuBar(LevelPage inPage){
        this.inPage = inPage;
        
        
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
        JOptionPane.showMessageDialog(
                this, 
                "LevelMenuBar::open not implemented", 
                "Open", 
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void save() {
        Level level = inPage.getCurrentLevel();
        System.out.println(level);
    }
}
