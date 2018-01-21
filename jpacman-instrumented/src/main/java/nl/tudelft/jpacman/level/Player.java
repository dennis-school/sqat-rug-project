package nl.tudelft.jpacman.level; import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite; import static nl.dennis.api.CoverageChecker.hit; public class Player extends Unit {

	/**
	 * The amount of points accumulated by this player.
	 */
	private int score;

	/**
	 * The animations for every direction.
	 */
	private final Map<Direction, Sprite> sprites;

	/**
	 * The animation that is to be played when Pac-Man dies.
	 */
	private final AnimatedSprite deathSprite;

	/**
	 * <code>true</code> iff this player is alive.
	 */
	private boolean alive;

	/**
	 * Creates a new player with a score of 0 points.
	 * 
	 * @param spriteMap
	 *            A map containing a sprite for this player for every direction.
	 * @param deathAnimation
	 *            The sprite to be shown when this player dies.
	 */
	protected Player(Map<Direction, Sprite> spriteMap, AnimatedSprite deathAnimation) {
		this.score = 0;
		this.alive = true;
		this.sprites = spriteMap;
		this.deathSprite = deathAnimation;
		deathSprite.setAnimating(false);
	}

	/**
	 * Returns whether this player is alive or not.
	 * 
	 * @return <code>true</code> iff the player is alive.
	 */
	public boolean isAlive() {hit("nl.tudelft.jpacman.level.Player","isAlive"); hit("nl.tudelft.jpacman.level.Player","isAlive",1); return alive;}

	/**
	 * Sets whether this player is alive or not.
	 * 
	 * @param isAlive
	 *            <code>true</code> iff this player is alive.
	 */
	public void setAlive(boolean isAlive) {hit("nl.tudelft.jpacman.level.Player","setAlive"); hit("nl.tudelft.jpacman.level.Player","setAlive",3); if (isAlive) {
			deathSprite.setAnimating(false); hit("nl.tudelft.jpacman.level.Player","setAlive",1);
		} hit("nl.tudelft.jpacman.level.Player","setAlive",4); if (!isAlive) {
			deathSprite.restart(); hit("nl.tudelft.jpacman.level.Player","setAlive",2);
		} hit("nl.tudelft.jpacman.level.Player","setAlive",5); this.alive = isAlive; hit("nl.tudelft.jpacman.level.Player","setAlive",6);}

	/**
	 * Returns the amount of points accumulated by this player.
	 * 
	 * @return The amount of points accumulated by this player.
	 */
	public int getScore() {hit("nl.tudelft.jpacman.level.Player","getScore"); hit("nl.tudelft.jpacman.level.Player","getScore",1); return score;}

	@Override
	public Sprite getSprite() {
		if (isAlive()) {
			return sprites.get(getDirection());
		}
		return deathSprite;
	}

	/**
	 * Adds points to the score of this player.
	 * 
	 * @param points
	 *            The amount of points to add to the points this player already
	 *            has.
	 */
	public void addPoints(int points) {hit("nl.tudelft.jpacman.level.Player","addPoints"); hit("nl.tudelft.jpacman.level.Player","addPoints",1); score += points; hit("nl.tudelft.jpacman.level.Player","addPoints",2);}
}
