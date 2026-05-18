package ejercicio2.gv;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ejercicio2.Datos2;
import us.lsi.graphs.virtual.VirtualVertex;

public record VertexEj02(Integer indice, List<Integer> capacidadesRestantes) implements VirtualVertex<VertexEj02, EdgeEj02, Integer> {
								// indice y capacidadesRestantes obligatorios, capacidadesRestantes me indica [2,0,9,8] que es que el contenedor 1 le queda 2 de espacio, al 2 le queda 0, ....
	@Override
	public List<Integer> actions() {					// Esta List<Integer> contiene los contenedores validos
		List<Integer> actions = new ArrayList<>();
		
		// PASO 1: ¿Quedan objetos por mirar?
		if (this.indice == Datos2.getNumElementos()) {
			return actions;
		}
		
		// PASO 2: La opción de "Descarte" (Siempre disponible)
		Integer accionNoAsignar = Datos2.getNumContenedores();		// USAMOS ESTE NUMERO (que nunca va a ser un contenedor usable) COMO CONTENEDOR BASURA
		actions.add(accionNoAsignar);
		
		// PASO 3: Evaluar las cajas (contenedores) una a una
		Integer tamElemento = Datos2.getTamElemento(this.indice);

		for (Integer i = 0; i < Datos2.getNumContenedores(); i++) {
			Integer capacidadContenedor = this.capacidadesRestantes.get(i);							// CAPACIDAD DINAMICA
			// ¿Se puede ubicar (por tipo) Y además tiene hueco suficiente?									RESTRICCIONES DE CAPACIDAD Y TIPO
			if (Datos2.getPuedeUbicarse(this.indice, i) && tamElemento <= capacidadContenedor) {
				actions.add(i);
			}
		}
		return actions;
	}

	@Override
	public VertexEj02 neighbor(Integer a) {				// a es el contenedor que estoy mirando
		Integer nuevoIndice = this.indice + 1;
		List<Integer> nuevaCapacidadRestante = new ArrayList<>(this.capacidadesRestantes);
		if (a < Datos2.getNumContenedores()) {												// Solo varia la capacidad del contenedor si asignamos un elemento al contenedor (a < Datos2.getNumContenedores()), si no se asigna seria a = Datos2.getNumContenedores() y el tamaño del contenedor se quedaria siendo una copia: List<Integer> nuevaCapacidadRestante = new ArrayList<>(this.capacidadesRestantes);
			Integer capacidadActualDelContenedor = nuevaCapacidadRestante.get(a);
			Integer tamElemento = Datos2.getTamElemento(this.indice);
			
			nuevaCapacidadRestante.set(a, capacidadActualDelContenedor - tamElemento);
		}
		return new VertexEj02(nuevoIndice, nuevaCapacidadRestante);
	}

	@Override
	public EdgeEj02 edge(Integer a) {
		// "Crea una arista que va desde MÍ (this), hasta mi VECINO, usando esta ACCIÓN (a)"
		Double weight = 0.0;
		if (a < Datos2.getNumContenedores()) {
			// Calculamos el vecino para ver cómo queda el contenedor DESPUÉS de meter el objeto
			VertexEj02 vecino = this.neighbor(a);
			
			// Si el hueco de la caja en el vecino se ha quedado a 0, ganamos 1 punto (1 contenedor lleno)			CONTENEDOR LLENO
			if (vecino.capacidadesRestantes().get(a) == 0) {												// Si esta completamente lleno
				weight = 1.0;
			}
		}
		return new EdgeEj02(this, this.neighbor(a), a, weight);
	}
	
	public Boolean goal() {
		return this.indice == Datos2.getNumElementos();
	}
	
	public Boolean goalHasSolution() {
		// Cualquier reparto (incluso no asignar ninguno) es una solución estructuralmente válida, 			PQ ME DICE QUE LLENE EL NUMERO MAXIMO, PERO NO QUE LOS LLENE TODOS
		// luego el algoritmo ya se encargará de buscar el que maximice la nota.
		return true;
	}
	
	public static Double heuristica(VertexEj02 v, Predicate<VertexEj02> goal, VertexEj02 end) {						// RESTRICCION DE QUE EL CONTENEDOR DEBE DE ESTAR LLENO
		// 1. Calculamos el "agua total": la suma del tamaño de todos los elementos que nos quedan por mirar
		Integer sumaTamanosRestantes = 0;
		for (int i = v.indice(); i < Datos2.getNumElementos(); i++) {
			sumaTamanosRestantes += Datos2.getTamElemento(i);
		}

		// 2. Filtramos los contenedores que AÚN tienen hueco (ignoramos los que ya están a 0)
		List<Integer> huecosDisponibles = new ArrayList<>();
		for (Integer cap : v.capacidadesRestantes()) {
			if (cap > 0) {
				huecosDisponibles.add(cap);
			}
		}

		// 3. Ordenamos los huecos de MENOR a MAYOR. 
		// (Para ser optimistas y ganar más puntos, intentamos llenar primero los huecos más pequeños)					PARA MAXIMIZAR
		huecosDisponibles.sort(null); 

		// 4. "Vertemos" el agua restante en las cajas
		Double contenedoresExtraLlenados = 0.0;
		
		for (Integer hueco : huecosDisponibles) {
			// Si tengo "agua" (tamaño) suficiente para llenar este hueco por completo...
			if (sumaTamanosRestantes >= hueco) {
				contenedoresExtraLlenados += 1.0; // ¡Gano 1 punto optimista!
				sumaTamanosRestantes -= hueco;    // Gasto esa cantidad de agua
			} else {
				// Si ya no me queda agua ni para llenar el hueco más pequeño que queda, paro
				break; 
			}
		}

		return contenedoresExtraLlenados;
	}

}
