package aima.core.search.informed;

import aima.core.agent.Action;
import aima.core.search.framework.EvaluationFunction;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchUtils;
import java.util.Collections;
import java.util.List;

public class IterativeDeepeningAStarSearch extends NodeExpander implements Search {
    private static String PATH_COST = "pathCost";
    private final EvaluationFunction evaluationFunction;
    private double initialLimit;
    private Node solutionNode; // Agrega una variable para mantener el nodo de la soluci�n

    public IterativeDeepeningAStarSearch(EvaluationFunction evaluationFunction) {
        this.evaluationFunction = evaluationFunction;
    }


    // Devuelve lista de acciones desde el nodo inicial hasta el nodo soluci�n
    public List<Action> search(Problem problem) throws Exception {
        clearInstrumentation();
        Node initialNode = new Node(problem.getInitialState());
        initialLimit = evaluationFunction.f(initialNode); // Calcular el l�mite inicial
        double limit = initialLimit;
        
        while (true) {
            double result = searchWithLimit(problem, limit);
            if (result == -1) {
                // Goal no encontrado
                return failure(); 
            }
            if (result == -2) {
                // Goal encontrado,actualiza el nodo de soluci�n
                setPathCost(solutionNode.getPathCost()); // Obtiene el coste del nodo soluci�n
                return SearchUtils.actionsFromNodes(solutionNode.getPathFromRoot());
        
            } else {
                // Cutoff, nodo descartado
                limit = result;
            }
        }
    }


    private double searchWithLimit(Problem problem, double limit) {
        clearInstrumentation();
        Node initialNode = new Node(problem.getInitialState());
        solutionNode = null; // Restablece el nodo de soluci�n en cada iteraci�n
        return recursiveSearch(initialNode, problem, limit);
    }

    private double recursiveSearch(Node node, Problem problem, double limit) {
        double f = evaluationFunction.f(node);
        if (f > limit) {
            return f; // Cutoff
        }
        if (SearchUtils.isGoalState(problem, node)) {
            // Goal encontrado,actualiza el nodo de soluci�n
            solutionNode = node;
            return -2;
        }
        //valor m�nimo empieza en infinito
        double min = Double.POSITIVE_INFINITY; 
        for (Node child : expandNode(node, problem)) {
            double result = recursiveSearch(child, problem, limit);
            if (result == -2) { // Goal encontrado
                return result;
            }
            if (result < min) {
                min = result; // Guarda el m�nimo valor al descartar
            }
        }
        return min;
    }

    private List<Action> failure() {
        return Collections.emptyList();
    }

    public double getPathCost() {
        return metrics.getDouble(PATH_COST);
    }

    public void setPathCost(Double pathCost) {
        metrics.set(PATH_COST, pathCost);
    }


}