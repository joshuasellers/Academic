/**
 * Class PostProxy provides the network proxy for the post objects in
 * the post. The post proxy resides in the board
 * program and communicates with the post programs.
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PostProxy {
    // Hidden data members.

    private DatagramSocket mailbox;
    private PostListener listener;

    // Exported constructors.

    /**
     * Construct a new post proxy.
     *
     * @param  mailbox  Mailbox.
     *
     * @exception IOException
     *     Thrown if an I/O error occurred.
     */
    public PostProxy(DatagramSocket mailbox) {this.mailbox = mailbox;}

    // Exported operations.

    /**
     * Set the post listener object for this view proxy.
     *
     * @param  listener  Sensor listener.
     */
    public void setListener(PostListener listener) {
        this.listener = listener;
        new ReaderThread().start();
    }

    // Hidden helper classes.

    /**
     * Class ReaderThread receives messages from the network, decodes them, and
     * invokes the proper methods to process them.
     */
    private class ReaderThread extends Thread {
        public void run() {
            byte[] payload = new byte [1024]; /* CAREFUL OF BUFFER SIZE! */
            byte[] message;
            byte[] tag;
            try {
                for (;;) {
                    tag  = new byte[32];
                    // read in packet
                    DatagramPacket packet = new DatagramPacket (payload, payload.length);
                    mailbox.receive(packet);
                    DataInputStream in = new DataInputStream(new ByteArrayInputStream(payload, 0, packet.getLength()));
                    in.read(payload);
                    // get tag
                    int l = 0;
                    if (packet.getLength() < 32) listener.error();
                    else {
                        message = new byte[packet.getLength() - 32];
                        for (int i = 0; i < message.length; i++) {
                            message[i] = payload[i];
                        }
                        int counter = 0;
                        for (int i = packet.getLength() - 32; i < packet.getLength(); i++) {
                            tag[counter] = payload[i];
                            counter++;
                        }
                        // post message
                        listener.post(message, tag);
                    }
                }
            }
            catch (IOException exc) {
                listener.error();
            }
        }
    }
}
