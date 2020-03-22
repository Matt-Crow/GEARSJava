package gears.sidescroller.gui;

import gears.sidescroller.entities.Player;
import gears.sidescroller.world.Area;
import gears.sidescroller.world.Level;
import gears.sidescroller.world.TileMap;
import gears.sidescroller.world.tiles.BasicColorTile;
import gears.sidescroller.world.tiles.ImageTile;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This serves as the starting
 * page for the GEARS sidescroller
 * application.
 * 
 * @author Matt Crow
 */
public class HomePage extends Page{
    public HomePage(GamePane pane) {
        super(pane);
        setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Gear Engine And Robot Sidescroller");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.PAGE_START);
        
        JPanel center = new JPanel();
        center.setBackground(Color.gray);
        add(center, BorderLayout.CENTER);
        
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton button1 = new JButton("Button 1");
        bottom.add(button1);
        JButton play = new JButton("Play a level");
        play.addActionListener((e)->{
            //change this to direct to a level selector
            getParentGamePane().switchToPage(GamePane.PLAY);
            Page p = getParentGamePane().getCurrentPage();
            if(p instanceof LevelPage){
                ((LevelPage)p).setCurrentLevel(getDefaultWorld());
            }
        });
        bottom.add(play);
        JButton button3 = new JButton("Button 3");
        bottom.add(button3);
        add(bottom, BorderLayout.PAGE_END);
    }
    
    /**
     * This method is temporary until
     * I implement the level creator so
     * I can use real levels
     * @return 
     */
    private Level getDefaultWorld(){
        TileMap map = new TileMap(20, 5);
        
        try {
            BufferedImage img = ImageIO.read(HomePage.class.getResourceAsStream("/images/jarLogo.png"));
            map.addToTileSet(1, new ImageTile(0, 0, true, img));
        } catch (IOException ex) {
            ex.printStackTrace();
            map.addToTileSet(1, new BasicColorTile(0, 0, true, Color.black, Color.red));
        }
        
        for(int i = 0; i < 20; i++){
            map.setTile(i, 4, 1);
        }
        
        map.buildMap();
        map.setLeftPlayerSpawnTile(0, 0);
        map.setRightPlayerSpawnTile(19, 0);
        
        Area a = new Area(map);
        Level l = new Level(new Area[]{a}, 0);
        l.loadPlayer(new Player());
        
        return l;
    }
}
