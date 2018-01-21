package nl.tudelft.jpacman.board; import static nl.dennis.api.CoverageChecker.hit; public class Board {

	/**
	 * The grid of squares with board[x][y] being the square at column x, row y.
	 */
	private final Square[][] board;

	/**
	 * Creates a new board.
	 * 
	 * @param grid
	 *            The grid of squares with grid[x][y] being the square at column
	 *            x, row y.
	 */
	Board(Square[][] grid) {
		assert grid != null;
		this.board = grid;
		assert invariant() : "Initial grid cannot contain null squares";
	}
	
	/**
	 * Whatever happens, the squares on the board can't be null.
	 * @return false if any square on the board is null.
	 */
	protected final boolean invariant() {
		for (Square[] row : board) {
			for (Square square : row) {
				if (square == null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the number of columns.
	 * 
	 * @return The width of this board.
	 */
	public int getWidth() {hit("nl.tudelft.jpacman.board.Board","getWidth"); hit("nl.tudelft.jpacman.board.Board","getWidth",1); return board.length;}

	/**
	 * Returns the number of rows.
	 * 
	 * @return The height of this board.
	 */
	public int getHeight() {hit("nl.tudelft.jpacman.board.Board","getHeight"); hit("nl.tudelft.jpacman.board.Board","getHeight",1); return board[0].length;}

	/**
	 * Returns the square at the given <code>x,y</code> position.
	 * 
	 * @param x
	 *            The <code>x</code> position (column) of the requested square.
	 * @param y
	 *            The <code>y</code> position (row) of the requested square.
	 * @return The square at the given <code>x,y</code> position (never null).
	 */
	public Square squareAt(int x, int y) {hit("nl.tudelft.jpacman.board.Board","squareAt"); hit("nl.tudelft.jpacman.board.Board","squareAt",1); assert withinBorders(x, y); hit("nl.tudelft.jpacman.board.Board","squareAt",2); Square result = board[x][y]; hit("nl.tudelft.jpacman.board.Board","squareAt",3); assert result != null : "Follows from invariant."; hit("nl.tudelft.jpacman.board.Board","squareAt",4); return result;}

	/**
	 * Determines whether the given <code>x,y</code> position is on this board.
	 * 
	 * @param x
	 *            The <code>x</code> position (row) to test.
	 * @param y
	 *            The <code>y</code> position (column) to test.
	 * @return <code>true</code> iff the position is on this board.
	 */
	public boolean withinBorders(int x, int y) {hit("nl.tudelft.jpacman.board.Board","withinBorders"); hit("nl.tudelft.jpacman.board.Board","withinBorders",1); return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();}
}
