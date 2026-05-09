package ejercicio1.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
										// MEJOR VER COMENTARIOS DE PDEj02
import us.lsi.common.Pair;
import ejercicio1.Datos1;
import ejercicio1.pd.HVEj1;


public class PD {
								// Integer action, y Double solucion (el pair es la solucion de Ej01HV, el problema)
	private Map<HVEj1, Pair<Integer, Double>> memoria;
	
	public List<Pair<Integer, Double>> search(HVEj1 inicial) {
		memoria = new HashMap<>();			// Iniciamos Memoria
		pd(inicial);
		return solucion(inicial);
	}
	

	private List<Pair<Integer, Double>> solucion(HVEj1 inicial) {
		List<Pair<Integer, Double>> sol = new ArrayList<>();
		
		// Caso INICIAL												PRIMERA VEZ QUE HACEMOS EL PROBLEMA
		Pair<Integer, Double> sp = memoria.get(inicial);				// Creamos una solucion parcial
		sol.add(sp);													// La añadimos a la solucion
		HVEj1 problema = inicial.neighbors(sp.first()).get(0);			// Pasamos al siguiente problema
		
		// Demas Casos												CONSULTAMOS EL PROBLEMA DE LA MEMORIA YA QUE LO HEMOS RESULETO ANTES
		while (problema.isBaseCase() == false) {		// Mientras no llegemos al final
			sp = memoria.get(problema);
			sol.add(sp);
			problema = problema.neighbors(sp.first()).get(0);
		}
		return sol;	
	}


	private Pair<Integer, Double> pd(HVEj1 problema) {
		Pair<Integer, Double> sp;		// Creamos una solucion parcial
		if (memoria.containsKey(problema)) {								// PROBLEMA YA RESUELTO EN MEMORIA
			return memoria.get(problema);
		}
		
		// SI HEMOS ACABADO de recorrer todos los vertices
		if (problema.isBaseCase()) {		// Si hemos acabado de ver todos los vertices
			if (problema.baseCaseWeight() == null) {		// Si el problema no es valido (baseCaseWeight() == null) devolvemos null
				return null;
			}
			sp = new Pair<>(null,0.0);
			memoria.put(problema, sp);			// Añadimos a memoria la solucion
			return sp;							// Devolvemos la solucion
		}
		
		// SI NO HEMOS ACABADO de recorrer todos los vertices
		Pair<Integer, Double> mejorSp = null;
		Double mejorPeso = -Double.MAX_VALUE;													// Pq queremos Maximizar se pone el -
		Double peso;
		
		for (Integer a: problema.actions()) {							// Recorremos las acciones
			for (HVEj1 nuevoProblema: problema.neighbors(a)) {			// Recorremos los vecinos
				sp = pd(nuevoProblema);
				if (sp == null) {				// Si el problema no es valido, nos saltamos esta solucion
					continue;
				}
				peso = sp.second() + pesoDeLaAccion(problema, a);;
				if (peso > mejorPeso) {						// Ya que queremos Maximizar
					mejorSp = new Pair<>(a, peso);
					mejorPeso = peso;
				}
			}
		}
		if (mejorSp == null) {		// Si no tenemos solucion devolvemos null
			return null;
		}
		memoria.put(problema, mejorSp);
		return mejorSp;
	}


	// Método auxiliar para calcular el peso real de la decisión
	private Double pesoDeLaAccion(HVEj1 problema, Integer accion) {
		if (accion == 1) {
			return (double) Datos1.getValoracion(problema.indice()); 
		} else {
			return 0.0;
		}
	}


	public static void main(String[] args) {
		Datos1.iniDatos("src/ejercicio1/gv/DatosEntrada1.txt");
		
		PD pd = new PD();
		HVEj1 inicial = new HVEj1(0, 0.0, 0, Datos1.getCualidades(), new ArrayList<>());
		
		System.out.println(pd.search(inicial));

	}

}
