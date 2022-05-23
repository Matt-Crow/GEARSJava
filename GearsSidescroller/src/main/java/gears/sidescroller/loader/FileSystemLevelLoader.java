package gears.sidescroller.loader;

import gears.sidescroller.world.levels.Level;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonWriter;

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
    public Level load(String identifier) throws LevelLoadingException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void save(Level level) throws LevelLoadingException {
        Path toFile = Paths.get(levelFolderPath, "foo.json");
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
