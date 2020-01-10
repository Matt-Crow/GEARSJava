package gui.imageEditor;

import fileIo.FileSelector;
import fileIo.FileType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.swing.JColorChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Matt Crow
 */
public class ImageEditorPane extends JPanel{
    private final EditableImage img;
    
    public ImageEditorPane(){
        super();
        setLayout(new BorderLayout());
        img = new EditableImage();
        try {
            img.setImage(ImageEditorPane.class.getResourceAsStream("/images/test.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        add(img, BorderLayout.CENTER);
        
        add(createMenuBar(), BorderLayout.PAGE_START);
        
        
        JColorChooser color = new JColorChooser(Color.RED);
        color.getSelectionModel().addChangeListener((e)->{
            img.setColor(color.getColor());
        });
        add(color, BorderLayout.PAGE_END);
        setBackground(Color.WHITE);
    }
    
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        
        JMenuItem load = new JMenuItem("Load an image");
        load.addActionListener((e)->{
            new FileSelector("Choose an image to load", FileType.IMAGE, (File f)->{
                try {
                    img.setImage(f);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).chooseFile();
        });
        menuBar.add(load);
        
        JMenuItem newImg = new JMenuItem("Create a new image");
        newImg.addActionListener((e)->{
            // create a blank image, then set it as this' image
            BufferedImage newBuff = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            int r;
            int g;
            int b;
            for(int x = 0; x < 100; x++){
                for(int y = 0; y < 100; y++){
                    r = x;
                    g = y;
                    b = x + y;
                    newBuff.setRGB(x, y, new Color(r, g, b).getRGB());
                }
            }
            img.setImage(newBuff);
        });
        menuBar.add(newImg);
        
        JMenuItem save = new JMenuItem("Save this image");
        save.addActionListener((e)->{
            if(img.getImage() != null){
                FileSelector.createNewFile((File f)->{
                    try {
                        img.saveImage(f);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });
        menuBar.add(save);
        
        return menuBar;
    }
}
