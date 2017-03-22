import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first = null;
    private Node last = null;
    private int dequeSize = 0;
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    public Deque() {
        
    }
    
    public static void main(String[] args) {
        
        // int n = Integer.parseInt(args[0]);
        Deque<String> myDeque = new Deque<String>();
       
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            myDeque.addLast(s);
        }
        
        /*
        while (!myDeque.isEmpty()) {
            String str = myDeque.removeFirst();
            System.out.printf("RemoveLast:%s, QueueSize=%d\n", str, myDeque.size());
        }
        */ 
        
        Iterator<String> i = myDeque.iterator();
        while (i.hasNext()) {
            String s = i.next();
            StdOut.println(s);
            // StdOut.printf("%s\n",s);
        }
    }
    
    public boolean isEmpty() {
        return (dequeSize == 0);
    }
    public int size() {
        return dequeSize;
    }
    public void addFirst(Item item) {
        
        // add a null item
        if (item == null)
            throw new java.lang.NullPointerException();
        
        Node oldFirst = first;
        Node newnode = new Node();
        newnode.item = item;
        newnode.next = oldFirst;
        newnode.prev = null;
        
        if (oldFirst == null) // empty at first
            last = newnode;
        else
            oldFirst.prev = newnode;
        
        first = newnode;
        dequeSize++;
    }
    public void addLast(Item item) {
        
        // add a null item
        if (item == null)
            throw new java.lang.NullPointerException();
        
        Node oldLast = last;
        Node newnode = new Node();
        newnode.item = item;
        newnode.next = null;
        newnode.prev = oldLast;
        
        if (oldLast == null) // empty at first
            first = newnode;
        else
            oldLast.next = newnode;
        
        last = newnode;
        dequeSize++;
    }
    public Item removeFirst() {
        
        // remove from empty deque
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        
        Node oldFirst = first;
        first = oldFirst.next;
        if (first == null)
            last = null;
        else
            first.prev = null;
        
        dequeSize--;
        return oldFirst.item;
    }
    public Item removeLast() {
        
        // remove from empty deque
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        
        Node oldLast = last;
        last = oldLast.prev;
        
        if (last == null)
            first = null;
        else
            last.next = null;
        
        dequeSize--;
        return oldLast.item;
    }
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {  // not supported
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (current == null)
                throw new java.util.NoSuchElementException();        
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}