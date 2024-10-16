package aima.gui.demo.search;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFitnessFunction;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */

public class NQueensLocal {

	private static final int size = 8;
	
	public static void main(String[] args) {

		newNQueensDemo();
	}

	private static void newNQueensDemo() {

		nQueensHillClimbingSearch_Statistics(10000);
		
		nQueensRandomRestartHillClimbing();
		
		nQueensSimulatedAnnealing_Statistics(1000);

		nQueensHillSimulatedAnnealingRestart();
		
		nQueensGeneticAlgorithmSearch();

	}
	
	public static NQueensBoard generateRandomNQ(int size) {
		NQueensBoard board = new NQueensBoard(size);
		for(int c=0; c<size; c++) { //recorro columnas
			board.addQueenAt(new XYLocation(c, (int)(Math.random())*size));//[0,7]
		}
		return board;
	}

	public static Set <NQueensBoard> generateSetNQueensBoard(int size, int listsize) {
		Set <NQueensBoard> set = new HashSet<NQueensBoard>();
		while(set.size() < listsize) { // cuando te devuelve la lista -> for(set<NQueens> board : set)
			set.add(generateRandomNQ(size));
		}
		return set;
	}

	private static float[] nQueensHillClimbingSearch() {
		//[porcentaje de fallos,media de pasos al fallar,éxitos,media de pasos en éxito]
		float res[] = {0,0,0,0};
		

		NQueensBoard tablero = new NQueensBoard(size);
		for(int c=0; c<size;c++)
		{
			tablero.addQueenAt(new XYLocation(c,(int)(Math.random()*size)));
		}
		

		try {
			Problem problem = new Problem(tablero,
					NQueensFunctionFactory.getCActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			HillClimbingSearch search = new HillClimbingSearch(
					new AttackingPairsHeuristic());
			SearchAgent agent = new SearchAgent(problem, search);
			
			if(search.getOutcome().toString().contentEquals("SOLUTION_FOUND"))
			{
				res[2]++;
				res[3] = agent.getActions().size();
			}
			else
			{
				res[0]++;
				res[1] = agent.getActions().size();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	public static void nQueensGeneticAlgorithmSearch() {
		System.out.println("\nNQueensDemo GeneticAlgorithm  -->");
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();

			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(size));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					size,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(size),
					0.15); //probabilidad de mutación

	
			Individual<Integer> mejorIndividuo = ga.geneticAlgorithm(
					population, fitnessFunction, fitnessFunction, 1000L);
			System.out.print("Parámetros iniciales: 		");
			System.out.print("Población: ");
			System.out.print("50 ");
			System.out.print("Probabilidad mutación: ");
			System.out.print("0,15\n");
			System.out.println("Mejor individuo=\n"
					+ fitnessFunction.getBoardForIndividual(mejorIndividuo));

			mejorIndividuo = ga.geneticAlgorithm(population, fitnessFunction,
					fitnessFunction, 0L);

			System.out.println("");
			System.out.println("Tamaño tablero      = " + size);
			System.out.println("Fitness         	= "
					+ fitnessFunction.getValue(mejorIndividuo));
			System.out.println("Es objetivo        = "
					+ fitnessFunction.isGoalState(mejorIndividuo));
			System.out.println("Tamaño de población = " + ga.getPopulationSize());
			System.out.println("Iteraciones       = " + ga.getIterations());
			System.out.println("Tiempo            = "
					+ ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void nQueensHillClimbingSearch_Statistics(int numExperiments)
	{ 
		float[] datos=new float[4];
		for(int i=0; i<numExperiments; i++)
		{
			float leidos[] = {0,0,0,0};
		
			leidos = nQueensHillClimbingSearch();
			
			datos[0] += leidos[0];
			datos[1] += leidos[1];
			datos[2] += leidos[2];
			datos[3] += leidos[3];
		}
		

		System.out.format("NQueensDemo HillClimbing con %s estados iniciales diferentes -->\n",numExperiments);
		System.out.format("Fallos: %.2f\n",datos[0]/100);  /*porcentaje es datos[0]*100/numExperiments*/
		datos[1] = datos[1]/datos[0];
		System.out.format("Coste medio fallos: %.2f\n",datos[1]);
		System.out.format("Exitos: %.2f\n",datos[2]/100);  /*porcentaje es datos[2]*100/numExperiments*/
		datos[3] = datos[3]/datos[2];
		System.out.format("Coste medio Exitos: %.2f\n",datos[3]);
		System.out.format("\n");
	}
	
	private static void nQueensRandomRestartHillClimbing()
	{
		
		float datos[] = {0,0,0,0};
		NQueensBoard tablero = new NQueensBoard(size);
		System.out.format("Search Outcome=SOLUTION_FOUND\nFinal State=\n");
		
		while(datos[2] == 0)
		{
	
			tablero = new NQueensBoard(size);
			for(int c=0; c<size;c++)
			{
				tablero.addQueenAt(new XYLocation(c,(int)(Math.random()*size)));
			}
			
			try {
				Problem problem = new Problem(tablero,
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				HillClimbingSearch search = new HillClimbingSearch(
						new AttackingPairsHeuristic());
				SearchAgent agent = new SearchAgent(problem, search);
				
				if(search.getOutcome().toString().contentEquals("SOLUTION_FOUND"))
				{
					datos[2]++;
					datos[3] += agent.getActions().size();
					
					System.out.println(search.getLastSearchState());
				}
				else
				{
					datos[0]++;
					datos[1] += agent.getActions().size();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		
		System.out.format("Número de intentos: %.2f\n",datos[0]);
		datos[1] = datos[1]/datos[0];
		System.out.format("Coste medio fallos: %.2f\n",datos[1]);
		System.out.format("Coste medio éxito: %.2f\n",datos[3]);
		System.out.format("\n");
		
	}
	
	private static void nQueensSimulatedAnnealing_Statistics(int numExperiments)
	{
		float datos[] = {0,0,0,0};
		NQueensBoard tablero = new NQueensBoard(size);
		
		Scheduler sched=new Scheduler (10,0.1,500);
		
		for(int i=0; i<numExperiments; i++)
		{
			tablero = new NQueensBoard(size);
			for(int c=0; c<size;c++)
			{
				tablero.addQueenAt(new XYLocation(c,(int)(Math.random()*size)));
			}
			
			try {
				Problem problem = new Problem(tablero,
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(),sched);
				SearchAgent agent = new SearchAgent(problem, search);
				
				if(search.getOutcome().toString().contentEquals("SOLUTION_FOUND"))
				{
					datos[2]++;
					datos[3] += agent.getActions().size();
				}
				else
				{
					datos[0]++;
					datos[1] += agent.getActions().size();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.format("NQueens Simulated Annealing con %s estados iniciales diferentes -->\n",numExperiments);
		System.out.format("Parámetros Scheduler: Scheduler (");
		System.out.format("10,0.1,500)\n\n");
		System.out.format("Fallos: %.2f\n",datos[0]/10); /*porcentaje es datos[0]*100/numExperiments*/
		datos[1] = datos[1]/datos[0];
		System.out.format("Coste medio fallos: %.2f\n",datos[1]);
		System.out.format("Exitos: %.2f\n",datos[2]/10);  /*porcentaje es datos[2]*100/numExperiments*/
		datos[3] = datos[3]/datos[2];
		System.out.format("Coste medio Exitos: %.2f\n",datos[3]);
		System.out.format("\n");
	}
	
	private static void nQueensHillSimulatedAnnealingRestart()
	{
		float datos[] = {0,0,0,0};
		NQueensBoard tablero = new NQueensBoard(size);
		
		Scheduler sched=new Scheduler (10,0.1,500);
		
		System.out.format("Search Outcome=SOLUTION_FOUND\nFinal State=\n");
		
		
		while(datos[2] == 0)
		{
			tablero = new NQueensBoard(size);
			for(int c=0; c<size;c++)
			{
				tablero.addQueenAt(new XYLocation(c,(int)(Math.random()*size)));
			}
			
			try {
				Problem problem = new Problem(tablero,
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(),sched);
				SearchAgent agent = new SearchAgent(problem, search);
				
				if(search.getOutcome().toString().contentEquals("SOLUTION_FOUND"))
				{
					datos[2]++;
					datos[3] += agent.getActions().size();
					
					System.out.println(search.getLastSearchState());
				}
				else
				{
					datos[0]++;
					datos[1] += agent.getActions().size();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.format("Número de intentos: %.2f\n",datos[0]);
		datos[1] = datos[1]/datos[0];
		System.out.format("Fallos: %.2f\n",datos[1]);
		System.out.format("Coste Exito: %.2f\n",datos[3]);
		System.out.format("\n");
	}

}