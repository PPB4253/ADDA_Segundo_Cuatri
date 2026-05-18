package ejercicioAdicional.pd;

import java.util.ArrayList;
import java.util.List;

import ejercicioAdicional.DatosAutovia;
import us.lsi.common.Pair;
import us.lsi.hypergraphs.VirtualHyperVertex;

public record HVEjAdicional(Integer incdice, Double presupuestoRestante) implements VirtualHyperVertex<HVEjAdicional, HEEjAdicional, Integer, Pair<Double, List<Integer>>> {

	@Override
	public List<Integer> actions() {
		List<Integer> actions = new ArrayList<>();
		
		// PASO 1: ¿Quedan ubicaciones por mirar?
		if (this.incdice == DatosAutovia.getNumAreasServicio()) {
			return actions;
		}
		
		// PASO 2: Evaluamos las ubicaciones
		actions.add(0);
		
		Double costeActual = DatosAutovia.getCosteConstruccion(this.incdice);
		if (costeActual <= presupuestoRestante) {									// R1: No podemos superar el presupuesto maximo
			actions.add(1);
		}
		return actions;
	}

	@Override
	public Boolean isBaseCase() {
		return this.incdice == DatosAutovia.getNumAreasServicio();
	}

	@Override
	public Double baseCaseWeight() {
		Double res = null;
		if (presupuestoRestante >= 0) {
			res = 0.0;
		}
		return res;
	}

	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public Pair<Double, List<Integer>> baseCaseSolution() {
		return new Pair<Double, List<Integer>>(0.0, new ArrayList<>());
	}

	@Override
	public Pair<Double, List<Integer>> solution(Integer a, List<Pair<Double, List<Integer>>> solutions) {
		Double peso = 0.0;
		if (a == 1) {											// FUNCION OBJETIVO
			peso += DatosAutovia.getBeneficio(this.incdice);
		}
		
		Double nuevoPeso = solutions.get(0).first() + peso;
		List<Integer> nuevasActions = new ArrayList<>(solutions.get(0).second());
		nuevasActions.add(0, a);
		return new Pair<>(nuevoPeso, nuevasActions);
	}

	@Override
	public List<HVEjAdicional> neighbors(Integer a) {
		Integer nuevoIndice = this.incdice + 1;
		Double nuevoPresupuestoRestante = this.presupuestoRestante;
		
		if (a == 1) {
			nuevoPresupuestoRestante -= DatosAutovia.getCosteConstruccion(this.incdice);
		}
		
		return List.of(new HVEjAdicional(nuevoIndice, nuevoPresupuestoRestante));
	}

	@Override
	public HEEjAdicional edge(Integer a) {
		return new HEEjAdicional(this, this.neighbors(a), a);
	}

}
