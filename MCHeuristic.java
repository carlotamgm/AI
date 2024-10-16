package aima.core.search.informed;

import aima.core.environment.Canibales.CanibalesBoard;
import aima.core.search.framework.HeuristicFunction;

public class MCHeuristic implements HeuristicFunction {
    
    
/* Calcula la heur�stica en funci�n del estado n.getState()
* y devolver un valor heur�stico
*/
    @Override
	public double h(Object state) {
        // Obt�n el estado actual
    	CanibalesBoard estado = (CanibalesBoard) state; // Aseg�rate de que el estado sea del tipo correcto

        // Obt�n la cantidad de misioneros y can�bales en el estado actual
        int misionerosIzq = estado.state[0];
        int canibalesIzq = estado.state[1];

        // Calcula la heur�stica seg�n la f�rmula h = (Mi + Ci) / 2
        double heuristic = (misionerosIzq + canibalesIzq) / 2.0;

        return heuristic;
    }


}

