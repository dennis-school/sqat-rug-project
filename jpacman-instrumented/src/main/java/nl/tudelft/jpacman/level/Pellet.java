package nl.tudelft.jpacman.level; import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.Sprite; import static nl.dennis.api.CoverageChecker.hit; public class Pellet extends Unit {

	/**
	 * The sprite of this unit.
	 */
	private final Sprite image;
	
	/**
	 * The point value of this pellet.
	 */
	private final int value;
	
	/**
	 * Creates a new pellet.
	 * @param points The point value of this pellet.
	 * @param sprite The sprite of this pellet.
	 */
	public Pellet(int points, Sprite sprite) {
		this.image = sprite;
		this.value = points;
	}
	
	/**
	 * Returns the point value of this pellet.
	 * @return The point value of this pellet.
	 */
	public int getValue() {hit("nl.tudelft.jpacman.level.Pellet","getValue"); hit("nl.tudelft.jpacman.level.Pellet","getValue",1); return value;}
	
	@Override
	public Sprite getSprite() {
		return image;
	}
}
