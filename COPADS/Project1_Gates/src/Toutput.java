/**
 * Class Toutput is the output program for the Programming Project 1.
 * It takes in the Monitor class and outputs the result.
 *
 * @author  Joshua Sellers
 * @version 19-Sep-2017
 */

public class Toutput implements Runnable{
    private WireHolder wireHolder;
    private int[] cOut;

    /**
     * Constructor.  Takes in a filled wireholder.
     *
     * @param wireHolder  monitor class containing values
     */
    public Toutput(WireHolder wireHolder, int[] cOut){
        this.wireHolder = wireHolder;
        this.cOut = cOut;
    }

    /**
     * Run method for the class.  Prints output c values
     * from specified indices.
     */
    @Override
    public void run(){
        for (int i = 0; i < cOut.length; i++){
            System.out.print(wireHolder.getValue(cOut[i]));
        }
        System.out.print("\n");
    }

}
