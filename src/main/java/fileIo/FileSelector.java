package fileIo;

import java.io.File;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Matt Crow
 */
public class FileSelector {
    private final JFileChooser chooser;
    private final Consumer<File> action;
    
    public FileSelector(String msg, FileType type, Consumer<File> onSelect){
        chooser = new JFileChooser();
        chooser.setFileSelectionMode((FileType.DIR.equals(type)) ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileNameExtensionFilter(type.getName(), type.getExtensions()));
        chooser.setDialogTitle(msg);
        action = onSelect;
    }
    
    public void chooseFile(File f){
        action.accept(f);
    }
    
    public void chooseFile(){
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            chooseFile(chooser.getSelectedFile());
        }
    }
    
    public static void createNewFile(String directory, String name, Consumer<File> andThen){
        andThen.accept(new File(directory + File.separator + name));
    }
    public static void createNewFile(String name, Consumer<File> andThen){
        new FileSelector("Choose a directory to save " + name + " to.", FileType.DIR, (File f)->{
            createNewFile(f.getAbsolutePath(), name, andThen);
        }).chooseFile();
    }
    public static void createNewFile(Consumer<File> andThen){
        String name = JOptionPane.showInputDialog(null, "Enter a name for this file:");
        createNewFile(name, andThen);
    }
}
