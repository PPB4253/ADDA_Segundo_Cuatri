package ejercicio2.manual;

import java.util.ArrayList;
import java.util.List;

import ejercicio2.Datos2;
import ejercicio2.gv.VertexEj02;

public class EstadoEj02 {
	
	VertexEj02 verticeActual;
	List<Integer> solucion;
	Double valorSolucion;
	List<VertexEj02> verticesAnteriores;
	
	public EstadoEj02() {											// Inicializamos valores
		List<Integer> capacidadInicial = new ArrayList<>();
		for (int i = 0; i < Datos2.getNumContenedores(); i++) {
			capacidadInicial.add(Datos2.getTamContenedor(i));
		}
		
		verticeActual = new VertexEj02(0, capacidadInicial);
		solucion = new ArrayList<>();
		valorSolucion = 0.0;
		verticesAnteriores = new ArrayList<>();
	}
	
	
	// METODOS
	public VertexEj02 verticeActual() {
		return verticeActual;
	}
	
	public List<Integer> solucion() {
		return new ArrayList<>(solucion);
	}
	
	public Double valorSolucion() {
		return valorSolucion;
	}
	
	
	public Double cota(Integer action) {
		// Lo que ya hemos conseguido hasta ahora (valor acumulad0) + Lo que estimamos que podemos conseguir a partir del vecino (heurística)		(f = g + h)
		Double pesoAccion = 0.0;
	    if (action < Datos2.getNumContenedores()) {
	        if (verticeActual.capacidadesRestantes().get(action) - Datos2.getTamElemento(verticeActual.indice()) == 0) {		// Si esta completamente lleno
	            pesoAccion = 1.0;
	        }
	    }
	    // IMPORTANTE: Sumar valorSolucion + punto de ahora + heurística futuro
	    return this.valorSolucion + pesoAccion + VertexEj02.heuristica(verticeActual.neighbor(action), null, null);		// METODO DE LA FUNCION OBJETIVO: pesoAccion que es llenar el mayor numero de contendores
	}
	
	public void forward(Integer action) {
	    // Si la acción es meterlo en un contenedor y este se llena (capacidad queda a 0)
	    Double pesoAccion = 0.0;  
	    if (action < Datos2.getNumContenedores()) {
	        // Miramos cómo quedaría la capacidad después de meter el elemento
	        if (verticeActual.capacidadesRestantes().get(action) - Datos2.getTamElemento(verticeActual.indice()) == 0) {		// Si esta completamente lleno
	            pesoAccion = 1.0;
	        }
	    }
		
		solucion.add(action);
		valorSolucion += pesoAccion;
		verticesAnteriores.add(verticeActual);					// Como es forward, el vertice actual ahora es anterior
		verticeActual = verticeActual.neighbor(action);			// Como es forward, el vertice nuevo actual ahora es el siguiente
	}
	
	public void backward() {
		verticeActual = verticesAnteriores.remove(verticesAnteriores.size() - 1);		// Como es backward pues quitamos el ulimo vertice, para ir para atras
		Integer ultimaAccion = solucion.remove(solucion.size() - 1);					// Como es backward quitamos la ultima solucion, para ir para atras
		
		Double pesoARestar = 0.0;
		if (ultimaAccion < Datos2.getNumContenedores()) {
			// Si al meter el elemento en la caja, esta se quedó a cero, es que sumamos 1.0
			// Usamos verticeActual (que ya es el anterior) para ver cómo quedó la caja
			if (verticeActual.capacidadesRestantes().get(ultimaAccion) - Datos2.getTamElemento(verticeActual.indice()) == 0) {		// Si esta completamente lleno
				pesoARestar = 1.0;
			}
		}
		valorSolucion -= pesoARestar;			// Como es backward quitamos la ultima solucion, para ir para atras
	}
	
}
