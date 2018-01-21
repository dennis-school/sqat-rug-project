package nl.tudelft.jpacman.ui; import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game; import static nl.dennis.api.CoverageChecker.hit; class BoardPanel extends JPanel {

	/**
	 * Default serialisation ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The background colour of the board.
	 */
	private static final Color BACKGROUND_COLOR = Color.BLACK;

	/**
	 * The size (in pixels) of a square on the board. The initial size of this
	 * panel will scale to fit a board with square of this size.
	 */
	private static final int SQUARE_SIZE = 16;

	/**
	 * The game to display.
	 */
	private final Game game;

	/**
	 * Creates a new board panel that will display the provided game.
	 * 
	 * @param game
	 *            The game to display.
	 */
	BoardPanel(Game game) {
		super();
		assert game != null;
		this.game = game;

		Board board = game.getLevel().getBoard();

		int w = board.getWidth() * SQUARE_SIZE;
		int h = board.getHeight() * SQUARE_SIZE;

		Dimension size = new Dimension(w, h);
		setMinimumSize(size);
		setPreferredSize(size);
	}

	@Override
	public void paint(Graphics g) {
		assert g != null;
		render(game.getLevel().getBoard(), g, getSize());
	}

	/**
	 * Renders the board on the given graphics context to the given dimensions.
	 * 
	 * @param board
	 *            The board to render.
	 * @param g
	 *            The graphics context to draw on.
	 * @param window
	 *            The dimensions to scale the rendered board to.
	 */
	private void render(Board board, Graphics g, Dimension window) {hit("nl.tudelft.jpacman.ui.BoardPanel","render"); hit("nl.tudelft.jpacman.ui.BoardPanel","render",6); int cellW = window.width / board.getWidth(); hit("nl.tudelft.jpacman.ui.BoardPanel","render",7); int cellH = window.height / board.getHeight(); hit("nl.tudelft.jpacman.ui.BoardPanel","render",8); g.setColor(BACKGROUND_COLOR); hit("nl.tudelft.jpacman.ui.BoardPanel","render",9); g.fillRect(0, 0, window.width, window.height); hit("nl.tudelft.jpacman.ui.BoardPanel","render",10); for (int y = 0; y < board.getHeight(); y++) {
			for (int x = 0; x < board.getWidth(); x++) {
				int cellX = x * cellW; hit("nl.tudelft.jpacman.ui.BoardPanel","render",1); int cellY = y * cellH; hit("nl.tudelft.jpacman.ui.BoardPanel","render",2); Square square = board.squareAt(x, y); hit("nl.tudelft.jpacman.ui.BoardPanel","render",3); render(square, g, cellX, cellY, cellW, cellH); hit("nl.tudelft.jpacman.ui.BoardPanel","render",4);
			} hit("nl.tudelft.jpacman.ui.BoardPanel","render",5);
		} hit("nl.tudelft.jpacman.ui.BoardPanel","render",11);}

	/**
	 * Renders a single square on the given graphics context on the specified
	 * rectangle.
	 * 
	 * @param square
	 *            The square to render.
	 * @param g
	 *            The graphics context to draw on.
	 * @param x
	 *            The x position to start drawing.
	 * @param y
	 *            The y position to start drawing.
	 * @param w
	 *            The width of this square (in pixels.)
	 * @param h
	 *            The height of this square (in pixels.)
	 */
	private void render(Square square, Graphics g, int x, int y, int w, int h) {hit("nl.tudelft.jpacman.ui.BoardPanel","render"); hit("nl.tudelft.jpacman.ui.BoardPanel","render",2); square.getSprite().draw(g, x, y, w, h); hit("nl.tudelft.jpacman.ui.BoardPanel","render",3); for (Unit unit : square.getOccupants()) {
			unit.getSprite().draw(g, x, y, w, h); hit("nl.tudelft.jpacman.ui.BoardPanel","render",1);
		} hit("nl.tudelft.jpacman.ui.BoardPanel","render",4);}
}