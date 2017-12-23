/**
 * Class BnsServer is the server main program for the Network Bns Game. The
 * command line arguments specify the host and port to which the server should
 * listen for connections.
 * <P>
 * Usage: java BnsServer <I>host</I> <I>port</I>
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BnsServer {

    /**
     * Main program.  User inputs host and port and the main
     * runs the server program.
     */
    public static void main(String[] args) throws Exception {
        // Two arguments needed
        if (args.length != 2) usage();
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        // Set up connection
        try {
        ServerSocket serversocket = new ServerSocket();
        serversocket.bind(new InetSocketAddress(host, port));
        // Create session manager
        SessionManager manager = new SessionManager();
        // Create proxies and connect
        for (; ; ) {
            Socket socket = serversocket.accept();
            ViewProxy proxy = new ViewProxy(socket);
            proxy.setViewListener(manager);
        }
        }
        catch (IOException e){
            System.out.println("Error in establishing connection");
            System.exit(1);
        }
    }


    private static void usage() {
        System.err.println ("Usage: java BnsServer <host> <port>");
        System.exit (1);
    }
}
