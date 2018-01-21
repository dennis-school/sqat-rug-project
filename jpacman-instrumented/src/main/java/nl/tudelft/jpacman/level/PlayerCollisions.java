package nl.tudelft.jpacman.level; import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost; import static nl.dennis.api.CoverageChecker.hit; public class PlayerCollisions implements CollisionMap {

	@Override
	public void collide(Unit mover, Unit collidedOn) {
		
		if (mover instanceof Player) {
			playerColliding((Player) mover, collidedOn);
		}
		else if (mover instanceof Ghost) {
			ghostColliding((Ghost) mover, collidedOn);
		}
		else if (mover instanceof Pellet) {
			pelletColliding((Pellet) mover, collidedOn);
		}
	}
	
	private void playerColliding(Player player, Unit collidedOn) {hit("nl.tudelft.jpacman.level.PlayerCollisions","playerColliding"); hit("nl.tudelft.jpacman.level.PlayerCollisions","playerColliding",3); if (collidedOn instanceof Ghost) {
			playerVersusGhost(player, (Ghost) collidedOn); hit("nl.tudelft.jpacman.level.PlayerCollisions","playerColliding",1);
		} hit("nl.tudelft.jpacman.level.PlayerCollisions","playerColliding",4); if (collidedOn instanceof Pellet) {
			playerVersusPellet(player, (Pellet) collidedOn); hit("nl.tudelft.jpacman.level.PlayerCollisions","playerColliding",2);
		} hit("nl.tudelft.jpacman.level.PlayerCollisions","playerColliding",5);}
	
	private void ghostColliding(Ghost ghost, Unit collidedOn) {hit("nl.tudelft.jpacman.level.PlayerCollisions","ghostColliding"); hit("nl.tudelft.jpacman.level.PlayerCollisions","ghostColliding",2); if (collidedOn instanceof Player) {
			playerVersusGhost((Player) collidedOn, ghost); hit("nl.tudelft.jpacman.level.PlayerCollisions","ghostColliding",1);
		} hit("nl.tudelft.jpacman.level.PlayerCollisions","ghostColliding",3);}
	
	private void pelletColliding(Pellet pellet, Unit collidedOn) {hit("nl.tudelft.jpacman.level.PlayerCollisions","pelletColliding"); hit("nl.tudelft.jpacman.level.PlayerCollisions","pelletColliding",2); if (collidedOn instanceof Player) {
			playerVersusPellet((Player) collidedOn, pellet); hit("nl.tudelft.jpacman.level.PlayerCollisions","pelletColliding",1);
		} hit("nl.tudelft.jpacman.level.PlayerCollisions","pelletColliding",3);}
	
	
	/**
	 * Actual case of player bumping into ghost or vice versa.
     *
     * @param player The player involved in the collision.
     * @param ghost The ghost involved in the collision.
	 */
	public void playerVersusGhost(Player player, Ghost ghost) {hit("nl.tudelft.jpacman.level.PlayerCollisions","playerVersusGhost"); hit("nl.tudelft.jpacman.level.PlayerCollisions","playerVersusGhost",1); player.setAlive(false); hit("nl.tudelft.jpacman.level.PlayerCollisions","playerVersusGhost",2);}
	
	/**
	 * Actual case of player consuming a pellet.
     *
     * @param player The player involved in the collision.
     * @param pellet The pellet involved in the collision.
	 */
	public void playerVersusPellet(Player player, Pellet pellet) {hit("nl.tudelft.jpacman.level.PlayerCollisions","playerVersusPellet"); hit("nl.tudelft.jpacman.level.PlayerCollisions","playerVersusPellet",1); pellet.leaveSquare(); hit("nl.tudelft.jpacman.level.PlayerCollisions","playerVersusPellet",2); player.addPoints(pellet.getValue()); hit("nl.tudelft.jpacman.level.PlayerCollisions","playerVersusPellet",3);}

}
