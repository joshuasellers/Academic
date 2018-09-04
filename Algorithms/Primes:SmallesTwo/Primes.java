/**
 * HW0: Testing the try system
 * Class: Primes
 * Description: contains all of the methods used to run the prime homework problem
 * Created by joshuasellers on 9/5/17.
 */

import java.lang.Math;  // Allowed for homework assignments
import java.io.*;  // Allowed for homework assignments
import java.util.Scanner;  // Allowed for homework assignments

/**
 * This class can return all primes less than and equal to a number.
 *
 * @author jds4433 (Josh Sellers)
 */
public class Primes {

    private static void listPrimes(int n){
        /**
         * Given a number, list all primes less than and equal to it.
         *
         * @param n Given number
         * @return Print the primes to standard output
         */
        boolean prime = true;
        for(int i = 2; i <= n; i++){
            // i is the number to be tested
            for (int j = 2; j < i; j++) {
                // test j on i
                if (i % j == 0) {
                    prime = false;
                    break;
                } else {
                    prime = true;
                }
            }
            if(prime == true){
                // i is prime
                System.out.println(i);
            }
        }
    }

    /**
     * The main method: take in a number from the input and finds all primes less than or equal to it
     *
     * @param args The number
     */
    public static void main(String[] args) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        try{
            String line = stdin.readLine();
            int n = Integer.parseInt(line);
            listPrimes(n); // Call method
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }
}
