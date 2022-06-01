package gears.sidescroller.loader;

/**
 * this exception is thrown by LevelLoaders when they cannot load or save a
 * Level
 * 
 * @author Matt Crow 
 */
public class LevelLoadingException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
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
