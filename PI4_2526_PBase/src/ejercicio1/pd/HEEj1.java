package ejercicio1.pd;

import java.util.List;

import ejercicio1.Datos1;
import us.lsi.hypergraphs.SimpleHyperEdge;

public record HEEj1(HVEj1 source, List<HVEj1> targets, Integer action) implements SimpleHyperEdge<HVEj1, HEEj1, Integer> {

	@Override
	public Double weight(List<Double> targetsWeight) {							// Funcion Objetivo
		Double valor = (action == 1) ? (double) Datos1.getValoracion(source.indice()) : 0.0;
		return targetsWeight.get(0) + valor;
	}

}
