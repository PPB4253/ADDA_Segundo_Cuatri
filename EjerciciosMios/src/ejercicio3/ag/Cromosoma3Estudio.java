package ejercicio3.ag;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma3Estudio implements RangeIntegerData<List<Integer>>{
	
	public Cromosoma3Estudio(String file) {
		Datos3.iniDatos(file);
	}

	@Override
	public Integer max(Integer i) {				// YA QUE ES EL RANGO DEL NUMERO DENTRO DEL CUADRAITO DEL CROMOSOMA
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
		Double goal = contenedoresLLenos(value);
		Double capacidad = capacidad(value);
		Double tipo = tipo(value);
		
		return goal - (1000*capacidad) - (1000*tipo);
	}

	private Double tipo(List<Integer> value) {
		Double noPuedeUbicarse = 0.0;
		for (Integer i = 0; i < size(); i++) {
			if (value.get(i) < Datos3.getNumContenedores() && Datos3.getNoPuedeUbicarse(i, value.get(i))) {
				noPuedeUbicarse += 1;
			}
		}
		return noPuedeUbicarse;
	}

	private Double capacidad(List<Integer> value) {
		Double exceso = 0.0;
		for (Integer j = 0; j < Datos3.getNumContenedores(); j++) {
			Double pesoMetido = 0.0;
			for (Integer i = 0; i < size(); i++) {
				if (value.get(i).equals(j)) {
					pesoMetido += Datos3.getTamElemento(i);
				}
			}
			if (pesoMetido > Datos3.getTamContenedor(j)) {
				exceso += (pesoMetido - Datos3.getTamContenedor(j));
			}
		}
		return exceso;
	}

	private Double contenedoresLLenos(List<Integer> value) {
		Double contenedoresLLenos = 0.0;
		for (Integer j = 0; j < Datos3.getNumContenedores(); j++) {
			Double pesoQueVamosAMeter = 0.0;
			for (Integer i = 0; i < size(); i++) {
				if (value.get(i).equals(j)) {							// IMPORTANTE QUE NO SE OLVIDE
					pesoQueVamosAMeter += Datos3.getTamElemento(i);
				}
			}
			if (pesoQueVamosAMeter.equals(Datos3.getTamContenedor(j).doubleValue())) {
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
