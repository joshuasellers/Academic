/**
 * Interface BoardListener specifies the interface for an object that receives
 * notifications of mouse clicks on the game board in the game of Balls and
 * Sticks.
 *
 * @author  Alan Kaminsky
 * @version 12-Oct-2017
 */
public interface BoardListener
	{

// Exported operations.

	/**
	 * Report that a ball was clicked.
	 *
	 * @param  i  Ball index (0..8).
	 */
	public void ballClicked
		(int i);

	/**
	 * Report that a stick was clicked.
	 *
	 * @param  i  Stick index (0..11).
	 */
	public void stickClicked
		(int i);

	}
