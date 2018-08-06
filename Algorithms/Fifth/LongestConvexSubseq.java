/**
 * Class LongestConvexSubseq finds the max length of a convex subsequence from
 * an input sequence.  It takes two lines of inputs <I>n</I>, and the sequence <I>a0a1a2...an</I>.
 * It outputs the length of the longest convex subsequence.
 * <P>
 * Usage: java LongestConvexSubseq < Input File
 *
 * @author  Joshua Sellers
 * @version 28-Oct-2017
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework

public class LongestConvexSubseq {
    /**
     * Runs the convex search dynamically
     *
     * @param sequence      input sequence
     * @return int          length of subsequence
     */
    public static int convex(Integer[] sequence){
        // already convex
        if(sequence.length <= 2) return sequence.length;
        // max counter
        Integer max = 0;
        Integer[][] S = new Integer[sequence.length][sequence.length];
        // initialize
        S[0][0] = 1;
        for(int i = 1; i < sequence.length; i++){
            for(int j = 0; j < i; j++){
                S[i][j] = 2;
                for(int k = 0; k < j; k++){
                    // check for convexness of the three numbers
                    if(S[j][k] + 1 > S[i][j] && ((sequence[i] - sequence[j]) >= (sequence[j] - sequence[k]))) {
                        S[i][j] = S[j][k] + 1;
                    }
                }
                max = Math.max(max, S[i][j]);
            }
        }
        // output the max
        return max;
    }

    /**
     * Main program. Gets inputs,
     * then outputs the number of length
     * of the longest convex subsequence.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer n = sc.nextInt();
        Integer[] sequence = new Integer[n];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            Integer s = sc.nextInt();
            sequence[i] = s;
        }
        // get max length of the subsequence
        int l = convex(sequence);
        System.out.println(l);
    }
}
