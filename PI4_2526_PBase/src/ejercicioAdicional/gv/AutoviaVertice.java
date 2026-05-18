package ejercicioAdicional.gv;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import ejercicioAdicional.DatosAutovia;
import us.lsi.graphs.virtual.VirtualVertex;

public record AutoviaVertice(Integer indice, Double presupuestoRestante) implements VirtualVertex<AutoviaVertice, AutoviaEdge, Integer> {

	@Override
	public List<Integer> actions() {									// Es variable Binaria (como el ejercicio 1), por eso elegimos entre 0 y 1
		List<Integer> actions = new ArrayList<>();
		
		// PASO 1: ¿Quedan ubicaciones por mirar?
		if (this.indice == DatosAutovia.getNumAreasServicio()) {
			return actions;
		}
		
		// PASO 2: Evaluamos las ubicaciones
		actions.add(0);
		
		Double costeActual = DatosAutovia.getCosteConstruccion(this.indice);
		if (costeActual <= this.presupuestoRestante) {							// R1: No superamos el presupuesto maximo
			actions.add(1);
		}
		return actions;
	}

	@Override
	public AutoviaVertice neighbor(Integer a) {
		Integer nuevoIndice = this.indice + 1;
		Double nuevoPresupuestoRestante = this.presupuestoRestante;
		
		if (a == 1) {
			nuevoPresupuestoRestante = nuevoPresupuestoRestante - DatosAutovia.getCosteConstruccion(this.indice);
		}
		
		return new AutoviaVertice(nuevoIndice, nuevoPresupuestoRestante);
	}

	@Override
	public AutoviaEdge edge(Integer a) {
		Double peso = 0.0;
		if (a == 1) {
			peso = DatosAutovia.getBeneficio(this.indice);				// El peso de la arista es la FUNCION OBJETIVO
		}
		return  new AutoviaEdge(this, this.neighbor(a), a, peso);
	}
	
	public Boolean goal() {
		return this.indice == DatosAutovia.getNumAreasServicio();
	}
	
	public Boolean goalHasSolution() {
		return this.presupuestoRestante >= 0.0;
	}

	public static Double heuristica(AutoviaVertice v, Predicate<AutoviaVertice> goal, AutoviaVertice end) {	
		// 1. CASO BASE: Si ya no nos queda dinero o hemos llegado al final, el beneficio extra futuro es 0.0
		if (v.indice == DatosAutovia.getNumAreasServicio() || v.presupuestoRestante <= 0.0) {
			return 0.0;
		}
		
		// 2. Recopilamos las ubicaciones que nos quedan por mirar
		List<Integer> ubicacionesPendientes = new ArrayList<>();
		for (Integer i = v.indice; i < DatosAutovia.getNumAreasServicio(); i++) {
			ubicacionesPendientes.add(i);
		}
		
		// 3. ORDENACIÓN VORAZ: Ordenamos de mayor a menor ratio (Beneficio / Coste)
		ubicacionesPendientes.sort(Comparator.comparingDouble((Integer i) -> DatosAutovia.getBeneficio(i) / DatosAutovia.getCosteConstruccion(i)).reversed());
		
		// 4. CALCULAMOS EL FUTURO IDEAL
		Double beneficioEstimado = 0.0;
		Double presupuestoRestante = v.presupuestoRestante();
		
		for (Integer i: ubicacionesPendientes) {
			Double coste = DatosAutovia.getCosteConstruccion(i);
			Double beneficio = DatosAutovia.getBeneficio(i);
			
			if (coste <= presupuestoRestante) {
				beneficioEstimado += beneficio;
				presupuestoRestante -= coste;
			} else {														// Esto se hace para poder llegar al final y para que A* vea el beneficio total maximo de este camino, aunque no construyamos medio edificio ni na
				Double cachoRestante = presupuestoRestante / coste;
				beneficioEstimado += beneficio * cachoRestante;
				break;
			}
		}
		return beneficioEstimado;
	}

}
