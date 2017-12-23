/**
 * Interface ViewListener specifies the interface for an object that is
 * triggered by events from the view object in the Network Bns Game.
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */
import java.io.IOException;

public interface ViewListener {
    /**
     * Click the ball
     *
     * @param  i    item index
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void clickBall(int i, ViewProxy v) throws IOException;

    /**
     * Click the stick
     *
     * @param  i    item index
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void clickStick(int i, ViewProxy v) throws IOException;

    /**
     * join the game
     *
     * @param  proxy    games ViewProxy
     * @param  name     oponents name
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void join(ViewProxy proxy, String name) throws IOException;

    /**
     * Create a new game
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void createNewGame() throws IOException;

    /**
     * Close the game
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void closeGame() throws IOException;

    /**
     * Tells model the game has ended
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public void gameStatus() throws IOException;

}
