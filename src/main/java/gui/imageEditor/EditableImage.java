package gui.imageEditor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author Matt Crow
 */
public class EditableImage extends JComponent{
    private int panX;
    private int panY;
    private double zoom;
    private BufferedImage image;
    
    public EditableImage(){
        super();
        panX = 0;
        panY = 0;
        zoom = 1.0;
        image = null;
    }
    public EditableImage(BufferedImage buff){
        this();
        setImage(buff);
    }
    
    public void setImage(BufferedImage newImg){
        image = newImg;
        repaint();
    }
    public void setImage(InputStream in) throws IOException{
        setImage(ImageIO.read(in));
    }
    public void setImage(File f) throws FileNotFoundException, IOException{
        setImage(new FileInputStream(f));
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public void saveImage(File f) throws IOException{
        if(image != null){
            ImageIO.write(image, "PNG", f);
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        if(image != null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.scale(zoom, zoom);
            g2d.drawImage(image, panX, panY, this);
        }
    }
}
