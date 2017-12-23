/**
 * Class BoardProxy provides the network proxy for the bulletin board object in
 * the project. The board proxies reside in the post programs and communicate with
 * the board program.
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class BoardProxy implements PostListener {

    // Hidden data members.

    private DatagramSocket mailbox;
    private SocketAddress destination;

    // Exported constructors.

    /**
     * Construct a board proxy.
     *
     * @param  mailbox      Mailbox.
     * @param  destination  Destination mailbox address.
     */
    public BoardProxy(DatagramSocket mailbox, SocketAddress destination) {
        this.mailbox = mailbox;
        this.destination = destination;
    }

    // Exported operations.

    /**
     * Post to the board.
     *
     * @param  message message to post
     * @param  tag     tag for authentication of post
     *
     * @exception IOException
     *     Thrown if an I/O error occurred.
     */
    public void post(byte[] message, byte[] tag) throws IOException {
        // create payload
        byte[] payload = new byte[message.length+tag.length];
        // populate payload
        for (int i = 0; i < message.length; i++){
            payload[i] = message[i];
        }
        for (int i = 0; i < tag.length; i++){
            payload[i+message.length] = tag[i];
        }
        // send payload
        mailbox.send (new DatagramPacket(payload, payload.length, destination));
    }

    /**
     * Used for if there is an error in a packet/transmission
     */
    public void error(){}
}
