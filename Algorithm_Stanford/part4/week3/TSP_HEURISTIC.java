import edu.princeton.cs.algs4.In;

public class TSP_HEURISTIC {
    
    private Node[] nodes;
    private boolean[] isVisited;
    private double[] cacheKey;
    
    private class Node {
        private double x;
        private double y;
        
        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public void set(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
    }
    
    public TSP_HEURISTIC(int n) {
        nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(0, 0);
        isVisited = new boolean[n];
        cacheKey = new double[n];
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int ncities = in.readInt();
        TSP_HEURISTIC tsp = new TSP_HEURISTIC(ncities);
        
        for (int i = 0; i < ncities; i++) {
            int icity = in.readInt();
            double x = in.readDouble();
            double y = in.readDouble();
            tsp.insert(icity, x, y);
        }
        
        //
        tsp.run();
        
    }
    public void insert(int index, double x, double y) {
        // System.out.printf("Insert index = %d (x,y) = (%f, %f)\n", index, x, y);
        nodes[index-1].set(x, y);
    }
    public void run() {
        double pathDist = 0.0;
        int nextVertex = 0;
        int minDist = Integer.MAX_VALUE;
        int count = 1;
        // Set vertex1 to the first visited node
        isVisited[0] = true;
        for (int i = 1; i < nodes.length; i++) {
            double distance = euclidean(i, 0);
            cacheKey[i] = distance;
        }
        while (count < nodes.length) {
            nextVertex = findMin();
            count++;
            pathDist += cacheKey[nextVertex];
            isVisited[nextVertex] = true;
            for (int i = 1; i < nodes.length; i++) {
                if (!isVisited[i]) {
                    if (true) {
                        cacheKey[i] = euclidean(i, nextVertex);
                    }
                }
            }
        }
        pathDist += euclidean(nextVertex, 0);
        System.out.printf("Total TSP distance is %f\n", pathDist);
        System.out.printf("Total TSP distance is %d\n", Math.round(pathDist));
    }
    private int findMin() {
        double minDist = Double.MAX_VALUE;
        int nextVertex = 1;
        for (int i = 1; i < nodes.length; i++) {
            if (cacheKey[i] < minDist && isVisited[i] == false) {
                minDist = cacheKey[i];
                nextVertex = i;
            }    
        }
        return nextVertex;
    }
    private double euclidean(int a, int b) {
        double xdiff = nodes[a].getX()-nodes[b].getX();
        double ydiff = nodes[a].getY()-nodes[b].getY();
        
        return Math.sqrt(xdiff*xdiff+ydiff*ydiff);
    }
}