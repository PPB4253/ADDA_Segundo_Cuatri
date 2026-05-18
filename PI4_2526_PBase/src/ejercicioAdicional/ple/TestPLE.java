package ejercicioAdicional.ple;

import java.io.IOException;
import java.util.Locale;



import ejercicioAdicional.DatosAutovia;
import us.lsi.gurobi.GurobiLp;
import us.lsi.solve.AuxGrammar;

public class TestPLE {

	public static void main(String[] args) throws IOException{			// NO MIRAR ESTA MAL
		Locale.setDefault(Locale.of("es", "ES"));
		
		DatosAutovia.iniDatos("src/ejercicioAdicional/DatosEntrada1.txt");
		// 3. Escribir el modelo en ".lsi".
		// En archivo .lsi
		
		// 4. Traducir el lsi a lo que entiende Gurobi (.lp) y ejecutar Gurobi.
		AuxGrammar.generate(DatosAutovia.class,"src/ejercicioAdicional/ple/autovia.lsi","src/ejercicioAdicional/ple/autovia.lp");
		
		// 5. Procesar la solución.
		var solucion = GurobiLp.gurobi("src/ejercicioAdicional/ple/autovia.lp");
		if (solucion.isPresent()) {				
			System.out.println(solucion.get().toString((vble, valor)-> valor>0));	// BiCondicional que solo me muestra las variables (vble) que tengan valor (valor) mayor que 0, ya que solo son las que me intersan, es decir, si el candidato es elegido, si no lo es me la pela
		} else {
			System.out.println("\n\n*****Modelo sin solución****");
		}
	}

}
