package aima.core.environment.quincepuzzle;

import java.util.HashSet;
//import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @author JAB
 * 
 */

public class GenerateInitialQuincePuzzleBoard {

	
	//devuelve un estado aleatorio de profundidad depth desde 
	//initial State. Ojo, no hay garantia de que no haya una solucion mas corta.
	public static QuincePuzzleBoard random(int depth, QuincePuzzleBoard initialState){
		 Set<Object> statesGenerated = new HashSet<Object>();

		Random r = new Random();
		QuincePuzzleBoard board = new QuincePuzzleBoard(initialState);
		QuincePuzzleBoard newBoard = new QuincePuzzleBoard(board);
		
		statesGenerated.add(new QuincePuzzleBoard(board));
		int pasos = 1;
		int limit = 100000; //OJO podria no encontrar solucion de esa profundidad. Ponemos un limite de iteraciones
		int cont = 1;
		while (pasos<=depth && cont < limit) {
			newBoard = new QuincePuzzleBoard(board);
			int th = r.nextInt(4);
			if (th == 0) {
				newBoard.moveGapUp();
				if   (!statesGenerated.contains(newBoard)){
					 statesGenerated.add(new QuincePuzzleBoard(newBoard));
					 board.moveGapUp();
					 pasos++;
				}
			}
			if (th == 1) {
				newBoard.moveGapDown();
				if   (!statesGenerated.contains(newBoard)){
					 statesGenerated.add(new QuincePuzzleBoard(newBoard));
					 board.moveGapDown();
					 pasos++;
				}
			}
			if (th == 2) {
				newBoard.moveGapLeft();
				if   (!statesGenerated.contains(newBoard)){
					 statesGenerated.add(new QuincePuzzleBoard(newBoard));
					 board.moveGapLeft();
					 pasos++;
				}			
			}
			if (th == 3) {
				newBoard.moveGapRight();
				if   (!statesGenerated.contains(newBoard)){
					 statesGenerated.add(new QuincePuzzleBoard(newBoard));
					 board.moveGapRight();
					 pasos++;
				}				
			}
			
		cont++;	
		}	

		return(board);
	}

}
