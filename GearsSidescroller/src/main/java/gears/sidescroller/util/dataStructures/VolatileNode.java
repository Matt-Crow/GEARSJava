package gears.sidescroller.util.dataStructures;

/**
 * VolatileNodes are used by a VolatileLinkedList to store its contents.
 * @author Matt
 */
class VolatileNode<T> {
    private final VolatileLinkedList<T> parent;
    private VolatileNode<T> prev;
    private VolatileNode<T> next;
    private final T value;
    private boolean hasBeenDeleted;
    
    VolatileNode(VolatileLinkedList<T> parent, T value){
        this.parent = parent;
        this.value = value;
        prev = null;
        next = null;
        hasBeenDeleted = false;
        
        if(value instanceof Removable){
            ((Removable)value).addRemovalListener((i)->{
                if(i.equals(value)){
                    delete();
                }
            });
        }
    }
    
    public final T getValue(){
        return value;
    }
    
    protected final void setNext(VolatileNode<T> next){
        this.next = next;
    }
    protected final VolatileNode<T> getNext(){
        return next;
    }
    protected final void setPrev(VolatileNode<T> prev){
        this.prev = prev;
    }
    protected final VolatileNode<T> getPrev(){
        return prev;
    }
    
    private void doDelete(){
        if(prev == null){
            // has no previous, so this is the head.
            parent.notifyHeadDelete();
        } else {
            prev.setNext(next);
        }
        
        if(next == null){
            // has no next, so this is the tail.
            parent.notifyTailDelete();
        } else {
            next.setPrev(prev);
        }
        
        hasBeenDeleted = true;
    }
    
    /**
     * In-place deletion from the list. Generally speaking, this will be invoked by the RemovalListener in this constructor
     */
    protected final void delete(){
        if(hasBeenDeleted){
            System.err.println("Trying to re-delete an object in VolatileNode");
        } else {
            doDelete();
        }
    }
}
