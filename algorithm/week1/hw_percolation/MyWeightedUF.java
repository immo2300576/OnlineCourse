public class MyWeightedUF {
    private int[] id;
    private int[] weight;
    
    
    public MyWeightedUF(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        
        id = new int[n];
        weight = new int[n];
        
        for (int i = 0; i < n; i++) {
            id[i] = i;
            weight[i] = 1;
        }
    }    
    public void union(int id1, int id2) {
        // check if it is already connected
        if (connected(id1, id2))
            return;
        
        int root1 = find(id1);
        int root2 = find(id2);
        
        // union
        if (count(root1) > count(root2)) {
            id[root2] = root1;
            weight[root1] += weight[root2];
        }
        else {
            id[root1] = root2;
            weight[root2] += weight[root1];
        }
        
    }
    public int find(int block) {
        // find the root
        while (id[block] != block) {
            id[block] = id[id[block]];
            block = id[block];
        }
        
        return block;
    }
    public boolean connected(int id1, int id2) {
        return (find(id1) == find(id2));
    }
    public int count(int index) {
        return weight[index];
    }
}
    