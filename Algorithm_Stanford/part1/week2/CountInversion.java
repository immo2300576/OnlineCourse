import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// merge sort
public class CountInversion {
    
    private CountInversion() {
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[] array = new int[n];
        
        for (int i = 0; i < n; i++)
            array[i] = in.readInt();
        
        // print the original array
        for (int i = 0; i < array.length; i++)
            System.out.printf("%d ", array[i]);
        System.out.println();
        
        // merge sort
        CountInversion obj = new CountInversion();
        long numbers = obj.mergeSort(array);
    
        // print the numbers of inversion 
        System.out.printf("Numbers of inversion are %d\n", numbers);
    }
    
    private long mergeSort(int[] array) {
        
        int[] copy = new int[array.length];
        for (int i = 0; i < array.length; i++)
            copy[i] = array[i];
          
        long count = sort(array, copy, 0, array.length-1);
        
        // print the sorted array
        for (int i = 0; i < array.length; i++)
            System.out.printf("%d ", array[i]);
        System.out.println();
        
        return count;
    }
    
    public long sort(int[] array, int[] aux, int lo, int hi) {
        // base case: one element
        if (lo >= hi)
            return 0;
        
        // recursive call
        int mid = lo + (hi-lo)/2;
        long firstHalf = sort(aux, array, lo, mid);
        long secondHalf = sort(aux, array, mid+1, hi);
        
        // merge the split array
        long count = merge(array, aux, lo, mid, hi);
        
        return firstHalf+secondHalf+count;
    }
    private long merge(int[] array, int[] aux, int lo, int mid, int hi) {
        int countI = lo;
        int countJ = mid+1;
        long countInv = 0;
        
        for (int i = lo; i <= hi; i++) {
            
            if (countI > mid)
                array[i] = aux[countJ++];
            else if (countJ > hi)
                array[i] = aux[countI++];
            else if (aux[countI] > aux[countJ]) {
                array[i] = aux[countJ++];
                countInv += mid-countI+1;
            }
            else
                array[i] = aux[countI++];
        }
        return countInv;
    }
}