import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdRandom;
// import java.util.Iterator;

// [To do]
// reservoir sampling
// randomly choose k items from a large set containing n items
// typically, we do not know the value of n
// ReservoirSample(S[1...n], R[1...k])
// for i = 1 to k
//     R[i] := S[i]

// for i = k+1 to n
//     j := random(1,i) inclusive!
//     if j <= k
//         R[j] := S[i]


public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> myQueue = new RandomizedQueue<String>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
             myQueue.enqueue(s);
        }
        while (count < k) {
            String s = myQueue.dequeue();
            StdOut.println(s);
            count++;
        }     
    }      
}