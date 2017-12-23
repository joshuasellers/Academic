/**
 * Class DoubleKnapsackSolution finds solution for the max cost of items put into two knapsacks.
 * It takes two lines of inputs <I>n W1 W2</I>, and n lines of <I>w c</I>.
 * It outputs the length of the max cost.
 * <P>
 * Usage: java DoubleKnapsackSolution < Input File
 *
 * @author  Joshua Sellers
 * @version 28-Oct-2017
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework

public class DoubleKnapsackSolution {
    /**
     * Runs the convex search dynamically
     *
     * @param a      input number
     * @param b      input number
     * @param c      input number
     * @return int   max number
     */
    public static int max(int a, int b, int c){ return Math.max(a,Math.max(b,c));}

    /**
     * Runs the dynamically search for the max knapsack cost soln
     *
     * @param items      items
     * @param W1         max weight of first sack
     * @param W2         max weight of second sack
     * @return Ret       max soln
     */
    public static Ret doubleKnapsackSol(Item[] items, int W1, int W2){
        // S array
        int[][][] S = new int[items.length+1][W1+1][W2+1];
        // zero out array
        for (int i = 0; i < items.length; i++){
            for (int j = 0; j < W1; j++){
                for (int k = 0; k < W2; k++) {
                    S[i][j][k] = 0;
                }
            }
        }
        // final spot in S
        int K = items.length;
        int wOne = W1;
        int wTwo = W2;
        for (int w1 = 0; w1 <= W1; w1++) {
            for (int w2 = 0; w2 <= W2; w2++) {
                for (int k = 0; k < items.length; k++) {
                    // initialize current value in S
                    S[k+1][w1][w2] = S[k][w1][w2];
                    // check to see which sack is able to and maximizes the items overall cost
                    if (items[k].getW() <= w1 && items[k].getW() <= w2) {
                        S[k+1][w1][w2] = max(S[k+1][w1][w2], S[k][w1 - items[k].getW()][w2] + items[k].getC(),
                                S[k][w1][w2 - items[k].getW()] + items[k].getC());
                    } else if (items[k].getW() <= w1) {
                        S[k+1][w1][w2] = Math.max(S[k+1][w1][w2], S[k][w1 - items[k].getW()][w2] + items[k].getC());
                    } else if (items[k].getW() <= w2) {
                        S[k+1][w1][w2] = Math.max(S[k+1][w1][w2], S[k][w1][w2 - items[k].getW()] + items[k].getC());
                    }
                }
            }
        }
        // first list
        int[] first = new int[W1];
        int countfirst = 0;
        // second list
        int[] second = new int[W2];
        int countsecond = 0;
        while (K > 0){
            // check to see which current sack could hold the weight
            // if one or both can, find the max sack and decrement that ones values
            if (items[K-1].getW() <= wOne && items[K-1].getW() <= wTwo) {
                if(S[K][wOne - items[K-1].getW()][wTwo] + items[K-1].getC() == max(S[K-1][wOne][wTwo], S[K-1][wOne - items[K-1].getW()][wTwo] + items[K-1].getC(), S[K-1][wOne][wTwo - items[K-1].getW()] + items[K-1].getC())){
                    first[countfirst] = K;
                    countfirst++;
                    wOne = wOne - items[K-1].getW();
                    K--;
                }
                else if(S[K-1][wOne][wTwo - items[K-1].getW()] + items[K-1].getC() ==
                        max(S[K-1][wOne][wTwo], S[K-1][wOne - items[K-1].getW()][wTwo] + items[K-1].getC(),
                                S[K-1][wOne][wTwo - items[K-1].getW()] + items[K-1].getC())){
                    second[countsecond] = K;
                    countsecond++;
                    wTwo = wTwo - items[K-1].getW();
                    K--;
                }
                else {
                    K--;
                }
            }
            else if (items[K-1].getW() <= wOne) {
                if(S[K-1][wOne - items[K-1].getW()][wTwo] + items[K-1].getC() ==
                        Math.max(S[K-1][wOne][wTwo], S[K-1][wOne - items[K-1].getW()][wTwo] + items[K-1].getC())){
                    first[countfirst] = K;
                    countfirst++;
                    wOne = wOne - items[K-1].getW();
                    K--;
                }
                else {
                    K--;
                }
            }
            else if (items[K-1].getW() <= wTwo) {
                if(S[K-1][wOne][wTwo - items[K-1].getW()] + items[K-1].getC() ==
                        Math.max(S[K-1][wOne][wTwo], S[K-1][wOne][wTwo - items[K-1].getW()] + items[K-1].getC())){
                    second[countsecond] = K;
                    countsecond++;
                    wTwo = wTwo - items[K-1].getW();
                    K--;
                }
                else {
                    K--;
                }
            }
            else{
                K--;
            }
        }
        // return lists
        return new Ret(first,second);
    }

    /**
     * Item class for each item put into sacks
     */
    public static class Item{
        private int c;
        private int w;

        /**
         * Initializer for items
         *
         * @param w         item weight
         * @param c         item cost
         */
        public Item(int w, int c){
            this.c = c;
            this.w = w;
        }
        // return weight
        public int getC(){return this.c;}
        // return cost
        public int getW(){return this.w;}
    }

    /**
     * Ret class for each list of items
     */
    public static class Ret{
        private int[] first;
        private int[] second;

        /**
         * Initializer for Ret
         *
         * @param first         item list
         * @param second        item list
         */
        public Ret(int[] first, int[] second){
            this.first = first;
            this.second = second;
        }
        /**
         * print items
         */
        public void print(){
            // print first
            for (int i = this.first.length-1; i >= 0; i--) {
                if (this.first[i] != 0) System.out.print(this.first[i] + " ");
            }
            System.out.println();
            // print second
            for (int i = this.second.length-1; i >= 0; i--) {
                if (this.second[i] != 0) System.out.print(this.second[i] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Main program. Gets inputs,
     * then outputs the solution for the max cost
     * in the sacks.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int W1 = sc.nextInt();
        int W2 = sc.nextInt();
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            int w = sc.nextInt();
            int c = sc.nextInt();
            items[i] = new Item(w,c);
        }
        // get lists
        Ret l = doubleKnapsackSol(items,W1,W2);
        // print lists
        l.print();
    }
}
