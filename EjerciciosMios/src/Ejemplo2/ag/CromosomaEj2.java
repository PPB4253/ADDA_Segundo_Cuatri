package Ejemplo2.ag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

// BinaryData<> es pq es una variable binaria
public class CromosomaEj2 implements BinaryData<List<Integer>>{		// Esto no debe hacerse BinaryData<List<Integer>> la lista debe de ser una clase externa o algo asi dice (EN PROXIMA CLASE SE VE CON LA CLASE SOLUTION)
	
	// Dandole add unimplemented methods, me pone lo de abajo solo
	
	
	public CromosomaEj2(String file) {
		DatosSubconjunto.iniDatos(file);
	}
	
	
	@Override
	public Integer size() {	// SIEMPRE IGUAL
		return DatosSubconjunto.getNumSubconjuntos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {	// LO CHUNGO, EL FITNESS
		Double goal = pesosSeleccionados(value);
		Integer elementosFaltan = elementosNoUniverso(value);
		
		return -goal -(elementosFaltan * 10000);	// -goal pq estamos minimizando		multiplicamos por un numero grande para quitarnos cromosomas erroneos
	}
	

	private Integer elementosNoUniverso(List<Integer> value) {
		Set<Integer> seleccionados = new HashSet<>();
		for (Integer i = 0; i < size(); i++) {						// Recorremos todos los cromosomas
			if (value.get(i) == 1) {
				seleccionados.addAll(DatosSubconjunto.getSubConjunto(i).elementos());		// si selecciono el subconjunto, con el if, en seleccionados meto sus conjuntos
			}
		}
		return DatosSubconjunto.getNumElementos() - seleccionados.size();
	}

	private Double pesosSeleccionados(List<Integer> value) {
		Double pesos = 0.0;
		for (Integer i = 0; i < size(); i++) {						// Recorremos todos los cromosomas
			pesos += (value.get(i) * DatosSubconjunto.getPeso(i));	// Función Objetico
		}
		return pesos;
	}
	
	
	

	@Override
	public List<Integer> solution(List<Integer> value) {	// Esta lista que devuelve es lo que le pasamos aqui BinaryData<List<Integer>> (EN PROXIMA CLASE SE VE CON LA CLASE SOLUTION), lo que recibe como parametro es el mejor cromosoma, una lista de enteros
		return new ArrayList<>(value);
	}

	@Override
	public ChromosomeType type() {	// SIEMPRE IGUAL
		return ChromosomeType.Binary;
	}
	
	

}
