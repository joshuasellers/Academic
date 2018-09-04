/**
 * Interface ModelListener specifies the interface for an object that is
 * triggered by events from the model object in the Network Bns Game.
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */

public interface ModelListener {
    /**
     * Report that the new game button was clicked
     */
    public void newGame();

    /**
     * Report that there one game waiting for another
     */
    public void waiting();

    /**
     * Report that the display has to be reset
     *
     * @param  b      boolean for if it's that player's turn
     * @param  name   name of the oponent
     */
    public void changeMessage(boolean b, String name);

    /**
     * Report that the games are closing
     */
    public void close();

    /**
     * Report that the stick is being updated
     *
     * @param  i         index
     * @param  visible   is the item visible
     */
    public void stickVisible(int i, boolean visible);

    /**
     * Report that the ball is being updated
     *
     * @param  i         index
     * @param  visible   is the item visible
     */
    public void ballVisible(int i, boolean visible);

    /**
     * Report that the game has ended
     *
     * @param  b         if this player won
     * @param  name      oponent's name
     */
    public void gameEnds(boolean b, String name);

}
