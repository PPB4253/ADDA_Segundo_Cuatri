package ejercicio2.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ejercicio2.Datos2;
import ejercicio2.pd.HVEj02;
import us.lsi.common.Pair;

public class PDEj02 {
					// Integer action, y Double solucion (el pair es la solucion de Ej01HV, el problema)
	private Map<HVEj02, Pair<Integer, Double>> memoria;
	
	public List<Pair<Integer, Double>> search(HVEj02 inicial) {
		memoria = new HashMap<>();			// Iniciamos Memoria
		pd(inicial);
		return solucion(inicial);
	}
	

	private List<Pair<Integer, Double>> solucion(HVEj02 inicial) {
		List<Pair<Integer, Double>> sol = new ArrayList<>();
		
		// Caso INICIAL
		Pair<Integer, Double> sp = memoria.get(inicial);				// Creamos una solucion parcial
		sol.add(sp);													// La añadimos a la solucion
		HVEj02 problema = inicial.neighbors(sp.first()).get(0);			// Pasamos al siguiente problema
		
		// Demas Casos
		while (problema.isBaseCase() == false) {		// Mientras no llegemos al final
			sp = memoria.get(problema);
			sol.add(sp);
			problema = problema.neighbors(sp.first()).get(0);
		}
		return sol;
	}


	private Pair<Integer, Double> pd(HVEj02 problema) {
		Pair<Integer, Double> sp;		// Creamos una solucion parcial
		if (memoria.containsKey(problema)) {								// PROBLEMA YA RESUELTO EN MEMORIA
			return memoria.get(problema);		// Devolvemos la solucion del problema
		}
		
		// SI HEMOS ACABADO de recorrer todos los vertices
		if (problema.isBaseCase()) {						// Si hemos acabado de recorrer todos los vertices
			if (problema.baseCaseWeight() == null) {		// Si el problema no tiene solucion
				return null;		// Devolvemos null
			}
			sp = new Pair<>(null, 0.0);						// Esto significa que HEMOS RECORRIDO TODAS LAS ACTIONS (null) y EL PROBLEMA TIENE SOUCION	(0.0)
			memoria.put(problema, sp);			// Añadimos a memoria la solucion
			return sp;							// Devolvemos la solucion
		}
		
		// SI NO HEMOS ACABADO de recorrer todos los vertices
		Pair<Integer, Double> mejorSp = null;
		Double mejorPeso = -Double.MAX_VALUE;		// Pq queremos Maximizar se pone el -
		Double peso;
		
		for (Integer a: problema.actions()) {							// Recorremos las acciones
			for (HVEj02 siguienteProblema: problema.neighbors(a)) {		// Recorremos los vecinos
				sp = pd(siguienteProblema);
				if (sp == null) {				// Si el problema no es valido, nos saltamos esta solucion
					continue;
				}
				peso = sp.second() + pesoDelaAccion(problema, a);		// Obtenemos el nuevo peso
				if (peso > mejorPeso) {					// Ya que queremos Maximizar
					mejorSp = new Pair<>(a, peso);		// Creamos la nueva Solucion Parcial
					mejorPeso = peso;				// Actualizamos el mejor peso
				}
			}
		}
		if (mejorSp == null) {		// Si no tenemos solucion devolvemos null
			return null;
		}
		memoria.put(problema, mejorSp);		// metemos en la memoria el problema con su mejor solucion
		return mejorSp;		// Devolvemos la mejor solucion
	}


	private Double pesoDelaAccion(HVEj02 problema, Integer a) {
		// 1. Si la acción es "descartar" (usar el contenedor basura)
		if (a.equals(Datos2.getNumContenedores())) {
			return 0.0;
		}
		
		// 2. Calculamos cuánto espacio le quedará al contenedor elegido
		Integer tamElemento = Datos2.getTamElemento(problema.indice());
		Integer capacidadActual = problema.capacidadesRestantes().get(a);
		Integer capacidadFinal = capacidadActual - tamElemento;
		
		// 3. Si al meter el elemento el contenedor se llena por completo (capacidad 0)
		if (capacidadFinal == 0) {
			return 1.0; // ¡Ganamos un punto porque hemos llenado un contenedor!
		} else {
			return 0.0; // No se ha llenado del todo, no ganamos puntos en este paso
		}
	}


	public static void main(String[] args) {
		Datos2.iniDatos("src/ejercicio2/gv/DatosEntrada1.txt");
		
		PDEj02 pd = new PDEj02();
		List<Integer> capacidadesRestantesIniciales = new ArrayList<>();
		for (Integer i = 0; i < Datos2.getNumContenedores(); i++) {
			capacidadesRestantesIniciales.add(Datos2.getTamContenedor(i));
		}
		HVEj02 inicial = new HVEj02(0, capacidadesRestantesIniciales);
		
		System.out.println(pd.search(inicial));

	}

}
