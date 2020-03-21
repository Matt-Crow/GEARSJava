package gears.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Consumer;
import javax.swing.JFileChooser;

/**
 *
 * @author Matt
 */
public class FileSelectorUtil {
    
    public static void chooseCreateFile(String prompt, String fileName, Consumer<File> onFileCreated) throws IOException{
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            File newFile = Paths.get(chooser.getSelectedFile().getAbsolutePath(), fileName).toFile();
            newFile.createNewFile();
            onFileCreated.accept(newFile);
        }
    }
}
