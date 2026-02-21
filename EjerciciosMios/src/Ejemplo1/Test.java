package Ejemplo1;

import java.io.IOException;

import us.lsi.gurobi.GurobiLp;
import us.lsi.solve.AuxGrammar;

public class Test {

	public static void main(String[] args) throws IOException{		// IMPORTANTE PONER throws IOException
		// 1. Ver y entender los datos de entrada.
		// Formalizar a papel primero
		
		// 2. Crear (o copiar) la clase de datos del problema y cargar los datos del problema.
		
		Datos.iniDatos("src/Ejemplo1/ejemplo1_1.txt");
		
		// 3. Escribir el modelo en ".lsi".
		// En archivo .lsi
		
		// 4. Traducir el lsi a lo que entiende Gurobi (.lp) y ejecutar Gurobi.
		AuxGrammar.generate(Datos.class, 
				"src/Ejemplo1/ejemplo1.lsi", 	// COGEMOS EL .lsi
				"src/Ejemplo1/ejemplo1.lp");	// GENERAMOS EL .lp
		
		var sol = GurobiLp.gurobi("src/Ejemplo1/ejemplo1.lp");	// EJECUTAMOS EL .lp
		
		// 5. Procesar la solución.
		if (sol.isEmpty()) {
			System.out.println("Sin solucion");
		} else {
			System.out.println(sol.get());
		}
	}
}
