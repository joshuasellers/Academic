/**
 * Class DoubleKnapsack finds the max cost of items put into two knapsacks.
 * It takes two lines of inputs <I>n W1 W2</I>, and n lines of <I>w c</I>.
 * It outputs the length of the max cost.
 * <P>
 * Usage: java DoubleKnapsack < Input File
 *
 * @author  Joshua Sellers
 * @version 28-Oct-2017
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework

public class DoubleKnapsack {
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
     * Runs the dynamically search for the max knapsack cost
     *
     * @param items      items
     * @param W1         max weight of first sack
     * @param W2         max weight of second sack
     * @return int       max cost
     */
    public static int doubleKnapsack(Item[] items, int W1, int W2){
        // counter
        int max = 0;
        // S array
        int[][] S = new int[W1+1][W2+1];
        // zero out array
        for (int i = 0; i < W1+1; i++){
            for (int j = 0; j < W2+1; j++){
                S[i][j]=0;
            }
        }

        for (int k = 0; k < items.length; k++) {
            for (int w1 = W1; w1 >= 0; w1--) {
                for (int w2 = W2; w2 >= 0; w2--) {
                    // check to see which sack is able to and maximizes the items overall cost
                    if (items[k].getW() <= w1 && items[k].getW() <= w2) {
                        S[w1][w2] = max(S[w1][w2], S[w1 - items[k].getW()][w2] + items[k].getC(),
                                S[w1][w2 - items[k].getW()] + items[k].getC());
                    } else if (items[k].getW() <= w1) {
                        S[w1][w2] = Math.max(S[w1][w2], S[w1 - items[k].getW()][w2] + items[k].getC());
                    } else if (items[k].getW() <= w2) {
                        S[w1][w2] = Math.max(S[w1][w2], S[w1][w2 - items[k].getW()] + items[k].getC());
                    }
                    // update max
                    max = Math.max(max, S[w1][w2]);
                }
            }
        }
        return max;
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
     * Main program. Gets inputs,
     * then outputs the max cost
     * in the sacks.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // get numbers
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
        // find max cost
        int l = doubleKnapsack(items,W1,W2);
        System.out.println(l);
    }

}
