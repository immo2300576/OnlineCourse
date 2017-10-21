import edu.princeton.cs.algs4.In;
import java.util.HashSet;

public class APSP { // all-pairs shortest path
    // Johnson's algorithm
    int[] vertexCost;
    int n;
    int m;
    Edge[] edges;
    
    public APSP(int n, int m) {
        this.vertexCost = new int[n];
        this.n = n;
        this.m = m;
        this.edges = new Edge[m];
        for (int i = 0; i < m; i++)
            this.edges[i] = new Edge(0, 0, 0);
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        
        int n = in.readInt();
        int m = in.readInt();     
        int tail, head, cost;
        APSP obj = new APSP(n, m);
        
        for (int i = 0; i < m; i++) {
            tail = in.readInt();
            head = in.readInt();
            cost = in.readInt();
            
            // create edge map
            obj.createEdge(i, head, tail, cost);
        }
        
        // Bellman-Ford calculate vertexCost
        // detect if there is a negative cycle
        if (!obj.bellmanFord()) {
            System.out.printf("Detect a negative cycle\n");
            return;
        }
        
        // re-weight
        // for each edge ce' = ce + vertexCost[u] - vertexCost[v];
        obj.reweight();
        
        Edge[] minDist;
        Edge minPath = new Edge(0, 0, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            // Dijkstra's algorithm for each vertex i
            Dijkstra dijk = new Dijkstra(n, obj.edges);
            minDist = dijk.calculation(i); // use vertex i as a source
            
            // return the true distance
            // d(u, v) = d'(u, v) - vertexCost[u] + vertexCost[v]
            for (int j = 0; j < n; j++) {
                minDist[j].cost = minDist[j].cost - obj.vertexCost[minDist[j].head-1] + obj.vertexCost[minDist[j].tail-1];
            
                if (minDist[j].cost < minPath.cost) {
                    minPath.cost = minDist[j].cost;
                    minPath.head = minDist[j].head;
                    minPath.tail = minDist[j].tail;           
                }
            }
        }
        
        System.out.printf("Minimum path is from %d to %d, dis = %d\n", minPath.head, minPath.tail, minPath.cost);
    }
    private void createEdge(int i, int head, int tail, int cost) {
        // System.out.printf("Edge cost of (%d, %d) is %d\n", head, tail, cost);
        edges[i] = new Edge(head, tail, cost);
    }
    private void reweight() {
        int tail, head, cost;
        for (int i = 0; i < m; i++){
            tail = edges[i].tail;
            head = edges[i].head;
            cost = edges[i].cost;
            
            edges[i].cost = cost + vertexCost[head-1] - vertexCost[tail-1];
        }        
    }
    private boolean bellmanFord() {
        int[][] a = new int[n+1][n+1]; // id = 0 is the s vertex
        
        for (int i = 1; i < n+1; i++)
            a[0][i] = Integer.MAX_VALUE;
        
        
        for (int i = 1; i < n+1; i++) {
            for (int k = 1; k < n+1; k++) {
                if (a[i-1][k] > a[i-1][0])
                    a[i][k] = a[i-1][0];
                else
                    a[i][k] = a[i-1][k];
            }
            for (int k = 0; k < m; k++) {
                if (a[i-1][edges[k].head] != Integer.MAX_VALUE) {
                    int sum = a[i-1][edges[k].head] + edges[k].cost;
                    if (a[i][edges[k].tail] > sum)
                        a[i][edges[k].tail] = sum;
                }
            }
        }
        
        // check if there is a negative cycle
        for (int i = 1; i < n+1; i++)
            if (a[n-1][i] != a[n][i])
                return false;
            
        for (int i = 0; i < n; i++)
            vertexCost[i] = a[n][i+1];
        /*
        for (int i = 0; i < n+1; i++) {
            for (int j = 0; j < n+1; j++)
                System.out.printf("%d ,", a[i][j]);
            System.out.println();
        }*/
        return true;
    }
}