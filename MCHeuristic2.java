package aima.core.search.informed;

import aima.core.environment.Canibales.CanibalesBoard;
import aima.core.search.framework.HeuristicFunction;

public class MCHeuristic2 implements HeuristicFunction {
    
    
/* Calcula la heur�stica en funci�n del estado n.getState()
* y devolver un valor heur�stico
*/
    @Override
	public double h(Object state) {
    	
    	int misioneros, canibales, f_orilla;
        // Obt�n el estado actual
    	CanibalesBoard estado = (CanibalesBoard) state; // Aseg�rate de que el estado sea del tipo correcto
    	misioneros = estado.state[0];
        canibales = estado.state[1];
    	
    	if(estado.state[2] == 0) { //orilla izquierda
    	
            f_orilla = 0;
    		
    	} else  { // if(estado.state[2] == 1) ->  orilla dcha   
 
            f_orilla = 1;
    	}

        // Calcula la heur�stica seg�n la f�rmula h2 = 2*(Mi+Ci)-f(orilla)	
        double heuristic =   2*(misioneros + canibales) - f_orilla;

        return heuristic;
    }


}

