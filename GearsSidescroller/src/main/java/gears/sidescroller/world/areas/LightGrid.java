package gears.sidescroller.world.areas;

import gears.sidescroller.util.Matrix;
import gears.sidescroller.world.core.Illuminating;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

/**
 * The LightGrid is used to apply dynamic lighting to an Area
 * 
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class LightGrid extends Matrix<Byte>{
    private final byte ambientLightLevel; // unsigned byte
    
    public LightGrid(int width, int height, byte ambientLightLevel) {
        super(width, height);
        this.ambientLightLevel = ambientLightLevel;
        setAllTo(this.ambientLightLevel);
    }
    
    public void update(Collection<Illuminating> lightSources){
        setAllTo(ambientLightLevel);
        lightSources.forEach((light)->{
            applyLightFrom(light);
        });
    }
    
    private void applyLightFrom(Illuminating lightSource){
        final int x0 = lightSource.getLightCenterX();
        final int y0 = lightSource.getLightCenterY();
        final int r = lightSource.getIlluminationRange();
        final int w = getWidthInCells();
        final int h = getHeightInCells();
        byte oldLight;
        byte newLight;
        
        for(int x = x0 - r; x <= x0 + r && x < w; ++x){
            for(int y = y0 - r; y <= y0 + r && y < h; ++y){
                oldLight = get(x, y);
                newLight = lightSource.getIlluminationAtDistance(
                        Math.abs(x - x0) + Math.abs(y - y0)
                );
                if(Byte.compareUnsigned(newLight, oldLight) > 0){
                    set(x, y, newLight);
                }
            }
        }
    }
    
    public void draw(Graphics g){
        forEachCell((lightLevel, xIdx, yIdx)->{
            int i = Byte.toUnsignedInt(lightLevel);
            Color c = new Color(0, 0, 0, 255 - i);
            g.setColor(c);
            g.fillRect(xIdx * TILE_SIZE, yIdx * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        });
    }
}
