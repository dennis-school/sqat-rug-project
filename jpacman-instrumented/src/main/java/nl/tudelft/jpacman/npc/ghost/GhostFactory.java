package nl.tudelft.jpacman.npc.ghost; import nl.tudelft.jpacman.sprite.PacManSprites; import static nl.dennis.api.CoverageChecker.hit; public class GhostFactory {

	/**
	 * The sprite store containing the ghost sprites.
	 */
	private final PacManSprites sprites;

	/**
	 * Creates a new ghost factory.
	 * 
	 * @param spriteStore The sprite provider.
	 */
	public GhostFactory(PacManSprites spriteStore) {
		this.sprites = spriteStore;
	}

	/**
	 * Creates a new Blinky / Shadow, the red Ghost.
	 * 
	 * @see Blinky
	 * @return A new Blinky.
	 */
	public Ghost createBlinky() {hit("nl.tudelft.jpacman.npc.ghost.GhostFactory","createBlinky"); hit("nl.tudelft.jpacman.npc.ghost.GhostFactory","createBlinky",1); return new Blinky(sprites.getGhostSprite(GhostColor.RED));}

	/**
	 * Creates a new Pinky / Speedy, the pink Ghost.
	 * 
	 * @see Pinky
	 * @return A new Pinky.
	 */
	public Ghost createPinky() {hit("nl.tudelft.jpacman.npc.ghost.GhostFactory","createPinky"); hit("nl.tudelft.jpacman.npc.ghost.GhostFactory","createPinky",1); return new Pinky(sprites.getGhostSprite(GhostColor.PINK));}

	/**
	 * Creates a new Inky / Bashful, the cyan Ghost.
	 * 
	 * @see Inky
	 * @return A new Inky.
	 */
	public Ghost createInky() {hit("nl.tudelft.jpacman.npc.ghost.GhostFactory","createInky"); hit("nl.tudelft.jpacman.npc.ghost.GhostFactory","createInky",1); return new Inky(sprites.getGhostSprite(GhostColor.CYAN));}

	/**
	 * Creates a new Clyde / Pokey, the orange Ghost.
	 * 
	 * @see Clyde
	 * @return A new Clyde.
	 */
	public Ghost createClyde() {hit("nl.tudelft.jpacman.npc.ghost.GhostFactory","createClyde"); hit("nl.tudelft.jpacman.npc.ghost.GhostFactory","createClyde",1); return new Clyde(sprites.getGhostSprite(GhostColor.ORANGE));}
}
