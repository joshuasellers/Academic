/**
 * Class Reservoir finds the maximum volume that fills in a depression in a 1-D landscape.
 * It takes two lines of inputs <I>n</I> and <I>a0a1a2a3a4...an</I>, where the second line
 * represents the landscape. It outputs the volume.
 * <P>
 * Usage: java Reservoir < Input File
 *
 * @author  Joshua Sellers
 * @version 27-Sep-2017
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework assignments

public class Reservoir {
    /**
     * Runs the first pass over the input, finding potential maxima
     *
     * @param list  input string
     * @return Integer[]  A list of maxima
     */
    public static Integer[] firstPass(Integer[] list){
        Integer[] heights = new Integer[list.length];
        // Initialize returned list
        for (int j = 0; j < heights.length; j++){
            heights[j] = 0;
        }
        boolean saved = false;
        for (int i = 0; i < list.length; i++){
            // Initial maxima
            if (saved == false && i+1 < list.length && list[i+1] < list[i]){
                saved = true;
                heights[i] = 1;
                i++;
            }
            // Second maxima
            else if (saved == true && i-1 > 0 && list[i-1] < list[i]){
                saved = false;
                heights[i] = 1;
            }
        }
        return heights;
    }

    /**
     * Runs the second pass over the input in the opposite direction,
     * finding more potential maxima
     *
     * @param list  input string
     * @return Integer[]  A list of maxima
     */
    public static Integer[] secondPass(Integer[] list, Integer[] heights){
        boolean saved = false;
        for (int i = list.length-1; i >= 0; i--){
            // Initial maxima
            if (saved == false && i-1 > 0 && list[i-1] < list[i]){
                saved = true;
                heights[i] = 1;
                i--;
            }
            // Second maxima
            else if (saved == true && i+1 < list.length && list[i+1] < list[i]){
                saved = false;
                heights[i] = 1;
            }
        }
        return heights;
    }

    /**
     * Runs the forward pass over the input, filling in land between
     * maxima to find the largest area.
     *
     * @param list  input string
     * @param heights contains maxima
     * @return long  largest area found
     */
    public static long maxAreaF(Integer[] list, Integer[] heights){
        // Holder index for starting maxima
        int index = 0;
        // Bounds for the loop
        int last_height = 0;
        int first_height = 0;
        long areaF1 = 0;
        long areaF2 = 0;
        // Maxima being used
        boolean held = false;
        for (int j = 0; j < heights.length; j++){
            if (heights[j] == 1) last_height = j;
        }
        for (int j = 0; j < heights.length; j++){
            if (heights[j] == 1) {
                first_height = j;
                break;
            }
        }
        for (int i = first_height; i < last_height; i++){
            if (heights[i] == 1){
                // No area started
                if (held == false){
                    index = i;
                    held = true;
                }
                // Adding to existing area
                else if (held == true && list[i] >= list[index]){
                    // Close out area
                    if (areaF1 > areaF2) {
                        areaF2 = 0;
                        index = i;
                    }
                    else {
                        areaF1 = areaF2;
                        areaF2 = 0;
                        index = i;
                    }
                }
                // Add to area
                else if (held == true && list[i] < list[index]){
                    areaF2 += list[index] - list[i];
                }
            }
            else {
                // Add to area
                if (held == true){
                    areaF2 += list[index] - list[i];
                }
            }
        }
        return areaF1;
    }

    /**
     * Runs the reverse pass over the input, filling in land between
     * maxima to find the largest area.
     *
     * @param list  input string
     * @param heights contains maxima
     * @return long  largest area found
     */
    public static long maxAreaR(Integer[] list, Integer[] heights){
        // Holder index for starting maxima
        int index = 0;
        // Bounds for the loop
        int first_height = 0;
        int last_height = 0;
        long areaR1 = 0;
        long areaR2 = 0;
        for (int j = 0; j < heights.length; j++){
            if (heights[j] == 1) last_height = j;
        }
        for (int j = 0; j < heights.length; j++){
            if (heights[j] == 1) {
                first_height = j;
                break;
            }
        }
        // Maxima being used
        boolean held = false;
        for (int i = last_height; i >= first_height; i--){
            if (heights[i] == 1){
                // No area started
                if (held == false){
                    index = i;
                    held = true;
                }
                else if (held == true && list[i] >= list[index]){
                    // Close out area
                    if (areaR1 > areaR2) {
                        areaR2 = 0;
                        index = i;
                    }
                    else {
                        areaR1 = areaR2;
                        areaR2 = 0;
                        index = i;
                    }
                }
                // Adding to existing area
                else if (held == true && list[i] < list[index]){
                    areaR2 += list[index] - list[i];
                }
            }
            else {
                // Adding to existing area
                if (held == true){
                    areaR2 += list[index] - list[i];
                }
            }
        }
        return areaR1;
    }

    /**
     * Main program. Runs area checking on inputs,
     * then outputs the maximum area.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer n = sc.nextInt();
        Integer[] list = new Integer[n];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            list[i] = sc.nextInt();
        }
        Integer[] first_heights = firstPass(list);
        Integer[] second_heights = secondPass(list, first_heights);
        long areaF = maxAreaF(list, second_heights);
        long areaR = maxAreaR(list, second_heights);
        // Find largest area
        if (areaF > areaR) System.out.println(areaF);
        else System.out.println(areaR);
    }
}
