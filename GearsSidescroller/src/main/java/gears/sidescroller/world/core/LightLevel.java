package gears.sidescroller.world.core;

/**
 * a LightLevel is used to encapsulate details about the world lighting system
 * 
 * a light value of 0 is complete darkness,
 * while a light value of 255 is the maximum brightness
 * 
 * @author Matt Crow 
 */
public class LightLevel {
    private final byte value; // unsigned byte
    
    public static final LightLevel NO_LIGHT = new LightLevel(0);
    
    public LightLevel(byte value){
        this.value = value;
    }
    
    public LightLevel(int intValue){
        if(intValue < 0){
            throw new IllegalArgumentException(String.format("cannot have negative light level: %d", intValue));
        }
        if(intValue > 255){
            throw new IllegalArgumentException(String.format("cannot have light level above 255: %d", intValue));
        }
        value = (byte)intValue;
    }
    
    /**
     * rounds intValue so if within the valid range of light values
     * @param intValue the raw light level
     * @return a lightlevel using the raw value, or a valid value if it's invalid
     */
    public static LightLevel capped(int intValue){
        if(intValue < 0){
            intValue = 0;
        } else if(intValue > 255){
            intValue = 255;
        }
        return new LightLevel(intValue);
    }
    
    public byte getValue(){
        return value;
    }
    
    public int getIntValue(){
        return Byte.toUnsignedInt(value);
    }
    
    @Override
    public String toString(){
        return Integer.toString(getIntValue());
    }
}
