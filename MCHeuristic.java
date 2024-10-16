package aima.core.search.informed;

import aima.core.environment.Canibales.CanibalesBoard;
import aima.core.search.framework.HeuristicFunction;

public class MCHeuristic implements HeuristicFunction {
    
    
/* Calcula la heurística en función del estado n.getState()
* y devolver un valor heurístico
*/
    @Override
	public double h(Object state) {
        // Obtén el estado actual
    	CanibalesBoard estado = (CanibalesBoard) state; // Asegúrate de que el estado sea del tipo correcto

        // Obtén la cantidad de misioneros y caníbales en el estado actual
        int misionerosIzq = estado.state[0];
        int canibalesIzq = estado.state[1];

        // Calcula la heurística según la fórmula h = (Mi + Ci) / 2
        double heuristic = (misionerosIzq + canibalesIzq) / 2.0;

        return heuristic;
    }


}

