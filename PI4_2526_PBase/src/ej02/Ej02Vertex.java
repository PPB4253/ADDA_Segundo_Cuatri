package ej02;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import us.lsi.graphs.virtual.VirtualVertex;

public record Ej02Vertex(Integer indice, Set<Integer> elementosPendientes) implements VirtualVertex<Ej02Vertex, Ej02Edge, Integer>{	// V (vertice) es Ej02Vertex siempre, la E (Arista) seria el record Ej02Edge, A (action) siempre Integer
				// indice pack que estoy mirando; elementosPendientes elementos que me quedan por coger
	
	
		// ADD UNIMPLEMENTEDS METHODS Y SE ME PONE LO DE ABAJO
	
	@Override
	public List<Integer> actions() {			// El más importante	(ahora esta hecho lo minimo para funcionar)
		List<Integer> actions = Arrays.asList(0,1);		// Porque queremos una lista de binarios		SE PUEDE CREAR UNA LISTA Y AÑADIR 0 Y 1, COMO SE QUIERA
				// 1 o 0, cojo el pack o no
		
		// Optimizaciones	(SIEMPRE IGUAL, ev no siempre igual)
		if (this.elementosPendientes.isEmpty()) {								// si no hay elementosPendientes, no voy a coger ningun pack mas
			actions = Arrays.asList(0);
			
		} else {		// O evitarnos este else poniendo return actions en cada if
			if (this.indice == DatosSubconjunto.getNumSubconjuntos()-1) {		// si hay elementosPendientes, voy a coger un pack mas
				actions = Arrays.asList(1);
			}
		}
		
		return actions;
	}

	@Override
	public Ej02Vertex neighbor(Integer a) {		// Integer a es la acción
			// si tomo el pack, pues paso al siguiente caso sabiendo que me he elimidado algunos elementosPendientes
		Integer nuevoIndice = this.indice + 1;
		Set<Integer> nuevosElementosPendientes = new HashSet<>(this.elementosPendientes);	// copia de elementosPendientes
		if (a == 1) {	// si cojo este pack, pues elimino los elementosPendientes que tuviese
			nuevosElementosPendientes.removeAll(DatosSubconjunto.getElementos(this.indice));
		}
		
		return new Ej02Vertex(nuevoIndice, nuevosElementosPendientes);
	}

	@Override
	public Ej02Edge edge(Integer a) {		// si cojo el pack, pues lo que me cuesta cogerlo (en este caso es el peso)
		Double weight = 0.0;
		if (a == 1) {
			weight = DatosSubconjunto.getPeso(this.indice);
		}
		return new Ej02Edge(this, this.neighbor(a), a, weight);	// Parametros del record Ej02Edge
	}
	
	public Boolean goal() {						// ES UN METODO POR DEFECTO DE LA CLASE VirtualVertex
		return this.indice == DatosSubconjunto.getNumSubconjuntos();
			// Le dice al algoritmo que has llegado al final del pasillo (has mirado todos los packs posibles)
	}
	
	public Boolean goalHasSolution() {			// ES UN METODO POR DEFECTO DE LA CLASE VirtualVertex
		return this.elementosPendientes.isEmpty();		// tiene solucion cuando elementosPendientes esta vacio
			// Le dice si esa ruta concreta es válida. Has ganado solamente si tu lista de la compra (elementosPendientes) ha quedado vacía. Si llegas al final y te faltan cosas, el algoritmo descarta este camino
	}

	
	
	// Heurística
	public static Double miHeu(Ej02Vertex vertice, Predicate<Ej02Vertex> goal, Ej02Vertex end) {	// El predicado y el ultimo vertice no se usan en este ejemplo
		// 1. Si ya hemos comprado todo, el coste restante es 0
		if (vertice.elementosPendientes().isEmpty()) {
			return 0.0;
		}
		
		// 2. Si no quedan elementos por mirar pero nos faltan cosas por comprar: callejón sin salida
		if (vertice.indice() >= DatosSubconjunto.getNumSubconjuntos()) {
			return 100000.0; // Una penalización gigante
		}

		Double minRatio = Double.MAX_VALUE;

		// 3. Buscamos el subconjunto restante con el mejor "precio por elemento"
		for (int i = vertice.indice(); i < DatosSubconjunto.getNumSubconjuntos(); i++) {
			
			// Vemos cuántos elementos de este subconjunto realmente necesitamos
			Set<Integer> utiles = new HashSet<>(DatosSubconjunto.getElementos(i));
			utiles.retainAll(vertice.elementosPendientes()); // Me quedo solo con la intersección
			
			if (!utiles.isEmpty()) {
				Double peso = DatosSubconjunto.getPeso(i);
				Double ratio = peso / utiles.size(); // Calculo el coste por elemento útil
				
				if (ratio < minRatio) {
					minRatio = ratio;
				}
			}
		}

		// Si minRatio sigue siendo MAX_VALUE, significa que ningún pack que queda tiene lo que busco
		if (minRatio == Double.MAX_VALUE) {
			return 100000.0; // Callejón sin salida
		}

		// 4. Mi estimación optimista es: (Mejor precio por elemento) * (Elementos que me faltan)
		return minRatio * vertice.elementosPendientes().size();
	}
}
