package ejercicioAdicional.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ejercicioAdicional.DatosAutovia;
import ejercicioAdicional.pd.HVEjAdicional;
import us.lsi.common.Pair;

public class PDEjAdicional {
					// Integer action, y Double solucion (el pair es la solucion de HVEjAdicional, el problema)
	private Map<HVEjAdicional, Pair<Integer, Double>> memoria;
	
	public List<Pair<Integer, Double>> search(HVEjAdicional inicial) {			// Devuelve lista de soluciones
		memoria = new HashMap<>();					// Iniciamos memoria
		pd(inicial);
		return solucion(inicial);
	}

	private List<Pair<Integer, Double>> solucion(HVEjAdicional inicial) {
		List<Pair<Integer, Double>> sol = new ArrayList<>();
		
		// Caso INICIAL
		Pair<Integer, Double> sp = memoria.get(inicial);
		sol.add(sp);
		HVEjAdicional problema = inicial.neighbors(sp.first()).get(0);
		
		// Demas Casos
		while (problema.isBaseCase() == false) {
			sp = memoria.get(problema);
			sol.add(sp);
			problema = problema.neighbors(sp.first()).get(0);
		}
		return sol;
	}

	private Pair<Integer, Double> pd(HVEjAdicional problema) {
		Pair<Integer, Double> sp;
		if (memoria.containsKey(problema)) {		// PROBLEMA YA RESUELTO EN MEMORIA
			return memoria.get(problema);
		}
		
		// SI HEMOS ACABADO de recorrer todos los vertices
		if (problema.isBaseCase()) {
			if (problema.baseCaseSolution() == null) {
				return null;
			}
			sp = new Pair<>(null, 0.0);
			memoria.put(problema, sp);
			return sp;
		}
		
		// SI NO HEMOS ACABADO de recorrer todos los vertices
		Pair<Integer, Double> mejorSp = null;
		Double mejorPeso = -Double.MAX_VALUE;		// Pq queremos MAXIMIZAR ponemos el -
		Double peso;
		
		for (Integer a: problema.actions()) {
			for (HVEjAdicional siguienteProblema: problema.neighbors(a)) {
				sp = pd(siguienteProblema);
				if (sp == null) {
					continue;
				}
				peso = sp.second() + pesoDelaAccion(problema, a);
				if (peso > mejorPeso) {						// Ya que queremos Maximizar
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

	private Double pesoDelaAccion(HVEjAdicional problema, Integer a) {
		Double peso = 0.0;
		if (a == 1) {
			peso += DatosAutovia.getBeneficio(problema.incdice());			// FUNCION OBJETIVO
		}
		return peso;
	}
	
	public static void main(String[] args) {
		DatosAutovia.iniDatos("src/ejercicioAdicional/DatosEntrada1.txt");
		
		PDEjAdicional pd = new PDEjAdicional();
		
		HVEjAdicional inicial = new HVEjAdicional(0, DatosAutovia.getPresupuestoMax());
		
		System.out.println(pd.search(inicial));

	}

}
