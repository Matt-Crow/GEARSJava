package gui.imageEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
        setBackground(Color.RED);
    }
}
