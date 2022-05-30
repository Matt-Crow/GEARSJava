package gears.sidescroller.loader;

import javax.json.JsonObject;

/**
 *
 * @author Matt Crow 
 * @param <T>
 */
public interface JsonResourceType<T extends JsonSerializable> {    
    public T deserialize(JsonObject json);
}
