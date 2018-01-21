package nl.tudelft.jpacman.level; import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.NPC; import static nl.dennis.api.CoverageChecker.hit; @SuppressWarnings("PMD.TooManyMethods")
public class Level {

	/**
	 * The board of this level.
	 */
	private final Board board;

	/**
	 * The lock that ensures moves are executed sequential.
	 */
	private final Object moveLock = new Object();

	/**
	 * The lock that ensures starting and stopping can't interfere with each
	 * other.
	 */
	private final Object startStopLock = new Object();

	/**
	 * The NPCs of this level and, if they are running, their schedules.
	 */
	private final Map<NPC, ScheduledExecutorService> npcs;

	/**
	 * <code>true</code> iff this level is currently in progress, i.e. players
	 * and NPCs can move.
	 */
	private boolean inProgress;

	/**
	 * The squares from which players can start this game.
	 */
	private final List<Square> startSquares;

	/**
	 * The start current selected starting square.
	 */
	private int startSquareIndex;

	/**
	 * The players on this level.
	 */
	private final List<Player> players;

	/**
	 * The table of possible collisions between units.
	 */
	private final CollisionMap collisions;

	/**
	 * The objects observing this level.
	 */
	private final Set<LevelObserver> observers;

	/**
	 * Creates a new level for the board.
	 * 
	 * @param b
	 *            The board for the level.
	 * @param ghosts
	 *            The ghosts on the board.
	 * @param startPositions
	 *            The squares on which players start on this board.
	 * @param collisionMap
	 *            The collection of collisions that should be handled.
	 */
	public Level(Board b, List<NPC> ghosts, List<Square> startPositions,
			CollisionMap collisionMap) {
		assert b != null;
		assert ghosts != null;
		assert startPositions != null;

		this.board = b;
		this.inProgress = false;
		this.npcs = new HashMap<>();
		for (NPC g : ghosts) {
			npcs.put(g, null);
		}
		this.startSquares = startPositions;
		this.startSquareIndex = 0;
		this.players = new ArrayList<>();
		this.collisions = collisionMap;
		this.observers = new HashSet<>();
	}

	/**
	 * Adds an observer that will be notified when the level is won or lost.
	 * 
	 * @param observer
	 *            The observer that will be notified.
	 */
	public void addObserver(LevelObserver observer) {hit("nl.tudelft.jpacman.level.SuppressWarnings","addObserver"); hit("nl.tudelft.jpacman.level.SuppressWarnings","addObserver",1); observers.add(observer); hit("nl.tudelft.jpacman.level.SuppressWarnings","addObserver",2);}

	/**
	 * Removes an observer if it was listed.
	 * 
	 * @param observer
	 *            The observer to be removed.
	 */
	public void removeObserver(LevelObserver observer) {hit("nl.tudelft.jpacman.level.SuppressWarnings","removeObserver"); hit("nl.tudelft.jpacman.level.SuppressWarnings","removeObserver",1); observers.remove(observer); hit("nl.tudelft.jpacman.level.SuppressWarnings","removeObserver",2);}

	/**
	 * Registers a player on this level, assigning him to a starting position. A
	 * player can only be registered once, registering a player again will have
	 * no effect.
	 * 
	 * @param p
	 *            The player to register.
	 */
	public void registerPlayer(Player p) {hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer"); hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",2); assert p != null; hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",3); assert !startSquares.isEmpty(); hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",4); if (players.contains(p)) {
			return;
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",5); players.add(p); hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",6); Square square = startSquares.get(startSquareIndex); hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",7); p.occupy(square); hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",8); startSquareIndex++; hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",9); startSquareIndex %= startSquares.size(); hit("nl.tudelft.jpacman.level.SuppressWarnings","registerPlayer",10);}

	/**
	 * Returns the board of this level.
	 * 
	 * @return The board of this level.
	 */
	public Board getBoard() {hit("nl.tudelft.jpacman.level.SuppressWarnings","getBoard"); hit("nl.tudelft.jpacman.level.SuppressWarnings","getBoard",1); return board;}

	/**
	 * Moves the unit into the given direction if possible and handles all
	 * collisions.
	 * 
	 * @param unit
	 *            The unit to move.
	 * @param direction
	 *            The direction to move the unit in.
	 */
	public void move(Unit unit, Direction direction) {hit("nl.tudelft.jpacman.level.SuppressWarnings","move"); hit("nl.tudelft.jpacman.level.SuppressWarnings","move",11); assert unit != null; hit("nl.tudelft.jpacman.level.SuppressWarnings","move",12); assert direction != null; hit("nl.tudelft.jpacman.level.SuppressWarnings","move",13); if (!isInProgress()) {
			return;
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","move",14); synchronized (moveLock) {
			unit.setDirection(direction); hit("nl.tudelft.jpacman.level.SuppressWarnings","move",6); Square location = unit.getSquare(); hit("nl.tudelft.jpacman.level.SuppressWarnings","move",7); Square destination = location.getSquareAt(direction); hit("nl.tudelft.jpacman.level.SuppressWarnings","move",8); if (destination.isAccessibleTo(unit)) {
				List<Unit> occupants = destination.getOccupants(); hit("nl.tudelft.jpacman.level.SuppressWarnings","move",3); unit.occupy(destination); hit("nl.tudelft.jpacman.level.SuppressWarnings","move",4); for (Unit occupant : occupants) {
					collisions.collide(unit, occupant); hit("nl.tudelft.jpacman.level.SuppressWarnings","move",2);
				} hit("nl.tudelft.jpacman.level.SuppressWarnings","move",5);
			} hit("nl.tudelft.jpacman.level.SuppressWarnings","move",9); updateObservers(); hit("nl.tudelft.jpacman.level.SuppressWarnings","move",10);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","move",15);}

	/**
	 * Starts or resumes this level, allowing movement and (re)starting the
	 * NPCs.
	 */
	public void start() {hit("nl.tudelft.jpacman.level.SuppressWarnings","start"); hit("nl.tudelft.jpacman.level.SuppressWarnings","start",6); synchronized (startStopLock) {
			if (isInProgress()) {
				return;
			} hit("nl.tudelft.jpacman.level.SuppressWarnings","start",2); startNPCs(); hit("nl.tudelft.jpacman.level.SuppressWarnings","start",3); inProgress = true; hit("nl.tudelft.jpacman.level.SuppressWarnings","start",4); updateObservers(); hit("nl.tudelft.jpacman.level.SuppressWarnings","start",5);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","start",7);}

	/**
	 * Stops or pauses this level, no longer allowing any movement on the board
	 * and stopping all NPCs.
	 */
	public void stop() {hit("nl.tudelft.jpacman.level.SuppressWarnings","stop"); hit("nl.tudelft.jpacman.level.SuppressWarnings","stop",5); synchronized (startStopLock) {
			if (!isInProgress()) {
				return;
			} hit("nl.tudelft.jpacman.level.SuppressWarnings","stop",2); stopNPCs(); hit("nl.tudelft.jpacman.level.SuppressWarnings","stop",3); inProgress = false; hit("nl.tudelft.jpacman.level.SuppressWarnings","stop",4);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","stop",6);}

	/**
	 * Starts all NPC movement scheduling.
	 */
	private void startNPCs() {hit("nl.tudelft.jpacman.level.SuppressWarnings","startNPCs"); hit("nl.tudelft.jpacman.level.SuppressWarnings","startNPCs",4); for (final NPC npc : npcs.keySet()) {
			ScheduledExecutorService service = Executors
					.newSingleThreadScheduledExecutor(); hit("nl.tudelft.jpacman.level.SuppressWarnings","startNPCs",1); service.schedule(new NpcMoveTask(service, npc),
					npc.getInterval() / 2, TimeUnit.MILLISECONDS); hit("nl.tudelft.jpacman.level.SuppressWarnings","startNPCs",2); npcs.put(npc, service); hit("nl.tudelft.jpacman.level.SuppressWarnings","startNPCs",3);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","startNPCs",5);}

	/**
	 * Stops all NPC movement scheduling and interrupts any movements being
	 * executed.
	 */
	private void stopNPCs() {hit("nl.tudelft.jpacman.level.SuppressWarnings","stopNPCs"); hit("nl.tudelft.jpacman.level.SuppressWarnings","stopNPCs",2); for (Entry<NPC, ScheduledExecutorService> e : npcs.entrySet()) {
			e.getValue().shutdownNow(); hit("nl.tudelft.jpacman.level.SuppressWarnings","stopNPCs",1);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","stopNPCs",3);}

	/**
	 * Returns whether this level is in progress, i.e. whether moves can be made
	 * on the board.
	 * 
	 * @return <code>true</code> iff this level is in progress.
	 */
	public boolean isInProgress() {hit("nl.tudelft.jpacman.level.SuppressWarnings","isInProgress"); hit("nl.tudelft.jpacman.level.SuppressWarnings","isInProgress",1); return inProgress;}

	/**
	 * Updates the observers about the state of this level.
	 */
	private void updateObservers() {hit("nl.tudelft.jpacman.level.SuppressWarnings","updateObservers"); hit("nl.tudelft.jpacman.level.SuppressWarnings","updateObservers",5); if (!isAnyPlayerAlive()) {
			for (LevelObserver o : observers) {
				o.levelLost(); hit("nl.tudelft.jpacman.level.SuppressWarnings","updateObservers",1);
			} hit("nl.tudelft.jpacman.level.SuppressWarnings","updateObservers",2);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","updateObservers",6); if (remainingPellets() == 0) {
			for (LevelObserver o : observers) {
				o.levelWon(); hit("nl.tudelft.jpacman.level.SuppressWarnings","updateObservers",3);
			} hit("nl.tudelft.jpacman.level.SuppressWarnings","updateObservers",4);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","updateObservers",7);}

	/**
	 * Returns <code>true</code> iff at least one of the players in this level
	 * is alive.
	 * 
	 * @return <code>true</code> if at least one of the registered players is
	 *         alive.
	 */
	public boolean isAnyPlayerAlive() {hit("nl.tudelft.jpacman.level.SuppressWarnings","isAnyPlayerAlive"); hit("nl.tudelft.jpacman.level.SuppressWarnings","isAnyPlayerAlive",3); for (Player p : players) {
			if (p.isAlive()) {
				return true;
			} hit("nl.tudelft.jpacman.level.SuppressWarnings","isAnyPlayerAlive",2);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","isAnyPlayerAlive",4); return false;}

	/**
	 * Counts the pellets remaining on the board.
	 * 
	 * @return The amount of pellets remaining on the board.
	 */
	public int remainingPellets() {hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets"); hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",5); Board b = getBoard(); hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",6); int pellets = 0; hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",7); for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				for (Unit u : b.squareAt(x, y).getOccupants()) {
					if (u instanceof Pellet) {
						pellets++; hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",1);
					} hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",2);
				} hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",3);
			} hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",4);
		} hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",8); assert pellets >= 0; hit("nl.tudelft.jpacman.level.SuppressWarnings","remainingPellets",9); return pellets;}

	/**
	 * A task that moves an NPC and reschedules itself after it finished.
	 * 
	 * @author Jeroen Roosen 
	 */
	private final class NpcMoveTask implements Runnable {

		/**
		 * The service executing the task.
		 */
		private final ScheduledExecutorService service;

		/**
		 * The NPC to move.
		 */
		private final NPC npc;

		/**
		 * Creates a new task.
		 * 
		 * @param s
		 *            The service that executes the task.
		 * @param n
		 *            The NPC to move.
		 */
		NpcMoveTask(ScheduledExecutorService s, NPC n) {
			this.service = s;
			this.npc = n;
		}

		@Override
		public void run() {
			Direction nextMove = npc.nextMove();
			if (nextMove != null) {
				move(npc, nextMove);
			}
			long interval = npc.getInterval();
			service.schedule(this, interval, TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * An observer that will be notified when the level is won or lost.
	 * 
	 * @author Jeroen Roosen 
	 */
	public interface LevelObserver {

		/**
		 * The level has been won. Typically the level should be stopped when
		 * this event is received.
		 */
		void levelWon();

		/**
		 * The level has been lost. Typically the level should be stopped when
		 * this event is received.
		 */
		void levelLost();
	}
}
