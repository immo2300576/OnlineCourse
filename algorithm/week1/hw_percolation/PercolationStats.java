import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] data;
    private int trials;
    private double dataMean;
    private double dataStddev;
        
    public PercolationStats(int n, int times) {
        
        // input invalid
        if (n <= 0 || times <= 0)
            throw new java.lang.IllegalArgumentException();
        
        data = new double[times];
        trials = times;
             
        int[] picks = new int[n*n];
        // initialize the order of the picked block id
        for (int i = 0; i < n*n; i++) picks[i] = i;
        
        // for times individual trials
        for (int i = 0; i < times; i++) {
            Percolation p = new Percolation(n);
            int count = 0;
            StdRandom.shuffle(picks);
            
            while (!p.percolates()) {
                int newblock = picks[count];              
                count++;
                int blockX = newblock/n+1;
                int blockY = newblock-blockX*n+n+1;
                // System.out.printf("Open %d blocks in (%d,%d) %d\n", count, blockX, blockY, newblock);
                p.open(blockX, blockY);
                
            }
            data[i] = (double) count/(n*n);
        }
        
        dataMean = StdStats.mean(data);
        dataStddev = StdStats.stddev(data);
    }
    public double mean() {
        return dataMean;
    }
    public double stddev() {
        return dataStddev;
    }
    public double confidenceLo() {
        return (dataMean-1.96*dataStddev/Math.sqrt(trials));
    }
    public double confidenceHi() {
        return (dataMean+1.96*dataStddev/Math.sqrt(trials));
    }
        
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int times = Integer.parseInt(args[1]);
        
        PercolationStats obj = new PercolationStats(n, times);
        // for(int i=0;i<times;i++)
        //    System.out.printf("count = %f\n",p_obj._data[i]);   
        System.out.printf("mean = %f\n", obj.mean());
        System.out.printf("stddev = %f\n", obj.stddev());
        System.out.printf("interval = [ %f, %f ]\n", obj.confidenceLo(), obj.confidenceHi());
        
    }
}
