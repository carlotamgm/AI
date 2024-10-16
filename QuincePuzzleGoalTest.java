package aima.core.environment.quincepuzzle;

import aima.core.search.framework.GoalTest;

/**
 * @author Carlota Moncasi Gosá 839841
 * 
 */

public class QuincePuzzleGoalTest implements GoalTest {
	static QuincePuzzleBoard goal = new QuincePuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5,
			6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });

	public boolean isGoalState(Object state) {
		QuincePuzzleBoard board = (QuincePuzzleBoard) state;
		return board.equals(goal);
	}

	public static void setGoalState(QuincePuzzleBoard board){
		goal = board;
	}
	
	public static QuincePuzzleBoard getGoalBoard() {
		return goal;
	}
}
