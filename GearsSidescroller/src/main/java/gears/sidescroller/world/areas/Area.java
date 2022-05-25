package gears.sidescroller.world.areas;

import gears.sidescroller.loader.JsonSerializable;
import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.world.core.*;
import gears.sidescroller.world.entities.Player;
import gears.sidescroller.world.machines.AbstractMachine;
import gears.sidescroller.world.tileMaps.TileMap;
import java.awt.Graphics;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import javax.json.*;

/**
 * An Area is a complete, playable world where a Player can explore. Areas are
 * contained within a Level, and contain many things to interact with.
 *
 * @author Matt Crow
 */
public class Area implements JsonSerializable {

    private final TileMap tileMap;
    private final PowerGrid powerGrid;
    private final LightGrid lightGrid;
    private final Set<ObjectInWorld> objects;

    /**
     * Creates a new Area with the given TileMap
     *
     * @param t the TileMap for this Area
     */
    public Area(TileMap t) {
        tileMap = t;
        powerGrid = new PowerGrid(t.getWidthInCells(), t.getHeightInCells());
        lightGrid = new LightGrid(t.getWidthInCells(), t.getHeightInCells(), (byte) 15);
        objects = new HashSet<>();
    }

    /**
     * Adds the given ObjectInWorld to this Area. Note that this method does not
     * alter the object's coordinates. This automatically invokes
     * {@code obj.setArea(this)}, so you needn't worry about that.
     *
     * @param obj the ObjectInWorld to add to this Area.
     *
     * @return this, for chaining purposes
     */
    public Area addToWorld(ObjectInWorld obj) {
        objects.add(obj);
        obj.setArea(this);
        return this;
    }

    /**
     * Removes the given ObjectInWorld from this Area, if it is present.
     *
     * @param obj the ObjectInWorld to remove from this Area.
     *
     * @return this, for chaining purposes.
     */
    public Area removeFromWorld(ObjectInWorld obj) {
        objects.remove(obj);
        return this;
    }

    /**
     * Use this method if you need to check for open tiles or other static
     * environment information.
     *
     * @return this Area's TileMap.
     */
    public final TileMap getTileMap() {
        return tileMap;
    }

    /**
     * Notifies all the interactable objects in the Area that a Player has
     * interacted, causing them to act as appropriate.
     *
     * @param p the Player who interacted with this Area
     */
    public final void playerInteracted(Player p) {
        each((obj) -> obj instanceof Interactable, (obj) -> (Interactable) obj, (i) -> i.interactWith(p));
    }

    /**
     * Updates this Area and everything within it. This method is invoked once
     * per frame by Level.
     *
     * @return this, for chaining purposes.
     */
    public Area update() {
        Collection<AbstractMachine> machines = objects.stream().filter((obj) -> {
            return obj instanceof AbstractMachine;
        }).map((obj) -> {
            return (AbstractMachine) obj;
        }).collect(Collectors.toSet());
        powerGrid.update(machines);

        Collection<Illuminating> lights = objects.stream().filter((obj) -> {
            return obj instanceof Illuminating;
        }).map((obj) -> {
            return (Illuminating) obj;
        }).collect(Collectors.toSet());
        lightGrid.update(lights);

        each((obj) -> obj instanceof MobileWorldObject, (obj) -> (MobileWorldObject) obj, (e) -> {
            if (e instanceof AbstractEntity) {
                ((AbstractEntity) e).update();
            }
            tileMap.checkForCollisions(e);

            each((obj) -> obj instanceof Collidable, (obj) -> (Collidable) obj, (c) -> {
                if (!c.equals(e)) { // don't collide with self
                    c.checkForCollisions(e);
                }
            });
        });

        machines.forEach((m) -> {
            if (m.isPowered()) {
                m.update();
            }
        });

        return this;
    }

    /**
     * Renders this Area and everything within it.
     *
     * @param g the Graphics to render this on.
     *
     * @return this, for chaining purposes.
     */
    public Area draw(Graphics g) {
        tileMap.draw(g);
        objects.forEach((obj) -> obj.draw(g));
        powerGrid.draw(g);
        lightGrid.draw(g);

        return this;
    }

    /**
     * performs an operation on each object in this Area that meets a criteria
     * note that this iterates over a shallow copy of the objects collection, so
     * it won't face concurrent modifications
     *
     * @param <T> the type matching objects will be cast to
     * @param matching the criteria
     * @param mapper converts matching objects to T
     * @param doThis runs on matching objects after conversion
     */
    private <T> void each(Predicate<ObjectInWorld> matching, Function<ObjectInWorld, T> mapper, Consumer<T> doThis) {
        new HashSet<>(objects).stream().filter(matching).map(mapper).forEach(doThis);
    }

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("tileMap", tileMap.toJson());

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        objects.forEach((e) -> arrayBuilder.add(e.toJson()));
        builder.add("objects", arrayBuilder.build());

        return builder.build();
    }
}
