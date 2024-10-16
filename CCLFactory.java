package aima.core.environment.TP6;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class CCLFactory {
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
			CCLBoard board = (CCLBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(CCLBoard.MoverCabra)) {
				actions.add(CCLBoard.MoverCabra);
			}
			if (board.canMoveGap(CCLBoard.MoverLobo)) {
				actions.add(CCLBoard.MoverLobo);
			}
			if (board.canMoveGap(CCLBoard.MoverCol)) {
				actions.add(CCLBoard.MoverCol);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CCLBoard board = (CCLBoard) s;

			if (CCLBoard.MoverCabra.equals(a)
					&& board.canMoveGap(CCLBoard.MoverCabra)) {
				CCLBoard newBoard = new CCLBoard(board);
				newBoard.moverCabra();
				return newBoard;
			} else if (CCLBoard.MoverCol.equals(a)
					&& board.canMoveGap(CCLBoard.MoverCol)) {
				CCLBoard newBoard = new CCLBoard(board);
				newBoard.moverCol();
				return newBoard;
			} else if (CCLBoard.MoverLobo.equals(a)
					&& board.canMoveGap(CCLBoard.MoverLobo)) {
				CCLBoard newBoard = new CCLBoard(board);
				newBoard.moverLobo();
				return newBoard;	
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}