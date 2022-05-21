package gears.sidescroller.loader;

import javax.json.JsonObject;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 * @param <T>
 */
public interface JsonResourceType<T extends JsonSerializable> {    
    public T deserialize(JsonObject json);
}
