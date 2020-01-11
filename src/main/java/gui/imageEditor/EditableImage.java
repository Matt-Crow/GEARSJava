package gui.imageEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author Matt Crow
 */
public class EditableImage extends JComponent{
    private int panX;
    private int panY;
    private double zoom;
    private BufferedImage image;
    private Color currentColor;
    private ImageEditorMode mode;
    
    private boolean mouseIsDown;
    
    private static final int PAN_SPEED = 5;
    private static final double ZOOM_SPEED = 0.1;
    
    public EditableImage(){
        super();
        panX = 0;
        panY = 0;
        zoom = 1.0;
        image = null;
        currentColor = Color.RED;
        registerKey(KeyEvent.VK_UP, true, ()->panUp());
        registerKey(KeyEvent.VK_DOWN, true, ()->panDown());
        registerKey(KeyEvent.VK_LEFT, true, ()->panLeft());
        registerKey(KeyEvent.VK_RIGHT, true, ()->panRight());
        registerKey(KeyEvent.VK_EQUALS, true, ()->zoomIn()); //VK_PLUS doesn't work
        registerKey(KeyEvent.VK_MINUS, true, ()->zoomOut());
        setBackground(Color.BLACK);
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                mouseIsDown = true;
                click(e.getX(), e.getY());
            }
            public void mouseReleased(MouseEvent e){
                mouseIsDown = false;
            }
        });
        addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                if(mouseIsDown){
                    click(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
        });
        mode = ImageEditorMode.FILL;
        mouseIsDown = false;
    }
    public EditableImage(BufferedImage buff){
        this();
        setImage(buff);
    }
    
    public void setImage(BufferedImage newImg){
        image = newImg;
        panX = 0;
        panY = 0;
        zoom = 1.0;
        repaint();
    }
    public void setImage(InputStream in) throws IOException{
        setImage(ImageIO.read(in));
    }
    public void setImage(File f) throws FileNotFoundException, IOException{
        setImage(new FileInputStream(f));
    }
    
    public void setColor(Color c){
        currentColor = c;
        setBackground(c);
        repaint();
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public void setMode(ImageEditorMode m){
        mode = m;
    }
    
    public void panUp(){
        panY += PAN_SPEED;
        if(image != null && panY > image.getHeight()){
            panY = image.getHeight();
        }
        repaint();
    }
    public void panDown(){
        panY -= PAN_SPEED;
        if(panY < -getHeight()){
            panY = -getHeight();
        }
        repaint();
    }
    public void panLeft(){
        panX += PAN_SPEED;
        if(image != null && panX > image.getWidth()){
            panX = image.getWidth();
        }
        repaint();
    }
    public void panRight(){
        panX -= PAN_SPEED;
        if(panX < -getWidth()){
            panX = -getWidth();
        }
        repaint();
    }
    
    public void zoomIn(){
        zoom += ZOOM_SPEED;
        if(zoom > 5.0){
            zoom = 5.0;
        }
        repaint();
    }
    public void zoomOut(){
        zoom -= ZOOM_SPEED;
        if(zoom < 0.1){
            zoom = 0.1;
        }
        repaint();
    }
    
    public void click(int x, int y){
        int trueX = (int) ((x + panX) / zoom);
        int trueY = (int) ((y + panY) / zoom);
        if(image != null){
            if(trueX >= 0 && trueX < image.getWidth() && trueY >= 0 && trueY < image.getHeight()){
                if(ImageEditorMode.FILL.equals(mode)){
                    image.setRGB(trueX, trueY, currentColor.getRGB());
                    repaint();
                } else if(ImageEditorMode.PICK_COLOR.equals(mode)){
                    setColor(new Color(image.getRGB(trueX, trueY)));
                }
            }
        }
    }
    
    public void registerKey(int key, boolean pressed, Runnable r){
        String text = key + ((pressed) ? " pressed" : " released");
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, 0, !pressed), text);
        getActionMap().put(text, new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                r.run();
            }
        });
    }
    
    public void saveImage(File f) throws IOException{
        if(image != null){
            ImageIO.write(image, "PNG", f);
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(currentColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(image != null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(-panX, -panY);
            g2d.scale(zoom, zoom);
            g2d.drawImage(image, 0, 0, this);
        }
    }
}
