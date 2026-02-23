package Ejemplo3;

import java.io.IOException;

import us.lsi.gurobi.GurobiLp;
import us.lsi.solve.AuxGrammar;

public class Test {
	
	// Si da error: Error en Parser : El resultado de invocar el m�todo getNumCandidatos con {} es null HAY QUE PONER UN exports EN EL MODULE INFO
	public static void main(String[] args) throws IOException{		// IMPORTANTE PONER throws IOException
		// 1. Ver y entender los datos de entrada.
		// Formalizar a papel primero
		
		// 2. Crear (o copiar) la clase de datos del problema y cargar los datos del problema.
		
		DatosAlumnos.iniDatos("src/Ejemplo3/ejemplo3_1.txt");
		
		// 3. Escribir el modelo en ".lsi".
		// En archivo .lsi
		
		// 4. Traducir el lsi a lo que entiende Gurobi (.lp) y ejecutar Gurobi.
		AuxGrammar.generate(DatosAlumnos.class, 
				"src/Ejemplo3/ejemplo3.lsi", 	// COGEMOS EL .lsi
				"src/Ejemplo3/ejemplo3.lp");	// GENERAMOS EL .lp
		
		var sol = GurobiLp.gurobi("src/Ejemplo3/ejemplo3.lp");	// EJECUTAMOS EL .lp		TODOS LOS METODOS DE GUROBI HACEN LO MISMO PERO .gurobi() MAS ELEGANTE
		
		// 5. Procesar la solución.
		if (sol.isEmpty()) {
			System.out.println("Sin solucion");
		} else {
			// System.out.println(sol.get().toString((s, d)->d>.0));  	MUESTRA SOLO LAS VARIABLES MAYORES QUE 0
			System.out.println(sol.get().toString((s, d)->d>.0));
		}
	}
}
