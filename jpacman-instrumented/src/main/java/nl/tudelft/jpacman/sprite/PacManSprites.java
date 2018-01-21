package nl.tudelft.jpacman.sprite; import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.npc.ghost.GhostColor; import static nl.dennis.api.CoverageChecker.hit; public class PacManSprites extends SpriteStore {

	/**
	 * The sprite files are vertically stacked series for each direction, this
	 * array denotes the order.
	 */
	private static final Direction[] DIRECTIONS = { Direction.NORTH,
			Direction.EAST, Direction.SOUTH, Direction.WEST };

	/**
	 * The image size in pixels.
	 */
	private static final int SPRITE_SIZE = 16;

	/**
	 * The amount of frames in the pacman animation.
	 */
	private static final int PACMAN_ANIMATION_FRAMES = 4;

	/**
	 * The amount of frames in the pacman dying animation.
	 */
	private static final int PACMAN_DEATH_FRAMES = 11;
	
	/**
	 * The amount of frames in the ghost animation.
	 */
	private static final int GHOST_ANIMATION_FRAMES = 2;

	/**
	 * The delay between frames.
	 */
	private static final int ANIMATION_DELAY = 200;

	/**
	 * @return A map of animated Pac-Man sprites for all directions.
	 */
	public Map<Direction, Sprite> getPacmanSprites() {hit("nl.tudelft.jpacman.sprite.PacManSprites","getPacmanSprites"); hit("nl.tudelft.jpacman.sprite.PacManSprites","getPacmanSprites",1); return directionSprite("/sprite/pacman.png", PACMAN_ANIMATION_FRAMES);}

	/**
	 * @return The animation of a dying Pac-Man.
	 */
	public AnimatedSprite getPacManDeathAnimation() {hit("nl.tudelft.jpacman.sprite.PacManSprites","getPacManDeathAnimation"); hit("nl.tudelft.jpacman.sprite.PacManSprites","getPacManDeathAnimation",1); String resource = "/sprite/dead.png"; hit("nl.tudelft.jpacman.sprite.PacManSprites","getPacManDeathAnimation",2); Sprite baseImage = loadSprite(resource); hit("nl.tudelft.jpacman.sprite.PacManSprites","getPacManDeathAnimation",3); AnimatedSprite animation = createAnimatedSprite(baseImage, PACMAN_DEATH_FRAMES,
				ANIMATION_DELAY, false); hit("nl.tudelft.jpacman.sprite.PacManSprites","getPacManDeathAnimation",4); animation.setAnimating(false); hit("nl.tudelft.jpacman.sprite.PacManSprites","getPacManDeathAnimation",5); return animation;}

	/**
	 * Returns a new map with animations for all directions.
	 * 
	 * @param resource
	 *            The resource name of the sprite.
	 * @param frames
	 *            The number of frames in this sprite.
	 * @return The animated sprite facing the given direction.
	 */
	private Map<Direction, Sprite> directionSprite(String resource, int frames) {hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite"); hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite",5); Map<Direction, Sprite> sprite = new HashMap<>(); hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite",6); Sprite baseImage = loadSprite(resource); hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite",7); for (int i = 0; i < DIRECTIONS.length; i++) {
			Sprite directionSprite = baseImage.split(0, i * SPRITE_SIZE, frames
					* SPRITE_SIZE, SPRITE_SIZE); hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite",1); AnimatedSprite animation = createAnimatedSprite(directionSprite,
					frames, ANIMATION_DELAY, true); hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite",2); animation.setAnimating(true); hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite",3); sprite.put(DIRECTIONS[i], animation); hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite",4);
		} hit("nl.tudelft.jpacman.sprite.PacManSprites","directionSprite",8); return sprite;}

	/**
	 * Returns a map of animated ghost sprites for all directions.
	 * 
	 * @param color
	 *            The colour of the ghost.
	 * @return The Sprite for the ghost.
	 */
	public Map<Direction, Sprite> getGhostSprite(GhostColor color) {hit("nl.tudelft.jpacman.sprite.PacManSprites","getGhostSprite"); hit("nl.tudelft.jpacman.sprite.PacManSprites","getGhostSprite",1); assert color != null; hit("nl.tudelft.jpacman.sprite.PacManSprites","getGhostSprite",2); String resource = "/sprite/ghost_" + color.name().toLowerCase()
				+ ".png"; hit("nl.tudelft.jpacman.sprite.PacManSprites","getGhostSprite",3); return directionSprite(resource, GHOST_ANIMATION_FRAMES);}

	/**
	 * @return The sprite for the wall.
	 */
	public Sprite getWallSprite() {hit("nl.tudelft.jpacman.sprite.PacManSprites","getWallSprite"); hit("nl.tudelft.jpacman.sprite.PacManSprites","getWallSprite",1); return loadSprite("/sprite/wall.png");}

	/**
	 * @return The sprite for the ground.
	 */
	public Sprite getGroundSprite() {hit("nl.tudelft.jpacman.sprite.PacManSprites","getGroundSprite"); hit("nl.tudelft.jpacman.sprite.PacManSprites","getGroundSprite",1); return loadSprite("/sprite/floor.png");}

	/**
	 * @return The sprite for the
	 */
	public Sprite getPelletSprite() {hit("nl.tudelft.jpacman.sprite.PacManSprites","getPelletSprite"); hit("nl.tudelft.jpacman.sprite.PacManSprites","getPelletSprite",1); return loadSprite("/sprite/pellet.png");}

	/**
	 * Overloads the default sprite loading, ignoring the exception. This class
	 * assumes all sprites are provided, hence the exception will be thrown as a
	 * {@link RuntimeException}.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Sprite loadSprite(String resource) {
		try {
			return super.loadSprite(resource);
		} catch (IOException e) {
			throw new PacmanConfigurationException("Unable to load sprite: " + resource, e);
		}
	}
}
