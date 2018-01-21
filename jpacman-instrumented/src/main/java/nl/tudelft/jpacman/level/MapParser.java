package nl.tudelft.jpacman.level; import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC; import static nl.dennis.api.CoverageChecker.hit; public class MapParser {

	/**
	 * The factory that creates the levels.
	 */
	private final LevelFactory levelCreator;

	/**
	 * The factory that creates the squares and board.
	 */
	private final BoardFactory boardCreator;

	/**
	 * Creates a new map parser.
	 * 
	 * @param levelFactory
	 *            The factory providing the NPC objects and the level.
	 * @param boardFactory
	 *            The factory providing the Square objects and the board.
	 */
	public MapParser(LevelFactory levelFactory, BoardFactory boardFactory) {
		this.levelCreator = levelFactory;
		this.boardCreator = boardFactory;
	}

	/**
	 * Parses the text representation of the board into an actual level.
	 * 
	 * <ul>
	 * <li>Supported characters:
	 * <li>' ' (space) an empty square.
	 * <li>'#' (bracket) a wall.
	 * <li>'.' (period) a square with a pellet.
	 * <li>'P' (capital P) a starting square for players.
	 * <li>'G' (capital G) a square with a ghost.
	 * </ul>
	 * 
	 * @param map
	 *            The text representation of the board, with map[x][y]
	 *            representing the square at position x,y.
	 * @return The level as represented by this text.
	 */
	public Level parseMap(char[][] map) {hit("nl.tudelft.jpacman.level.MapParser","parseMap"); hit("nl.tudelft.jpacman.level.MapParser","parseMap",1); int width = map.length; hit("nl.tudelft.jpacman.level.MapParser","parseMap",2); int height = map[0].length; hit("nl.tudelft.jpacman.level.MapParser","parseMap",3); Square[][] grid = new Square[width][height]; hit("nl.tudelft.jpacman.level.MapParser","parseMap",4); List<NPC> ghosts = new ArrayList<>(); hit("nl.tudelft.jpacman.level.MapParser","parseMap",5); List<Square> startPositions = new ArrayList<>(); hit("nl.tudelft.jpacman.level.MapParser","parseMap",6); makeGrid(map, width, height, grid, ghosts, startPositions); hit("nl.tudelft.jpacman.level.MapParser","parseMap",7); Board board = boardCreator.createBoard(grid); hit("nl.tudelft.jpacman.level.MapParser","parseMap",8); return levelCreator.createLevel(board, ghosts, startPositions);}

	private void makeGrid(char[][] map, int width, int height,
			Square[][] grid, List<NPC> ghosts, List<Square> startPositions) {hit("nl.tudelft.jpacman.level.MapParser","makeGrid"); hit("nl.tudelft.jpacman.level.MapParser","makeGrid",4); for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				char c = map[x][y]; hit("nl.tudelft.jpacman.level.MapParser","makeGrid",1); addSquare(grid, ghosts, startPositions, x, y, c); hit("nl.tudelft.jpacman.level.MapParser","makeGrid",2);
			} hit("nl.tudelft.jpacman.level.MapParser","makeGrid",3);
		} hit("nl.tudelft.jpacman.level.MapParser","makeGrid",5);}

	private void addSquare(Square[][] grid, List<NPC> ghosts,
			List<Square> startPositions, int x, int y, char c) {hit("nl.tudelft.jpacman.level.MapParser","addSquare"); hit("nl.tudelft.jpacman.level.MapParser","addSquare",17); switch (c) {
		case ' ':
			grid[x][y] = boardCreator.createGround(); hit("nl.tudelft.jpacman.level.MapParser","addSquare",1); break;
		case '#':
			grid[x][y] = boardCreator.createWall(); hit("nl.tudelft.jpacman.level.MapParser","addSquare",3); break;
		case '.':
			Square pelletSquare = boardCreator.createGround(); hit("nl.tudelft.jpacman.level.MapParser","addSquare",5); grid[x][y] = pelletSquare; hit("nl.tudelft.jpacman.level.MapParser","addSquare",6); levelCreator.createPellet().occupy(pelletSquare); hit("nl.tudelft.jpacman.level.MapParser","addSquare",7); break;
		case 'G':
			Square ghostSquare = makeGhostSquare(ghosts); hit("nl.tudelft.jpacman.level.MapParser","addSquare",9); grid[x][y] = ghostSquare; hit("nl.tudelft.jpacman.level.MapParser","addSquare",10); break;
		case 'P':
			Square playerSquare = boardCreator.createGround(); hit("nl.tudelft.jpacman.level.MapParser","addSquare",12); grid[x][y] = playerSquare; hit("nl.tudelft.jpacman.level.MapParser","addSquare",13); startPositions.add(playerSquare); hit("nl.tudelft.jpacman.level.MapParser","addSquare",14); break;
		default:
			throw new PacmanConfigurationException("Invalid character at "
					+ x + "," + y + ": " + c);
		}}

	private Square makeGhostSquare(List<NPC> ghosts) {hit("nl.tudelft.jpacman.level.MapParser","makeGhostSquare"); hit("nl.tudelft.jpacman.level.MapParser","makeGhostSquare",1); Square ghostSquare = boardCreator.createGround(); hit("nl.tudelft.jpacman.level.MapParser","makeGhostSquare",2); NPC ghost = levelCreator.createGhost(); hit("nl.tudelft.jpacman.level.MapParser","makeGhostSquare",3); ghosts.add(ghost); hit("nl.tudelft.jpacman.level.MapParser","makeGhostSquare",4); ghost.occupy(ghostSquare); hit("nl.tudelft.jpacman.level.MapParser","makeGhostSquare",5); return ghostSquare;}

	/**
	 * Parses the list of strings into a 2-dimensional character array and
	 * passes it on to {@link #parseMap(char[][])}.
	 * 
	 * @param text
	 *            The plain text, with every entry in the list being a equally
	 *            sized row of squares on the board and the first element being
	 *            the top row.
	 * @return The level as represented by the text.
	 * @throws PacmanConfigurationException If text lines are not properly formatted.
	 */
	public Level parseMap(List<String> text) {hit("nl.tudelft.jpacman.level.MapParser","parseMap"); hit("nl.tudelft.jpacman.level.MapParser","parseMap",3); checkMapFormat(text); hit("nl.tudelft.jpacman.level.MapParser","parseMap",4); int height = text.size(); hit("nl.tudelft.jpacman.level.MapParser","parseMap",5); int width = text.get(0).length(); hit("nl.tudelft.jpacman.level.MapParser","parseMap",6); char[][] map = new char[width][height]; hit("nl.tudelft.jpacman.level.MapParser","parseMap",7); for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = text.get(y).charAt(x); hit("nl.tudelft.jpacman.level.MapParser","parseMap",1);
			} hit("nl.tudelft.jpacman.level.MapParser","parseMap",2);
		} hit("nl.tudelft.jpacman.level.MapParser","parseMap",8); return parseMap(map);}
	
	/**
	 * Check the correctness of the map lines in the text.
	 * @param text Map to be checked
	 * @throws PacmanConfigurationException if map is not OK.
	 */
	private void checkMapFormat(List<String> text) {hit("nl.tudelft.jpacman.level.MapParser","checkMapFormat"); hit("nl.tudelft.jpacman.level.MapParser","checkMapFormat",6); if (text == null) {
			throw new PacmanConfigurationException(
					"Input text cannot be null.");
		} hit("nl.tudelft.jpacman.level.MapParser","checkMapFormat",7); if (text.isEmpty()) {
			throw new PacmanConfigurationException(
					"Input text must consist of at least 1 row.");
		} hit("nl.tudelft.jpacman.level.MapParser","checkMapFormat",8); int width = text.get(0).length(); hit("nl.tudelft.jpacman.level.MapParser","checkMapFormat",9); if (width == 0) {
			throw new PacmanConfigurationException(
				"Input text lines cannot be empty.");
		} hit("nl.tudelft.jpacman.level.MapParser","checkMapFormat",10); for (String line : text) {
			if (line.length() != width) {
				throw new PacmanConfigurationException(
					"Input text lines are not of equal width.");
			} hit("nl.tudelft.jpacman.level.MapParser","checkMapFormat",5);
		} hit("nl.tudelft.jpacman.level.MapParser","checkMapFormat",11);}

	/**
	 * Parses the provided input stream as a character stream and passes it
	 * result to {@link #parseMap(List)}.
	 * 
	 * @param source
	 *            The input stream that will be read.
	 * @return The parsed level as represented by the text on the input stream.
	 * @throws IOException
	 *             when the source could not be read.
	 */
	public Level parseMap(InputStream source) throws IOException {hit("nl.tudelft.jpacman.level.MapParser","parseMap"); hit("nl.tudelft.jpacman.level.MapParser","parseMap",5); try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				source, "UTF-8"))) {
			List<String> lines = new ArrayList<>(); hit("nl.tudelft.jpacman.level.MapParser","parseMap",2); while (reader.ready()) {
				lines.add(reader.readLine()); hit("nl.tudelft.jpacman.level.MapParser","parseMap",1);
			} hit("nl.tudelft.jpacman.level.MapParser","parseMap",3); return parseMap(lines);
		}}
}
