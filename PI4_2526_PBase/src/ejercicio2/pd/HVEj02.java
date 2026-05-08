package ejercicio2.pd;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.util.Pair;

import ejercicio2.Datos2;
import us.lsi.hypergraphs.VirtualHyperVertex;

public record HVEj02(Integer indice, List<Integer> capacidadesRestantes) implements VirtualHyperVertex<HVEj02, HEEj02, Integer, Pair<Double, List<Integer>>> {

	@Override
	public List<Integer> actions() {
		List<Integer> actions = new ArrayList<Integer>();
		
		// PASO 1: ¿Quedan objetos por mirar?
		if (this.indice == Datos2.getNumElementos()) {
			return actions;
		}
		
		// PASO 2: La opción de "Descarte" (Siempre disponible)
		Integer contenedorDescarte = Datos2.getNumContenedores();
		actions.add(contenedorDescarte);
		
		// PASO 3: Evaluar las cajas (contenedores) una a una
		Integer tamElemento = Datos2.getTamElemento(this.indice);
		for (Integer i = 0; i < Datos2.getNumContenedores(); i++) {
			Integer capacidadContenedor = this.capacidadesRestantes.get(i);							// CAPACIDAD DINAMICA
			if (Datos2.getPuedeUbicarse(this.indice, i) && tamElemento <= capacidadContenedor) {
				actions.add(i);
			}
		}
		return actions;
	}

	@Override
	public Boolean isBaseCase() {
		return this.indice == Datos2.getNumElementos();
	}

	@Override
	public Double baseCaseWeight() {
		// Si es un caso base válido (hemos llegado al final del índice), el peso es 0
		return this.isBaseCase() ? 0.0 : null;				// 0.0 problema valido, null problema no valido, basicamente si recorremos todos los elementos, pues es VALIDO
	}

	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public Pair<Double, List<Integer>> baseCaseSolution() {					// Inicializamos la solucion
		return new Pair<Double, List<Integer>>(0.0, new ArrayList<>());
	}

	@Override
	public Pair<Double, List<Integer>> solution(Integer a, List<Pair<Double, List<Integer>>> solutions) {
		Double weight = 0.0;
		if (a < Datos2.getNumContenedores()) {
			HVEj02 vecino = this.neighbors(a).get(0);
			if (vecino.capacidadesRestantes().get(a) == 0) {				// Si esta completamente lleno
				weight = 1.0;
			}
		}
		
		Double nuevoPeso = solutions.get(0).getFirst() + weight;
	    List<Integer> nuevasActions = new ArrayList<>(solutions.get(0).getSecond());
	    nuevasActions.add(0, a); // IMPORTANTE: Al principio (0) PARA QUE LA LISTA NO SALGA INVERTIDA
	    return new Pair<>(nuevoPeso, nuevasActions);
	}

	@Override
	public List<HVEj02> neighbors(Integer a) {
		Integer nuevoIndice = this.indice + 1;
		List<Integer> nuevaCapacidadesRestante = new ArrayList<>(this.capacidadesRestantes);
		if (a < Datos2.getNumContenedores()) {
			Integer tamElementos = Datos2.getTamElemento(this.indice);
			Integer capacidadRestante = nuevaCapacidadesRestante.get(a);
			
			nuevaCapacidadesRestante.set(a, capacidadRestante - tamElementos);
		}
		return List.of(new HVEj02(nuevoIndice, nuevaCapacidadesRestante));
	}

	@Override
	public HEEj02 edge(Integer a) {
		return new HEEj02(this, this.neighbors(a), a);
	}

}
