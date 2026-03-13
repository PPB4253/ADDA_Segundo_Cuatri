package ejercicio4.ag;

import java.util.List;

import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma4 implements PermutationData<Solucion4>{
	
	public Cromosoma4(String file) {
		Datos4.iniDatos(file);
	}

	@Override
	public Integer size() {
		return Datos4.N;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = esfuerzo(value);
		Double totalDuracion = totalDuracion(value);
		Double totalConsecutivos = totalConsecutivos(value); 

		return -goal - (1000*totalDuracion) - (1000*totalConsecutivos);
	}

	private Double totalConsecutivos(List<Integer> value) {
		boolean hayDosSeguidos = false;
		
		// 1. Comprobamos los tramos intermedios
		for (Integer i = 0; i < size() - 1; i++) {
			Integer origen = value.get(i);
			Integer destino = value.get(i + 1);
			
			// Si encontramos dos seguidos con monumento, marcamos true y rompemos el bucle
			if (Datos4.sonMonumentos(origen, destino)) {
				hayDosSeguidos = true;
				break;
			}
		}
		
		// 2. Si no los hemos encontrado en el camino de ida, miramos el de vuelta por si acaso								 A ESTO SE REFIERE DEL ULTIMO VERTICE AL PRIMERO PARA CERRAR EL CAMINO CERRADO
		if (!hayDosSeguidos) {
			Integer ultimo = value.get(size() - 1);
			Integer primero = value.get(0);
			if (Datos4.sonMonumentos(ultimo, primero)) {
				hayDosSeguidos = true;
			}
		}
		
		// 3. Devolvemos la multa: si hay dos seguidos devolvemos 0. Si no, devolvemos 1 (que se multiplicará por 1000)
		if (hayDosSeguidos) {
			return 0.0;
		} else {
			return 1.0;
		}
	}

	private Double totalDuracion(List<Integer> value) {
		Double duracionTotal = 0.0;
		
		// 1. Sumamos la duración de los tramos intermedios
		for (Integer i = 0; i < size() - 1; i++) {
			Integer origen = value.get(i);
			Integer destino = value.get(i + 1);
			
			duracionTotal += Datos4.tiempo(origen, destino);
		}
		
		// 2. Sumamos la duración de volver a casa
		Integer ultimo = value.get(size() - 1);
		Integer primero = value.get(0);
		duracionTotal += Datos4.tiempo(ultimo, primero);
		
		// 3. Calculamos la penalización
		// En tu clase Datos4 ya tienes calculado Datos4.maxTime (que es el 75%)
		if (duracionTotal <= Datos4.maxTime) {
			return 0.0; // Todo en orden, 0 multas
		}
		// Si nos pasamos, devolvemos cuánto tiempo extra hemos gastado
		return duracionTotal - Datos4.maxTime; 
	}

	private Double esfuerzo(List<Integer> value) {
		Double esfuerzoTotal = 0.0;
		
		// 1. Sumamos los tramos intermedios (bucle hasta n - 1 para que i+1 no explote)
		for (Integer i = 0; i < size() - 1; i++) {				// EL -1 SE PONE PQ CUANDO VAYAMOS A RECORRER EL SIGUIENTE NO DE ERROR, YA QUE ABAJO HACEMOS value.get(i+1)
			Integer origen = value.get(i);
			Integer destino = value.get(i + 1);
			
			esfuerzoTotal += Datos4.esfuerzo(origen, destino);
		}
		
		// 2. Cerramos el ciclo (de la última intersección volvemos a la primera)
		Integer ultimo = value.get(size() - 1);
		Integer primero = value.get(0);
		esfuerzoTotal += Datos4.esfuerzo(ultimo, primero);
		
		return esfuerzoTotal;
	}

	@Override
	public Solucion4 solution(List<Integer> value) {
		return Solucion4.create(value);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}

}
