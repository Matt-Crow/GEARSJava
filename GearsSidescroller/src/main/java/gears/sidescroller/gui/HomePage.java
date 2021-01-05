package gears.sidescroller.gui;

import gears.sidescroller.world.areas.Area;
import gears.sidescroller.world.tileMaps.TileMap;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.util.Direction;
import gears.sidescroller.world.items.GearItem;
import gears.sidescroller.world.machines.PowerGenerator;
import gears.sidescroller.world.levels.Level;
import gears.sidescroller.world.tiles.TileGenerator;
import gears.sidescroller.world.levels.LevelGenerator;
import gears.sidescroller.world.machines.ConveyorBelt;
import gears.sidescroller.world.machines.GearMachine;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
            LevelPage newPage = new LevelPage(
                getParentGamePane(),
                getDefaultWorld(),
                new Player()
            );
            getParentGamePane().switchToPage(newPage);
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
        //Level l = new LevelGenerator().generateRandom(3);
        TileMap t = new TileMap((byte)10, (byte)10);
        t.addToTileSet((byte)0, new TileGenerator().generateRandom(false));
        t.addToTileSet((byte)1, new TileGenerator().generateRandom(true));
        t.setTile((byte)5, (byte)5, (byte)1);
        PowerGenerator gen = new PowerGenerator(300, 300);
        ConveyorBelt belt = new ConveyorBelt(600, 300, false, TILE_SIZE / 10, Direction.LEFT);
        GearMachine gear = new GearMachine(700, 400);
        
        Area testArea = new Area(t);
        testArea.addMachine(gen);
        testArea.addMachine(belt);
        testArea.addMachine(gear);
        
        GearItem[] items = new GearItem[]{
            new GearItem(100, 100, Color.CYAN),
            new GearItem(200, 100, Color.CYAN),
            new GearItem(100, 200, Color.CYAN)
        };
        for(int i = 0; i < items.length; i++){
            testArea.addItem(items[i]);
        }
        
        Level l = new Level(new Area[][]{{testArea}}, 0, 0);
        l.init();
        
        return l;
    }
}
