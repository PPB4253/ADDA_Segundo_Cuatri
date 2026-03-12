package ejercicio3.ag;

import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma3 implements RangeIntegerData<Solucion3>{
	
	
	public Cromosoma3(String file) {
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
		Double goal = ocupados(value);
		Double errorCapacidad = errorCapacidad(value);
		Double errorTipos = errorTipos(value);
		
		return goal - (1000*errorCapacidad) - (1000*errorTipos);
	}

	private Double errorTipos(List<Integer> value) {
		Double errores = 0.0;
		
		for (Integer i = 0; i < size(); i++) {
			Integer j = value.get(i); // j es el contenedor donde ha caído el elemento i
			
			// Si j es menor que el num de contenedores (o sea, no se ha quedado fuera) y es incompatible
			if (j < Datos3.getNumContenedores() && Datos3.getNoPuedeUbicarse(i, j)) {
				errores += 1.0;
			}
		}
		return errores;
	}

	private Double errorCapacidad(List<Integer> value) {
		Double excesoTotal = 0.0;
		
		for (Integer j = 0; j < Datos3.getNumContenedores(); j++) {
			Double pesoMetido = 0.0;
			
			for (Integer i = 0; i < size(); i++) {	// value ES UN CROMOSOMA QUE AL HACER value.get(i), LO QUE HAGO ES VER QUE EL ELEMENTO i ESTA EN EL CONTENEDOR que el resultado del value.get(i) me diga 
				if (value.get(i).equals(j)) {					// Mira dentro del contenedor
					pesoMetido += Datos3.getTamElemento(i);
				}
			}
			
			// Hacemos la resta justo aquí para sumar el exceso de este contenedor
			if (pesoMetido > Datos3.getTamContenedor(j)) {
				excesoTotal += (pesoMetido - Datos3.getTamContenedor(j));
			}
		}
		// Devolvemos la suma de todos los kilos que nos hayamos pasado en total
		return excesoTotal; 
	}

	private Double ocupados(List<Integer> value) {		// value ES UN CROMOSOMA QUE AL HACER value.get(i), LO QUE HAGO ES VER QUE EL ELEMENTO i ESTA EN EL CONTENEDOR que el resultado del value.get(i) me diga 
		Double contenedoresLlenos = 0.0;
		
		// Miramos contenedor por contenedor (j)
		for (Integer j = 0; j < Datos3.getNumContenedores(); j++) {
			Double pesoMetido = 0.0;
			
			// Recorremos la lista a ver qué elementos (i) han caído en este contenedor j
			for (Integer i = 0; i < size(); i++) {
				if (value.get(i).equals(j)) {
					pesoMetido += Datos3.getTamElemento(i);
				}
			}
			
			// Si lo que hemos metido es igual a la capacidad, ¡punto para la FO!
			if (pesoMetido.equals(Datos3.getTamContenedor(j).doubleValue())) {
				contenedoresLlenos += 1.0;
			}
		}
		return contenedoresLlenos;
	}

	@Override
	public Solucion3 solution(List<Integer> value) {
		return Solucion3.create(value);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}

}
