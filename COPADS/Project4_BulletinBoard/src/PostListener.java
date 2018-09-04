/**
 * Interface PostListener specifies the interface to an object that receives
 * notifications from a post in the project.
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

import java.io.IOException;

public interface PostListener {
    // Exported operations.

    /**
     * Send post sent to board.
     *
     * @param  message message to post
     * @param  tag     authentication tag
     *
     * @exception IOException
     *     Thrown if an I/O error occurred.
     */
    public void post(byte[] message, byte[] tag) throws IOException;


    /**
     * Used for if there is an error in a packet/transmission
     */
    public void error();

}
