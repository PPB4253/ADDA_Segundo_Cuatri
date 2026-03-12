package ejercicio3.ag;

import java.util.List;



import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class TestsE3 {

	public static void main(String[] args) {
		// GENETICO
		
		AlgoritmoAG.POPULATION_SIZE = 2000;  // aumentar si no se obtiene un optimo
		StoppingConditionFactory.NUM_GENERATIONS = 20000; // aumentar si no se obtiene un optimo
		
		List.of(1,2,3).forEach(i -> { // indique los tests a realizar
			var cr = new Cromosoma3("src/ejercicio3/ag/DatosEntrada"+i+".txt");
			var ag = AlgoritmoAG.of(cr);
			ag.ejecuta();
			
			System.out.println("================================");
			System.out.println(ag.bestSolution());
			System.out.println("================================");
		});
	}	
}