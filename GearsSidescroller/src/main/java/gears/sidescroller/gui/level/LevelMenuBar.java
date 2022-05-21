package gears.sidescroller.gui.level;

import gears.sidescroller.world.levels.Level;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
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
        JsonObject asJson = level.toJson();
        StringWriter out = new StringWriter();
        Map<String, Boolean> options = new HashMap<>();
        options.put(JsonGenerator.PRETTY_PRINTING, Boolean.TRUE);
        JsonWriterFactory factory = Json.createWriterFactory(options);
        JsonWriter writer = factory.createWriter(out);
        writer.writeObject(asJson);
        writer.close();
        System.out.println(out);
    }
}
