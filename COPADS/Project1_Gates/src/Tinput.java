/**
 * Class Tinput is the input program for the Programming Project 1.
 * It takes in the Monitor class and populates it with the inputs.
 *
 * @author  Joshua Sellers
 * @version 19-Sep-2017
 */

public class Tinput implements Runnable {
    private String arrA;
    private Integer[] inpA;
    private String arrB;
    private Integer[] inpB;
    private WireHolder wireHolder;
    private int[] inp_arr;

    /**
     * Constructor.
     * Takes in the two input strings of ones and zeros.
     *
     * @param arrA  a values
     * @param arrB  b values
     */
    public Tinput(WireHolder wireHolder, String arrA, String arrB,
                  int[] inp_arr){
        this.inp_arr = inp_arr;
        this.wireHolder = wireHolder;
        this.arrA = arrA;
        this.inpA = new Integer[arrA.length()];
        // Convert a char values to ints
        for (int i = 0; i < arrA.length(); i++){
            this.inpA[i] = Character.getNumericValue(this.arrA.charAt(i));
        }
        this.arrB = arrB;
        this.inpB = new Integer[arrB.length()];
        // Convert b char values to ints
        for (int i = 0; i < arrB.length(); i++){
            this.inpB[i] = Character.getNumericValue(this.arrB.charAt(i));
        }
    }

    /**
     * returns the wireholder
     *
     * @return WireHolder Class's wireholder
     */
    public WireHolder getWireHolder(){return this.wireHolder;}

    /**
     * Run method for the class.  Puts input in wireholder at
     * specified locations.
     */
    @Override
    public void run(){
        wireHolder.putValue(inp_arr[0],0);
        int countb = 0;
        // Input b values
        for (int i = 1; i <= inpB.length; i++){
            wireHolder.putValue(inp_arr[i], inpB[countb]);
            countb++;
        }
        // Input a values
        int counta = 0;
        for (int i = 1 + inpB.length; i < inp_arr.length; i++){
            wireHolder.putValue(inp_arr[i], inpA[counta]);
            counta++;
        }
    }
}
