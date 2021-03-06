package nl.tudelft.jpacman.npc.ghost; import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.sprite.Sprite; import static nl.dennis.api.CoverageChecker.hit; public class Pinky extends Ghost {

	private static final int SQUARES_AHEAD = 4;

	/**
	 * The variation in intervals, this makes the ghosts look more dynamic and
	 * less predictable.
	 */
	private static final int INTERVAL_VARIATION = 50;

	/**
	 * The base movement interval.
	 */
	private static final int MOVE_INTERVAL = 200;

	/**
	 * Creates a new "Pinky", a.k.a. "Speedy".
	 * 
	 * @param spriteMap
	 *            The sprites for this ghost.
	 */
	public Pinky(Map<Direction, Sprite> spriteMap) {
		super(spriteMap, MOVE_INTERVAL, INTERVAL_VARIATION);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * When the ghosts are not patrolling their home corners, Pinky wants to go
	 * to the place that is four grid spaces ahead of Pac-Man in the direction
	 * that Pac-Man is facing. If Pac-Man is facing down, Pinky wants to go to
	 * the location exactly four spaces below Pac-Man. Moving towards this place
	 * uses the same logic that Blinky uses to find Pac-Man's exact location.
	 * Pinky is affected by a targeting bug if Pac-Man is facing up - when he
	 * moves or faces up, Pinky tries moving towards a point up, and left, four
	 * spaces.
	 * </p>
	 */
	@Override
	public Direction nextMove() {
		Unit player = Navigation.findNearest(Player.class, getSquare());
		if (player == null) {
			return randomMove();
		}

		Direction targetDirection = player.getDirection();
		Square destination = player.getSquare();
		for (int i = 0; i < SQUARES_AHEAD; i++) {
			destination = destination.getSquareAt(targetDirection);
		}

		List<Direction> path = Navigation.shortestPath(getSquare(),
				destination, this);
		if (path != null && !path.isEmpty()) {
			return path.get(0);
		}
		return randomMove();
	}
}
