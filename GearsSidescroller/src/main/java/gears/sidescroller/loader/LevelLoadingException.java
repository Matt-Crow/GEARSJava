package gears.sidescroller.loader;

/**
 * this exception is thrown by LevelLoaders when they cannot load or save a
 * Level
 * 
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class LevelLoadingException extends RuntimeException {
    
    public LevelLoadingException(){
        super();
    }
    
    public LevelLoadingException(String message){
        super(message);
    }
    
    public LevelLoadingException(Exception nestedException){
        super(nestedException);
    }
    
    public LevelLoadingException(String message, Exception nestedException){
        super(message, nestedException);
    }
}
