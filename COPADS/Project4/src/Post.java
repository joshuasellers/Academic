/**
 * Class Post is the post main program for the bulliten board simulation.
 * The command line arguments specify the host and port of the
 * board's mailbox, the host and port of the post program's
 * mailbox, the key and the message.
 * <P>
 * Usage: <TT>java Post <I>bhost</I> <I>bport</I>
 * <I>phost</I> <I>pport</I> <I>key</I> <I>message</I></TT>
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

import edu.rit.util.Hex;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class Post {

    /**
     * Main program.  Takes in command line arguments and creates post
     * objects.  THen it sends message to board.
     */
    public static void main(String[] args) throws Exception {
        // correct number of arguments
        if (args.length != 6) usage();
        // read in args
        String bhost = args[0];
        int bport = Integer.parseInt (args[1]);
        String phost = args[2];
        int pport = Integer.parseInt (args[3]);
        String key = args[4];
        String message = args[5];
        // convert message to byte array
        byte[] b = message.getBytes(Charset.forName("UTF-8"));
        try {// converts key to byte array
            byte[] k = Hex.toByteArray(key);
            if (k.length != 8) usage2();
            // create mailbox
            DatagramSocket mailbox = new DatagramSocket(new InetSocketAddress(phost, pport));
            // create board proxy and post model
            BoardProxy proxy = new BoardProxy(mailbox, new InetSocketAddress(bhost, bport));
            PostModel model = new PostModel(b, proxy, k);
        }
        catch (IOException e){
            // any IO or connection errors
            System.out.println("Error in establishing connection");
            System.exit(1);
        }
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java Post <bhost> <bport> <phost> <pport> <key> <message>");
        System.exit (1);
    }

    /**
     * Print a usage message for invalid key and exit.
     */
    private static void usage2()
    {
        System.err.println ("Invalid key length");
        System.exit (1);
    }

}
