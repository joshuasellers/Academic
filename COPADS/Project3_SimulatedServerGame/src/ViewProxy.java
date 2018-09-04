/**
 * Class ViewProxy provides the network proxy for the view object in the Network
 * Bns Game. The view proxy resides in the server program and communicates with
 * the client program.
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ViewProxy implements ModelListener{

    // Hidden data members.

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ViewListener viewListener;

    /**
     * Construct a new view proxy.
     *
     * @param  socket  Socket.
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public ViewProxy(Socket socket) throws IOException {
        this.socket = socket;
        socket.setTcpNoDelay (true);
        out = new DataOutputStream (socket.getOutputStream());
        in = new DataInputStream (socket.getInputStream());
    }

    // Exported operations.

    /**
     * Set the view listener object for this view proxy.
     * Set oponent's name.
     *
     * @param  viewListener  View listener.
     */
    public void setViewListener(ViewListener viewListener) {
        if (this.viewListener == null) {
            this.viewListener = viewListener;
            new ReaderThread().start();
        }
        else {
            this.viewListener = viewListener;
        }
    }

    /**
     * Report that a ball is visible
     *
     * @param  i            ball index
     * @param  visible      is ball visible
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void ballVisible (int i, boolean visible) {
        try {
            out.writeByte('B');
            out.writeByte(i);
            out.writeBoolean(visible);
            out.flush();
        }
        catch (IOException e){

        }
    }

    /**
     * Report that a stick is visible
     *
     * @param  i            stick index
     * @param  visible      is stick visible
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void stickVisible (int i, boolean visible) {
        try {
            out.writeByte('S');
            out.writeByte(i);
            out.writeBoolean(visible);
            out.flush();
        }
        catch (IOException e){

        }
    }

    /**
     * Report that a new game occurs
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void newGame(){
        try {
            out.writeByte('N');
            out.flush();
        }
        catch (IOException e){

        }
    }

    /**
     * Report that a display is reset
     *
     * @param  b          who's turn it is
     * @param  name       oponent's name
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void changeMessage(boolean b, String name){
        try {
            out.writeByte('D');
            out.writeBoolean(b);
            out.writeUTF(name);
            out.flush();
        }
        catch (IOException e){

        }
    }

    /**
     * Report that a player is waiting
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void waiting(){
        try {
            out.writeByte('W');
            out.flush();
        }
        catch (IOException e){

        }
    }

    /**
     * Report that the game closes
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void close(){
        try {
            out.writeByte('C');
            out.flush();
        }
        catch (IOException e){

        }
    }

    /**
     * Report that a game has ended
     *
     * @param  b            did the player win
     * @param  name         oponent's name
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void gameEnds(boolean b, String name){
        try {
            out.writeByte('E');
            out.writeBoolean(b);
            out.writeUTF(name);
            out.flush();
        }
        catch (IOException e){

        }
    }

    /**
     * Class ReaderThread receives messages from the network, decodes them, and
     * invokes the proper methods to process them.
     *
     * @author  Josh Sellers
     * @version 11-Nov-2017
     */
    private class ReaderThread extends Thread {
        public void run() {
            try {
                // go through possible messages
                for (;;) {
                    int i;
                    String n;
                    byte b = in.readByte();
                    switch (b) {
                        case 'J':
                            n = in.readUTF();
                            viewListener.join(ViewProxy.this, n);
                            break;
                        case 'B':
                            i = in.readByte();
                            viewListener.clickBall(i, ViewProxy.this);
                            viewListener.gameStatus();
                            break;
                        case 'S':
                            i = in.readByte();
                            viewListener.clickStick(i,ViewProxy.this);
                            viewListener.gameStatus();
                            break;
                        case 'C':
                            viewListener.closeGame();
                            break;
                        case 'N':
                            viewListener.createNewGame();
                            break;
                        default:
                            System.err.println ("Bad message in View Proxy");
                            break;
                    }
                }
            }
            catch (EOFException exc) {}
            catch (IOException exc) {exc.printStackTrace (System.err);}
            finally {
                try {socket.close();}
                catch (IOException exc) {}
            }
        }
    }
}
