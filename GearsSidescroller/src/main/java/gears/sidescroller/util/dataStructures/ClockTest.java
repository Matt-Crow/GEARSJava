package gears.sidescroller.util.dataStructures;

import java.util.LinkedList;
import java.util.function.Consumer;

/**
 *
 * @author Matt
 */
public class ClockTest implements Removable<ClockTest> {
    private final LinkedList<RemovalListener<ClockTest>> removalListeners;
    private int timeLeft;
    
    public ClockTest(int timeLeft){
        removalListeners = new LinkedList<>();
        this.timeLeft = timeLeft;
    }
    
    public final int getTime(){
        return timeLeft;
    }
    
    @Override
    public void addRemovalListener(RemovalListener<ClockTest> listener) {
        removalListeners.add(listener);
    }

    @Override
    public void forEachRemovalListener(Consumer<RemovalListener<ClockTest>> doThis) {
        removalListeners.forEach(doThis);
    }
    
    public void tick(){
        System.out.printf("Clock#%d: %d...%n", this.hashCode(), timeLeft);
        timeLeft--;
        if(timeLeft == 0){
            System.out.printf("Clock#%d is ringing!%n", this.hashCode());
            remove();
        }
    }
    
    @Override
    public String toString(){
        return String.format("Clock with %d ticks left", timeLeft);
    }
}
