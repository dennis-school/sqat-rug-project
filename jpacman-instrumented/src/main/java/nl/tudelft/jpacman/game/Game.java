package nl.tudelft.jpacman.game; import java.util.List;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Level.LevelObserver;
import nl.tudelft.jpacman.level.Player; import static nl.dennis.api.CoverageChecker.hit; public abstract class Game implements LevelObserver {

	/**
	 * <code>true</code> if the game is in progress.
	 */
	private boolean inProgress;

	/**
	 * Object that locks the start and stop methods.
	 */
	private final Object progressLock = new Object();

	/**
	 * Creates a new game.
	 */
	protected Game() {
		inProgress = false;
	}

	/**
	 * Starts or resumes the game.
	 */
	public void start() {hit("nl.tudelft.jpacman.game.Game","start"); hit("nl.tudelft.jpacman.game.Game","start",7); synchronized (progressLock) {
			if (isInProgress()) {
				return;
			} hit("nl.tudelft.jpacman.game.Game","start",5); if (getLevel().isAnyPlayerAlive()
					&& getLevel().remainingPellets() > 0) {
				inProgress = true; hit("nl.tudelft.jpacman.game.Game","start",2); getLevel().addObserver(this); hit("nl.tudelft.jpacman.game.Game","start",3); getLevel().start(); hit("nl.tudelft.jpacman.game.Game","start",4);
			} hit("nl.tudelft.jpacman.game.Game","start",6);
		} hit("nl.tudelft.jpacman.game.Game","start",8);}

	/**
	 * Pauses the game.
	 */
	public void stop() {hit("nl.tudelft.jpacman.game.Game","stop"); hit("nl.tudelft.jpacman.game.Game","stop",5); synchronized (progressLock) {
			if (!isInProgress()) {
				return;
			} hit("nl.tudelft.jpacman.game.Game","stop",2); inProgress = false; hit("nl.tudelft.jpacman.game.Game","stop",3); getLevel().stop(); hit("nl.tudelft.jpacman.game.Game","stop",4);
		} hit("nl.tudelft.jpacman.game.Game","stop",6);}

	/**
	 * @return <code>true</code> iff the game is started and in progress.
	 */
	public boolean isInProgress() {hit("nl.tudelft.jpacman.game.Game","isInProgress"); hit("nl.tudelft.jpacman.game.Game","isInProgress",1); return inProgress;}

	/**
	 * @return An immutable list of the participants of this game.
	 */
	public abstract List<Player> getPlayers();

	/**
	 * @return The level currently being played.
	 */
	public abstract Level getLevel();

	/**
	 * Moves the specified player one square in the given direction.
	 * 
	 * @param player
	 *            The player to move.
	 * @param direction
	 *            The direction to move in.
	 */
	public void move(Player player, Direction direction) {hit("nl.tudelft.jpacman.game.Game","move"); hit("nl.tudelft.jpacman.game.Game","move",2); if (isInProgress()) {
			// execute player move.
			getLevel().move(player, direction); hit("nl.tudelft.jpacman.game.Game","move",1);
		} hit("nl.tudelft.jpacman.game.Game","move",3);}
	
	@Override
	public void levelWon() {
		stop();
	}
	
	@Override
	public void levelLost() {
		stop();
	}
}
