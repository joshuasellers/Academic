/**
 * HW1
 * Class: DiffT
 * Description: contains all of the methods used to run DiffT
 * Created by joshuasellers on 9/11/17.
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.lang.reflect.Array;
import java.util.Scanner;  // Allowed for homework assignments

/**
 * This class can returns the number of diff pairs from the input
 *
 * @author jds4433 (Josh Sellers)
 */
public class DiffT {

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

    public static Integer binarySearchLower(Integer[] line, Integer start, Integer len, Integer inp, Integer t, int Pos){
        /**
         * returns lower bound for binary search
         */
        if (len >= start)
        {
            int mid = start + (len - start)/2;

            // If the element is present at the middle itself
            if (line[mid]- inp == t)  {
                if(mid!= Pos) {
                    if (mid - 1 == Pos) {
                        return mid;
                    } else if (line[mid - 1] - inp != t) {
                        return mid;
                    }
                }
            }

            // element can only be present
            // in right subarray
            if (line[mid]- inp < t) return binarySearchLower(line, mid+1,len, inp, t, Pos);

            // Else the element can only be present in left subarray
            return binarySearchLower(line, start, mid-1, inp, t, Pos);
        }

        // We reach here when element is not present in array
        return Pos;
    }

    public static Integer binarySearchUpper(Integer[] line, Integer start, Integer len, Integer inp, Integer t, int Pos){
        /**
         * returns upper bound for binary search
         */
        if (len >= start)
        {
            int mid = start + (len - start)/2;

            // If the element is present at the middle itself
            if (line[mid]- inp == t){
                if(mid != Pos){
                    if (mid+1 != line.length) {
                        if (line[mid + 1] - inp != t) {
                            return mid;
                        }
                    }
                    else if (mid+1 == line.length){
                        return mid;
                    }
                }
            }

            // element can only be present
            // in left subarray
            if (line[mid]- inp > t) return binarySearchUpper(line, start, mid-1, inp, t, Pos);

            // Else the element can only be present in right subarray
            return binarySearchUpper(line, mid+1,len, inp, t, Pos);
        }

        // We reach here when element is not present in array
        return Pos;
    }

    private static void diff(Integer t, Integer[] line){
        /**
         * Given numbers and a diff, print the number of ints that have that diff. Bounds from search check for
         * duplicates.
         *
         * @param t Given diff
         * @param line input numbers
         * @return Print the lines to standard output
         */
        line = heapsort(line);
        long count = 0;
        for (int i = 0; i < line.length; i++) {
            Integer j = binarySearchLower(line, i + 1, line.length - 1, line[i], t, i);
            Integer k = binarySearchUpper(line, i + 1, line.length - 1, line[i], t, i);
            if (j != i && k != i) {
                count += (k - j + 1);
            }
        }

        System.out.println(count);
    }

    /**
     * The main method: take in numbers from the args and return the amount that match the diff
     *
     * @param args The numbers
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer n = sc.nextInt();
        Integer t =  sc.nextInt();
        Integer[] list = new Integer[n];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            list[i] = sc.nextInt();
        }
        diff(t,list);
    }
}
