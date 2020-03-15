package gui.imageEditor;

import javax.swing.JFrame;
import java.awt.Toolkit;

/**
 *
 * @author Matt Crow
 */
public class ImageEditorFrame extends JFrame{
    public ImageEditorFrame(){
        super();
        setTitle("Image Editor");
        setSize(
            (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom
        );
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(new ImageEditorPane());
        revalidate();
        repaint();
    }
}
