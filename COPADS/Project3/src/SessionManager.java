/**
 * Class SessionManager maintains the sessions' model objects in the Network Bns
 * Game server.
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class SessionManager implements ViewListener {
    // Hidden data members.
    private BnsModel model;

    /**
     * Construct a new session manager.
     */
    public SessionManager() {}

    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     * @param  name     player's name
     *
     * @exception IOException
     *     Thrown if an I/O error occurred.
     */
    public synchronized void join(ViewProxy proxy, String name) throws IOException {
        // if no game running
        if (model == null) {
            model = new BnsModel();
            // model has no players
            if (model.isNull()) {
                model.addModelListener(proxy, name);
                proxy.setViewListener(model);
            }
        }
        // add to a running game
        else {
            // game has one player
            if (model.oneNull()) {
                model.addModelListener(proxy, name);
                proxy.setViewListener(model);
                model = null;
            }
            // create new game
            else {
                model = null;
                model = new BnsModel();
                if (model.isNull()) {
                    model.addModelListener(proxy, name);
                    proxy.setViewListener(model);
                }
            }
        }

    }

    /**
     * Close the game
     */
    public void closeGame() {}

    /**
     * Create a new game
     */
    public void createNewGame() {}

    /**
     * Click the ball
     *
     * @param  i    item index
     * @param  t    who's turn it is
     */
    public void clickBall(int i,ViewProxy t) {}

    /**
     * Click the stick
     *
     * @param  i    item index
     * @param  t    who's turn it is
     */
    public void clickStick(int i,ViewProxy t) {}

    /**
     * Tells model the game has ended
     */
    public void gameStatus(){}
}
