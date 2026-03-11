package Ejemplo2.ag;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class Main {

	public static void main(String[] args) {
		// 1. Ver y entender los datos del problema. Cargarlos
		DatosSubconjunto.iniDatos("src/Ejemplo2/ag/ejemplo2_1.txt");
		
		// 2 Precondicion. Hacer la clase solución, 
		// 2. Elegir la interfaz cromosoma e implementar
		// 2.1 Función fitness
		// 2.2. Cuidado con los defaults
		
		// 3. Copiar y pegar las constantes del algoritmo
		AlgoritmoAG.ELITISM_RATE  = 0.30;			// ESTAS CONSTANTES ESTAN MUY BIEN, SI EL TXT1 ME DA BIEN Y EL 2 NO, HAY QUE MODIFICAR LAS CONSTANTES
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 5000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		// 4. Crear el algoritmo y ejecutar
		var algG = AlgoritmoAG.of(new CromosomaEj2());
		algG.ejecuta();
		var sol = algG.bestSolution();		// var en nuestro caso es List<Integer> pero no deberia de serlo, deberia de ser s, por eso lo de var (EN PROXIMA CLASE SE VE CON LA CLASE SOLUTION)
		
		// 5. Procesar solución
		System.out.println(sol);

	}

}
