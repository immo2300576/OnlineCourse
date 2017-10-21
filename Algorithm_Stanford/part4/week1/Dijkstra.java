import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class Dijkstra {
    private BinaryHeap graph;
    private Vertex[] vertexList;
    private int[] minDist;
    private boolean debug = false;
    
    private class Vertex {
        private Stack<EdgeInfo> edgeList;
        
    }
    private class EdgeInfo {
        private int nextNode;
        private int length;
        private EdgeInfo(int id, int length) {
            this.nextNode = id;
            this.length = length;
        }
    }
    
    public static void main(String[] args) {  
    }
    public Dijkstra(int n, Edge[] edges) {
        graph = new BinaryHeap(n);
        vertexList = new Vertex[n];
        minDist = new int[n];
        
        for (int i = 0; i < n; i++) {
            vertexList[i] = new Vertex();
            vertexList[i].edgeList = new Stack<EdgeInfo>();
        }
        
        for (int i = 0; i < edges.length; i++) {
            int head = edges[i].head;
            int tail = edges[i].tail;
            int cost = edges[i].cost;
            
            EdgeInfo e = new EdgeInfo(tail, cost);
            vertexList[head-1].edgeList.push(e);
        }
        
    }
    public Edge[] calculation(int n) {
        
        // initial the graph (V-X), X = source
        for (int i = 0; i < vertexList.length; i++) {
            if (i == n) // use vertex 1 as source
                minDist[i] = 0;
            else
                minDist[i] = 1000000;
            graph.insert(i+1, minDist[i]);
        }
        // StdOut.printf("Finish initializing graph (Size = %d)\n", graph.size());

        while (graph.size()!=0) {
            // graph.printBinaryHeap();
            int extractNode = graph.extractMin();
            if (debug)
                StdOut.printf("Graph size = %d, extract No. %d, length = %d\n", graph.size(), extractNode, minDist[extractNode-1]);
            update(extractNode);
        }
        
        Edge[] minPath = new Edge[vertexList.length];
        for (int i = 0; i < vertexList.length; i++)
            minPath[i] = new Edge(n+1, i+1, minDist[i]);
        
        
        return minPath;
    }
    private void update(int nodeID) {
        // StdOut.printf("Update node %d\n", nodeID);
        for (EdgeInfo e : vertexList[nodeID-1].edgeList) {
            int nextID = e.nextNode;
            int originLength = minDist[nextID-1];
            if (minDist[nextID-1] > minDist[nodeID-1] + e.length) {
                minDist[nextID-1] = minDist[nodeID-1] + e.length;
                // StdOut.printf("Node update: (%d) length %d better than original length %d\n", nextID, minDist[nextID-1], originLength);
                graph.update(nextID, minDist[nextID-1]);
            }  
        }
        
    }
}