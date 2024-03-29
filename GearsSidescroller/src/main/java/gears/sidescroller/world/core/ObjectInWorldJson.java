package gears.sidescroller.world.core;

import gears.sidescroller.loader.JsonResourceType;
import gears.sidescroller.util.Direction;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.*;
import gears.sidescroller.world.machines.*;
import java.awt.Color;
import javax.json.*;

/**
 *
 * @author Matt Crow 
 */
public class ObjectInWorldJson implements JsonResourceType<WorldObject> {

    @Override
    public WorldObject deserialize(JsonObject json) {
        WorldObject obj = null;
        
        switch(json.getString("type")){
            case "Player": {
                obj = deserializePlayer(json);
                break;
            }
            case "GearItem": {
                obj = deserializeGearItem(json);
                break;
            }
            case "LanternItem": {
                obj = deserializeLanternItem(json);
                break;
            }
            case "ConveyorBeltSegment": {
                obj = deserializeConveyorBeltSegment(json);
                break;
            }
            case "Door": {
                obj = deserializeDoor(json);
                break;
            }
            case "GearMachine": {
                obj = deserializeGearMachine(json);
                break;
            }
            case "LanternMachine": {
                obj = deserializeLanternMachine(json);
                break;
            }
            case "PowerPlant": {
                obj = deserializePowerPlant(json);
                break;
            }
            default: {
                throw new JsonException("Unsupported value for JSON type: " + json.getString("type"));
            }
        }
        
        return obj;
    }

    private WorldObject deserializePlayer(JsonObject json) {
        Player p = new Player();
        p.setX(json.getInt("x"));
        p.setY(json.getInt("y"));
        return p;
    }

    private WorldObject deserializeGearItem(JsonObject json) {
        return new GearItem(
                json.getInt("x"),
                json.getInt("y"),
                new Color(json.getInt("color"), true) // has alpha
        );
    }
    
    private WorldObject deserializeLanternItem(JsonObject json){
        return new LanternItem(
                json.getInt("x"),
                json.getInt("y"),
                new LightLevel(json.getInt("lightLevel"))
        );
    }

    private WorldObject deserializeConveyorBeltSegment(JsonObject json) {
        return new ConveyorBeltSegment(
                json.getInt("x"),
                json.getInt("y"),
                json.getBoolean("isSelfPowering"),
                json.getInt("speed"),
                Direction.fromName(json.getString("movesStuffInThisDirection"))
        );
    }

    private WorldObject deserializeGearMachine(JsonObject json) {
        return new GearMachine(json.getInt("x"), json.getInt("y"));
    }
    
    private WorldObject deserializeLanternMachine(JsonObject json){
        return new LanternMachine(
                json.getInt("x"),
                json.getInt("y"),
                json.getBoolean("isSelfPowering"), 
                new LightLevel(json.getInt("lightLevel"))
        );
    }
    
    private WorldObject deserializeDoor(JsonObject json) {
        return new Door(json.getInt("x"), json.getInt("y"));
    }

    private WorldObject deserializePowerPlant(JsonObject json) {
        return new PowerPlant(json.getInt("x"), json.getInt("y"));
    }
}
