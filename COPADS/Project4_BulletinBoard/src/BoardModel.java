/**
 * Class BoardModel provides the application logic for the bulletin board in the
 * project.
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

import java.nio.charset.Charset;


public class BoardModel implements PostListener {
    // Hidden data members.
    private BoardUI ui;
    private byte[] key;
    private HMAC hmac;
    // Exported constructors.

    /**
     * Construct a new board model object.
     *
     * @param  ui  User interface.
     * @param  key Private key
     */
    public BoardModel(BoardUI ui, byte[] key) {
        this.ui = ui;
        this.key = key;
    }

    // exported operations

    /**
     * Post a message to the board.
     *
     * @param  message   Message to post
     * @param  tag       Authentication tag
     */
    public void post(byte[] message, byte[] tag){
        // create HMAC object
        hmac = new HMAC(message,key);
        // get tag to check
        byte[] check = hmac.getTag();
        // output message
        if (hmac.compareTags(tag)){
            ui.output(new String(message, Charset.forName("UTF-8")));
        }
        else ui.output("FORGERY");
        hmac = null;
    }

    /**
     * Used for if there is an error in a packet/transmission
     */
    public void error(){
        ui.output("ERROR");
    }
}
