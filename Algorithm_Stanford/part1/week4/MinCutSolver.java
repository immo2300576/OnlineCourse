import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Random;

public class MinCutSolver {
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
        int minCount = 999;
        int ntrials = n*n;
        int count = 0;
        StdOut.printf("nTrials = %d\n", ntrials);
        for (int i = 0; i < ntrials; i++) {
            KargerMinCut trial = new KargerMinCut(obj);
            // trial.printGraph();
            count = trial.kargerMinCut();
            
            if (count < minCount)
                minCount = count;
            // StdOut.printf("trial no.%d, Cut Count = %d\n", i, count);
        }
        
        StdOut.printf("From KargerMinCut Method: the minimum cut number is %d\n", minCount);
    }
}