package Ejemplo3.ag;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class CromosomaEj3 implements PermutationData<List<Integer>>{
	
	public Integer maxMultiplicity(int index) {
		return DatosAlumnos.getTamGrupo();			// ESTO ES POR SI DA ERROR DE QUE SE SALE DEL TAMAÑO, PUES LO QUE HACE ESTO ES ADAPTARLO AL TAMAÑO DEL GRUPO
	}

	@Override
	public Integer size() {
		return DatosAlumnos.getNumGrupos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = afinidadconGrupos(value);
		Integer alumnosEnCero = cuentaAfinidadCero(value);
		
		return goal - (1000*alumnosEnCero);
	}

	private Integer cuentaAfinidadCero(List<Integer> value) {
		Integer cero = 0;
		for (Integer i = 0; i < value.size(); i++) {
			if (DatosAlumnos.getAfinidad(i, value.get(i)) == 0) {
				cero += 1;
			}
		}
		return cero;
	}

	private Double afinidadconGrupos(List<Integer> value) {
		Double goal = 0.0;
		for (Integer i = 0; i < value.size(); i++) {
			goal += DatosAlumnos.getAfinidad(i, value.get(i));
		}
		return goal;
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
