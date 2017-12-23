import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class BnsModel provides the server-side model object in the Network Bns Game.
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */

public class BnsModel implements ViewListener{
    // Hidden data members.
    private boolean[] balls = new boolean[9];
    private int[][] ballCombos = {{0,6},{0,1,8},{1,10},{6,2,7},{2,3,8,9},{3,10,11},{4,7},{4,9,5},{5,11}};
    private boolean[] sticks = new boolean[12];
    private ModelListener player1 = null;
    private  ModelListener player2 = null;
    private boolean p1Turn = true;
    private boolean p2Turn = false;
    private String p1Name;
    private String p2Name;
    /**
     * Construct a new Bns model.
     */
    public BnsModel() {}
    // Exported methods

    /**
     * Add the given model listener to this Bns model.
     *
     * @param  modelListener  Model listener.
     */
    public synchronized void addModelListener(ModelListener modelListener, String name) {
        // if there is only one player
        if(player1 == null) {
            player1 = modelListener;
            p1Name = name;
            player1.waiting();
        }
        // if there is two players
        else {
            player2 = modelListener;
            p2Name = name;
            // Update board state.
            for (int i = 0; i < balls.length; i++){
                balls[i] = true;
            }

            // Report update to all clients.
            for (int i = 0; i < balls.length; i++){
                player2.ballVisible(i,true);
                player1.ballVisible(i,true);
            }

            // Update board state.
            for (int i = 0; i < sticks.length; i++){
                sticks[i] = true;
            }

            // Report update to all clients.
            for (int i = 0; i < sticks.length; i++){
                player2.stickVisible(i,true);
                player1.stickVisible(i,true);
            }
            // set the message
            player1.changeMessage(p1Turn,p2Name);
            player2.changeMessage(p2Turn,p1Name);
        }
    }


    /**
     * Return if game has any no players.
     * @return boolean  if there are no players
     */
    public synchronized boolean isNull() {
        if (player1 == null && player2 == null) return true;
        else return false;
    }

    /**
     * Return if game has one player.
     * @return boolean  if there is one player
     */
    public synchronized boolean oneNull() {
        if ((player1 != null && player2 == null)||(player1 == null && player2 != null)) return true;
        else return false;
    }

    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     * @param  name     Name of the oponent
     */
    public synchronized void join(ViewProxy proxy, String name) throws IOException{}

    /**
     * Update a ball on the board
     *
     * @param  i      index of the ball
     * @param  v      who clicked
     */
    public synchronized void clickBall(int i, ViewProxy v) {
        if ((v == player1 && p1Turn)||(v == player2 && p2Turn)) {
            balls[i] = false;
            player1.ballVisible(i, false);
            player2.ballVisible(i, false);
            for (int j = 0; j < ballCombos[i].length; j++){
                sticks[ballCombos[i][j]] = false;
                player1.stickVisible(ballCombos[i][j], false);
                player2.stickVisible(ballCombos[i][j], false);
            }
            // change turn
            p1Turn = !p1Turn;
            p2Turn = !p2Turn;
            player2.changeMessage(p2Turn,p1Name);
            player1.changeMessage(p1Turn,p2Name);
        }
    }

    /**
     * Update a stick on the board
     *
     * @param  i      index of the stick
     * @param  v      who's turn it is
     */
    public synchronized void clickStick(int i, ViewProxy v) {
        if ((v == player1 && p1Turn) ||(v == player2 && p2Turn)) {
            // Update board state.
            sticks[i] = false;
            // Report update to all clients.
            player1.stickVisible(i, false);
            player2.stickVisible(i, false);
            // change turn
            p1Turn = !p1Turn;
            p2Turn = !p2Turn;
            player2.changeMessage(p2Turn,p1Name);
            player1.changeMessage(p1Turn,p2Name);
        }
    }

    /**
     * Close the client programs
     */
    public synchronized void closeGame(){
        // Report update to all clients.
        if (player1 != null) {
            player1.close();
            player1 = null;
        }
        if (player2!=null) {
            player2.close();
            player2 = null;
        }
    }

    /**
     * Create a new game state for the board.
     * Set everything to visible.
     */
    public synchronized void createNewGame(){
        // Update board state.
        for (int i = 0; i < balls.length; i++){
            balls[i] = true;
        }
        // Report update to all clients.
        for (int i = 0; i < balls.length; i++){
            player2.ballVisible(i,true);
            player1.ballVisible(i,true);
        }
        // Update board state.
        for (int i = 0; i < sticks.length; i++){
            sticks[i] = true;
        }
        // Report update to all clients.
        for (int i = 0; i < sticks.length; i++){
            player2.stickVisible(i,true);
            player1.stickVisible(i,true);
        }
        p1Turn = true;
        p2Turn = false;
        // Reset back to the first players turn
        player2.changeMessage(p2Turn,p1Name);
        player1.changeMessage(p1Turn,p2Name);

    }

    /**
     * Checks to see if the game is over and reports who won.
     */
    public synchronized void gameStatus(){
        boolean ended = true;
        // check balls
        for (int i = 0; i < balls.length; i++){
            if (balls[i]){
                ended = false;
            }
        }
        // check sticks
        for (int i = 0; i < sticks.length; i++){
            if (sticks[i]){
                ended = false;
            }
        }
        // report who won
        if (ended && p1Turn){
            player1.gameEnds(false, p2Name);
            player2.gameEnds(true, p1Name);
        }
        else if (ended && p2Turn){
            player1.gameEnds(true, p2Name);
            player2.gameEnds(false, p1Name);
        }
    }

}
