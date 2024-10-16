package aima.core.environment.TP6;

import aima.core.search.framework.GoalTest;

/**
 * @author Ravi Mohan
 * 
 */
public class CCLTest implements GoalTest {
	CCLBoard goal = new CCLBoard(new int[] { 1, 1, 1, 1 });

	public boolean isGoalState(Object state) {
		CCLBoard board = (CCLBoard) state;
		return board.equals(goal);
	}
}