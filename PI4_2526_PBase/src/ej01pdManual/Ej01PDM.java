package ej01pdManual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Pair;
import ej01.DatosMulticonjunto;
import ej01.Ej01HV;

public class Ej01PDM {
						// Integer action, y Double solucion (el pair es la solucion de Ej01HV, el problema)
	private Map<Ej01HV, Pair<Integer, Double>> memoria;			// Pq tenemos memoria es map, y usamos los hypervertices e hyperaristas
	
	public List<Pair<Integer, Double>> search(Ej01HV inicial) {
		memoria = new HashMap<>();	// Inicializamos memoria
		pd(inicial);				// Esto me llena memoria
		return solucion(inicial);		// Devolvemos la solucion del problema inicial
	}
	

	private List<Pair<Integer, Double>> solucion(Ej01HV inicial) {
		List<Pair<Integer, Double>> sol = new ArrayList<>();
		
		// Caso INICIAL
		Pair<Integer, Double> sp = memoria.get(inicial);
		sol.add(sp);
		Ej01HV problema = inicial.neighbors(sp.first()).get(0);
		
		// Demas Casos
		while (problema.isBaseCase() == false) {
			sp = memoria.get(problema);
			sol.add(sp);
			problema = problema.neighbors(sp.first()).get(0);
		}
		
		return sol;
	}


	private Pair<Integer, Double> pd(Ej01HV problema) {
		Pair<Integer, Double> sp;					// Creamos una solucion parcial
		if (memoria.containsKey(problema)) {
			return memoria.get(problema);
		}
		if (problema.isBaseCase()) {
			if(problema.baseCaseWeight() == null) {
				return null;
			}
			sp = new Pair<>(null, 0.0);
			memoria.put(problema, sp);
			return sp;
		}
		Pair<Integer, Double> mejorSp = null;			// DE AQUI PARA ABAJO ES COMO UN ELSE DEBAJO DEL IF
		Double mejorPeso = Double.MAX_VALUE;			// Pq queremos Minimizar
		Double peso;
		
		for (Integer a: problema.actions()) {			// Aqui no hay cota ni heuristica
			for (Ej01HV nuevoProblema: problema.neighbors(a)) {
				sp = pd(nuevoProblema);
				if (sp == null) {
					continue;		// Si no tenemos solucion nos saltamos este problema, este action
				}
				peso = sp.second() + a;
				if (peso < mejorPeso) {
					mejorSp = new Pair<>(a, peso);
					mejorPeso = peso;
				}
			}
		}
		if (mejorSp == null) {
			return null;
		}
		memoria.put(problema, mejorSp);
		return mejorSp;
		
	}


	public static void main(String[] args) {
		DatosMulticonjunto.iniDatos("src/ej01/ejemplo1_1.txt");
		
		Ej01PDM pd = new Ej01PDM();
		Ej01HV inicial = new Ej01HV(0, DatosMulticonjunto.SUMA);
		
		System.out.println(pd.search(inicial));

	}

}
