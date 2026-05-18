package ejercicioAdicional.ag;

import java.util.ArrayList;
import java.util.List;

import ejercicioAdicional.DatosAutovia;
import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;


public class CromosomaAutovia implements BinaryData<List<Integer>>{

	public CromosomaAutovia(String file) {
		DatosAutovia.iniDatos(file);
	}
	
	public Integer size() {
		return DatosAutovia.getNumAreasServicio();
	}
	
	public Double fitnessFunction(List<Integer> value) {
		Double goal = beneficio(value);
		Double presupuestoPenalizacion = presupuestoPenalizacion(value);
		
		return goal - (presupuestoPenalizacion*1000);					// +goal pq queremos MAXIMIZAR
	}
	
	private Double presupuestoPenalizacion(List<Integer> value) {
		Double presupuesto = 0.0;
		for (Integer i = 0; i < size(); i++) {
			presupuesto += (value.get(i) * DatosAutovia.getCosteConstruccion(i));
		}
		if (presupuesto <= DatosAutovia.getPresupuestoMax()) {
			return 0.0;
		}
		return presupuesto - DatosAutovia.getPresupuestoMax();
	}

	private Double beneficio(List<Integer> value) {
		Double beneficio = 0.0;
		for (Integer i = 0; i < size(); i ++) {
			beneficio += (value.get(i) *DatosAutovia.getBeneficio(i));
		}
		return beneficio;
	}

	public List<Integer> solution(List<Integer> value) {
		return new ArrayList<>(value);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
}
