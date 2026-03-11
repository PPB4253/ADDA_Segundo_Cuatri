package Ejemplo3.ple;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Test_Todos_TXT {

	public static void main(String[] args) throws IOException {
		// PROGRAMACION LIENAL
		
		Locale.setDefault(Locale.of("es", "ES"));
		for(Integer i: List.of(1, 2, 3)) { // indique los tests a realizar
			DatosAlumnos.iniDatos("src/Ejemplo3/ple/ejemplo3_"+i+".txt");
			AuxGrammar.generate(DatosAlumnos.class,"src/Ejemplo3/ple/ejemplo3.lsi","src/Ejemplo3/ple/ejemplo"+i+".lp");
			
			Optional<GurobiSolution> solucion = GurobiLp.gurobi("src/Ejemplo3/ple/ejemplo"+i+".lp");
			if (solucion.isPresent()) {				
				System.out.println(solucion.get().toString((vble, valor)-> valor>0));
			} else {
				System.out.println("\n\n*****Modelo sin solución****");
			}
		}
	}

}
