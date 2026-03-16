package ejercicio1.ag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1Estudio implements BinaryData<List<Integer>>{
	
	public Cromosoma1Estudio(String file) {
		Datos1.iniDatos(file);
	}

	@Override
	public Integer size() {
		return Datos1.getNumCandidatos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = valoraciones(value);
		Double presupuesto = presupuestoo(value);
		Double incompatibles = incompatibles(value);
		Double cualidades = cualidades(value);
		
		return goal - (1000*presupuesto) - (1000*incompatibles) - (1000*cualidades);
	}

	private Double cualidades(List<Integer> value) {
		Set<String> cualidades = new HashSet<>();
		for (Integer i = 0; i < size(); i++) {
			if (value.get(i) == 1) {
				cualidades.addAll(Datos1.getCualidades(i));
			}
		}
		return (double) (Datos1.getNumCualidades() - cualidades.size());
	}

	private Double incompatibles(List<Integer> value) {
		Double incompatibles = 0.0;
		for (Integer i = 0; i < size(); i++) {
			if (value.get(i) == 0) {				// SE PUEDE PONER SOLO if (value.get(i) == 1) Y CONTINUAR CON EL BUCLE ABAJO
				continue;
			}
			for (Integer k = 0; k < size(); k++) {
				if (value.get(i) == 1 && value.get(k) == 1 && Datos1.getSonIncompatibles(i,k)) {
					incompatibles += 1;
				}
			}
		}
		return incompatibles;
	}

	private Double presupuestoo(List<Integer> value) {
		Double presupuesto = 0.0;
		for (Integer i = 0; i < size(); i++) {
			presupuesto += (value.get(i) * Datos1.getSueldoMin(i));
		}
		if (presupuesto <= Datos1.getPresupuestoMax()) {
			return 0.0;
		}
		return presupuesto - Datos1.getPresupuestoMax();
	}

	private Double valoraciones(List<Integer> value) {
		Double valoraciones = 0.0;
		for (Integer i = 0; i < size(); i++) {
			valoraciones += (value.get(i) * Datos1.getValoracion(i));
		}
		return valoraciones;
	}

	@Override
	public List<Integer> solution(List<Integer> value) {
		return new ArrayList<>(value);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}

}
