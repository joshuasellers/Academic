/**
 * Class IntervalsBreaks finds the max number of nonoverlapping intervals in a set of
 * intervals with break times.  It takes two lines of inputs <I>n</I>, n lines of
 * <I>s f</I>, and the break times to the other intervals <I>d0d1d2d...dn</I>.
 * It outputs the number of intervals in the max independent set.
 * <P>
 * Usage: java IntervalsBreaks < Input File
 *
 * @author  Joshua Sellers
 * @version 17-Oct-2017
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework

public class IntervalsBreaks {
    /**
     * Runs the heapify modify the array of intervals.
     *
     * @param arr     ending point
     * @param n       root
     * @param i       largest root
     * @return int    length of subsequence
     */
    public static void heapify(ClassTime[] arr, int n, int i) {
        int largest = i;  // Initialize largest as root
        int l = 2 * i + 1;  // left = 2*i + 1
        int r = 2 * i + 2;  // right = 2*i + 2
        // If left child is larger than root
        if (l < n && arr[l].f > arr[largest].f) largest = l;
        // If right child is larger than largest so far
        if (r < n && arr[r].f > arr[largest].f) largest = r;
        // If largest is not root
        if (largest != i) {
            ClassTime swap = arr[i];
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
    public static ClassTime[] sort(ClassTime[] arr) {
        int n = arr.length;
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end
            ClassTime temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
        return arr;
    }

    public static int length(ClassTime[] intervals){
        intervals = sort(intervals);
        Integer[] S = new Integer[intervals.length];
        S[0] = 1;
        for (int i = 1; i < intervals.length; i++){
            S[i] = 1;
            for (int j = i-1; j >= 0; j--){
                if (intervals[j].f >= intervals[i].s) continue;
                else if (intervals[j].f + intervals[j].travels[intervals[i].label] > intervals[i].s) continue;
                else {
                    if (1 + S[j] > S[i]) S[i] = 1 + S[j];
                }
            }
        }
        int max = 0;
        for (int i = 0; i < S.length; i++){
            if (max < S[i]) max = S[i];
        }
        return max;
    }
    /**
     * Public class for a tuple that holds
     * the array and the return value
     */
    public static class ClassTime {
        private Integer s;
        private Integer f;
        private Integer label;
        private int[] travels;
        /**
         * Construcor for tuple class
         */
        public ClassTime(Integer s, Integer f, Integer label, int[] travels) {
            this.s = s;
            this.f = f;
            this.label = label;
            this.travels = travels;
            travels[0] = 0;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer n = sc.nextInt();
        ClassTime[] intervals = new ClassTime[n];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            Integer s = sc.nextInt();
            Integer f = sc.nextInt();
            int[] holder = new int[1];
            holder[0] = 0;
            ClassTime classTime = new ClassTime(s, f, i, holder);
            intervals[i] = classTime;
        }
        for (int i = 0; i < n; i++) {
            int[] distances = new int[n];
            for (int j = 0; j < n; j++) {
                Integer d = sc.nextInt();
                distances[j] = d;
            }
            intervals[i].travels = distances;
        }
        int l = length(intervals);
        System.out.println(l);
    }
}
