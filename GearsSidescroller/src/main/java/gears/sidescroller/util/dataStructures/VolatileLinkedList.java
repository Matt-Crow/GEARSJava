package gears.sidescroller.util.dataStructures;

import java.util.function.Consumer;

/**
 *
 * @author Matt
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
        nn.setNext(head);
        head = nn;
        // insert before head to prevent issues while iterating
        if(tail == null){
            tail = nn;
        }
    }
    
    public final void forEach(Consumer<T> doThis){
        VolatileNode<T> curr = head;
        while(curr != null){
            doThis.accept(curr.getValue());
            curr = curr.getNext();
        }
    }
    
    protected final void notifyHeadDelete(){
        if(head == null){
            throw new UnsupportedOperationException("Only head should invoke this method");
        }
        head = head.getNext();
    }
    
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
        VolatileLinkedList<Integer> list = new VolatileLinkedList<>();
        for(int i = 1; i < 5; i++){
            list.add(i);
            System.out.println(list);
        }
        
        list.forEach((i)->list.add(i*i));
        System.out.println(list);
    }
}
