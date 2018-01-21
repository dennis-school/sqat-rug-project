package nl.tudelft.jpacman.ui; import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter; import static nl.dennis.api.CoverageChecker.hit; public class PacManUI extends JFrame {

	/**
	 * Default serialisation UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The desired frame rate interval for the graphics in milliseconds, 40
	 * being 25 fps.
	 */
	private static final int FRAME_INTERVAL = 40;

	/**
	 * The panel displaying the player scores.
	 */
	private final ScorePanel scorePanel;

	/**
	 * The panel displaying the game.
	 */
	private final BoardPanel boardPanel;

	/**
	 * Creates a new UI for a JPac-Man game.
	 * 
	 * @param game
	 *            The game to play.
	 * @param buttons
	 *            The map of caption-to-action entries that will appear as
	 *            buttons on the interface.
	 * @param keyMappings
	 *            The map of keyCode-to-action entries that will be added as key
	 *            listeners to the interface.
	 * @param sf
	 *            The formatter used to display the current score. 
	 */
	public PacManUI(final Game game, final Map<String, Action> buttons,
			final Map<Integer, Action> keyMappings, ScoreFormatter sf) {
		super("JPac-Man");
		assert game != null;
		assert buttons != null;
		assert keyMappings != null;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		PacKeyListener keys = new PacKeyListener(keyMappings);
		addKeyListener(keys);

		JPanel buttonPanel = new ButtonPanel(buttons, this);

		scorePanel = new ScorePanel(game.getPlayers());
		if (sf != null) {
			scorePanel.setScoreFormatter(sf);
		}
		
		boardPanel = new BoardPanel(game);
		
		Container contentPanel = getContentPane();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		contentPanel.add(scorePanel, BorderLayout.NORTH);
		contentPanel.add(boardPanel, BorderLayout.CENTER);

		pack();
	}

	/**
	 * Starts the "engine", the thread that redraws the interface at set
	 * intervals.
	 */
	public void start() {hit("nl.tudelft.jpacman.ui.PacManUI","start"); hit("nl.tudelft.jpacman.ui.PacManUI","start",5); setVisible(true); hit("nl.tudelft.jpacman.ui.PacManUI","start",6); ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor(); hit("nl.tudelft.jpacman.ui.PacManUI","start",7); service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {hit("nl.tudelft.jpacman.ui.PacManUI","start"); hit("nl.tudelft.jpacman.ui.PacManUI","start",1); hit("nl.tudelft.jpacman.ui.PacManUI","start",1); hit("nl.tudelft.jpacman.ui.PacManUI","start",2); nextFrame(); hit("nl.tudelft.jpacman.ui.PacManUI","start",3); hit("nl.tudelft.jpacman.ui.PacManUI","start",2); hit("nl.tudelft.jpacman.ui.PacManUI","start",4);}
		}, 0, FRAME_INTERVAL, TimeUnit.MILLISECONDS); hit("nl.tudelft.jpacman.ui.PacManUI","start",8);}

	/**
	 * Draws the next frame, i.e. refreshes the scores and game.
	 */
	private void nextFrame() {hit("nl.tudelft.jpacman.ui.PacManUI","nextFrame"); hit("nl.tudelft.jpacman.ui.PacManUI","nextFrame",1); boardPanel.repaint(); hit("nl.tudelft.jpacman.ui.PacManUI","nextFrame",2); scorePanel.refresh(); hit("nl.tudelft.jpacman.ui.PacManUI","nextFrame",3);}
}
