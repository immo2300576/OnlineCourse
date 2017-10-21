import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class QuickSort {
    
    public QuickSort() {
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
        
        // quick sort
        QuickSort obj = new QuickSort();
        long numbers = obj.quickSort(array);
        
        // print the numbers of comparison
        System.out.printf("Numbers of comparison are %d\n", numbers);
    }
    private long quickSort(int[] array) {
        
        long count = sort(array, 0, array.length-1);
        
        // print the sorted array
        for (int i = 0; i < array.length; i++)
            System.out.printf("%d ", array[i]);
        System.out.println();
        
        return count;
    }
    private long sort(int[] array, int lo, int hi) {
        
        // base case: one element
        if (lo >= hi)
            return 0;
        
        // chose the pivot
        // Choice 1: the first element
        int p = array[lo];
        
        // Choice 2: the last element
        // int p = array[hi];
        // array[hi] = array[lo]; 
        // array[lo] = p;
        
        /*
        // Choice 3: Median-of-the-three
        int mid, p;
        if ((hi-lo+1)%2==0) {
            mid = lo + (hi-lo+1)/2 -1; 
        }
        else
            mid = lo+(hi-lo)/2;
         
        if (array[lo]>array[mid] && array[mid]>array[hi]) {
            p = array[mid];
            array[mid] = array[lo];
            array[lo] = p;
        }
        else if (array[lo]>array[hi] && array[hi]>array[mid]) {
            p = array[hi];
            array[hi] = array[lo];
            array[lo] = p;
        }
        else if (array[mid]>array[hi] && array[hi]>array[lo]) {
            p = array[hi];
            array[hi] = array[lo];
            array[lo] = p;
        }
        else if (array[mid]>array[lo] && array[lo]>array[hi]) {
            p = array[lo];
        }
        else if (array[hi]>array[lo] && array[lo]>array[mid]) {
            p = array[lo];
        }
        else {
            p = array[mid];
            array[mid] = array[lo];
            array[lo] = p;
        }  */
        
        int comparisonNum = hi-lo;
        // split into two part
        // every item in the first part is less than the pivot
        // every item in the second part is larger than the pivot
        int i = lo+1;
        for (int j = lo+1; j < hi+1; j++) {
            if (array[j]<p) {
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
                i++;
            }
        }
        array[lo] = array[i-1];
        array[i-1] = p;
       
        // recursive call
        long countFirst = sort(array, lo, i-2);
        long countSecond = sort(array, i, hi);
        
        return comparisonNum+countFirst+countSecond;
    }
}