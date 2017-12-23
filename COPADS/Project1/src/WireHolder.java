/**
 * Class WireHolder is the monitor class for the Programming Project 1.
 *
 * @author  Joshua Sellers
 * @version 19-Sep-2017
 */

public class WireHolder {
    private int[] mon;
    /**
     * Constructor.  Creates an array to hold the
     * different wires.
     */
    public WireHolder() {
        this.mon = new int[25];
        for (int i = 0; i < this.mon.length; i++){
            // For state, -1 means no input
            this.mon[i] = -1;
        }
    }

    /**
     * Puts a value in the mon array, changes the -1 value
     *
     * @param i  index for value
     * @param value  value
     */
    public synchronized void putValue (int i, int value){
        // Error checking
        if(value != 0 && value != 1){
            System.out.println("Values must be zero or one");
            System.exit(0);
        }
        if (mon[i] != -1 && i != 0){
            System.out.println("Monitor array already has value at index "+i);
            System.exit(0);
        }
        // Input value at index
        this.mon[i] = value;
        // Notify threads
        notifyAll();
    }

    /**
     * Gets a value in the mon array if state array has notified
     *
     * @param i  index for value
     * @return int the value in wireholder
     */
    public synchronized int getValue (int i) {
        // Check is index has number
        while (this.mon[i] == -1) {
            try { wait(); }
            catch (InterruptedException e) { }
            finally { }
        }
        // Notify threads
        return this.mon[i];
    }
}
