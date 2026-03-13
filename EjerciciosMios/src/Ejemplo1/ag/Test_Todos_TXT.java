package Ejemplo1.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class Test_Todos_TXT {

	public static void main(String[] args) {
		// GENETICO
		
		AlgoritmoAG.POPULATION_SIZE = 100;  // aumentar si no se obtiene un optimo
		StoppingConditionFactory.NUM_GENERATIONS = 1000; // aumentar si no se obtiene un optimo
		
		List.of(1,2,3).forEach(i -> { // indique los tests a realizar
			var cr = new Cromosoma1("src/Ejemplo1/ag/ejemplo1_"+i+".txt");
			var ag = AlgoritmoAG.of(cr);
			ag.ejecuta();
			
			System.out.println("================================");
			System.out.println(ag.bestSolution());
			System.out.println("================================");
		});

	}

}
