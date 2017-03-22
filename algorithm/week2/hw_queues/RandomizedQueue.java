import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] s;
    private int head = 0;
    private int tail = 0;
    private int capacity = 1;
    
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> myRandque = new RandomizedQueue<String>();
       
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            myRandque.enqueue(s);
        }
        
        /*
        while (!myRandque.isEmpty()) {
            String str = myRandque.dequeue();
            StdOut.printf("Random Remove:%s, QueueSize=%d\n", str, myRandque.size());
        }
        */
        
        Iterator<String> i = myRandque.iterator();
        while (i.hasNext()) {
            String s = i.next();
            StdOut.println(s);
            // StdOut.printf("%s\n",s);
        }
    }
    
    private void resize(int newCapacity) {
        Item[] copy = (Item[]) new Object[newCapacity];
        for (int i = 0; i < (tail-head); i++)
            copy[i] = s[head+i];
        s = copy;
        tail = tail-head;
        head = 0;
    }
    public boolean isEmpty() {
        return (head == tail);
    }
    public int size() {
        return tail-head;
    }
    public void enqueue(Item item) {
        // add a null item
        if (item == null)
            throw new java.lang.NullPointerException();
        // add item at tail
        s[tail] = item;
        tail++;
        // double capacity if tail exceeds capacity
        if (tail == capacity) {
            capacity *= 2;
            resize(capacity);
        }        
    }
    public Item dequeue() {
        // remove from empty deque
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        // random pick one of the array index [head,tail)
        int pick = StdRandom.uniform(head, tail);
        Item pickItem = s[pick];
        // fill the array index with the head item 
        s[pick] = s[head];
        head++;
        // shrink array if array is one-quarter full 
        if (size() == capacity/4) {
           capacity = capacity/2;
           resize(capacity);
        }
        
        return pickItem;
    }
    public Item sample() {
        // sample from empty deque
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        // random pick one of the array index
        int pick = StdRandom.uniform(head, tail);
        return s[pick];
    }
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator<Item> {
        private int copyHead = head;
        private Item[] copy;
        private int remainSize = tail-head;
        
        private ArrayIterator() {
            copy = (Item[]) new Object[capacity];
            for (int j = head; j < tail; j++)
                copy[j] = s[j];
        }
        public boolean hasNext() {
            return remainSize > 0;
        }
        public void remove() {  // not supported
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (remainSize == 0)
                throw new java.util.NoSuchElementException();
            
            int pick = StdRandom.uniform(copyHead, tail);
            Item item = copy[pick];
            if (pick != copyHead)
                copy[pick] = copy[copyHead];
            copyHead++;
            remainSize--;
            
            return item;
        }
    }
    
}