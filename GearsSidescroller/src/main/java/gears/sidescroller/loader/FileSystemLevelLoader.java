package gears.sidescroller.loader;

import gears.sidescroller.world.levels.*;
import java.io.*;
import java.nio.file.*;
import java.util.Collection;
import javax.json.*;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class FileSystemLevelLoader implements LevelLoader {
    
    private final String levelFolderPath;
    
    public FileSystemLevelLoader(){
        String userHome = System.getProperty("user.home", "./");
        Path levelFolder = Paths.get(userHome, "gears", "levels");
        try {
            Files.createDirectories(levelFolder);
        } catch (IOException ex) {
            throw new LevelLoadingException("failed to create level folder", ex);
        }
        levelFolderPath = levelFolder.toString();
        
    }
    
    @Override
    public Collection<String> getAvailableLevelName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Level load(String identifier) throws LevelLoadingException {
        Level level = null;
        Path toFile = Paths.get(levelFolderPath, identifier + ".json");
        File asFile = toFile.toFile();
        
        try (
                FileInputStream fin = new FileInputStream(asFile);
                JsonReader reader = Json.createReader(fin);
        ){
            JsonObject obj = reader.readObject();
            level = new LevelJson().deserialize(obj);
        } catch (IOException ex) {
            throw new LevelLoadingException("failed to load Level " + identifier, ex);
        }
        
        return level;
    }

    @Override
    public void save(String identifier, Level level) throws LevelLoadingException {
        Path toFile = Paths.get(levelFolderPath, identifier + ".json");
        File asFile = toFile.toFile();
        
        try (
                FileOutputStream fout = new FileOutputStream(asFile);
                JsonWriter writer = Json.createWriter(fout);
        ) {
            writer.writeObject(level.toJson());
        } catch (IOException ex) {
            throw new LevelLoadingException("failed to save Level", ex);
        }
    }
}
