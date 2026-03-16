package ejercicio4.ag;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cormosoma4Estudio2 implements PermutationData<List<Integer>>{
	
	public Cormosoma4Estudio2(String file) {
		Datos4.iniDatos(file);
	}

	@Override
	public Integer size() {
		return Datos4.N;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = esfuerzo(value);
		Double duracion = duracion(value);
		Double monumentos = monumentos(value);
		
		return -goal - (1000*duracion) - (1000*monumentos);
	}

	private Double monumentos(List<Integer> value) {
		Boolean hayMonumento = false;
		for (Integer i = 0; i < size() - 1; i++) {
			Integer primero = value.get(i);
			Integer siguiente = value.get(i+1);
			if (Datos4.sonMonumentos(primero, siguiente)) {
				hayMonumento = true;
			}
		}
		if (!hayMonumento) {
			Integer ultimo = value.get(size() - 1);
			Integer primero = value.get(0);
			if (Datos4.sonMonumentos(ultimo, primero)) {
				hayMonumento = true;
			}
		}
		return hayMonumento ? 0.0 : 1.0;
	}

	private Double duracion(List<Integer> value) {
		Double duracion = 0.0;
		for (Integer i = 0; i < size() - 1; i++) {
			Integer primero = value.get(i);
			Integer siguiente = value.get(i+1);
			
			duracion += Datos4.tiempo(primero, siguiente);
		}
		Integer ultimo = value.get(size() - 1);
		Integer primero = value.get(0);
		duracion += Datos4.tiempo(ultimo, primero);
		
		if (duracion <= Datos4.maxTime) {
		    return 0.0;
		}
		
		return duracion - Datos4.maxTime;
	}

	private Double esfuerzo(List<Integer> value) {
		Double esfuerzo = 0.0;
		for (Integer i = 0; i < size() - 1; i++) {
			Integer primero = value.get(i);
			Integer siguiente = value.get(i+1);
			
			esfuerzo += Datos4.esfuerzo(primero, siguiente);
		}
		Integer ultimo = value.get(size() - 1);
		Integer primero = value.get(0);
		esfuerzo += Datos4.esfuerzo(ultimo, primero);
		
		return esfuerzo;
	}

	@Override
	public List<Integer> solution(List<Integer> value) {
		return new ArrayList<>(value);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}

}
