/**
 * Class MatrixChainParenthesize finds solution chain multiplication.
 * It takes two lines of inputs <I>n</I> and <I>a0a1...an</I>.
 * It outputs the steps and parethasizing.
 * <P>
 * Usage: java MatrixChainParenthesize < Input File
 *
 * @author  Joshua Sellers
 * @version 28-Oct-2017
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework

public class MatrixChainParenthesize {
    /**
     * Runs the dynamic algorithm for chain multplicatin
     *
     * @param matrices      input matrices
     */
    public static void paren(int[] matrices){
        // S array
        int[][] S = new int[matrices.length-1][matrices.length-1];
        int[][] k_array = new int[matrices.length-1][matrices.length-1];
        for (int i = 0; i < matrices.length-1; i++){
            for (int j = 0; j < matrices.length-1; j++){
                if (i == j){
                    S[i][j] = 0;
                }
            }
        }
        // run dynamic algorithm
        for (int d = 1; d < matrices.length; d++){
            for (int L = 1;L < matrices.length-d; L++){
                int R = L + d;
                S[L-1][R-1] = -1;
                for (int k = L; k <= R-1; k++){
                    // check for minumum steps
                    int tmp = S[L-1][k-1]+S[k][R-1]+ matrices[L-1]*matrices[k]*matrices[R];
                    if (S[L-1][R-1] == -1) {
                        S[L-1][R-1] = tmp;
                        k_array[L-1][R-1] = k;

                    }
                    else if (S[L-1][R-1] > tmp) {
                        S[L-1][R-1] = tmp;
                        k_array[L-1][R-1] = k;
                    }
                }
            }
        }
        // print steps
        System.out.println(S[0][matrices.length-2]);
        int l = 0;
        int r = matrices.length-2;
        // print parens
        printM(k_array,l,r);
        System.out.println();
    }

    /**
     * Runs the printing
     *
     * @param k_array
     * @param l
     * @param r
     */
    public static void printM(int[][] k_array, int l, int r){
        if (l == r){
            System.out.print("A"+(l+1) + " ");
        }
        else{
            System.out.print("( ");
            // recursion
            printM(k_array,l,k_array[l][r]-1);
            System.out.print("x ");
            // recursion
            printM(k_array,k_array[l][r],r);
            System.out.print(") ");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] items = new int[n+1];
        for (int i = 0; i <= n; i++) {
            // Takes in the lines as a string
            int a = sc.nextInt();
            items[i] = a;
        }
        //int[] items = {2, 3, 1, 4, 2};
        paren(items);
    }
}
