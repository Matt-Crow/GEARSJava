package gui.imageEditor;

import fileIo.FileSelector;
import fileIo.FileType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public ImageEditorPane(){
        super();
        setLayout(new BorderLayout());
        imgLabel = new JLabel();
        try {
            BufferedImage buff = ImageIO.read(ImageEditorPane.class.getResourceAsStream("/images/test.png"));
            imgLabel.setIcon(new ImageIcon(buff));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        add(imgLabel, BorderLayout.CENTER);
        
        
        
        
        add(createMenuBar(), BorderLayout.PAGE_START);
        
        
        
        setBackground(Color.RED);
    }
    
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        
        JMenuItem load = new JMenuItem("Load an image");
        load.addActionListener((e)->{
            new FileSelector("Choose an image to load", FileType.IMAGE, (File f)->{
                try {
                    BufferedImage newImg = ImageIO.read(f);
                    imgLabel.setIcon(new ImageIcon(newImg));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).chooseFile();
        });
        menuBar.add(load);
        
        JMenuItem newImg = new JMenuItem("Create a new image");
        newImg.addActionListener((e)->{
            
        });
        menuBar.add(newImg);
        
        JMenuItem save = new JMenuItem("Save this image");
        save.addActionListener((e)->{
        
        });
        menuBar.add(save);
        
        return menuBar;
    }
}
