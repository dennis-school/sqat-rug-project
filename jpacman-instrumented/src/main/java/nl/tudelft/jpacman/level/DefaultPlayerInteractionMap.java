package nl.tudelft.jpacman.level; import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.CollisionInteractionMap.CollisionHandler;
import nl.tudelft.jpacman.npc.ghost.Ghost; import static nl.dennis.api.CoverageChecker.hit; public class DefaultPlayerInteractionMap implements CollisionMap {

	private final CollisionMap collisions = defaultCollisions();

	@Override
	public void collide(Unit mover, Unit movedInto) {
		collisions.collide(mover, movedInto);
	}

	/**
	 * Creates the default collisions Player-Ghost and Player-Pellet.
	 * 
	 * @return The collision map containing collisions for Player-Ghost and
	 *         Player-Pellet.
	 */
	private static CollisionInteractionMap defaultCollisions() {
		CollisionInteractionMap collisionMap = new CollisionInteractionMap();

		collisionMap.onCollision(Player.class, Ghost.class,
				new CollisionHandler<Player, Ghost>() {

					@Override
					public void handleCollision(Player player, Ghost ghost) {
						player.setAlive(false);
					}
				});

		collisionMap.onCollision(Player.class, Pellet.class,
				new CollisionHandler<Player, Pellet>() {

					@Override
					public void handleCollision(Player player, Pellet pellet) {
						pellet.leaveSquare();
						player.addPoints(pellet.getValue());
					}
				});
		return collisionMap;
	}
}
