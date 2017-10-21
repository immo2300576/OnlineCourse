import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Random;

public class KargerMinCut {
    // nodeList: 1, 2, 3, ..., nodeCount
    private Edge[] edgeList;
    private int nodeCount;
    private int edgeCount;
    private int edgeCapacity = 0;
        
    private class Edge { // edge(n1, n2) assert n1 < n2
        int node1;
        int node2;
    }
    
    public KargerMinCut(int n) {
        this.edgeList = new Edge[n/2]; // every node has at least 1 edge
        this.nodeCount = n;
        this.edgeCount = 0;
        this.edgeCapacity = n/2;
    }
    public KargerMinCut(KargerMinCut that) {
        this.nodeCount = that.nodeCount;
        this.edgeCount = that.edgeCount;
        this.edgeCapacity = that.edgeCapacity;
        this.edgeList = new Edge[that.edgeCount];
        for (int i = 0; i < edgeCount; i++) {
            this.edgeList[i] = new Edge();
            this.edgeList[i].node1 = that.edgeList[i].node1;
            this.edgeList[i].node2 = that.edgeList[i].node2;
        }
    }
    public static void main(String[] args) {
        // Initialize the gragh
        In in = new In(args[0]);
        int n = in.readInt();
        KargerMinCut obj = new KargerMinCut(n);
        String line = in.readLine();
        String[] nums;
        
        int index0 = 0;
        int index = 0;
        for (int i = 0; i < n; i++) {
            line = in.readLine();
            // StdOut.printf("First Line is %s\n", line);
            nums = obj.getNums(line);
            index0 = Integer.valueOf(nums[0]);
            for (int j = 1; j < nums.length; j++) {
                index = Integer.valueOf(nums[j]);
                obj.addEdge(index0, index);
            }
        }
        
        // obj.printGraph();
        // calucalte min cut
        // obj.printGraph();
        int count = obj.kargerMinCut();
            
        
        StdOut.printf("From KargerMinCut Method: the minimum cut number is %d\n", count);
    }
    public int kargerMinCut() {
        return calculation();
    }
    private int calculation() {
        
        // repeat until there are only two nodes left
        Random rn = new Random();
        while (nodeCount > 2) {
            // uniformly random chose one edge
            int n = rn.nextInt(edgeCount); 
            // StdOut.printf("Choose %d from (0,%d)\n", n, edgeCount);
            int node1 = edgeList[n].node1;
            int node2 = edgeList[n].node2;
            // StdOut.printf("Edge from %d --> %d is removed\n", node1, node2);
            // remove the edge
            edgeList[n] = edgeList[edgeCount-1];
            edgeCount--;
            // merge node1 and node2 into single node
            // delete self-loops
            // StdOut.printf("Delete node %d\n", node2);
            for (int i = 0; i < edgeCount; i++) {
                if (edgeList[i].node1 == node2)
                    edgeList[i].node1 = node1;
                else if (edgeList[i].node2 == node2) {
                    int tmp = edgeList[i].node1;
                    if (tmp < node1) {
                        edgeList[i].node1 = tmp;
                        edgeList[i].node2 = node1;
                    }
                    else if (tmp > node1){
                        edgeList[i].node1 = node1;
                        edgeList[i].node2 = tmp;
                    }
                    else { // self-loops
                        // exchange with last edge
                        edgeList[i].node1 = edgeList[edgeCount-1].node1;
                        edgeList[i].node2 = edgeList[edgeCount-1].node2;
                        i--;
                        edgeCount--;
                    }
                }    
            }     
            nodeCount--;
            /*
            StdOut.printf("Debug EdgeList:\n");
            for (int i = 0; i < edgeCount; i++)
                StdOut.printf("[Edge %d --> %d] ", edgeList[i].node1, edgeList[i].node2);
            StdOut.println();
            */
        }
        return edgeCount;
    }
    public void addEdge(int n1, int n2) {
        Edge newEdge = new Edge();
        if (n1 > n2) {
            newEdge.node1 = n2;
            newEdge.node2 = n1;
        }
        else {
            newEdge.node1 = n1;
            newEdge.node2 = n2;
        }
        
        boolean isRepeat = false;
        for (int i = 0; i < edgeCount; i++)
            if (edgeList[i].node1 == newEdge.node1 && edgeList[i].node2 == newEdge.node2) {
                isRepeat = true;
                break;
            }
        
        if (isRepeat == false) {
            edgeList[edgeCount++] = newEdge;
            // StdOut.printf("Add Edge %d --> %d \n", newEdge.node1, newEdge.node2);
            if (edgeCount == edgeCapacity)
                resize(2*edgeCapacity);
        }
    }
    private void resize(int size) {
        Edge[] copy = new Edge[size];
        for (int i = 0; i < edgeCapacity; i++)
            copy[i] = edgeList[i];
        edgeCapacity = size;
        edgeList = copy;
    }
    public  void printGraph() {
        StdOut.printf("There are %d nodes: \n", nodeCount);
        for (int i = 0; i < edgeCount; i++)
            StdOut.printf("Edge connects %d --> %d \n", edgeList[i].node1, edgeList[i].node2);
    }
    public String[] getNums(String str) {
        String[] stack = new String[200];
        
        int head = 0;
        int tail = 0;
        int index = 0;
        boolean isNum = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                tail++;
                isNum = true;
            }
            else if (isNum){
                stack[index++] = str.substring(head, tail);
                head = i+1;
                tail = i+1;
                isNum = false;
            }
            else {
                head++;
                tail++;
            }
        }
        
        if (isNum)
            stack[index++] = str.substring(head, tail);
        
        String[] copy = new String[index];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = stack[i];
            // StdOut.printf("%s ", copy[i]);
        }
        // StdOut.println();
        return copy;
    }
}