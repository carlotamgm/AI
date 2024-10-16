package aima.core.environment.quincepuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction15p implements HeuristicFunction {

	public double h(Object state) {
		QuincePuzzleBoard board = (QuincePuzzleBoard) state;
		QuincePuzzleBoard goal = QuincePuzzleGoalTest.getGoalBoard();
		int retVal = 0;
		for (int i = 1; i < 16; i++) {
			XYLocation loc = board.getLocationOf(i);
			XYLocation goal_loc = goal.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(goal_loc, loc);
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