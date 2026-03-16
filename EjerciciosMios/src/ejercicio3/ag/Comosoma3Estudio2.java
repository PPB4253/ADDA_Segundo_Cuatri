package ejercicio3.ag;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Comosoma3Estudio2 implements RangeIntegerData<List<Integer>>{
	
	public Comosoma3Estudio2(String file) {
		Datos3.iniDatos(file);
	}

	@Override
	public Integer max(Integer i) {
		return Datos3.getNumContenedores()+1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

	@Override
	public Integer size() {
		return Datos3.getNumElementos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = llenos(value);
		Double capacidad = capacidad(value);
		Double incompatible = incompatible(value);
		
		return goal - (1000*capacidad) - (1000*incompatible);
	}

	private Double incompatible(List<Integer> value) {
		Double incompatibles = 0.0;
		for (Integer i = 0; i < size(); i++) {
			if (value.get(i) < Datos3.getNumContenedores() && Datos3.getNoPuedeUbicarse(i, value.get(i))) {
				incompatibles += 1;
			}
		}
		return incompatibles;
	}

	private Double capacidad(List<Integer> value) {
		Double exceso = 0.0;
		for (Integer j = 0; j < Datos3.getNumContenedores(); j++) {
			Double espacioLLeno = 0.0;
			for (Integer i = 0; i < size(); i++) {
				if (value.get(i).equals(j)) {
					espacioLLeno += Datos3.getTamElemento(i);
				}
			}
			if (espacioLLeno > Datos3.getTamContenedor(j)) {
				exceso += (espacioLLeno - Datos3.getTamContenedor(j));
			}
		}
		return exceso;
	}

	private Double llenos(List<Integer> value) {
		Double contenedoresLLenos = 0.0;
		for (Integer j = 0; j < Datos3.getNumContenedores(); j++) {
			Double espacioLLeno = 0.0;
			for (Integer i = 0; i < size(); i++) {
				if (value.get(i).equals(j)) {
					espacioLLeno += Datos3.getTamElemento(i);
				}
			}
			if (espacioLLeno.equals(Datos3.getTamContenedor(j).doubleValue())) {
				contenedoresLLenos += 1;
			}
		}
		return contenedoresLLenos;
	}

	@Override
	public List<Integer> solution(List<Integer> value) {
		return new ArrayList<>(value);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}

}
