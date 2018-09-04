/**
 * Class WeightedInversions finds the weighted inversions for an
 * unordered array. It takes two lines of inputs <I>n</I> and <I>a0a1a2a3a4...an</I>,
 * where the second line represents the array. It outputs the weight.
 * <P>
 * Usage: java WeightedInversions < Input File
 *
 * @author  Joshua Sellers
 * @version 27-Sep-2017
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework assignments
/**
 * Created by joshuasellers on 9/25/17.
 */
public class WeightedInversions {
    /**
     * Runs merge over two inputs, returning an ordered list
     *
     * @param L  Left array
     * @param R  Right array
     * @return Integer[]  Combined array
     */
    public static Integer[] merge(Integer L[], Integer R[])
    {
        Integer[] arr = new Integer[L.length + R.length];
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
        // Initial index of merged subarry array
        int k = 0;
        // Add ordered elements
        while (i < L.length && j < R.length)
        {
            if (L[i] <= R[j])
            {
                arr[k] = L[i];
                i++;
            }
            else
            {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        // Copy any remaining elements of L[]
        while (i < L.length)
        {
            arr[k] = L[i];
            i++;
            k++;
        }
        // Copy any remaining elements of R[]
        while (j < R.length)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
        return arr;
    }

    /**
     * Public class for a tuple that holds
     * the array and the return value
     */
    public static class Tuple<X, Y> {
        public final X x;
        public final Y y;
        /**
         * Construcor for tuple class
         */
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Runs the weighting algorithm recursively via
     * divide and conquer on list. Merging elements
     * on the way out of the recursion.
     *
     * @param list  unordered list
     * @return Tuple  Weight and list
     */
    public static Tuple<Long, Integer[]> countInv(Integer[] list){
        int n = list.length;
        // Split lists
        Integer[] B;
        Integer[] C;
        if (n == 1){
            // Base case
            return new Tuple<Long, Integer[]>(Long.valueOf(0),list);
        }
        // If input list has length of two
        if (n == 2){
            B = new Integer[1];
            C = new Integer[1];
        }
        // Otherwise split in half
        else {
            B = new Integer[list.length / 2];
            C = new Integer[list.length- B.length];
        }
        for (int i = 0; i < B.length; i++){
            B[i] = list[i];
        }
        for (int i = 0; i < list.length - B.length; i++){
            C[i] = list[i+B.length];
        }
        // Recursive calls
        Tuple<Long, Integer[]> leftcount = countInv(B);
        Tuple<Long, Integer[]> rightcount = countInv(C);
        // Count weights on two lists
        Long middlecount = countLefttoRightInv(leftcount.y,rightcount.y);
        // Merge lists
        Integer[] Asorted = merge(leftcount.y,rightcount.y);
        // Return output and ordered list
        return new Tuple<>(leftcount.x+rightcount.x+middlecount,Asorted);
    }

    /**
     * Counts left to right inversions (i > j) across input lists
     *
     * @param B  Left array
     * @param C  Right array
     * @return long  Weight
     */
    public static Long countLefttoRightInv(Integer[] B, Integer[] C){
        long x = 0;
        int i = 0;
        int j = 0;
        // Find differences in B
        Long[] Bdiffs = new Long[B.length-1];
        for (int k = 0; k < Bdiffs.length; k++){
            Bdiffs[k] = Long.valueOf(B[k+1]) - Long.valueOf(B[k]);
        }
        int counter = 1;
        // Add them together to get subsequent sums after each index
        Long[] BD = new Long[B.length-1];
        for (int m = BD.length-1; m >= 0; m--){
            if (m+1 == BD.length) {
                BD[m] = Bdiffs[m];
                counter++;
            }
            else {
                BD[m] = BD[m+1] + counter*Bdiffs[m];
                counter++;
            }
        }
        // Find differences in C
        Long[] Cdiffs = new Long[C.length-1];
        for (int l = 0; l < Cdiffs.length; l++){
            Cdiffs[l] = Long.valueOf(C[l+1]) - Long.valueOf(C[l]);
        }
        // Go through arrays
        while (i < B.length && j < C.length){
            if(B.length == 1 && C.length == 1){
                if (B[i] < C[j]) i++;
                else {
                    x += Math.abs(B[i] - C[j]);
                    j++;
                }
            }
            else if (B.length == 1 && C.length != 1){
                if (B[i] < C[j]) i++;
                else {
                    x += Math.abs(B[i] - C[j]);
                    j++;
                }
            }
            else if (B.length != 1 && C.length == 1){
                if (B[i] < C[j]) i++;
                else {
                    x += Math.abs(B[i] - C[j]);
                    // If next value will be larger
                    if (B[i] < Cdiffs[i] + C[j]) i++;
                    else j++;
                }
            }
            else {
                if (B[i] < C[j]) i++;
                else {
                    x += Math.abs(B[i] - C[j]);
                    // If there still are elements in C
                    if (j+1 < C.length) {
                        if (B[i] < Cdiffs[j] + C[j]) i++;
                        // If there are still elements in B
                        else if (i + 1 < B.length) {
                            // Add subsequent sums
                            x += Math.abs(B[i] - C[j]) * (BD.length - i) + BD[i];
                            j++;
                        }
                        else j++;
                    }
                    else i++;
                }
            }
        }
        return x;
    }

    /**
     * Main program. Runs weight checking on inputs,
     * then outputs the weight.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer n = sc.nextInt();
        Integer[] list = new Integer[n];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            list[i] = sc.nextInt();
        }
        // Get weight
        Tuple<Long, Integer[]> result = countInv(list);
        System.out.println(result.x);
    }
}
