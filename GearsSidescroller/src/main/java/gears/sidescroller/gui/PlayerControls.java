package gears.sidescroller.gui;

import gears.sidescroller.world.entities.Player;
import java.awt.event.KeyEvent;

/**
 *
 * @author Matt
 */
public class PlayerControls extends EntityControls {

    public PlayerControls(Player controlledEntity) {
        super(controlledEntity);
    }
    
    @Override
    public void registerTo(LevelPage p){
        super.registerTo(p);
        p.registerKey(KeyEvent.VK_I, true, ()->{
            if(!((Player)this.getContolledEntity()).useItem(0, p.getCurrentLevel().getCurrentArea())){
                System.out.println("From PlayerControls: Cannot use item here");
            }
        });
    }
}
