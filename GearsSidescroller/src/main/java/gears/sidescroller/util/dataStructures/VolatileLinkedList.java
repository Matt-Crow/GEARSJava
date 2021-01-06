package gears.sidescroller.util.dataStructures;

import java.util.function.Consumer;

/**
 * A VolatileLinkedList is used to store a list of items which are expected to be removed at some point.
 * 
 * Items are inserted in backwards order, so items added during iteration are not iterated over during that iteration.
 * Items may be removed from this list during iteration without interrupting the iteration.
 * 
 * Currently, this is O(1) for both insertions and in-place-deletions, but only O(n) for searching and invoking the delete() method. 
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
    
    /**
     * Deletes the given item from the list,
     * if this contains it. Note this method
     * is O(n). While I'd prefer it to be O(1),
     * that isn't possible with a linked list.
     * 
     * @param value the value to remove from the list. 
     */
    public final void delete(T value){
        VolatileNode<T> curr = head;
        while(curr != null){
            if(curr.getValue().equals(value)){
                curr.delete();
                curr = null;
            } else {
                curr = curr.getNext();
            }
        }
    }
    
    public final void forEach(Consumer<T> doThis){
        VolatileNode<T> curr = head;
        while(curr != null){
            doThis.accept(curr.getValue());
            curr = curr.getNext();
        }
    }
    
    public final Object[] toArray(){
        Object[] ret = new Object[getSize()]; // can't do generic array
        int idx = ret.length - 1; // go backwards
        VolatileNode<T> curr = head;
        while(curr != null){
            ret[idx] = curr.getValue();
            idx--;
            curr = curr.getNext();
        }
        return ret;
    }
    
    public final int getSize(){
        int ret = 0;
        VolatileNode<T> curr = head;
        while(curr != null){
            ret++;
            curr = curr.getNext();
        }
        return ret;
    }
    
    public final boolean isEmpty(){
        return head == null;
    }
    
    /**
     * Fired by the head when it is deleted.
     */
    protected final void notifyHeadDelete(){
        if(head == null){
            //throw new UnsupportedOperationException("Only head should invoke this method");
        } else {
            head = head.getNext();
        }
    }
    
    /**
     * Fired by the tail when it is deleted.
     */
    protected final void notifyTailDelete(){
        if(tail == null){
            //throw new UnsupportedOperationException("Only tail should invoke this method");
        } else {
            tail = tail.getPrev();
        }
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
        /*
        int time = 0;
        while(!list.isEmpty()){
            System.out.println(list);
            list.forEach((Integer i)->{
                if(i < time){
                    list.delete(i);
                }
            });
            time++;
        }*/
    }
}
