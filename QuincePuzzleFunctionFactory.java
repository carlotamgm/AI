package aima.core.environment.quincepuzzle;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class QuincePuzzleFunctionFactory {
	private static ActionsFunction _actionsFunction = null; 
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new EPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new EPResultFunction();
		}
		return _resultFunction;
	}

	private static class EPActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			QuincePuzzleBoard board = (QuincePuzzleBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(QuincePuzzleBoard.UP)) {
				actions.add(QuincePuzzleBoard.UP);
			}
			if (board.canMoveGap(QuincePuzzleBoard.DOWN)) {
				actions.add(QuincePuzzleBoard.DOWN);
			}
			if (board.canMoveGap(QuincePuzzleBoard.LEFT)) {
				actions.add(QuincePuzzleBoard.LEFT);
			}
			if (board.canMoveGap(QuincePuzzleBoard.RIGHT)) {
				actions.add(QuincePuzzleBoard.RIGHT);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			QuincePuzzleBoard board = (QuincePuzzleBoard) s;

			if (QuincePuzzleBoard.UP.equals(a)
					&& board.canMoveGap(QuincePuzzleBoard.UP)) {
				QuincePuzzleBoard newBoard = new QuincePuzzleBoard(board); 
				newBoard.moveGapUp();
				return newBoard;
			} else if (QuincePuzzleBoard.DOWN.equals(a)
					&& board.canMoveGap(QuincePuzzleBoard.DOWN)) {
				QuincePuzzleBoard newBoard = new QuincePuzzleBoard(board);
				newBoard.moveGapDown();
				return newBoard;
			} else if (QuincePuzzleBoard.LEFT.equals(a)
					&& board.canMoveGap(QuincePuzzleBoard.LEFT)) {
				QuincePuzzleBoard newBoard = new QuincePuzzleBoard(board);
				newBoard.moveGapLeft();
				return newBoard;
			} else if (QuincePuzzleBoard.RIGHT.equals(a)
					&& board.canMoveGap(QuincePuzzleBoard.RIGHT)) {
				QuincePuzzleBoard newBoard = new QuincePuzzleBoard(board);
				newBoard.moveGapRight();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}