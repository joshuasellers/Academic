/**
 * Class Bns is the client main program for the Network Bns Game. The
 * command line arguments specify the host and port to which the server is
 * listening for connections.
 * <P>
 * Usage: java Bns <I>host</I> <I>port</I> <I>name</I>
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */

// Imports
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Bns {

    /**
     * Main program.  User inputs host, port, name and the main
     * runs the program.
     */
    public static void main(String[] args) throws Exception{
        // Three arguments needed
        if (args.length != 3) usage();
        // Take in main args
        String host = args[0];
        int port = Integer.parseInt (args[1]);
        String playerName = args[2];
        try {
            // Set up connection
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            // Set up network proxies and view
            BnsUI view = BnsUI.create(playerName);
            ModelProxy proxy = new ModelProxy(socket);
            view.setViewListener(proxy);
            proxy.setModelListener(view);
            proxy.join(null, playerName);
        }
        catch (IOException e){
            System.out.println("Error in establishing connection");
            System.exit(1);
        }
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage() {
        System.err.println ("Usage: java Bns <host> <port> <name>");
        System.exit (1);
    }
}
