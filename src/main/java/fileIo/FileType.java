package fileIo;

/**
 *
 * @author Matt Crow
 */
public enum FileType {
    IMAGE("Image", new String[]{"PNG", "JPEG", "GIF"}),
    DIR("Directory", new String[]{});
    
    private final String name;
    private final String[] extensions;
    
    FileType(String n, String[] exts){
        name = n;
        extensions = exts.clone();
    }
    
    public String getName(){
        return name;
    }
    
    public String[] getExtensions(){
        return extensions.clone();
    }
}
