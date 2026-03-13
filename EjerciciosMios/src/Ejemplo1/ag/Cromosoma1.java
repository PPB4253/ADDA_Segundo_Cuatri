package Ejemplo1.ag;

import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements RangeIntegerData<SolucionMulticonjunto>{
	
	public Cromosoma1(String file) {
		DatosMulticonjunto.iniDatos(file);
	}

	@Override
	public Integer max(Integer i) {
		return DatosMulticonjunto.getMultiplicidad(i) + 1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

	@Override
	public Integer size() {
		return DatosMulticonjunto.getNumElementos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Integer goal = numMinimo(value);
		Integer exceso = exceso(value);
		
		return (double) (-goal - (1000*exceso));
	}

	private Integer exceso(List<Integer> value) {
		Integer exceso = 0;
		for (Integer i = 0; i < size(); i++) {
			exceso += value.get(i) * DatosMulticonjunto.getElemento(i);
		}
		return Math.abs(exceso - DatosMulticonjunto.getSuma());		// Ponemos el Math.abs() pq si me quedo corto en vez de excederme, ya que debe de ser exactamente igual, hariamos -15 + 20 por ejemplo y me daria -5, y en el fitness al restarlo se convierte en + y es premio en vez de castigo
	}

	private Integer numMinimo(List<Integer> value) {
		Integer numMinimo = 0;
		for (Integer i = 0; i < size(); i++) {
			numMinimo += value.get(i);
		}
		return numMinimo;
	}

	@Override
	public SolucionMulticonjunto solution(List<Integer> value) {
		return SolucionMulticonjunto.of_Range(value);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}

}
