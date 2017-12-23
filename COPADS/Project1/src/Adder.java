/**
 * Class Adder is the main program for the Programming Project 1. Multiple threads
 * run a binary adder. One thread takes the inputs <I>a5a4a3a2a1a0</I>
 * <I>b5b4b3b2b1b0</I>, a another group of threads sums them and a final thread
 * outputs the summation. The threads are synchronized with a monitor.
 * <P>
 * Usage: java Adder <I>a5a4a3a2a1a0</I> <I>b5b4b3b2b1b0</I>
 *
 * @author  Joshua Sellers
 * @version 19-Sep-2017
 */

public class Adder {

    /**
     * Checks if the string contains only numbers
     *
     * @param inp  input string
     * @return boolean  Whether inp is a int
     */
    private static boolean isInt(String inp){
        // no inp
        if(inp.isEmpty()){
            return false;
        }
        for(int i = 0; i < inp.length(); i++) {
            if(i == 0 && inp.charAt(i) == '-') {
                // only '-'
                if(inp.length() == 1) {
                    return false;
                }
                else {
                    continue;
                }
            }
            // character is not numeric
            if(Character.digit(inp.charAt(i),10) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the string contains only ones and zeros
     *
     * @param inp  input string
     * @return boolean  Whether inp is binary
     */
    private static boolean isBin(String inp){
        for(int i = 0; i < inp.length(); i++) {
            if(Character.digit(inp.charAt(i),10) != 0
                    && Character.digit(inp.charAt(i),10) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Main program. Runs error checking on inputs,
     * then runs the binary adder threads, calling start on them.
     */
    public static void main(String[] args) throws Throwable {
        // Error checking
        if (args.length != 2){
            System.out.println("Error: Program requires two arguments");
            System.exit(0);
        }
        else if (!isInt(args[0])|| !isInt(args[1])){
            System.out.println("Error: Inputs are not numbers");
            System.exit(0);
        }
        else if (!isBin(args[0])||!isBin(args[1])){
            System.out.println("Error: Inputs are not binary");
            System.exit(0);
        }
        else if (args[0].length() != 6 || args[1].length() != 6){
            System.out.println("Error: Inputs are not correct length");
            System.exit(0);
        }
        // Specify wires for input and output
        // For inp order is: first carry (1 item), b (6 items), a (6 items)
        int[] inp = {0,6,5,4,3,2,1,12,11,10,9,8,7};
        int[] out = {24,23,22,21,20,19,18};
        // Create objects
        WireHolder w = new WireHolder();
        Tinput tinput = new Tinput(w, args[0],args[1], inp);
        Toutput toutput = new Toutput(tinput.getWireHolder(), out);
        SUM sum0 = new SUM(tinput.getWireHolder(), 7, 1, 0, 18);
        MAJ maj0 = new MAJ(tinput.getWireHolder(), 7, 1, 0, 13);
        SUM sum1 = new SUM(tinput.getWireHolder(), 8, 2, 13, 19);
        MAJ maj1 = new MAJ(tinput.getWireHolder(), 8, 2, 13, 14);
        SUM sum2 = new SUM(tinput.getWireHolder(), 9, 3, 14, 20);
        MAJ maj2 = new MAJ(tinput.getWireHolder(), 9, 3, 14, 15);
        SUM sum3 = new SUM(tinput.getWireHolder(), 10, 4, 15, 21);
        MAJ maj3 = new MAJ(tinput.getWireHolder(), 10, 4, 15, 16);
        SUM sum4 = new SUM(tinput.getWireHolder(), 11, 5, 16, 22);
        MAJ maj4 = new MAJ(tinput.getWireHolder(), 11, 5, 16, 17);
        SUM sum5 = new SUM(tinput.getWireHolder(), 12, 6, 17, 23);
        MAJ maj5 = new MAJ(tinput.getWireHolder(), 12, 6, 17, 24);
        // Create threads
        Thread tout = new Thread(toutput);
        Thread s0 = new Thread(sum0);
        Thread m0 = new Thread(maj0);
        Thread s1 = new Thread(sum1);
        Thread m1 = new Thread(maj1);
        Thread s2 = new Thread(sum2);
        Thread m2 = new Thread(maj2);
        Thread s3 = new Thread(sum3);
        Thread m3 = new Thread(maj3);
        Thread s4 = new Thread(sum4);
        Thread m4 = new Thread(maj4);
        Thread s5 = new Thread(sum5);
        Thread m5 = new Thread(maj5);
        Thread tin = new Thread(tinput);
        // Start threads
        tout.start();
        s0.start();
        m0.start();
        s1.start();
        m1.start();
        s2.start();
        m2.start();
        s3.start();
        m3.start();
        s4.start();
        m4.start();
        s5.start();
        m5.start();
        tin.start();
    }
}
