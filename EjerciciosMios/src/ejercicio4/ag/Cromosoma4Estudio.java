package ejercicio4.ag;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma4Estudio implements PermutationData<List<Integer>>{
	
	public Cromosoma4Estudio(String file) {
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
		
		return -goal - (1000*duracion) - (1000*monumentos);		//	 MINIMIZAR
	}

	private Double monumentos(List<Integer> value) {
		Boolean hayMonumentos = false;
		for (Integer i = 0; i < size() - 1; i++) {				// size() - 1 para no excederme // EL -1 SE PONE PQ CUANDO VAYAMOS A RECORRER EL SIGUIENTE NO DE ERROR, YA QUE ABAJO HACEMOS value.get(i+1)
			Integer inicio = value.get(i);
			Integer fin = value.get(i+1);
			if(Datos4.sonMonumentos(inicio, fin)) {
				hayMonumentos = true;
			}
		}
		if (!hayMonumentos) {
			Integer ultimo = value.get(size() - 1);
			Integer primero = value.get(0);
			if(Datos4.sonMonumentos(ultimo, primero)) {
				hayMonumentos = true;
			}
		}
		return hayMonumentos ? 0.0 : 1.0;
	}

	private Double duracion(List<Integer> value) {
		Double duracion = 0.0;
		for (Integer i = 0; i < size() - 1; i++) {
			Integer inicio = value.get(i);
			Integer fin = value.get(i+1);
			
			duracion += Datos4.tiempo(inicio, fin);
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
			Integer inicio = value.get(i);
			Integer fin = value.get(i+1);
			
			esfuerzo += Datos4.esfuerzo(inicio, fin);
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
