import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BinaryHeap {
    //
    // Insert / Extract-Min operation O(logN)
    private class Node {
        private int key; // minCost
        private int vertexID; //
        public Node() {
        }
        public Node(int vertexID, int key) {
            this.vertexID = vertexID;
            this.key = key;
        }
    }
    private Node[] array;
    private int[] dictionary; // look for the array pos from the vertexID
    private int size = 0;
    private int capacity = 1;
    private boolean debug = false;
    public static void main(String[] args) {
        BinaryHeap obj = new BinaryHeap(0);
        
        for (int i = 100; i > 0; i--)
            obj.insert(i,i);
        
        obj.insert(-5, -5);
        obj.insert(-2, -2);
        obj.insert(-8, -8);
        obj.insert(-23, -23);
        obj.insert(500, 500);
        
        int output;
        
        while (obj.size() > 0) {
            output = obj.extractMin();
            
            StdOut.printf("Output: %d\n", output);
        }
    }
    
    public BinaryHeap(int n) {
        array = new Node[capacity];
        dictionary = new int[n];
    }
    public int size() {
        return size;    
    }
    public void update(int vertexID, int key) {

        int pos = dictionary[vertexID-1];
        
        if (debug)
            StdOut.printf("Update Array pos = %d, vertexID = %d, key = %d\n", pos, vertexID, key);
        
        if (pos > size-1) {
            StdOut.printf("Dictionary error\n");
            StdOut.printf("Update Array pos = %d, vertexID = %d, key = %d\n", pos, vertexID, key);
            
            // for (int i = 0; i < size; i++)
            //     StdOut.printf("Dictionary: VertexID: %d, Pos %d, check %d\n", array[i].vertexID, i, dictionary[array[i].vertexID-1]);
        }
        // delete first
        array[pos].key = key;
        bubbleUp(pos+1);
        
        if (debug)
            printBinaryHeap();      
    }
    public void insert(int vertexID, int key) {
        // insert at the next leaf and then bubble-up
        Node node = new Node(vertexID, key);
        // StdOut.printf("Inserting key %d @ size %d...\n", key, size+1);      
        array[size] = node;
        dictionary[vertexID-1] = size;
        size++;
        // bubble-up
        bubbleUp(size);
        
        if (size == capacity)
            resize();
    }
    public int extractMin() {
        int minID = array[0].vertexID;
        // exchange with the last leaf and then bubble-down
        exch(0, size-1);
        array[size-1] = null;
        size--;
        //bubbleDown the root
        bubbleDown(1);
        
        return minID;
    }
    private void bubbleUp(int index) {
        if (index == 1)
            return;
        else if (index%2 == 0) { // left children
            if (array[index-1].key < array[index/2-1].key) {
                exch(index-1, index/2-1);
                if (debug)
                    StdOut.printf("[BubbleUp] Vertex(left) %d(value=%d) exchanges with parent %d(%d)\n", array[index/2-1].vertexID, array[index/2-1].key, array[index-1].vertexID, array[index-1].key);
                bubbleUp(index/2);
            }
        }
        else { // right children
            if (array[index-1].key < array[(index-1)/2-1].key) {
                exch(index-1, (index-1)/2-1);
                if (debug)
                    StdOut.printf("[BubbleUp] Vertex(right) %d(value=%d) exchanges with parent %d(%d)\n", array[(index-1)/2-1].vertexID, array[(index-1)/2-1].key, array[index-1].vertexID, array[index-1].key);
                bubbleUp((index-1)/2);
            }
        }
    }
    private void bubbleDown(int index) {
        if (2*index > size) // no child
            return;
        else if (2*index+1 > size) { // one child
            if (array[index-1].key > array[2*index-1].key) {
                exch(index-1, 2*index-1);
                if (debug)
                    StdOut.printf("[BubbleDown] Vertex %d(value=%d) exchanges with left children %d(%d)\n", array[2*index-1].vertexID, array[2*index-1].key, array[index-1].vertexID, array[index-1].key);
            }
        }
        else { // two children
            int childIdx = 2*index;
            if (array[2*index-1].key > array[2*index].key)
                childIdx++;
            if (array[index-1].key > array[childIdx-1].key) {
                exch(index-1, childIdx-1);
                if (debug)
                    StdOut.printf("[BubbleDown] Vertex %d(value=%d) exchanges with left children %d(%d)\n", array[2*index-1].vertexID, array[2*index-1].key, array[index-1].vertexID, array[index-1].key);
                bubbleDown(childIdx);
            }    
        }
    }
    private void exch(int index1, int index2) {
        Node tmp = new Node();
        int vertex1 = array[index1].vertexID;
        int vertex2 = array[index2].vertexID;
        
        tmp.vertexID = array[index1].vertexID;
        tmp.key = array[index1].key;
        array[index1].vertexID = array[index2].vertexID;
        array[index1].key = array[index2].key;
        array[index2].vertexID = tmp.vertexID;
        array[index2].key = tmp.key;
        
        dictionary[vertex1-1] = index2;
        dictionary[vertex2-1] = index1;     
    }
    
    public void printBinaryHeap() {
        int top10 = 10;
        if (size < 10)
            top10 = size;
        for (int i = 0; i < top10; i++)
            StdOut.printf("Heap pos %d, VertexID %d, Key %d\n", i+1, array[i].vertexID, array[i].key);
    }
    private void resize() {
        Node[] copy = new Node[2*capacity];
        for (int i = 0; i < size; i++)
            copy[i] = array[i];
        
        capacity = 2*capacity;
        array = copy;
    }
}