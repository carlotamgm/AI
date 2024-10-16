package aima.gui.demo.search;

import java.util.Iterator;


import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.Canibales.CanibalesBoard;
import aima.core.environment.Canibales.CanibalesFunctionFactory;
import aima.core.environment.Canibales.CanibalesGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;

/**
 * @author Ravi Mohan
 * 
 */

public class Canibales {
	static CanibalesBoard estadoInicial = new CanibalesBoard(
			new int[] { 3, 3, 0, 0, 0 });;

	
	public static void executeActions(List<Action> actions, Problem problem) {
		Object initialState = problem.getInitialState();
		ResultFunction resultFunction = problem.getResultFunction();
		Object state = initialState;
		System.out.println(state);
		for (Action action : actions) {
		 System.out.printf(action.toString());
		 state = resultFunction.result(state, action);
		 System.out.println(state);
		}
	}

	public static void main(String[] args) {

		
		
		canibalesSearch(new BreadthFirstSearch(new GraphSearch()), estadoInicial, "BFS" );
		canibalesSearch(new DepthLimitedSearch(11), estadoInicial, "DLS(11)" );
		canibalesSearch(new IterativeDeepeningSearch(), estadoInicial, "IDLS" );
}
	private static void canibalesSearch(Search tipoBusqueda, CanibalesBoard estado, String nombreProblema) {
		try {
				
				Problem problem = new Problem(estado, CanibalesFunctionFactory
						.getActionsFunction(), CanibalesFunctionFactory
						.getResultFunction(), new CanibalesGoalTest());
				
				long ti = System.currentTimeMillis();
				SearchAgent agent = new SearchAgent(problem, tipoBusqueda);
				ti = System.currentTimeMillis() - ti;
				int depth, expandedNodes;
				String pathcostM =agent.getInstrumentation().getProperty("pathCost");
				if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
				else depth = 0;
				if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
				else expandedNodes =
				(int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));

				System.out.printf("Misioneros y canibales %s -->\npathCost: %d\nnodesExpanded: %d\nTiempo: %dms\n\nSOLUCIÓN:\nGOAL STATE\n", nombreProblema, depth,
						expandedNodes, ti);
				System.out.printf("RIBERA-IZQ			           --RIO-- BOTE M M M C C C  RIBERA-DCH\n" + "CAMINO ENCONTRADO\n"+ 
						"ESTADO INICIAL 			    RIBERA-IZQ M M M C C C BOTE --RIO--                   RIBERA-DCH			\n    		");
				executeActions(agent.getActions(),problem);
				
			} catch (Exception e) {
					e.printStackTrace();
			}
	}


}