import edu.princeton.cs.algs4.In;
import java.util.Random;
import java.util.ArrayList;
    
public class SAT2 {
    // Papadimitriou's Algorithm
    private class Clause {
        private int arg1;
        private int arg2;
        private boolean truth1;
        private boolean truth2;
        
        public Clause(int a, int b) {
            if (a > 0) {
                this.arg1 = a;
                this.truth1 = true;
            }
            else {
                this.arg1 = -1*a;
                this.truth1 = false;
            }
            if (b > 0) {
                this.arg2 = b;
                this.truth2 = true;
            }
            else {
                this.arg2 = -1*b;
                this.truth2 = false;
            }    
        }
        public String toString() {
            int a = arg1;
            int b = arg2;
            if (truth1 == false) a = -a;
            if (truth2 == false) b = -b;
            
            return "( "+ String.valueOf(a) + ", " + String.valueOf(b) + ")";
        }
        public boolean test(boolean[] table) {
            return (table[arg1-1] == truth1 || table[arg2-1] == truth2);
        }
        public int select() {
            Random rand = new Random();
            int trial = rand.nextInt(2);
            if (trial == 0)
                return arg1;
            else
                return arg2;
        }
    }
    private boolean[] nodes;
    private Clause[] test;
    public SAT2(int n) {
        nodes = new boolean[n];
        test = new Clause[n];
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        SAT2 obj = new SAT2(n);
            
        for (int i = 0; i < n; i++) {
            int a = in.readInt();
            int b = in.readInt();
            obj.addClause(i, a, b);
        }
        System.out.println("Finish parsing the clause.");
        
        if (obj.algorithm()) {
            System.out.println("Solvable.");
            obj.printNode();
        }
        else
            System.out.println("Unsolvable.");
    }
    public void addClause(int index, int a, int b) {
        test[index] = new Clause(a, b);
    }
    public boolean algorithm() {
        int n = nodes.length;
        Random rand = new Random();
        int size = 0;
        int times = (int) Math.round(Math.log((double) n)/Math.log(2.0));
        for (int i = 0; i < times; i++) {
            // randomly assign initial state
            randomState();
            for (int j = 0; j < 2*n*n; j++) {
                size = 0;
                for (int k = 0; k < n; k++) {
                    if (test[k].test(nodes) == false) {
                        size++;
                        if (k != size-1)
                            exch(test, k, size-1);
                    }
                }
                if (size == 0)
                    return true;
                else {
                    int trial = rand.nextInt(size);
                    int sel = test[trial].select();
                    nodes[sel-1] = !nodes[sel-1];
                }
            }
        }
        return false;
    }
    public void randomState() {
        Random rand = new Random();
        for (int i = 0; i < nodes.length; i++) {
            int trial = rand.nextInt(2);
            nodes[i] = (trial == 0)? true:false;
        }
    }
    private void exch(Clause[] test, int a, int b) {
        Clause tmp = test[a];
        test[a] = test[b];
        test[b] = tmp;
    }
    private void printNode() {
        for (int i = 0; i < nodes.length; i++)
            System.out.printf("Node %d = " + nodes[i] + "\n", i+1);
    }
}