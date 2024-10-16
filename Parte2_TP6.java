package aima.gui.demo.search;

import java.util.List;

import aima.core.agent.Action;
import aima.core.environment.Canibales.CanibalesBoard;
import aima.core.environment.Canibales.CanibalesFunctionFactory;
import aima.core.environment.Canibales.CanibalesGoalTest;
import aima.core.environment.quincepuzzle.GenerateInitialQuincePuzzleBoard;
import aima.core.environment.quincepuzzle.ManhattanHeuristicFunction15p;
import aima.core.environment.quincepuzzle.QuincePuzzleBoard;
import aima.core.environment.quincepuzzle.QuincePuzzleFunctionFactory;
import aima.core.environment.quincepuzzle.QuincePuzzleGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.IterativeDeepeningAStarSearch;
import aima.core.search.informed.MCHeuristic;
import aima.core.search.informed.MCHeuristic2;
import aima.core.search.informed.RecursiveBestFirstSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;



/**
 * @author Carlota Moncasi Gos� 839841
 * 
 */
public class Parte2_TP6 {

		public static void main(String[] args) {
			
			CanibalesBoard estadoInicial = new CanibalesBoard(new int[] { 3, 3, 0, 0, 0 });;
			QuincePuzzleBoard inicio15p = generarTablero15p(22);
			
			
			System.out.printf("%10s|%10s|%10s|%10s|%10s|%10s|%10s%n","Problem","Depth","Gener.","Expand","Q.Size","MaxQS","tiempo ms");
			
			HeuristicFunction mcHeuristic = new MCHeuristic(); //h1 de Can�bales
			HeuristicFunction mcHeuristic2 = new MCHeuristic2(); //h2 de Can�bales
			HeuristicFunction ManhattanHeuristicFunction15p = new ManhattanHeuristicFunction15p();
			AStarEvaluationFunction evaluationFunction = new AStarEvaluationFunction(mcHeuristic2);
			AStarEvaluationFunction ef15p = new AStarEvaluationFunction(ManhattanHeuristicFunction15p);
			
			CanibalesyMisionerosSearch(new DepthFirstSearch(new GraphSearch()),estadoInicial,false,"dfs.MC");
			System.out.println("\n");
			CanibalesyMisionerosSearch(new AStarSearch(new GraphSearch(), mcHeuristic2),estadoInicial,false,"A*.MC");
			System.out.println("\n");
			CanibalesyMisionerosSearch(new BreadthFirstSearch(new GraphSearch()),estadoInicial,false,"BFS.MC");
			System.out.println("\n");
			CanibalesyMisionerosSearch(new RecursiveBestFirstSearch(evaluationFunction),estadoInicial, false,"RBFS.MC");
			System.out.println("\n");
			CanibalesyMisionerosSearch(new IterativeDeepeningAStarSearch(evaluationFunction), estadoInicial,false, "IDA*.MC");
			System.out.println("\n");
			
			
 			
			QuincePuzzleSearch(new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction15p()),inicio15p,false,"A*-15p");
			QuincePuzzleSearch( new RecursiveBestFirstSearch(ef15p),inicio15p, false, "RBFS-15p");
			QuincePuzzleSearch( new IterativeDeepeningAStarSearch(ef15p), inicio15p, false, "IDA*-15p");
			
		}
		
		private static QuincePuzzleBoard generarTablero15p(int depth) {

			QuincePuzzleBoard board = QuincePuzzleGoalTest.getGoalBoard();
			QuincePuzzleBoard goal = GenerateInitialQuincePuzzleBoard.random(depth, board);
			
			return goal;
			
		}
		
		private static void CanibalesyMisionerosSearch(Search tipoBusqueda, CanibalesBoard estado, boolean error, String nombreProblema) {
			if(error) {
				System.out.printf("%10s|%11s|%10s|%10s|%10s|%10s%n",nombreProblema,"---","---","---","---");
			}
			else {
				Problem problema = new Problem(estado, CanibalesFunctionFactory
						.getActionsFunction(), CanibalesFunctionFactory
						.getResultFunction(), new CanibalesGoalTest());
				try {
					long ti = System.currentTimeMillis();
					SearchAgent agent = new SearchAgent(problema, tipoBusqueda);
					ti =System.currentTimeMillis() - ti;
		
					String pathcostM =agent.getInstrumentation().getProperty("pathCost");
					int depth, generatedNodes, expandedNodes, queueSize, maxQueueSize;
					if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
					else depth = 0; 
					if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
					else expandedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded")); 
					if (agent.getInstrumentation().getProperty("queueSize")==null) queueSize=0; 
					else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize")); 
					if (agent.getInstrumentation().getProperty("maxQueueSize")==null) maxQueueSize= 0;
					else maxQueueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
					if (agent.getInstrumentation().getProperty("nodesGenerated")==null) generatedNodes= 0; 
					else generatedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
				
				System.out.printf("%10s|%11d|%10d|%10d|%10d|%10d|%10d%n",nombreProblema,depth,generatedNodes,expandedNodes,queueSize,maxQueueSize,ti);
			
				} catch (Exception e) {
			        // Maneja la excepci�n o imprime un mensaje de error
			        e.printStackTrace();
				}
			}
		}
		
		private static void QuincePuzzleSearch(Search busqueda, QuincePuzzleBoard estadoIni, boolean error, String nombre) {
			if(error) {
				System.out.printf("%10s|%11s|%10s|%10s|%10s|%10s%n",nombre,"---","---","---","---");
			}
			else {
				Problem problema = new Problem(estadoIni, QuincePuzzleFunctionFactory
						.getActionsFunction(), QuincePuzzleFunctionFactory
						.getResultFunction(), new QuincePuzzleGoalTest());
				try {
					long ti = System.currentTimeMillis();
					SearchAgent agent = new SearchAgent(problema, busqueda);
					ti =System.currentTimeMillis() - ti;
	
					String pathcostM =agent.getInstrumentation().getProperty("pathCost");
					int depth, generatedNodes, expandedNodes, queueSize, maxQueueSize;
					if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
					else depth = 0; 
					if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
					else expandedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded")); 
					if (agent.getInstrumentation().getProperty("queueSize")==null) queueSize=0; 
					else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize")); 
					if (agent.getInstrumentation().getProperty("maxQueueSize")==null) maxQueueSize= 0;
					else maxQueueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
					if (agent.getInstrumentation().getProperty("nodesGenerated")==null) generatedNodes= 0; 
					else generatedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
				
				System.out.printf("%10s|%11d|%10d|%10d|%10d|%10d|%10d%n",nombre,depth,generatedNodes,expandedNodes,queueSize,maxQueueSize,ti);

				} catch (Exception e) {
			        // Maneja la excepci�n o imprime un mensaje de error
			        e.printStackTrace();
				}
			}
		}
		
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
		
}
