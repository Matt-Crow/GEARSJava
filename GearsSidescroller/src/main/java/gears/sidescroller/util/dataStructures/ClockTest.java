package gears.sidescroller.util.dataStructures;

import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * This is an implementation example for the Removable interface
 * @author Matt
 */
public class ClockTest implements Removable {
    private final LinkedList<RemovalListener> removalListeners;
    private int timeLeft;
    
    public ClockTest(int timeLeft){
        removalListeners = new LinkedList<>();
        this.timeLeft = timeLeft;
    }
    
    public final int getTime(){
        return timeLeft;
    }
    
    @Override
    public void addRemovalListener(RemovalListener listener) {
        removalListeners.add(listener);
    }

    @Override
    public void forEachRemovalListener(Consumer<RemovalListener> doThis) {
        removalListeners.forEach(doThis);
    }
    
    public void tick(){
        System.out.printf("Clock#%d: %d...%n", this.hashCode(), timeLeft);
        timeLeft--;
        if(timeLeft == 0){
            System.out.printf("Clock#%d is ringing!%n", this.hashCode());
            this.removalListeners.forEach((l)->this.removeFrom(l));
        }
    }
    
    @Override
    public String toString(){
        return String.format("Clock with %d ticks left", timeLeft);
    }
}
