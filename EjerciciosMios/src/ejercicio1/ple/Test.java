package ejercicio1.ple;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.solve.AuxGrammar;

public class Test {

	public static void main(String[] args) throws IOException {		// Si da error: Error en Parser : El resultado de invocar el m�todo getNumCandidatos con {} es null HAY QUE PONER UN exports EN EL MODULE INFO
		// 1. Ver y entender los datos de entrada.
		// Formalizar a papel primero
		
		// 2. Crear (o copiar) la clase de datos del problema y cargar los datos del problema.

		Locale.setDefault(Locale.of("es", "ES"));
		for(Integer i: List.of(1, 2, 3)) { // indique los tests a realizar
			Datos1.iniDatos("src/ejercicio1/ple/DatosEntrada"+i+".txt");
			// 3. Escribir el modelo en ".lsi".
			// En archivo .lsi
			
			// 4. Traducir el lsi a lo que entiende Gurobi (.lp) y ejecutar Gurobi.
			AuxGrammar.generate(Datos1.class,"src/ejercicio1/ple/Ejercicio1.lsi","src/ejercicio1/ple/Ejercicio1-"+i+".lp");
			
			// 5. Procesar la solución.
			var solucion = GurobiLp.gurobi("src/ejercicio1/ple/Ejercicio1-"+i+".lp");
			if (solucion.isPresent()) {				
				System.out.println(solucion.get().toString((vble, valor)-> valor>0));	// BiCondicional que solo me muestra los que tengan valor mayor que 0
			} else {
				System.out.println("\n\n*****Modelo sin solución****");
			}
		}

	}

}
