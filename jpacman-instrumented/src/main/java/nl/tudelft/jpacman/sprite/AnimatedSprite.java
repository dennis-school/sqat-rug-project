package nl.tudelft.jpacman.sprite; import java.awt.Graphics; import static nl.dennis.api.CoverageChecker.hit; public class AnimatedSprite implements Sprite {

	/**
	 * Static empty sprite to serve as the end of a non-looping sprite.
	 */
	private static final Sprite END_OF_LOOP = new EmptySprite();

	/**
	 * The animation itself, in frames.
	 */
	private final Sprite[] animationFrames;

	/**
	 * The delay between frames.
	 */
	private final int animationDelay;

	/**
	 * Whether is animation should be looping or not.
	 */
	private final boolean looping;

	/**
	 * The index of the current frame.
	 */
	private int current;

	/**
	 * Whether this sprite is currently animating or not.
	 */
	private boolean animating;

	/**
	 * The {@link System#currentTimeMillis()} stamp of the last update.
	 */
	private long lastUpdate;

	/**
	 * Creates a new animating sprite that will change frames every interval. By
	 * default the sprite is not animating.
	 * 
	 * @param frames
	 *            The frames of this animation.
	 * @param delay
	 *            The delay between frames.
	 * @param loop
	 *            Whether or not this sprite should be looping.
	 */
	public AnimatedSprite(Sprite[] frames, int delay, boolean loop) {
		this(frames, delay, loop, false);
	}

	/**
	 * Creates a new animating sprite that will change frames every interval.
	 * 
	 * @param frames
	 *            The frames of this animation.
	 * @param delay
	 *            The delay between frames.
	 * @param loop
	 *            Whether or not this sprite should be looping.
	 * @param isAnimating
	 *            Whether or not this sprite is animating from the start.
	 */
	public AnimatedSprite(Sprite[] frames, int delay, boolean loop,
			boolean isAnimating) {
		assert frames.length > 0;

		this.animationFrames = frames.clone();
		this.animationDelay = delay;
		this.looping = loop;
		this.animating = isAnimating;

		this.current = 0;
		this.lastUpdate = System.currentTimeMillis();
	}

	/**
	 * @return The frame of the current index.
	 */
	private Sprite currentSprite() {hit("nl.tudelft.jpacman.sprite.AnimatedSprite","currentSprite"); hit("nl.tudelft.jpacman.sprite.AnimatedSprite","currentSprite",2); Sprite result = END_OF_LOOP; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","currentSprite",3); if (current < animationFrames.length) {
			result = animationFrames[current]; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","currentSprite",1);
		} hit("nl.tudelft.jpacman.sprite.AnimatedSprite","currentSprite",4); assert result != null; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","currentSprite",5); return result;}

	/**
	 * Starts or stops the animation of this sprite.
	 * 
	 * @param isAnimating
	 *            <code>true</code> to animate this sprite or <code>false</code>
	 *            to stop animating this sprite.
	 */
	public void setAnimating(boolean isAnimating) {hit("nl.tudelft.jpacman.sprite.AnimatedSprite","setAnimating"); hit("nl.tudelft.jpacman.sprite.AnimatedSprite","setAnimating",1); this.animating = isAnimating; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","setAnimating",2);}
	
	/**
	 * (Re)starts the current animation.
	 */
	public void restart() {hit("nl.tudelft.jpacman.sprite.AnimatedSprite","restart"); hit("nl.tudelft.jpacman.sprite.AnimatedSprite","restart",1); this.current = 0; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","restart",2); this.lastUpdate = System.currentTimeMillis(); hit("nl.tudelft.jpacman.sprite.AnimatedSprite","restart",3); setAnimating(true); hit("nl.tudelft.jpacman.sprite.AnimatedSprite","restart",4);}

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		update();
		currentSprite().draw(g, x, y, width, height);
	}

	@Override
	public Sprite split(int x, int y, int width, int height) {
		update();
		return currentSprite().split(x, y, width, height);
	}

	/**
	 * Updates the current frame index depending on the current system time.
	 */
	private void update() {hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update"); hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",8); long now = System.currentTimeMillis(); hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",9); if (animating) {
			while (lastUpdate < now) {
				lastUpdate += animationDelay; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",3); current++; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",4); if (looping) {
					current %= animationFrames.length; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",1);
				} else if (current == animationFrames.length) {
					animating = false; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",2);
				} hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",5);
			} hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",6);
		} else {
			lastUpdate = now; hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",7);
		} hit("nl.tudelft.jpacman.sprite.AnimatedSprite","update",10);}

	@Override
	public int getWidth() {
		assert currentSprite() != null;
		return currentSprite().getWidth();
	}

	@Override
	public int getHeight() {
		assert currentSprite() != null;
		return currentSprite().getHeight();
	}

}
