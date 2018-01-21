package nl.tudelft.jpacman.npc.ghost; import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit; import static nl.dennis.api.CoverageChecker.hit; public final class Navigation {

	private Navigation() {
	}
	
	/**
	 * Calculates the shortest path. This is done by BFS. This search ensures
	 * the traveller is allowed to occupy the squares on the way, or returns the
	 * shortest path to the square regardless of terrain if no traveller is
	 * specified.
	 * 
	 * @param from
	 *            The starting square.
	 * @param to
	 *            The destination.
	 * @param traveller
	 *            The traveller attempting to reach the destination. If
	 *            traveller is set to <code>null</code>, this method will ignore
	 *            terrain and find the shortest path whether it can actually be
	 *            reached or not.
	 * @return The shortest path to the destination or <code>null</code> if no
	 *         such path could be found. When the destination is the current
	 *         square, an empty list is returned.
	 */
	public static List<Direction> shortestPath(Square from, Square to,
			Unit traveller) {
		if (from.equals(to)) {
			return new ArrayList<>();
		}

		List<Node> targets = new ArrayList<>();
		Set<Square> visited = new HashSet<>();
		targets.add(new Node(null, from, null));
		while (!targets.isEmpty()) {
			Node n = targets.remove(0);
			Square s = n.getSquare();
			if (s.equals(to)) {
				return n.getPath();
			}
			visited.add(s);
			addNewTargets(traveller, targets, visited, n, s);
		}
		return null;
	}

	private static void addNewTargets(Unit traveller, List<Node> targets,
			Set<Square> visited, Node n, Square s) {
		for (Direction d : Direction.values()) {
			Square target = s.getSquareAt(d);
			if (!visited.contains(target)
					&& (traveller == null || target
							.isAccessibleTo(traveller))) {
				targets.add(new Node(d, target, n));
			}
		}
	}

	/**
	 * Finds the nearest unit of the given type and returns its location. This
	 * method will perform a breadth first search starting from the given
	 * square.
	 * 
	 * @param type
	 *            The type of unit to search for.
	 * @param currentLocation
	 *            The starting location for the search.
	 * @return The nearest unit of the given type, or <code>null</code> if no
	 *         such unit could be found.
	 */
	public static Unit findNearest(Class<? extends Unit> type,
			Square currentLocation) {
		List<Square> toDo = new ArrayList<>();
		Set<Square> visited = new HashSet<>();

		toDo.add(currentLocation);

		while (!toDo.isEmpty()) {
			Square square = toDo.remove(0);
			Unit unit = findUnit(type, square);
			if (unit != null) {
				return unit;
			}
			visited.add(square);
			for (Direction d : Direction.values()) {
				Square newTarget = square.getSquareAt(d);
				if (!visited.contains(newTarget) && !toDo.contains(newTarget)) {
					toDo.add(newTarget);
				}
			}
		}
		return null;
	}

	/**
	 * Determines whether a square has an occupant of a certain type.
	 * 
	 * @param type
	 *            The type to search for.
	 * @param square
	 *            The square to search.
	 * @return A unit of type T, iff such a unit occupies this square, or
	 *         <code>null</code> of none does.
	 */
	public static Unit findUnit(Class<? extends Unit> type, Square square) {
		for (Unit u : square.getOccupants()) {
			if (type.isInstance(u)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Helper class to keep track of the path.
	 * 
	 * @author Jeroen Roosen 
	 */
	private static final class Node {

		/**
		 * The direction for this node, which is <code>null</code> for the root
		 * node.
		 */
		private final Direction direction;

		/**
		 * The parent node, which is <code>null</code> for the root node.
		 */
		private final Node parent;

		/**
		 * The square associated with this node.
		 */
		private final Square square;

		/**
		 * Creates a new node.
		 * 
		 * @param d
		 *            The direction, which is <code>null</code> for the root
		 *            node.
		 * @param s
		 *            The square.
		 * @param p
		 *            The parent node, which is <code>null</code> for the root
		 *            node.
		 */
		Node(Direction d, Square s, Node p) {
			this.direction = d;
			this.square = s;
			this.parent = p;
		}

		/**
		 * @return The direction for this node, or <code>null</code> if this
		 *         node is a root node.
		 */
		private Direction getDirection() {hit("nl.tudelft.jpacman.npc.ghost.Navigation","getDirection"); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getDirection",1); hit("nl.tudelft.jpacman.npc.ghost.Node","getDirection"); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getDirection",2); hit("nl.tudelft.jpacman.npc.ghost.Node","getDirection",1); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getDirection",3); return direction;}

		/**
		 * @return The square for this node.
		 */
		private Square getSquare() {hit("nl.tudelft.jpacman.npc.ghost.Navigation","getSquare"); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getSquare",1); hit("nl.tudelft.jpacman.npc.ghost.Node","getSquare"); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getSquare",2); hit("nl.tudelft.jpacman.npc.ghost.Node","getSquare",1); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getSquare",3); return square;}

		/**
		 * @return The parent node, or <code>null</code> if this node is a root
		 *         node.
		 */
		private Node getParent() {hit("nl.tudelft.jpacman.npc.ghost.Navigation","getParent"); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getParent",1); hit("nl.tudelft.jpacman.npc.ghost.Node","getParent"); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getParent",2); hit("nl.tudelft.jpacman.npc.ghost.Node","getParent",1); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getParent",3); return parent;}

		/**
		 * Returns the list of values from the root of the tree to this node.
		 * 
		 * @return The list of values from the root of the tree to this node.
		 */
		private List<Direction> getPath() {hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath"); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",2); hit("nl.tudelft.jpacman.npc.ghost.Node","getPath"); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",3); hit("nl.tudelft.jpacman.npc.ghost.Node","getPath",2); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",4); if (getParent() == null) {
				return new ArrayList<>();
			} hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",5); hit("nl.tudelft.jpacman.npc.ghost.Node","getPath",3); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",6); List<Direction> path = parent.getPath(); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",7); hit("nl.tudelft.jpacman.npc.ghost.Node","getPath",4); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",8); path.add(getDirection()); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",9); hit("nl.tudelft.jpacman.npc.ghost.Node","getPath",5); hit("nl.tudelft.jpacman.npc.ghost.Navigation","getPath",10); return path;}
	}
}
