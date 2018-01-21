package nl.tudelft.jpacman.sprite; import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO; import static nl.dennis.api.CoverageChecker.hit; public class SpriteStore {
	
	/**
	 * We only need to load images once, so we keep track
	 * of them in a hash map.
	 */
	private final Map<String, Sprite> spriteMap;
	
	/**
	 * Create a new sprite store.
	 */
	public SpriteStore() {
		spriteMap = new HashMap<>();
	}
	
	/**
	 * Loads a sprite from a resource on the class path.
	 * Sprites are loaded once, and then stored in the store
	 * so that they can be efficiently retrieved.
	 * 
	 * @param resource
	 *            The resource path.
	 * @return The sprite for the resource.
	 * @throws IOException
	 *             When the resource could not be loaded.
	 */
	public Sprite loadSprite(String resource) throws IOException {hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSprite"); hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSprite",3); Sprite result = spriteMap.get(resource); hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSprite",4); if (result == null) {
			result = loadSpriteFromResource(resource); hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSprite",1); spriteMap.put(resource, result); hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSprite",2);
		} hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSprite",5); return result;}

	/**
	 * Loads a sprite from a resource on the class path.
	 * 
	 * @param resource
	 *            The resource path.
	 * @return A new sprite for the resource.
	 * @throws IOException
	 *             When the resource could not be loaded.
	 */
	private Sprite loadSpriteFromResource(String resource) throws IOException {hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSpriteFromResource"); hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSpriteFromResource",5); try (InputStream input = SpriteStore.class.getResourceAsStream(resource)) {
			if (input == null) {
				throw new IOException("Unable to load " + resource
					+ ", resource does not exist.");
			} hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSpriteFromResource",2); BufferedImage image = ImageIO.read(input); hit("nl.tudelft.jpacman.sprite.SpriteStore","loadSpriteFromResource",3); return new ImageSprite(image);
		}}

	/**
	 * Creates a new {@link AnimatedSprite} from a base image.
	 * 
	 * @param baseImage
	 *            The base image to convert into an animation.
	 * @param frames
	 *            The amount of frames of the animation.
	 * @param delay
	 *            The delay between frames.
	 * @param loop
	 *            Whether this sprite is a looping animation or not.
	 * @return The animated sprite.
	 */
	public AnimatedSprite createAnimatedSprite(Sprite baseImage, int frames,
			int delay, boolean loop) {hit("nl.tudelft.jpacman.sprite.SpriteStore","createAnimatedSprite"); hit("nl.tudelft.jpacman.sprite.SpriteStore","createAnimatedSprite",2); assert baseImage != null; hit("nl.tudelft.jpacman.sprite.SpriteStore","createAnimatedSprite",3); assert frames > 0; hit("nl.tudelft.jpacman.sprite.SpriteStore","createAnimatedSprite",4); int frameWidth = baseImage.getWidth() / frames; hit("nl.tudelft.jpacman.sprite.SpriteStore","createAnimatedSprite",5); Sprite[] animation = new Sprite[frames]; hit("nl.tudelft.jpacman.sprite.SpriteStore","createAnimatedSprite",6); for (int i = 0; i < frames; i++) {
			animation[i] = baseImage.split(i * frameWidth, 0, frameWidth,
					baseImage.getHeight()); hit("nl.tudelft.jpacman.sprite.SpriteStore","createAnimatedSprite",1);
		} hit("nl.tudelft.jpacman.sprite.SpriteStore","createAnimatedSprite",7); return new AnimatedSprite(animation, delay, loop);}

}
