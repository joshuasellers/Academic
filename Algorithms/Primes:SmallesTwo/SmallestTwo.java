/**
 * HW0: Testing the try system
 * Class: SmallestTwo
 * Description: contains all of the methods used to run the lines homework problem
 * Created by joshuasellers on 9/5/17.
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework assignments

/**
 * This class can returns the smallest two numbers from the input
 *
 * @author jds4433 (Josh Sellers)
 */
public class SmallestTwo {

    private static void smallest(Integer[] n){
        /**
         * Given numbers, print the smallest two of those two numbers
         *
         * @param n Given numbers
         * @return Print the lines to standard output
         */
        int first = n[0];  // number to be printed
        int second = n[0]; // number to be printed
        // find smallest
        for(int i = 0; i < n.length; i++){
            if(n[i] < first){
                first = n[i];
            }
        }
        // find second smallest
        for(int i = 0; i < n.length; i++){
            if(n[i] < second && n[i] > first){
                second = n[i];
            }
        }
        System.out.println(first);
        System.out.println(second);
    }

    /**
     * The main method: take in numbers from the args and return the smallest two
     *
     * @param args The numbers
     */
    public static void main(String[] args) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String list = "";
        String line;
        try {
            // Takes in the lines as a string
            while ((line = stdin.readLine()) != null) {
                list = list + " " + line;
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }
        // Converts the string to a list of strings
        String[] nums = list.split(" ");
        Integer[] lines = new Integer[nums.length-1];
        // Converts the list to a list of ints
        for(int i = 1; i < nums.length; i++){
            lines[i-1] = Integer.parseInt(nums[i]);
        }
        smallest(lines);
    }
}