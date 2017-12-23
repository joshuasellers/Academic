/**
 * Class Tgate is the gate program for the Programming Project 1.
 * It has the SUM and MAJ gates used for the adder as subclasses.
 *
 * @author  Joshua Sellers
 * @version 19-Sep-2017
 */

public abstract class Tgate implements Runnable {
    protected WireHolder wireHolder;
    protected int a;
    protected int b;
    protected int carry;
    protected int output;

    /**
     * Constructor.  Generic constructor for the SUM and
     * MAJ subclasses.  Takes in the wireholder object and
     * the specified indices.
     *
     * @param wireHolder  monitor class
     * @param a  a index
     * @param b  b index
     * @param carry  carry index
     */
    public Tgate(WireHolder wireHolder, int a, int b, int carry, int output){
        this.wireHolder = wireHolder;
        this.a = a;
        this.b = b;
        this.carry = carry;
        this.output = output;
    }

    /**
     * Run method for the superclass.
     * Computes values and puts them in correct location.
     */
    @Override
    public void run(){
        int a_val = wireHolder.getValue(this.a);
        int b_val = wireHolder.getValue(this.b);
        int carry_val = wireHolder.getValue(this.carry);
        int  out = compute(a_val, b_val, carry_val);
        wireHolder.putValue(output,out);
    }

    /**
     * Compute method.  Overridden in subclasses.
     *
     * @param a_val  value to be added
     * @param b_val  value to be added
     * @param carry_val  value to be added
     * @return int   value to be outputted
     */
    public abstract int compute(int a_val, int b_val, int carry_val);
}
