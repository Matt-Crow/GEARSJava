package gears.sidescroller.start;

import gears.io.FileSelectorUtil;
import gears.sidescroller.gui.GameFrame;
import gears.sidescroller.world.TileMap;
import gears.sidescroller.world.TileMapIO;
import gears.sidescroller.world.tiles.BasicColorTile;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Matt
 */
public class GameMain {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        new GameFrame();
        /*
        String name = JOptionPane.showInputDialog("Enter a name for the tile map:");
        FileSelectorUtil.chooseCreateFile("Where do you want to save the tile map?", name, (file)->{
            TileMap map = new TileMap(5, 5);
            map.addToTileSet(1, new BasicColorTile(0, 0, Color.gray, Color.yellow));
            for(int i = 0; i < 5; i++){
                map.setTile(i, i, 1);
            }
            try {
                TileMapIO.writeTileMapCsv(new FileOutputStream(file), map);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });*/
    }
    
}
