package Ejemplo2;

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
		for(Integer i: List.of(1, 2)) { // indique los tests a realizar
			DatosSubconjunto.iniDatos("src/Ejemplo2/ejemplo2_"+i+".txt");
			AuxGrammar.generate(DatosSubconjunto.class,"src/Ejemplo2/ejemplo2.lsi","src/Ejemplo2/ejemplo"+i+".lp");
			
			Optional<GurobiSolution> solucion = GurobiLp.gurobi("src/Ejemplo2/ejemplo"+i+".lp");
			if (solucion.isPresent()) {				
				System.out.println(solucion.get().toString((vble, valor)-> valor>0));
			} else {
				System.out.println("\n\n*****Modelo sin solución****");
			}
		}
	}

}
