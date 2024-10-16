package aima.core.environment.Canibales;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class CanibalesFunctionFactory {
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
			CanibalesBoard board = (CanibalesBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(CanibalesBoard.Mover1C)) {
				actions.add(CanibalesBoard.Mover1C);
			}
			if (board.canMoveGap(CanibalesBoard.Mover2C)) {
				actions.add(CanibalesBoard.Mover2C);
			}
			if (board.canMoveGap(CanibalesBoard.Mover1M)) {
				actions.add(CanibalesBoard.Mover1M);
			}
			if (board.canMoveGap(CanibalesBoard.Mover2M)) {
				actions.add(CanibalesBoard.Mover2M);
			}
			if (board.canMoveGap(CanibalesBoard.Mover1M1C)) {
				actions.add(CanibalesBoard.Mover1M1C);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CanibalesBoard board = (CanibalesBoard) s;

			if (CanibalesBoard.Mover1C.equals(a)
					&& board.canMoveGap(CanibalesBoard.Mover1C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover1C();
				return newBoard;
			} else if (CanibalesBoard.Mover2C.equals(a)
					&& board.canMoveGap(CanibalesBoard.Mover2C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover2C();
				return newBoard;
			} else if (CanibalesBoard.Mover1M.equals(a)
					&& board.canMoveGap(CanibalesBoard.Mover1M)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover1M();
				return newBoard;
			} else if (CanibalesBoard.Mover2M.equals(a)
					&& board.canMoveGap(CanibalesBoard.Mover2M)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover2M();
				return newBoard;
			} else if (CanibalesBoard.Mover1M1C.equals(a)
					&& board.canMoveGap(CanibalesBoard.Mover1M1C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover1M1C();
				return newBoard;
				
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}