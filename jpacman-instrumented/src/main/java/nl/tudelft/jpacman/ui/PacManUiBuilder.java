package nl.tudelft.jpacman.ui; import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter; import static nl.dennis.api.CoverageChecker.hit; public class PacManUiBuilder {

	/**
	 * Caption for the default stop button.
	 */
	private static final String STOP_CAPTION = "Stop";

	/**
	 * Caption for the default start button.
	 */
	private static final String START_CAPTION = "Start";

	/**
	 * Map of buttons and their actions.
	 */
	private final Map<String, Action> buttons;

	/**
	 * Map of key events and their actions.
	 */
	private final Map<Integer, Action> keyMappings;

	/**
	 * <code>true</code> iff this UI has the default buttons.
	 */
	private boolean defaultButtons;
	
	/**
	 * Way to format the score.
	 */
	private ScoreFormatter scoreFormatter = null;

	/**
	 * Creates a new Pac-Man UI builder without any mapped keys or buttons.
	 */
	public PacManUiBuilder() {
		this.defaultButtons = false;
		this.buttons = new LinkedHashMap<>();
		this.keyMappings = new HashMap<>();
	}

	/**
	 * Creates a new Pac-Man UI with the set keys and buttons.
	 * 
	 * @param game
	 *            The game to build the UI for.
	 * @return A new Pac-Man UI with the set keys and buttons.
	 */
	public PacManUI build(final Game game) {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","build"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","build",3); assert game != null; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","build",4); if (defaultButtons) {
			addStartButton(game); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","build",1); addStopButton(game); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","build",2);
		} hit("nl.tudelft.jpacman.ui.PacManUiBuilder","build",5); return new PacManUI(game, buttons, keyMappings, scoreFormatter);}

	/**
	 * Adds a button with the caption {@value #STOP_CAPTION} that stops the
	 * game.
	 * 
	 * @param game
	 *            The game to stop.
	 */
	private void addStopButton(final Game game) {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",5); assert game != null; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",6); buttons.put(STOP_CAPTION, new Action() {
			@Override
			public void doAction() {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",1); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",1); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",2); game.stop(); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",3); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",2); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",4);}
		}); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStopButton",7);}

	/**
	 * Adds a button with the caption {@value #START_CAPTION} that starts the
	 * game.
	 * 
	 * @param game
	 *            The game to start.
	 */
	private void addStartButton(final Game game) {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",5); assert game != null; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",6); buttons.put(START_CAPTION, new Action() {
			@Override
			public void doAction() {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",1); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",1); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",2); game.start(); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",3); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",2); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",4);}
		}); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addStartButton",7);}

	/**
	 * Adds a key listener to the UI.
	 * 
	 * @param keyCode
	 *            The key code of the key as used by {@link java.awt.event.KeyEvent}.
	 * @param action
	 *            The action to perform when the key is pressed.
	 * @return The builder.
	 */
	public PacManUiBuilder addKey(Integer keyCode, Action action) {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addKey"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addKey",1); assert keyCode != null; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addKey",2); assert action != null; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addKey",3); keyMappings.put(keyCode, action); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addKey",4); return this;}

	/**
	 * Adds a button to the UI.
	 * 
	 * @param caption
	 *            The caption of the button.
	 * @param action
	 *            The action to execute when the button is clicked.
	 * @return The builder.
	 */
	public PacManUiBuilder addButton(String caption, Action action) {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addButton"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addButton",1); assert caption != null; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addButton",2); assert !caption.isEmpty(); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addButton",3); assert action != null; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addButton",4); buttons.put(caption, action); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","addButton",5); return this;}

	/**
	 * Adds a start and stop button to the UI. The actual actions for these
	 * buttons will be added upon building the UI.
	 * 
	 * @return The builder.
	 */
	public PacManUiBuilder withDefaultButtons() {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","withDefaultButtons"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","withDefaultButtons",1); defaultButtons = true; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","withDefaultButtons",2); buttons.put(START_CAPTION, null); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","withDefaultButtons",3); buttons.put(STOP_CAPTION, null); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","withDefaultButtons",4); return this;}
	
	/**
	 * Provide formatter for the score.
	 * 
	 * @param sf
	 *         The score formatter to be used.
	 * 
	 * @return The builder.
	 */
	public PacManUiBuilder withScoreFormatter(ScoreFormatter sf) {hit("nl.tudelft.jpacman.ui.PacManUiBuilder","withScoreFormatter"); hit("nl.tudelft.jpacman.ui.PacManUiBuilder","withScoreFormatter",1); scoreFormatter = sf; hit("nl.tudelft.jpacman.ui.PacManUiBuilder","withScoreFormatter",2); return this;}
}
