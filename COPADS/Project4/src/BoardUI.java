/**
 * Class BoardUI provides the bulletin board user interface for the project.
 *
 * @author  Josh Sellers
 * @version 9-Dec-2017
 */

public class BoardUI {
    // Hidden constructors.

    /**
     * Construct a new board UI.
     */
    private BoardUI() {}

    // Exported operations.

    /**
     * An object holding a reference to a board UI.
     */
    private static class BoardUIRef {public BoardUI ui;}

    /**
     * Construct a new board UI.
     */
    public static BoardUI create() {
        final BoardUIRef ref = new BoardUIRef();
        ref.ui = new BoardUI();
        return ref.ui;
    }

    /**
     * Print output from a post.
     *
     * @param  message  message to be printed
     */
    public void output(final String message) {
        System.out.println(message);
    }
}
