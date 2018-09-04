/**
 * Class BulletinBoard is the bulletin board main program for the project.
 * The command line arguments specify the host and port of the
 * program's mailbox.
 * <P>
 * Usage: <TT>java BulletinBoard <I>bhost</I> <I>bport</I> <I>key</I></TT>
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import edu.rit.util.Hex;

public class BulletinBoard {

    /**
     * Main program.  Takes in arguments and sets up mailbox.
     * Waits for messages to come in from posts.
     */
    public static void main(String[] args) throws Exception {
        // makes sure there are three arguments
        if (args.length != 3) usage();
        // read in args
        String stationhost = args[0];
        int stationport = Integer.parseInt (args[1]);
        String key = args[2];
        try {
            // converts key to byte array
            byte[] k = Hex.toByteArray(key);
            if (k.length != 8) usage2();
            // create mailbox
            DatagramSocket mailbox = new DatagramSocket(new InetSocketAddress(stationhost, stationport));
            // create ui, model and proxy
            BoardUI ui = BoardUI.create();
            BoardModel model = new BoardModel(ui, k);
            PostProxy proxy = new PostProxy(mailbox);
            proxy.setListener(model);
        }
        catch (IllegalArgumentException p){
            System.out.println("Invalid key");
            System.exit(1);
        }
        catch (IOException e){
            System.out.println("Error in establishing connection");
            System.exit(1);
        }
    }

    /**
     * Print a usage message for invalid key and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java BulletinBoard <bhost> <bport> <key>");
        System.exit (1);
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage2()
    {
        System.err.println ("Invalid key length");
        System.exit (1);
    }
}
