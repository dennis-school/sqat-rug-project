package nl.tudelft.jpacman.board; import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite; import static nl.dennis.api.CoverageChecker.hit; public class BoardFactory {

	/**
	 * The sprite store providing the sprites for the background.
	 */
	private final PacManSprites sprites;

	/**
	 * Creates a new BoardFactory that will create a board with the provided
	 * background sprites.
	 * 
	 * @param spriteStore
	 *            The sprite store providing the sprites for the background.
	 */
	public BoardFactory(PacManSprites spriteStore) {
		this.sprites = spriteStore;
	}

	/**
	 * Creates a new board from a grid of cells and connects it.
	 * 
	 * @param grid
	 *            The square grid of cells, in which grid[x][y] corresponds to
	 *            the square at position x,y.
	 * @return A new board, wrapping a grid of connected cells.
	 */
	public Board createBoard(Square[][] grid) {hit("nl.tudelft.jpacman.board.BoardFactory","createBoard"); hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",8); assert grid != null; hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",9); Board board = new Board(grid); hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",10); int width = board.getWidth(); hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",11); int height = board.getHeight(); hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",12); for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Square square = grid[x][y]; hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",5); for (Direction dir : Direction.values()) {
					int dirX = (width + x + dir.getDeltaX()) % width; hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",1); int dirY = (height + y + dir.getDeltaY()) % height; hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",2); Square neighbour = grid[dirX][dirY]; hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",3); square.link(neighbour, dir); hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",4);
				} hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",6);
			} hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",7);
		} hit("nl.tudelft.jpacman.board.BoardFactory","createBoard",13); return board;}

	/**
	 * Creates a new square that can be occupied by any unit.
	 * 
	 * @return A new square that can be occupied by any unit.
	 */
	public Square createGround() {hit("nl.tudelft.jpacman.board.BoardFactory","createGround"); hit("nl.tudelft.jpacman.board.BoardFactory","createGround",1); return new Ground(sprites.getGroundSprite());}

	/**
	 * Creates a new square that cannot be occupied by any unit.
	 * 
	 * @return A new square that cannot be occupied by any unit.
	 */
	public Square createWall() {hit("nl.tudelft.jpacman.board.BoardFactory","createWall"); hit("nl.tudelft.jpacman.board.BoardFactory","createWall",1); return new Wall(sprites.getWallSprite());}

	/**
	 * A wall is a square that is inaccessible to anyone.
	 * 
	 * @author Jeroen Roosen 
	 */
	private static final class Wall extends Square {

		/**
		 * The background for this square.
		 */
		private final Sprite background;

		/**
		 * Creates a new wall square.
		 * 
		 * @param sprite
		 *            The background for the square.
		 */
		Wall(Sprite sprite) {
			this.background = sprite;
		}

		@Override
		public boolean isAccessibleTo(Unit unit) {
			return false;
		}

		@Override
		public Sprite getSprite() {
			return background;
		}
	}

	/**
	 * A wall is a square that is accessible to anyone.
	 * 
	 * @author Jeroen Roosen 
	 */
	private static final class Ground extends Square {

		/**
		 * The background for this square.
		 */
		private final Sprite background;

		/**
		 * Creates a new ground square.
		 * 
		 * @param sprite
		 *            The background for the square.
		 */
		Ground(Sprite sprite) {
			this.background = sprite;
		}

		@Override
		public boolean isAccessibleTo(Unit unit) {
			return true;
		}

		@Override
		public Sprite getSprite() {
			return background;
		}
	}
}
