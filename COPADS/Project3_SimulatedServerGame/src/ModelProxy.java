/**
 * Class ModelProxy provides the network proxy for the model object in the
 * Network Bns Game. The model proxy resides in the client program and
 * communicates with the server program.
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;


public class ModelProxy implements ViewListener {
    // Hidden data members.
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ModelListener modelListener;

    // Exported constructors.
    /**
     * Construct a new model proxy.
     *
     * @param  socket  Socket.
     *
     * @exception IOException
     *     Thrown if an I/O error occurred.
     */
    public ModelProxy(Socket socket) throws IOException {
        this.socket = socket;
        socket.setTcpNoDelay (true);
        out = new DataOutputStream (socket.getOutputStream());
        in = new DataInputStream (socket.getInputStream());
    }

    /**
     * Set the model listener object for this model proxy.
     *
     * @param  modelListener  Model listener.
     */
    public void setModelListener(ModelListener modelListener) {
        this.modelListener = modelListener;
        new ReaderThread().start();
    }

    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     * @param  name     Reference to the player's name
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void join(ViewProxy proxy, String name) throws IOException {
        out.writeByte ('J');
        out.writeUTF(name);
        out.flush();
    }

    /**
     * Update that the ball has been clicked
     *
     * @param  i    Reference to ball's index
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void clickBall(int i, ViewProxy v) throws IOException {
        out.writeByte ('B');
        out.writeByte (i);
        out.flush();
    }

    /**
     * Update that the stick has been clicked
     *
     * @param  i    Reference to stick's index
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void clickStick(int i, ViewProxy v) throws IOException {
        out.writeByte ('S');
        out.writeByte (i);
        out.flush();
    }

    /**
     * Update that the games must close
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void closeGame() throws IOException {
        out.writeByte ('C');
        out.flush();
    }

    /**
     * Update that a new game is created
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void createNewGame() throws IOException {
        out.writeByte ('N');
        out.flush();
    }

    /**
     * Update that the game has ended
     *
     */
    public void gameStatus(){}

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
                    boolean bool;
                    String name;
                    byte b = in.readByte();
                    switch (b) {
                        case 'B':
                            i = in.readByte();
                            bool = in.readBoolean();
                            modelListener.ballVisible(i,bool);
                            break;
                        case 'S':
                            i = in.readByte();
                            bool = in.readBoolean();
                            modelListener.stickVisible(i,bool);
                            break;
                        case 'N':
                            modelListener.newGame();
                            break;
                        case 'C':
                            modelListener.close();
                            break;
                        case 'E':
                            bool = in.readBoolean();
                            name = in.readUTF();
                            modelListener.gameEnds(bool, name);
                            break;
                        case 'D':
                            bool = in.readBoolean();
                            name = in.readUTF();
                            modelListener.changeMessage(bool,name);
                            break;
                        case 'W':
                            modelListener.waiting();
                            break;
                        default:
                            System.err.println ("Bad message in Model Proxy");
                            break;
                    }
                }
            }
            catch (EOFException exc) {}
            catch (IOException exc) {exc.printStackTrace (System.err);}
            finally {
                try {
                    socket.close();
                }
                catch (IOException exc) {
                }
            }
        }
    }

}
