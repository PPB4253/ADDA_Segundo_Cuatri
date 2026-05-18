package ejercicioAdicional.pd;

import java.util.List;

import ejercicioAdicional.DatosAutovia;
import us.lsi.hypergraphs.SimpleHyperEdge;

public record HEEjAdicional(HVEjAdicional source, List<HVEjAdicional> targets, Integer action) implements SimpleHyperEdge<HVEjAdicional, HEEjAdicional, Integer> {

	@Override
	public Double weight(List<Double> targetsWeight) {
		Double peso = 0.0;
		if (this.action == 1) {
			peso += DatosAutovia.getBeneficio(this.source.incdice());					// FUNCION OBJETIVO
		}
		return targetsWeight.get(0) + peso;
	}

}
