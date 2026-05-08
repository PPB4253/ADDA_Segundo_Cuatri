package ejercicio2.gv;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ejercicio2.Datos2;
import us.lsi.graphs.virtual.VirtualVertex;

public record VertexEj02Greedy(Integer indice, List<Integer> capacidadesRestantes) implements VirtualVertex<VertexEj02Greedy, EdgeEj02Greedy, Integer> {
	// indice y capacidadesRestantes obligatorios, capacidadesRestantes me indica [2,0,9,8] que es que el contenedor 1 le queda 2 de espacio, al 2 le queda 0, ....
	@Override
	public List<Integer> actions() {
		List<Integer> actions = new ArrayList<>();					// Esta List<Integer> contiene los contenedores validos
		
		// PASO 1: ¿Quedan objetos por mirar?
		if (this.indice == Datos2.getNumElementos()) {
			return actions;
		}
		
		// PASO 2: La opción de "Descarte" (Siempre disponible)
		Integer contenedorDescarte = Datos2.getNumContenedores();		// USAMOS ESTE NUMERO (que nunca va a ser un contenedor usable, y siempre va a estar disponible pq es 1 mas que el numero de contenedores usables) COMO CONTENEDOR BASURA
		actions.add(contenedorDescarte);
		
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
	
	public Integer greedyAction() {									// SE PODRIA HACER MAS CLARO, MEJOR VER EL EJERCICIO 3 GREEDY
		// 1. Pedimos las "llaves" de las puertas legales
		List<Integer> accionesValidas = actions();
		// ID de nuestra papelera
		Integer accionDescarte = Datos2.getNumContenedores();
		// Variables para guardar al "ganador"
		Integer mejorAccion = accionDescarte; // Por defecto, a la basura si no hay cajas
		Integer menorHuecoSobrante = Integer.MAX_VALUE;										// Deberia de ser MIN_VALUE, pero entonces el if (if (huecoSobrante < menorHuecoSobrante) ) falla siempre pq no encuentra algo menor

		// 2. Si no hay acciones válidas (hemos llegado al final), devolvemos descarte
		if (accionesValidas.isEmpty()) {
			return accionDescarte;
		}
		
		// 3. Buscamos la caja que se quede más ajustada (Best Fit)
		Integer tamElemento = Datos2.getTamElemento(this.indice);
		for (Integer accion : accionesValidas) {
			// Ignoramos la opción de tirar a la basura porque nuestra prioridad voraz es meterlo
			if (accion.equals(accionDescarte)) {
				continue;
			}
			// Calculamos cuánto hueco quedaría en ESA caja si metemos el objeto
			Integer huecoSobrante = this.capacidadesRestantes.get(accion) - tamElemento;
			// Si esta caja se queda más ajustada que la anterior que habíamos visto, es nuestra nueva favorita
			if (huecoSobrante < menorHuecoSobrante) {
				menorHuecoSobrante = huecoSobrante;
				mejorAccion = accion;
			}
		}
		
		// 4. Entregamos la mejor acción inmediata (el ID de la caja ganadora, o la papelera si no cabía en ninguna)
		return mejorAccion;
	}

	@Override
	public VertexEj02Greedy neighbor(Integer a) {				// a es el contenedor que estoy mirando
		Integer nuevoIndice = this.indice + 1;
		List<Integer> nuevaCapacidadRestante = new ArrayList<>(this.capacidadesRestantes);
		if (a < Datos2.getNumContenedores()) {
			Integer tamElemento = Datos2.getTamElemento(this.indice);
			Integer capacidadRestante = nuevaCapacidadRestante.get(a);
			
			nuevaCapacidadRestante.set(a, capacidadRestante - tamElemento);
		}
		return new VertexEj02Greedy(nuevoIndice, nuevaCapacidadRestante);
	}

	@Override
	public EdgeEj02Greedy edge(Integer a) {
		Double weight = 0.0;
		if (a < Datos2.getNumContenedores()) {
			VertexEj02Greedy vecino = this.neighbor(a);
			if (vecino.capacidadesRestantes().get(a) == 0) {
				weight = 1.0;
			}
		}
		return new EdgeEj02Greedy(this, this.neighbor(a), a, weight);
	}
	
	public Boolean goal() {
		return this.indice == Datos2.getNumElementos();
	}
	
	public Boolean goalHasSolution() {
		// Cualquier reparto (incluso no asignar ninguno) es una solución estructuralmente válida, 			PQ ME DICE QUE LLENE EL NUMERO MAXIMO, PERO NO QUE LOS LLENE TODOS
		// luego el algoritmo ya se encargará de buscar el que maximice la nota.
		return true;
	}
	
	public static Double heuristica(VertexEj02Greedy v, Predicate<VertexEj02Greedy> goal, VertexEj02Greedy end) {						// RESTRICCION DE QUE EL CONTENEDOR DEBE DE ESTAR LLENO
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
