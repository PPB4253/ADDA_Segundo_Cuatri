package ej03pdManual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ej03.DatosAlumnos;
import ej03.Ej03HV;
import us.lsi.common.Pair;

public class Ej03PDM {

	// Integer action, y Double solucion (el pair es la solucion de Ej01HV, el problema)
	private Map<Ej03HV, Pair<Integer, Double>> memoria;			// Pq tenemos memoria es map, y usamos los hypervertices e hyperaristas
	
	public List<Pair<Integer, Double>> search(Ej03HV inicial) {
		memoria = new HashMap<>();	// Inicializamos memoria
		pd(inicial);				// Esto me llena memoria
		return solucion(inicial);		// Devolvemos la solucion del problema inicial
	}
	

	private List<Pair<Integer, Double>> solucion(Ej03HV inicial) {
		List<Pair<Integer, Double>> sol = new ArrayList<>();
		
		// Caso INICIAL
		Pair<Integer, Double> sp = memoria.get(inicial);
		sol.add(sp);
		Ej03HV problema = inicial.neighbors(sp.first()).get(0);
		
		// Demas Casos
		while (problema.isBaseCase() == false) {
			sp = memoria.get(problema);
			sol.add(sp);
			problema = problema.neighbors(sp.first()).get(0);
		}
		
		return sol;
	}


	private Pair<Integer, Double> pd(Ej03HV problema) {
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
		Double mejorPeso = Double.MIN_VALUE;			// Pq queremos Maximizar
		Double peso;
		
		for (Integer a: problema.actions()) {			// Aqui no hay cota ni heuristica
			for (Ej03HV nuevoProblema: problema.neighbors(a)) {
				sp = pd(nuevoProblema);
				if (sp == null) {
					continue;		// Si no tenemos solucion nos saltamos este problema, este action
				}
				peso = sp.second() + DatosAlumnos.getAfinidad(problema.indice(), a);
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
		DatosAlumnos.iniDatos("src/ej03/ejemplo3_1.txt");
		
		Ej03PDM pd = new Ej03PDM();
		List<Integer> plazas = new ArrayList<>();
		for (Integer i = 0; i < DatosAlumnos.getNumGrupos(); i++) {
			plazas.add(DatosAlumnos.getTamGrupo());
		}
		Ej03HV inicial = new Ej03HV(0, plazas);
		
		System.out.println(pd.search(inicial));

	}


}
