/**
 * Class CountIntervals finds the number of nonoverlapping intervals in a set of
 * intervals.  It takes two lines of inputs <I>n</I> and n lines of <I>s f</I>,
 * where the second set of inputs represents the intervals. It outputs the number
 * of intervals.
 * <P>
 * Usage: java CountIntervals < Input File
 *
 * @author  Joshua Sellers
 * @version 17-Oct-2017
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.math.BigInteger;
import java.util.Scanner;  // Allowed for homework

public class CountIntervals {
    /**
     * Runs the heapify modify the array of intervals.
     *
     * @param arr     ending point
     * @param n       root
     * @param i       largest root
     * @return int    length of subsequence
     */
    public static void heapify(Interval[] arr, int n, int i) {
        int largest = i;  // Initialize largest as root
        int l = 2 * i + 1;  // left = 2*i + 1
        int r = 2 * i + 2;  // right = 2*i + 2
        // If left child is larger than root
        if (l < n && arr[l].y > arr[largest].y) largest = l;
        // If right child is larger than largest so far
        if (r < n && arr[r].y > arr[largest].y) largest = r;
        // If largest is not root
        if (largest != i) {
            Interval swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    /**
     * Runs heapsort on the array based
     * on end time
     *
     * @param arr   array to be sorted
     * @return Interval[]  sorted array
     */
    public static Interval[] sort(Interval[] arr) {
        int n = arr.length;
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end
            Interval temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            // call heapify on the reduced heap
            heapify(arr, i, 0);
        }
        return arr;
    }

    /**
     * Finds the possible number of independent subsets
     * of intervals for a set t
     *
     * @param t     set of intervals
     * @return BigInteger  number of subsets
     */
    public static BigInteger pwrRule(Interval[] t){
        BigInteger count = BigInteger.ZERO;
        // Sort array by end time
        t = sort(t);
        // Dynamic array
        BigInteger[] S = new BigInteger[t.length];
        // Initialize first value
        S[0] = BigInteger.ONE;
        for (int i = 1; i < t.length; i++){
            // Next interval
            S[i] = S[i-1].add(BigInteger.ONE);
            for (int j = i-1; j >= 0; j--){
                // If they overlap
                if (t[j].y >= t[i].x) continue;
                // Add previous intervals values
                else {
                    S[i] = S[i].add(S[j]);
                    break;
                }
            }
        }
        count = S[t.length-1];
        // Factor in null set
        return count.add(BigInteger.ONE);
    }

    /**
     * Public class for an interval
     */
    public static class Interval{
        private Integer x;
        private Integer y;
        /**
         * Construcor for interval class
         */
        public Interval(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Main program. Runs subset checking on input,
     * then outputs the number of subsets.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Number of intervals
        Integer n = sc.nextInt();
        // Array of intervals
        Interval[] intervals = new Interval[n];
        for (int i = 0; i < n; i++) {
            //Takes in the interval
            Integer s = sc.nextInt();
            Integer f = sc.nextInt();
            Interval interval = new Interval(s,f);
            intervals[i] = interval;
        }
        // Get size
        BigInteger num = pwrRule(intervals);
        // Print size
        System.out.println(num);
    }

}
