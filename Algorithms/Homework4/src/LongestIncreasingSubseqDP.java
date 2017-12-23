/**
 * Class LongestIncreasingSubseqDP finds the longest increasing subsequence
 * of an array dynamically. It takes two lines of inputs <I>n</I> and <I>a0a1a2a3a4...an</I>,
 * where the second line represents the array. It outputs the length.
 * <P>
 * Usage: java LongestIncreasingSubseqDP < Input File
 *
 * @author  Joshua Sellers
 * @version 17-Oct-2017
 */
import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework

public class LongestIncreasingSubseqDP {
    /**
     * Runs dynamic subsequece search over array,
     * returning an int for the length of the longest array.
     *
     * @param set   array
     * @return int  length of subsequence
     */
    public static int longestSub(int[] set) {
        int[] S = new int[set.length];
        for (int j = 0; j < set.length; j++) {
            S[j] = 1;
            for (int k = 0; k < j; k++){
                if (set[k] < set[j] && S[j] < S[k]+1){
                    S[j] = S[k] + 1;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < S.length; i++){
            max = Math.max(max, S[i]);
        }
        return max;
    }

    /**
     * Main program. Runs subsequence checking on inputs,
     * then outputs the length of the max subsequence.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer n = sc.nextInt();
        int[] set = new int[n];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            set[i] =  sc.nextInt();
        }
        int out = longestSub(set);
        System.out.println(out);
    }
}
