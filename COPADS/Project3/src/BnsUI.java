import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * Class BnsUI provides the graphical user interface for the game of Balls and
 * Sticks.
 *
 * @author  Josh Sellers
 * @version 11-Nov-2017
 */
public class BnsUI implements BoardListener, ModelListener{

	// Hidden constants.
	private static final int GAP = 10;
	// Hidden data members.
	private JFrame frame;
	private JBoard board;
	private JTextField messageField;
	private JButton newGameButton;
	private ViewListener viewListener;

	// Hidden constructors.

	/**
	 * Construct a new game UI.
	 *
	 * @param  name  Player's name.
	 */
	private BnsUI(String name) {
		frame = new JFrame ("B & S -- "+name);
		JPanel panel = new JPanel();
		panel.setLayout (new BoxLayout (panel, BoxLayout.Y_AXIS));
		panel.setBorder (BorderFactory.createEmptyBorder (GAP, GAP, GAP, GAP));
		frame.add (panel);
		// add board
		board = new JBoard();
		// add board to panel
		board.setFocusable (false);
		panel.add (board);
		panel.add (Box.createVerticalStrut (GAP));
		// add message field
		messageField = new JTextField (1);
		panel.add (messageField);
		messageField.setEditable (false);
		messageField.setFocusable (false);
		panel.add (Box.createVerticalStrut (GAP));
		// add new game button
		newGameButton = new JButton ("New Game");
		newGameButton.setAlignmentX (0.5f);
		newGameButton.setFocusable (false);
		panel.add (newGameButton);
		// Closing the window exits both clients
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					viewListener.closeGame();
				}
				catch (IOException ex){}
			}
		});
		// Exit On Close
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		// Clicking the New Game button informs the view listener.
		newGameButton.addActionListener (new ActionListener()
		{
			public void actionPerformed (ActionEvent e)
			{
				newGame();
			}
		});
		// Display frame
		frame.pack();
		frame.setVisible (true);
		}


	/**
	 * An object holding a reference to a Bns UI.
	 */
	private static class BnsUIRef{
		public BnsUI ui;
	}

	/**
	 * Construct a new Bns UI.
	 *
	 * @param  name   name.
	 */
	public static BnsUI create(final String name) {
		final BnsUIRef ref = new BnsUIRef();
		onSwingThreadDo(new Runnable() {
			public void run() {
				ref.ui = new BnsUI (name);
				// set board listener
				ref.ui.board.setBoardListener(ref.ui);
			}
		});
		return ref.ui;
	}

	/**
	 * Set the view listener for this Bns UI.
	 *
	 * @param  viewListener  View listener.
	 */
	public void setViewListener(final ViewListener viewListener) {
		onSwingThreadDo (new Runnable() {
			public void run() {
				BnsUI.this.viewListener = viewListener;
			}
		});
	}

	/**
	 * Report that a ball was clicked
	 *
	 * @param  i  index of ball
	 */
	public void ballClicked(int i) {
		try {
			// have ball clicked and check if game ended
			viewListener.clickBall(i, null);
		}
		catch (IOException e){}
	}

	/**
	 * Report that a stick was clicked
	 *
	 * @param  i  index of stick
	 */
	public void stickClicked(int i) {
		try {
			// have ball clicked and check if game ended
			viewListener.clickStick(i,null);
		}
		catch (IOException e) {}
	}

	/**
	 * Tell board listener that ball is visible
	 *
	 * @param  i  index of ball
	 * @param  visible what to set ball
	 */
	public void ballVisible(int i, boolean visible){
		onSwingThreadDo(new Runnable() {
			public void run() {
				board.setBallVisible(i, visible);
			}
		});
	}

	/**
	 * Tell board listener that stick is visible
	 *
	 * @param  i  index of stick
	 * @param  visible what to set stick
	 */
	public void stickVisible(int i, boolean visible){
		onSwingThreadDo(new Runnable() {
			public void run() {
				board.setStickVisible(i, visible);
			}
		});
	}

	/**
	 * Tell board listener that new game was clicked
	 */
	public void newGame(){
		try {
			viewListener.createNewGame();
		}
		catch (IOException e) {}
	}

	/**
	 * Output that display must be reset
	 *
	 * @param  b  if it's the player's turn
	 * @param  name name of oponent
	 */
	public void changeMessage(boolean b, String name){
		onSwingThreadDo (new Runnable() {
			public void run() {
				if (!b) {
					messageField.setText(name + "'s turn");
				}
				else {
					messageField.setText("Your turn");
				}
				newGameButton.setEnabled(true);
			}
		});
	}

	/**
	 * Output that there is only one player
	 */
	public void waiting(){
		onSwingThreadDo (new Runnable() {
			public void run() {
				// disable newgame button
				newGameButton.setEnabled(false);
				messageField.setText("Waiting for partner");
			}
		});
	}

	/**
	 * Out put who won the game
	 *
	 * @param  b  if player won the game
	 * @param  name name of oponent
	 */
	public void gameEnds(boolean b, String name){
		onSwingThreadDo (new Runnable() {
			public void run() {
				if (!b) {
					messageField.setText("You win!");
				} else {
					messageField.setText(name + " wins!");
				}
			}
		});
	}

	/**
	 * close the client program
	 */
	public void close(){
		System.exit(0);
	}


	/**
	 * Execute the given runnable object on the Swing thread.
	 */
	private static void onSwingThreadDo(Runnable task){
		try {SwingUtilities.invokeAndWait (task);}
		catch (Throwable exc) {
			exc.printStackTrace (System.err);
			System.exit (1);
		}
	}

	}
