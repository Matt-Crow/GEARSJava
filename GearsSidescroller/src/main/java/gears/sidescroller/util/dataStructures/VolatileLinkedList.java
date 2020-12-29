package gears.sidescroller.util.dataStructures;

import java.util.function.Consumer;

/**
 * A VolatileLinkedList is used to store a list of items which are expected to be removed at some point.
 * 
 * Items are inserted in backwards order, so items added during iteration are not iterated over during that iteration.
 * Items may be removed from this list during iteration without interrupting the iteration.
 * 
 * @author Matt Crow
 * @param <T> the type of element which will be stored in each node
 */
public class VolatileLinkedList<T> {
    private VolatileNode<T> head;
    private VolatileNode<T> tail;
    
    public VolatileLinkedList(){
        super();
        head = null;
        tail = null;
    }
    
    public final void add(T value){
        VolatileNode<T> nn = new VolatileNode<>(this, value);
        if(head == null){
            tail = nn; // nn is the only node
        } else {
            head.setPrev(nn);
        }
        nn.setNext(head);
        head = nn;
        // insert before head to prevent issues while iterating
    }
    
    public final void forEach(Consumer<T> doThis){
        VolatileNode<T> curr = head;
        while(curr != null){
            doThis.accept(curr.getValue());
            curr = curr.getNext();
        }
    }
    
    public final boolean isEmpty(){
        return head == null;
    }
    
    /**
     * Fired by the head when it is deleted.
     */
    protected final void notifyHeadDelete(){
        if(head == null){
            throw new UnsupportedOperationException("Only head should invoke this method");
        }
        head = head.getNext();
    }
    
    /**
     * Fired by the tail when it is deleted.
     */
    protected final void notifyTailDelete(){
        if(tail == null){
            throw new UnsupportedOperationException("Only tail should invoke this method");
        }
        tail = tail.getPrev();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        VolatileNode<T> curr = head;
        while(curr != null){
            sb.append(curr.getValue());
            curr = curr.getNext();
            if(curr != null){ // operating at node level, not value, so can't use forEach
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args){
        VolatileLinkedList<ClockTest> list = new VolatileLinkedList<>();
        for(int i = 1; i < 5; i++){
            list.add(new ClockTest(i));
            System.out.println(list);
        }
        
        list.forEach((i)->list.add(new ClockTest(i.getTime() * i.getTime())));
        System.out.println(list);
        
        while(!list.isEmpty()){
            System.out.println(list);
            list.forEach((clock)->clock.tick());
        }
    }
}
