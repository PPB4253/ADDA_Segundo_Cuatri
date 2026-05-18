package ejercicio2.pd;

import java.util.List;

import ejercicio2.Datos2;
import us.lsi.hypergraphs.SimpleHyperEdge;

public record HEEj02(HVEj02 source, List<HVEj02> targets, Integer action) implements SimpleHyperEdge<HVEj02, HEEj02, Integer> {

	@Override
	public Double weight(List<Double> targetsWeight) {									// PRACTICAMENTE ES EL MISMO METODO QUE EL edge DE VertexEj02Greedy
		// 1. Empezamos con el peso que traen los hijos (vecinos)
		Double totalWeight = targetsWeight.get(0);
		
		// 2. Si la acción fue meter el objeto en un contenedor...
		if (this.action < Datos2.getNumContenedores()) {							// Funcion Objetivo
			// Miramos el primer vecino (solo hay uno en este problema)
			HVEj02 vecino = this.targets.get(0);
			
			// Si en ese vecino el contenedor que acabamos de usar se ha quedado a 0
			if (vecino.capacidadesRestantes().get(this.action) == 0) {					// Si esta completamente lleno
				totalWeight += 1.0; // ¡Sumamos el punto por caja llena!
			}
		}
		return totalWeight;
	}

}
