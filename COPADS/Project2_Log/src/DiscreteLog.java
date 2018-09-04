/**
 * Class DiscreteLog is the main program for the Programming Project 2.
 * A discrete log calculator is run using the PJ2 library.
 * The inputs are taken in and looped to find the max and min
 * values for the exponent in the discrete log equation (if they exist).
 * <P>
 * Usage: java pj2 DiscreteLog <I>p</I> <I>a</I> <I>b</I>
 *
 * @author  Joshua Sellers
 * @version 8-Oct-2017
 */

import java.math.BigInteger;
import edu.rit.pj2.vbl.IntVbl;
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;

public class DiscreteLog extends Task {
    /**
     * Find all n values that match the
     * discrete logarithmic function
     * a^n (mod p) == b
     *
     * @param p  prime number
     * @param b  value to check on
     * @param a  value to run n's on
     * @return int[]  max and min n values
     */
    private int[] primeCheck(BigInteger p, BigInteger b, BigInteger a){
        // Create n[] for the max and min values
        int[] n = new int[2];
        // max and min holders
        IntVbl.Min nMin = new IntVbl.Min(p.intValue());
        IntVbl.Max nMax = new IntVbl.Max();
        // Test numbers for a^n (mod p) == b
        parallelFor (0, p.intValue() - 1) .exec (new Loop()
        {
            IntVbl.Min min;
            IntVbl.Max max;
            public void start()
            {
                max = threadLocal(nMax);
                min = threadLocal(nMin);
            }
            public void run (int i) {
                // If true, update max and min
                if (a.modPow(new BigInteger(Integer.toString(i)), p).compareTo(b) == 0){
                    // Change them if i is max or min value
                    max.item = Math.max (max.item, i);
                    min.item = Math.min (min.item, i);
                }
            }
        });
        n[0] = nMin.intValue();
        n[1] = nMax.intValue();
        return n;
    }

    /**
     * Print the discrete log results
     *
     * @param p  p value
     * @param b  b value
     * @param a  a value
     * @param n  list of possible n values
     * @return int[]  min and max n values
     */
    private void print(int p, int b, int a, int[] n){
        // No n values
        if (n[0] == p && n[1] == 0){
            System.out.println("Undefined");
        }
        // One n value
        else if (n[0] == n[1]){
            System.out.println(a + "^" + n[1] + " (mod " + p + ") = " + b);
        }
        // Max/Min n values
        else{
            System.out.println(a + "^" + n[0] + " (mod " + p + ") = " + b);
            System.out.println(a + "^" + n[1] + " (mod " + p + ") = " + b);
        }
    }

    /**
     * Main program. Runs error checking on inputs,
     * then runs then checks for discrete logarithmic
     * values on the inputs in the form a^n(mod p) == b.
     */
    public void main(String[] args) throws Exception {
        // Error checking
        if (args.length != 3){
            System.out.println("Error: Program requires three arguments");
            System.exit(0);
        }
        else {
            try{
                Integer.parseInt(args[0]);
                Integer.parseInt(args[1]);
                Integer.parseInt(args[2]);
            }
            catch(NumberFormatException e){
                System.out.println("Error: Inputs are not numbers");
                System.exit(0);
            }
        }
        // Create BigIntegers for the inputs
        BigInteger p, b, a;
        p = new BigInteger(args[0]);
        a = new BigInteger(args[1]);
        b = new BigInteger(args[2]);
        // More error checking
        if (p.compareTo(new BigInteger("2")) == -1){
            System.out.println("Error: <p> must be greater than or equal to 2");
            System.exit(0);
        }
        else if (!p.isProbablePrime(64)){
            System.out.println("Error: <p> must be prime");
            System.exit(0);
        }
        else if (b.compareTo(new BigInteger("1")) == -1
                || b.compareTo(new BigInteger(Integer.toString(p.intValue()-1))) == 1){
            System.out.println("Error: <b> must follow equality: 1 <= <b> <= <p> -1");
            System.exit(0);
        }
        else if (a.compareTo(new BigInteger("1")) == -1
                || a.compareTo(new BigInteger(Integer.toString(p.intValue()-1))) == 1){
            System.out.println("Error: <a> must follow equality: 1 <= <b> <= <p> -1");
            System.exit(0);
        }
        // Get possible n values
        int[] n = primeCheck(p,b,a);
        // Print results
        print(p.intValue(),b.intValue(),a.intValue(),n);
    }
}
