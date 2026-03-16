package ejercicio1.ag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1Estudio2 implements BinaryData<List<Integer>>{
	
	public Cromosoma1Estudio2(String file) {
		Datos1.iniDatos(file);
	}

	@Override
	public Integer size() {
		return Datos1.getNumCandidatos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = valoraciones(value);
		Double cualidades = cualidades(value);
		Double sueldos = sueldos(value);
		Double incompatibilidades = incompatibilidades(value);
		
		return goal - (1000*cualidades) - (1000*sueldos) - (1000*incompatibilidades);
	}

	private Double incompatibilidades(List<Integer> value) {
		Double incompatibilidades = 0.0;
		for (Integer i = 0; i < size(); i++) {
			if (value.get(i) == 1) {
				for (Integer k = 0; k < size(); k++) {
					if (value.get(i) == 1 && value.get(k) == 1 && Datos1.getSonIncompatibles(i, k)) {
						incompatibilidades += 1;
					}
				}
			}
		}
		return incompatibilidades;
	}

	private Double sueldos(List<Integer> value) {
		Double sueldo = 0.0;
		for (Integer i = 0; i < size(); i++) {
			sueldo += (value.get(i) * Datos1.getSueldoMin(i));
		}
		if (sueldo <= Datos1.getPresupuestoMax()) {
			return 0.0;
		}
		return sueldo - Datos1.getPresupuestoMax();
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
