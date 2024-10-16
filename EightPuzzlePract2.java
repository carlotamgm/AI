package aima.gui.demo.search;

import java.util.Iterator;

import java.util.List;
import java.util.Properties;


import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.util.math.*;

/**
 * @author Ravi Mohan 
 * 
 */

public class EightPuzzlePract2 {

	public static void main(String[] args) {
		String linea = "---------------------------------------------------------------------";
		System.out.format("%s\n",linea);
		System.out.format("||   ||        Nodos Generados      ||                b*           ||\n");
		System.out.format("%s\n",linea);
		System.out.format("||  d|| BFS | IDS | A*h(1) | A*h(2) || BFS | IDS | A*h(1) | A*h(2) ||\n");
		System.out.format("%s\n",linea);
		System.out.format("%s\n",linea);
		
		int[] BFS=new int[2];
		int[] IDS=new int[2];
		int[] misplaced=new int[2];
		int[] manhattan=new int[2];
		
		int prof[] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
		
		int nodosBFS = 0;
		int nodosIDS = 0;
		int nodosMisplaced = 0;
		int nodosManhattan = 0;
		
		double bBFS = 0;
		double bIDS = 0;
		double bMisplaced = 0;
		double bManhattan = 0;
		
		String sinRes = "-----";
		int profundidad;
		for(int j = 0; j < 23; j++)
		{
			profundidad = prof[j];
			for(int i = 0; i < 100; i++) 
			{
				EightPuzzleBoard ini = GenerateInitialEightPuzzleBoard.randomIni();
				EightPuzzleBoard goal = GenerateInitialEightPuzzleBoard.random(profundidad, ini);

				BFS = eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()),ini,goal);
				if(profundidad == BFS[0])
				{
					nodosBFS += BFS[1];
					if(profundidad <= 10)
					{
						IDS = eightPuzzleSearch(new IterativeDeepeningSearch(),ini,goal);
						nodosIDS += IDS[1];
					}
					
					misplaced = eightPuzzleSearch(new AStarSearch(new GraphSearch(),new MisplacedTilleHeuristicFunction()),ini,goal);
					nodosMisplaced += misplaced[1];
					
					manhattan = eightPuzzleSearch(new AStarSearch(new GraphSearch(),new ManhattanHeuristicFunction()),ini,goal);
					nodosManhattan += manhattan[1];
				}
				else
				{
					if(i > 0)
					{
						i--;
					}
				}
			}
			
			nodosBFS = nodosBFS/100;
			nodosMisplaced = nodosMisplaced/100;
			nodosManhattan = nodosManhattan/100;
			
			
			Biseccion b = new Biseccion();
			b.setDepth(profundidad);
			b.setGeneratedNodes(nodosBFS);
			bBFS = b.metodoDeBiseccion(1.00000001, 4.0, 1E-10 );
			b.setGeneratedNodes(nodosMisplaced);
			bMisplaced = b.metodoDeBiseccion(1.00000001, 4.0, 1E-10 );
			b.setGeneratedNodes(nodosManhattan);
			bManhattan = b.metodoDeBiseccion(1.00000001, 4.0, 1E-10 );

			if(profundidad <= 10)
			{
				nodosIDS = nodosIDS/100;
				b.setGeneratedNodes(nodosIDS);
				bIDS = b.metodoDeBiseccion(1.00000001, 4.0, 1E-10 );
				System.out.format("%5s %6s %5s %7s %7s %7.2f %7.2f %7.2f %7.2f\n",profundidad,nodosBFS,nodosIDS,nodosMisplaced,nodosManhattan,bBFS,bIDS,bMisplaced,bManhattan);
			}
			else
			{
				System.out.format("%5s %6s %5s %7s %7s %7.2f %7s %7.2f %7.2f\n",profundidad,nodosBFS,sinRes,nodosMisplaced,nodosManhattan,bBFS,sinRes,bMisplaced,bManhattan);
			}
		}
		
		System.out.format("\n%s\n",linea);
	
	}

	private static int [] eightPuzzleSearch(Search search,  EightPuzzleBoard ini, EightPuzzleBoard finalState) {
		int[] res=new int[2];
		try {
				EightPuzzleGoalTest board = new EightPuzzleGoalTest();
				board.setGoal(finalState);
				Problem problem = new Problem(ini, EightPuzzleFunctionFactory
						.getActionsFunction(), EightPuzzleFunctionFactory
						.getResultFunction(), new EightPuzzleGoalTest());
				SearchAgent agent = new SearchAgent(problem, search);
				
				String pathcostM =agent.getInstrumentation().getProperty("pathCost");
				int depth;
				if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
				else depth = 0;
								
				int generatedNodes;
			
				if (agent.getInstrumentation().getProperty("nodesGenerated")==null) generatedNodes= 0;
				else generatedNodes =
				(int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
					
				
				res[0]=depth;
				res[1]=generatedNodes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}