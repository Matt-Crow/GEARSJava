package gears.sidescroller.world.core;

import gears.sidescroller.loader.JsonResourceType;
import gears.sidescroller.util.Direction;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.items.GearItem;
import gears.sidescroller.world.machines.ConveyorBeltSegment;
import gears.sidescroller.world.machines.GearMachine;
import gears.sidescroller.world.machines.PowerPlant;
import java.awt.Color;
import javax.json.*;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class ObjectInWorldJson implements JsonResourceType<ObjectInWorld> {

    @Override
    public ObjectInWorld deserialize(JsonObject json) {
        ObjectInWorld obj = null;
        
        switch(json.getString("type")){
            case "Player": {
                obj = deserializePlayer(json);
                break;
            }
            case "GearItem": {
                obj = deserializeGearItem(json);
                break;
            }
            case "ConveyorBeltSegment": {
                obj = deserializeConveyorBeltSegment(json);
                break;
            }
            case "GearMachine": {
                obj = deserializeGearMachine(json);
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

    private ObjectInWorld deserializePlayer(JsonObject json) {
        Player p = new Player();
        p.setX(json.getInt("x"));
        p.setY(json.getInt("y"));
        return p;
    }

    private ObjectInWorld deserializeGearItem(JsonObject json) {
        return new GearItem(
                json.getInt("x"),
                json.getInt("y"),
                new Color(json.getInt("color"), true) // has alpha
        );
    }

    private ObjectInWorld deserializeConveyorBeltSegment(JsonObject json) {
        return new ConveyorBeltSegment(
                json.getInt("x"),
                json.getInt("y"),
                json.getBoolean("isSelfPowering"),
                json.getInt("speed"),
                Direction.fromName(json.getString("movesStuffInThisDirection"))
        );
    }

    private ObjectInWorld deserializeGearMachine(JsonObject json) {
        return new GearMachine(json.getInt("x"), json.getInt("y"));
    }

    private ObjectInWorld deserializePowerPlant(JsonObject json) {
        return new PowerPlant(json.getInt("x"), json.getInt("y"));
    }
}
