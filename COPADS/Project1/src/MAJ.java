/**
 * Created by joshuasellers on 9/29/17.
 */
/**
 * Class MAJ is the MAJ gate subclass for Tgate.
 */
public class MAJ extends Tgate implements Runnable{

    /**
     * Constructor. Uses the Tgates constructor.
     *
     * @param wireHolder  monitor class
     * @param a  a index
     * @param b  b index
     * @param carry  carry index
     */
    public MAJ(WireHolder wireHolder, int a, int b, int carry, int output){
        super(wireHolder,a,b,carry, output);
    }

    /**
     * Compute method.  Overridden in subclasses.
     *
     * @param a_val  value to be added
     * @param b_val  value to be added
     * @param carry_val  value to be added
     * @return int   value to be outputted
     */
    public int compute(int a_val, int b_val, int carry_val){
        int check = a_val + b_val + carry_val;
        int out;
        // Compute output
        if (check == 2 || check == 3) out = 1;
        else out = 0;
        return out;
    }
}
