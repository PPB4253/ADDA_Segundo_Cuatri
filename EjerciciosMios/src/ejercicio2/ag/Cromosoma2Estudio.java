package ejercicio2.ag;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma2Estudio implements RangeIntegerData<List<Integer>>{
	
	public Cromosoma2Estudio(String file) {
		Datos2.iniDatos(file);
	}

	@Override
	public Integer max(Integer i) {				// YA QUE ES EL RANGO DEL NUMERO DENTRO DEL CUADRAITO DEL CROMOSOMA
		return Datos2.getUnidsSemanaProd(i)+1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

	@Override
	public Integer size() {
		return Datos2.getNumComponentes();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = ingresos(value);
		Double tiempoProd = tiempoProd(value);
		Double tiempoElab = tiempoElab(value);
		
		return goal - (1000*tiempoProd) - (1000*tiempoElab);
	}

	private Double tiempoElab(List<Integer> value) {
		Double tiempoElab = 0.0;
		for (Integer i = 0; i < size(); i++) {
			tiempoElab += (value.get(i) * Datos2.getTiempoElabProd(i));
		}
		if (tiempoElab <= Datos2.getTiempoElabTotal()) {
			return 0.0;
		}
		return tiempoElab - Datos2.getTiempoElabTotal();
	}

	private Double tiempoProd(List<Integer> value) {
		Double tiempoProd = 0.0;
		for (Integer i = 0; i < size(); i++) {
			tiempoProd += (value.get(i) * Datos2.getTiempoProdProd(i));
		}
		if (tiempoProd <= Datos2.getTiempoProdTotal()) {
			return 0.0;
		}
		return tiempoProd - Datos2.getTiempoProdTotal();
	}

	private Double ingresos(List<Integer> value) {
		Double ingresos = 0.0;
		for (Integer i = 0; i < size(); i++) {
			ingresos += (value.get(i) * Datos2.getPrecioProd(i));
		}
		return ingresos;
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
