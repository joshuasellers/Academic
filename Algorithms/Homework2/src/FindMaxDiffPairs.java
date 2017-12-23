/**
 * HW1
 * Class: FindMaxDiffPairs
 * Description: contains all of the methods used to run FindMaxDiffPairs
 * Created by joshuasellers on 9/11/17.
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework assignments

/**
 * This class can returns the largest diff pair from the input
 *
 * @author jds4433 (Josh Sellers)
 */
public class FindMaxDiffPairs {

    public static void exchange(Integer[] line,int i, int j) {
        /**
         * exchanges
         *
         * @param line input numbers
         * @param i    indices
         * @param j    indices
         * @return void
         */
        Integer k = line[i];
        line[i] = line[j];
        line[j] = k;
    }

    public static void heapify(Integer[] line, int i,int size) {
        /**
         * heapifies
         *
         * @param line input numbers
         * @param i    indice
         * @param size size
         * @return void
         */
        int left = 2*i+1;
        int right = 2*i+2;
        int max;
        if(left <= size && line[left] > line[i]){
            max = left;
        } else {
            max = i;
        }

        if(right <= size && line[right] > line[max]) {
            max = right;
        }

        if(max != i) {
            exchange(line, i, max);
            heapify(line, max, size);
        }
    }

    public static void buildheap(Integer []line) {
        /**
         * Builds heap
         *
         * @param line input numbers
         * @return void
         */
        for(int i=(line.length-1)/2; i >= 0; i--){
            heapify(line,i,line.length-1);
        }
    }

    public static Integer[] heapsort(Integer[] line) {
        /**
         * Heapsort Algorithm
         *
         * @param line input numbers
         * @return Orted list
         */
        buildheap(line);
        int size=line.length-1;
        for(int i=size; i > 0; i--) {
            exchange(line,0, i);
            size=size-1;
            heapify(line, 0,size);
        }
        return line;
    }

    private static void diffLarge(Integer[] line){
        /**
         * Given numbers, print the largest diff in the list
         *
         * @param line input numbers
         * @return Print the lines to standard output
         */
        line = heapsort(line);
        int tBound = line[line.length-1]-line[0]+1;
        Integer[] tholder = new Integer[tBound];
        for(int i = 0 ; i < tholder.length; i++) {
            tholder[i] = 0;
        }
        for (int k = 1; k < line.length-1; k++){
            for (int l = k+1; l < line.length; l++){
                tholder[(line[l]-line[k])]++;
            }
        }
        long placeholder = 0;
        long placeholdercount = 0;
        for (int m = 0; m < tholder.length; m++){
            if (tholder[m] >= placeholder){
                placeholder = tholder[m];
                placeholdercount = m;
            }
        }
        System.out.println(placeholdercount);

    }

    /**
     * The main method: take in numbers from the args and return the largest diff
     *
     * @param args The numbers
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer n = sc.nextInt();
        Integer[] list = new Integer[n];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            list[i] = sc.nextInt();
        }
        diffLarge(list);
    }
}
