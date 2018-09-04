import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import static java.awt.RenderingHints.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Class JBoard provides the graphical user interface for the game board in the
 * game of Balls and Sticks.
 *
 * @author  Alan Kaminsky
 * @version 12-Oct-2017
 */
public class JBoard
	extends JPanel
	{

// Exported constants.

	/**
	 * Number of balls.
	 */
	public static final int N_BALLS = 9;

	/**
	 * Number of sticks.
	 */
	public static final int N_STICKS = 12;

// Hidden constants.

	private static final Color BORDER_COLOR = Color.BLACK;  // Border color
	private static final Color BG_COLOR = Color.LIGHT_GRAY; // Background color
	private static final Color BALL_COLOR = Color.BLUE;     // Ball color
	private static final Color STICK_COLOR = Color.RED;     // Stick color

	private static final int BD = 30; // Ball diameter
	private static final int SW = 10; // Stick width
	private static final int SL = 50; // Stick length
	private static final int MG = 10; // Margin

	private static final Rectangle[] ballRect = new Rectangle [N_BALLS];
	private static final Rectangle[] stickRect = new Rectangle [N_STICKS];
	static
		{
		int i;

		// Set up ball rectangles.
		i = 0;
		for (int r = 0; r <= 2; ++ r)
			for (int c = 0; c <= 2; ++ c)
				{
				ballRect[i++] = new Rectangle
					(/*x     */ MG + c*(BD + SL),
					 /*y     */ MG + r*(BD + SL),
					 /*width */ BD,
					 /*height*/ BD);
				}

		// Set up horizontal stick rectangles.
		i = 0;
		for (int r = 0; r <= 2; ++ r)
			for (int c = 0; c <= 1; ++ c)
				{
				stickRect[i++] = new Rectangle
					(/*x     */ MG + BD/2 + c*(BD + SL),
					 /*y     */ MG + BD/2 + r*(BD + SL) - SW/2,
					 /*width */ BD + SL,
					 /*height*/ SW);
				}

		// Set up vertical stick rectangles.
		for (int c = 0; c <= 2; ++ c)
			for (int r = 0; r <= 1; ++ r)
				{
				stickRect[i++] = new Rectangle
					(/*x     */ MG + BD/2 + c*(BD + SL) - SW/2,
					 /*y     */ MG + BD/2 + r*(BD + SL),
					 /*width */ SW,
					 /*height*/ BD + SL);
				}
		}

// Hidden data members.

	private BoardListener listener;
	private boolean[] ballVisible = new boolean [N_BALLS];
	private boolean[] stickVisible = new boolean [N_STICKS];

// Exported constructors.

	/**
	 * Construct a new game board UI. Initially, all balls are invisible, all
	 * sticks are invisible, and there is no board listener.
	 */
	public JBoard()
		{
		Dimension d = new Dimension
			(/*width */ MG + 3*BD + 2*SL + MG,
			 /*height*/ MG + 3*BD + 2*SL + MG);
		setMinimumSize (d);
		setMaximumSize (d);
		setPreferredSize (d);
		setBackground (BG_COLOR);
		setBorder (BorderFactory.createLineBorder (BORDER_COLOR));
		addMouseListener (new MouseAdapter()
			{
			public void mouseClicked (MouseEvent e)
				{
				if (listener == null) return;
				Point p = e.getPoint();
				for (int i = 0; i < N_BALLS; ++ i)
					if (ballRect[i].contains (p))
						{
						if (ballVisible[i])
							listener.ballClicked (i);
						return;
						}
				for (int i = 0; i < N_STICKS; ++ i)
					if (stickRect[i].contains (p))
						{
						if (stickVisible[i])
							listener.stickClicked (i);
						return;
						}
				}
			});
		}

// Exported operations.

	/**
	 * Set the board listener that will receive notifications of mouse clicks on
	 * this game board UI.
	 *
	 * @param  listener  Board listener.
	 */
	public void setBoardListener
		(BoardListener listener)
		{
		this.listener = listener;
		}

	/**
	 * Set the visible state of the given ball on this game board UI.
	 *
	 * @param  i  Ball index (0..8).
	 * @param  v  True for visible, false for invisible.
	 */
	public void setBallVisible
		(int i,
		 boolean v)
		{
		if (ballVisible[i] != v)
			{
			ballVisible[i] = v;
			Rectangle r = ballRect[i];
			repaint (r.x, r.y, r.width, r.height);
			}
		}

	/**
	 * Set the visible state of the given stick on this game board UI.
	 *
	 * @param  i  Stick index (0..11).
	 * @param  v  True for visible, false for invisible.
	 */
	public void setStickVisible
		(int i,
		 boolean v)
		{
		if (stickVisible[i] != v)
			{
			stickVisible[i] = v;
			Rectangle r = stickRect[i];
			repaint (r.x, r.y, r.width, r.height);
			}
		}

// Hidden operations.

	/**
	 * Paint this game board UI in the given graphics context.
	 *
	 * @param  g  Graphics context.
	 */
	protected void paintComponent
		(Graphics g)
		{
		// Paint panel.
		super.paintComponent (g);
		Graphics2D gg = (Graphics2D) g.create();
		gg.setRenderingHint (KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

		// Paint sticks.
		gg.setColor (STICK_COLOR);
		for (int i = 0; i < N_STICKS; ++ i)
			{
			if (stickVisible[i])
				{
				Rectangle r = stickRect[i];
				gg.fillRect (r.x, r.y, r.width, r.height);
				}
			}

		// Paint balls.
		gg.setColor (BALL_COLOR);
		for (int i = 0; i < N_BALLS; ++ i)
			{
			if (ballVisible[i])
				{
				Rectangle r = ballRect[i];
				gg.fillOval (r.x, r.y, r.width, r.height);
				}
			}
		}

	}
