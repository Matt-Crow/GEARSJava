package gui.imageEditor;

import fileIo.FileSelector;
import fileIo.FileType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Matt Crow
 */
public class ImageEditorPane extends JPanel{
    private JLabel imgLabel;
    private BufferedImage buff;
    public ImageEditorPane(){
        super();
        setLayout(new BorderLayout());
        imgLabel = new JLabel();
        setImage(ImageEditorPane.class.getResourceAsStream("/images/test.png"));
        
        add(imgLabel, BorderLayout.CENTER);
        
        add(createMenuBar(), BorderLayout.PAGE_START);
        
        setBackground(Color.RED);
    }
    
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        
        JMenuItem load = new JMenuItem("Load an image");
        load.addActionListener((e)->{
            new FileSelector("Choose an image to load", FileType.IMAGE, (File f)->{
                setImage(f);
            }).chooseFile();
        });
        menuBar.add(load);
        
        JMenuItem newImg = new JMenuItem("Create a new image");
        newImg.addActionListener((e)->{
            // create a blank image, then set it as this' image
        });
        menuBar.add(newImg);
        
        JMenuItem save = new JMenuItem("Save this image");
        save.addActionListener((e)->{
            FileSelector.createNewFile((File f)->{
                try {
                    ImageIO.write(buff, "png", f);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        });
        menuBar.add(save);
        
        return menuBar;
    }
    
    private void setImage(InputStream in){
        try {
            buff = ImageIO.read(in);
            imgLabel.setIcon(new ImageIcon(buff));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void setImage(File f){
        try {
            setImage(new FileInputStream(f));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
