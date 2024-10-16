package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;

import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		EightPuzzleBoard goal = EightPuzzleGoalTest.goal;
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation loc = board.getLocationOf(i);
			XYLocation goal_loc = goal.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(loc, goal_loc);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(XYLocation goal_loc, XYLocation loc) {
		int retVal = -1;
		int xpos = loc.getXCoOrdinate();
		int goal_xpos = goal_loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		int goal_ypos = goal_loc.getYCoOrdinate();
		retVal = Math.abs(goal_xpos-xpos) + Math.abs(goal_ypos - ypos);
		return retVal;
	}
}