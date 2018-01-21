package nl.tudelft.jpacman; import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

import java.io.*; import static nl.dennis.api.CoverageChecker.hit; public class Launcher {

  private static final PacManSprites SPRITE_STORE = new PacManSprites();

  public static final String DEFAULT_MAP = "/board.txt";
  private String levelMap = DEFAULT_MAP;

  private PacManUI pacManUI;
  private Game game;

  /**
   * @return The game object this launcher will start when {@link #launch()} is
   *         called.
   */
  public Game getGame() {hit("nl.tudelft.jpacman.Launcher","getGame"); hit("nl.tudelft.jpacman.Launcher","getGame",7); switch (3) {
    case 1:
      break;
    default:
      break;
    case 2:
      break;
    case 3:
      break;
    } try {
      System.out.println("Trying to catch a general exception"); hit("nl.tudelft.jpacman.Launcher","getGame",5);
    } catch (Exception ex) {

    hit("nl.tudelft.jpacman.Launcher","getGame",6);}

    return game;}

  /**
   * The map file used to populate the level.
   * 
   * @return The name of the map file.
   */
  protected String getLevelMap() {hit("nl.tudelft.jpacman.Launcher","getLevelMap"); hit("nl.tudelft.jpacman.Launcher","getLevelMap",1); int a = 0; hit("nl.tudelft.jpacman.Launcher","getLevelMap",2); int b = 4; hit("nl.tudelft.jpacman.Launcher","getLevelMap",3); int c = a = b; hit("nl.tudelft.jpacman.Launcher","getLevelMap",4); b = a = c; hit("nl.tudelft.jpacman.Launcher","getLevelMap",5); return levelMap;}

  /**
   * Set the name of the file containing this level's map.
   * 
   * @param fileName
   *          Map to be used.
   * @return Level corresponding to the given map.
   */
  public Launcher withMapFile(String fileName) {hit("nl.tudelft.jpacman.Launcher","withMapFile"); hit("nl.tudelft.jpacman.Launcher","withMapFile",1); levelMap = fileName; hit("nl.tudelft.jpacman.Launcher","withMapFile",2); return this;}

  /**
   * Creates a new game using the level from {@link #makeLevel()}.
   * 
   * @return a new Game.
   */
  public Game makeGame() {hit("nl.tudelft.jpacman.Launcher","makeGame"); hit("nl.tudelft.jpacman.Launcher","makeGame",1); GameFactory gf = getGameFactory(); hit("nl.tudelft.jpacman.Launcher","makeGame",2); Level level = makeLevel(); hit("nl.tudelft.jpacman.Launcher","makeGame",3); return gf.createSinglePlayerGame(level);}

  /**
   * Creates a new level. By default this method will use the map parser to parse
   * the default board stored in the <code>board.txt</code> resource.
   * 
   * @return A new level.
   */
  public Level makeLevel() {hit("nl.tudelft.jpacman.Launcher","makeLevel"); hit("nl.tudelft.jpacman.Launcher","makeLevel",3); MapParser parser = getMapParser(); hit("nl.tudelft.jpacman.Launcher","makeLevel",4); try (InputStream boardStream = Launcher.class.getResourceAsStream(getLevelMap())) {
      return parser.parseMap(boardStream);
    } catch (IOException e) {
      throw new PacmanConfigurationException("Unable to create level.", e);
    }}

  /**
   * @return A new map parser object using the factories from
   *         {@link #getLevelFactory()} and {@link #getBoardFactory()}.
   */
  protected MapParser getMapParser() {hit("nl.tudelft.jpacman.Launcher","getMapParser"); hit("nl.tudelft.jpacman.Launcher","getMapParser",1); return new MapParser(getLevelFactory(), getBoardFactory());}

  /**
   * @return A new board factory using the sprite store from
   *         {@link #getSpriteStore()}.
   */
  protected BoardFactory getBoardFactory() {hit("nl.tudelft.jpacman.Launcher","getBoardFactory"); hit("nl.tudelft.jpacman.Launcher","getBoardFactory",1); return new BoardFactory(getSpriteStore());}

  /**
   * @return The default {@link PacManSprites}.
   */
  protected PacManSprites getSpriteStore() {hit("nl.tudelft.jpacman.Launcher","getSpriteStore"); hit("nl.tudelft.jpacman.Launcher","getSpriteStore",1); return SPRITE_STORE;}

  /**
   * @return A new factory using the sprites from {@link #getSpriteStore()} and
   *         the ghosts from {@link #getGhostFactory()}.
   */
  protected LevelFactory getLevelFactory() {hit("nl.tudelft.jpacman.Launcher","getLevelFactory"); hit("nl.tudelft.jpacman.Launcher","getLevelFactory",1); return new LevelFactory(getSpriteStore(), getGhostFactory());}

  /**
   * @return A new factory using the sprites from {@link #getSpriteStore()}.
   */
  protected GhostFactory getGhostFactory() {hit("nl.tudelft.jpacman.Launcher","getGhostFactory"); hit("nl.tudelft.jpacman.Launcher","getGhostFactory",1); return new GhostFactory(getSpriteStore());}

  /**
   * @return A new factory using the players from {@link #getPlayerFactory()}.
   */
  protected GameFactory getGameFactory() {hit("nl.tudelft.jpacman.Launcher","getGameFactory"); hit("nl.tudelft.jpacman.Launcher","getGameFactory",1); return new GameFactory(getPlayerFactory());}

  /**
   * @return A new factory using the sprites from {@link #getSpriteStore()}.
   */
  protected PlayerFactory getPlayerFactory() {hit("nl.tudelft.jpacman.Launcher","getPlayerFactory"); hit("nl.tudelft.jpacman.Launcher","getPlayerFactory",1); return new PlayerFactory(getSpriteStore());}

  /**
   * Adds key events UP, DOWN, LEFT and RIGHT to a game.
   * 
   * @param builder
   *          The {@link PacManUiBuilder} that will provide the UI.
   * @param game
   *          The game that will process the events.
   */
  protected void addSinglePlayerKeys(final PacManUiBuilder builder, final Game game) {hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys"); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",17); final Player p1 = getSinglePlayer(game); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",18); builder.addKey(KeyEvent.VK_UP, new Action() {

      @Override
      public void doAction() {hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys"); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",1); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",1); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",2); game.move(p1, Direction.NORTH); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",3); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",2); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",4);}
    }).addKey(KeyEvent.VK_DOWN, new Action() {

      @Override
      public void doAction() {hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys"); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",5); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",1); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",6); game.move(p1, Direction.SOUTH); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",7); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",2); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",8);}
    }).addKey(KeyEvent.VK_LEFT, new Action() {

      @Override
      public void doAction() {hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys"); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",9); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",1); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",10); game.move(p1, Direction.WEST); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",11); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",2); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",12);}
    }).addKey(KeyEvent.VK_RIGHT, new Action() {

      @Override
      public void doAction() {hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys"); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",13); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",1); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",14); game.move(p1, Direction.EAST); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",15); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",2); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",16);}
    }); hit("nl.tudelft.jpacman.Launcher","addSinglePlayerKeys",19);}

  private Player getSinglePlayer(final Game game) {hit("nl.tudelft.jpacman.Launcher","getSinglePlayer"); hit("nl.tudelft.jpacman.Launcher","getSinglePlayer",2); List<Player> players = game.getPlayers(); hit("nl.tudelft.jpacman.Launcher","getSinglePlayer",3); if (players.isEmpty()) {
      throw new IllegalArgumentException("Game has 0 players.");
    } hit("nl.tudelft.jpacman.Launcher","getSinglePlayer",4); return players.get(0);}

  /**
   * Creates and starts a JPac-Man game.
   */
  public void launch() {hit("nl.tudelft.jpacman.Launcher","launch"); hit("nl.tudelft.jpacman.Launcher","launch",1); game = makeGame(); hit("nl.tudelft.jpacman.Launcher","launch",2); PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons(); hit("nl.tudelft.jpacman.Launcher","launch",3); addSinglePlayerKeys(builder, game); hit("nl.tudelft.jpacman.Launcher","launch",4); pacManUI = builder.build(game); hit("nl.tudelft.jpacman.Launcher","launch",5); pacManUI.start(); hit("nl.tudelft.jpacman.Launcher","launch",6);}

  /**
   * Disposes of the UI. For more information see
   * {@link javax.swing.JFrame#dispose()}.
   */
  public void dispose() {hit("nl.tudelft.jpacman.Launcher","dispose"); hit("nl.tudelft.jpacman.Launcher","dispose",1); pacManUI.dispose(); hit("nl.tudelft.jpacman.Launcher","dispose",2);}

  /**
   * Main execution method for the Launcher.
   * 
   * @param args
   *          The command line arguments - which are ignored.
   * @throws IOException
   *           When a resource could not be read.
   */
  public static void main(String[] args) throws IOException {
    new Launcher().launch();
  }
}
