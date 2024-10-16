agent.getActions().size(); //número de pasos
public static NQueensBoard generateRandomNQ(int size) {
	NQueens board = new NQueensBoard(size);
	for(int c=0; c<size; c++) { //recorro columnas
		board.addQueenAt(new XYLocation(c, (int)(Math.random)*size);//[0,7]
	}
	return board;
}

public static Set <NQueensBoard> generateSetNQueensBoard(int size, int listsize) {
	Set <NQueensBoard> set = new HashSet<NQueensBoard>();
	while(set.size) { // cuando te devuelve la lista -> for(set<NQueens> board : set)
		set.add(generateRandomNQ(size));
	}
	return set;
}

search.getOutCome().toString().contentEquals("SOLUTION_FOUND");3

cambiar por 56 sucesores:
getISearch... -> getCSearch...

MISIONEROS Y CANÍBALES:
h1=((Mi+Ci)/2)
h2 = si orilla izq(1), si dcha(0): 2*(Mi+Ci)-f(orilla)

Como mucho hasta 20, usar generarAProfundidad() en 15puzzle

TP-6:
new RBFS(new EvaluationFunction(argumentos) //recursivo IDS->IDA*, búsqueda limitada por valor -> por función, hay que devolver el valor mínimo al que llega (clase CutOut ActionValue para recuperar valor)
