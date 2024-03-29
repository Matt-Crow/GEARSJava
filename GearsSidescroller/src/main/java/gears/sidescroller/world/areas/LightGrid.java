package gears.sidescroller.world.areas;

import gears.sidescroller.util.Matrix;
import gears.sidescroller.world.core.Illuminating;
import gears.sidescroller.world.core.LightLevel;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

/**
 * The LightGrid is used to apply dynamic lighting to an Area
 * 
 * @author Matt Crow 
 */
public class LightGrid extends Matrix<Byte>{
    private final LightLevel ambientLightLevel;
    
    public LightGrid(int width, int height, LightLevel ambientLightLevel) {
        super(width, height);
        this.ambientLightLevel = ambientLightLevel;
        setAllTo(ambientLightLevel.getValue());
    }
    
    public void update(Collection<Illuminating> lightSources){
        setAllTo(ambientLightLevel.getValue());
        lightSources.forEach((light)->{
            applyLightFrom(light);
        });
    }
    
    private void applyLightFrom(Illuminating lightSource){
        final int x0 = lightSource.getLightCenterXIdx();
        final int y0 = lightSource.getLightCenterYIdx();
        final int r = lightSource.getIlluminationRange();
        final int w = getWidthInCells();
        final int h = getHeightInCells();
        byte oldLight;
        byte newLight;
        
        for(int x = Math.max(x0 - r, 0); x <= x0 + r && x < w; ++x){
            for(int y = Math.max(y0 - r, 0); y <= y0 + r && y < h; ++y){
                oldLight = get(x, y);
                newLight = lightSource.getIlluminationAtDistance(
                        Math.abs(x - x0) + Math.abs(y - y0)
                ).getValue();
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
