package nl.tudelft.jpacman.board; import nl.tudelft.jpacman.sprite.Sprite; import static nl.dennis.api.CoverageChecker.hit; public abstract class Unit {

	/**
	 * The square this unit is currently occupying.
	 */
	private Square square;
	
	/**
	 * The direction this unit is facing.
	 */
	private Direction direction;

	/**
	 * Creates a unit that is facing east.
	 */
	protected Unit() {
		this.direction = Direction.EAST;
	}
	
	/**
	 * Sets this unit to face the new direction.
	 * @param newDirection The new direction this unit is facing.
	 */
	public void setDirection(Direction newDirection) {hit("nl.tudelft.jpacman.board.Unit","setDirection"); hit("nl.tudelft.jpacman.board.Unit","setDirection",1); this.direction = newDirection; hit("nl.tudelft.jpacman.board.Unit","setDirection",2);}
	
	/**
	 * Returns the current direction this unit is facing.
	 * @return The current direction this unit is facing.
	 */
	public Direction getDirection() {hit("nl.tudelft.jpacman.board.Unit","getDirection"); hit("nl.tudelft.jpacman.board.Unit","getDirection",1); return this.direction;}
	
	/**
	 * Returns the square this unit is currently occupying.
	 * 
	 * @return The square this unit is currently occupying, or <code>null</code>
	 *         if this unit is not on a square.
	 */
	public Square getSquare() {hit("nl.tudelft.jpacman.board.Unit","getSquare"); hit("nl.tudelft.jpacman.board.Unit","getSquare",1); assert invariant(); hit("nl.tudelft.jpacman.board.Unit","getSquare",2); return square;}

	/**
	 * Occupies the target square iff this unit is allowed to as decided by
	 * {@link Square#isAccessibleTo(Unit)}.
	 * 
	 * @param target
	 *            The square to occupy.
	 */
	public void occupy(Square target) {hit("nl.tudelft.jpacman.board.Unit","occupy"); hit("nl.tudelft.jpacman.board.Unit","occupy",2); assert target != null; hit("nl.tudelft.jpacman.board.Unit","occupy",3); if (square != null) {
			square.remove(this); hit("nl.tudelft.jpacman.board.Unit","occupy",1);
		} hit("nl.tudelft.jpacman.board.Unit","occupy",4); square = target; hit("nl.tudelft.jpacman.board.Unit","occupy",5); target.put(this); hit("nl.tudelft.jpacman.board.Unit","occupy",6); assert invariant(); hit("nl.tudelft.jpacman.board.Unit","occupy",7);}
	
	/**
	 * Leaves the currently occupying square, thus removing this unit from the board.
	 */
	public void leaveSquare() {hit("nl.tudelft.jpacman.board.Unit","leaveSquare"); hit("nl.tudelft.jpacman.board.Unit","leaveSquare",3); if (square != null) {
			square.remove(this); hit("nl.tudelft.jpacman.board.Unit","leaveSquare",1); square = null; hit("nl.tudelft.jpacman.board.Unit","leaveSquare",2);
		} hit("nl.tudelft.jpacman.board.Unit","leaveSquare",4); assert invariant(); hit("nl.tudelft.jpacman.board.Unit","leaveSquare",5);}

	/**
	 * Tests whether the square this unit is occupying has this unit listed as
	 * one of its occupiers.
	 * 
	 * @return <code>true</code> if the square this unit is occupying has this
	 *         unit listed as one of its occupiers, or if this unit is currently
	 *         not occupying any square.
	 */
	protected boolean invariant() {hit("nl.tudelft.jpacman.board.Unit","invariant"); hit("nl.tudelft.jpacman.board.Unit","invariant",1); return square == null || square.getOccupants().contains(this);}

	/**
	 * Returns the sprite of this unit.
	 * 
	 * @return The sprite of this unit.
	 */
	public abstract Sprite getSprite();

}
