package Ejemplo3_ag;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class CromosomaEj3 implements BinaryData<List<Integer>>{

	@Override
	public Integer size() {
		return DatosAlumnos.getNumAlumnos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Auto-generated method stub
		return null;
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
