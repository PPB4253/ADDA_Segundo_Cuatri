package ejercicio1.ag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements BinaryData<Solucion1> {		// Esto es lo que varia con la clase solucion o usar la lista
	
	public Cromosoma1(String file) {
		Datos1.iniDatos(file);
	}

	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
	public Integer size() {
		// Tantos genes como candidatos
		return Datos1.getNumCandidatos();
	}

	public Double fitnessFunction(List<Integer> value) {
		double goal = valoraciones(value);
		Integer exceso = excedePresupuesto(value);
		Integer cualidadesNo = cuentaCualidadesNo(value);
		Integer incompatibilidades = cuentaIncompatiblilidades(value);
		return goal - (1000*exceso) - (1000*cualidadesNo) -(1000*incompatibilidades); 	// Esto se hace para que los cromosomas malos desaparezcan
	}

	private Integer cuentaIncompatiblilidades(List<Integer> value) {
		Integer incompatibles = 0;
		for (Integer i = 0; i < size(); i++) {
			if (value.get(i) == 0) {
				continue;
			}
			for (Integer k = 0; k < size(); k++) {
				if ((value.get(k) == 1) && (value.get(k) == 1) && (Datos1.getSonIncompatibles(i, k))) {	// ESTAN ELEGIDOS ESOS 2 Y ADEMAS SON INCOMPATIBLES
					incompatibles += 1;
				}
			}
		}
		return incompatibles;
	}

	private Integer cuentaCualidadesNo(List<Integer> value) {
		Set<String> cualidades = new HashSet<>();
		for (Integer i = 0; i < size(); i++) {
			if(value.get(i) == 1) {
				cualidades.addAll(Datos1.getCualidades(i));
			}
		}
		return Datos1.getNumCualidades() - cualidades.size();
	}

	private Integer excedePresupuesto(List<Integer> value) {
		Double presupuesto = 0.0;
		for (Integer i = 0; i < size(); i++) {
			presupuesto += (value.get(i) * Datos1.getSueldoMin(i));
		}
		if (presupuesto <= Datos1.getPresupuestoMax()) {
			return 0;
		}
		return presupuesto.intValue() - Datos1.getPresupuestoMax();
	}

	private double valoraciones(List<Integer> value) {
		Double valoraciones = 0.0;
		for (Integer i = 0; i < size(); i++) {
			valoraciones += (value.get(i) * Datos1.getValoracion(i));
		}
		return valoraciones;
	}

	// MIRAR COMO SE USA ESTO
	public Solucion1 solution(List<Integer> value) {		// Esto es lo que varia con la clase solucion o usar la lista
		return Solucion1.create(value);
	}

}