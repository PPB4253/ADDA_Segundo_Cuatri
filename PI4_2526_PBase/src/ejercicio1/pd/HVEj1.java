package ejercicio1.pd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ejercicio1.Datos1;
import us.lsi.common.Pair;
import us.lsi.hypergraphs.VirtualHyperVertex;

public record HVEj1(Integer indice, Double sueldoAcumulado, Integer valoracionAcumulada, Set<String> cualidadesPendientes, List<Integer> contratados) implements VirtualHyperVertex<HVEj1, HEEj1, Integer, Pair<Double, List<Integer>>> {

	@Override
	public List<Integer> actions() {						// AQUI ESTAN LAS RESTRICCIONES DE PRESUPUESTO MAXIMO E INCOMPATIBILIDADES
		List<Integer> actions = new ArrayList<>();
		
		// Si hemos evaluado a todos los candidatos, no hay acciones posibles
		if (this.indice == Datos1.getNumCandidatos()) {
			return actions;
		}

		// Acción 0: NO lo contrato (siempre es una opción válida)
		actions.add(0);

		// Acción 1: SÍ lo contrato (¡OJO! Solo si cumple las normas)
		Double nuevoSueldo = this.sueldoAcumulado + Datos1.getSueldoMin(this.indice);
		boolean cumplePresupuesto = nuevoSueldo <= Datos1.getPresupuestoMax();
		
		// Comprobamos que no se lleve mal con nadie de los que ya hemos contratado
		boolean esCompatible = true;
		for (Integer contratadoPrevio : contratados) {
			if (Datos1.getSonIncompatibles(this.indice, contratadoPrevio)) {
				esCompatible = false;
				break;
			}
		}

		// Solo le doy al algoritmo la opción de contratarlo si hay dinero y no hay peleas
		if (cumplePresupuesto && esCompatible) {
			actions.add(1);
		}

		return actions;
	}
	@Override
	public Boolean isBaseCase() {
		return this.indice == Datos1.getNumCandidatos();
	}

	@Override
	public Double baseCaseWeight() {				// ULTIMA RESTRICCION DE QUE SE CUBREN TODAS LAS CUALIDADES
		// Si hemos llegado al final pero nos faltan cualidades por cubrir, este camino NO vale.
		if (!this.cualidadesPendientes.isEmpty()) {
			return null;
		}
		return 0.0;
	}

	@Override
	public Boolean isValid() {						// BASICAMENTE SI TENGO UN BUEN ACTIONS PONGO QUE DEVUELVA SIEMPRE TRUE, SI NO, AQUI ES DONDE VAN LAS RESTRICCIONES DE POR EJEMPLO QUE LA SUMA DE LOS SUELDOS NO SUPERE EL PRESUPUESTO MAXIMO Y ESO
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Pair<Double, List<Integer>> baseCaseSolution() {					// Inicializamos la solucion
		return new Pair<Double, List<Integer>>(0.0, new ArrayList<>());
	}

	@Override														// Double = peso, List<Integer> = solucion
	public Pair<Double, List<Integer>> solution(Integer a, List<Pair<Double, List<Integer>>> solutions) {		// Este metodo es complicado
		if (solutions.get(0) == null) {			// En vd no es necesario, es para que no pete por si
			return null;
		}
		
		Double valor = (a == 1) ? (double) Datos1.getValoracion(this.indice) : 0.0;		// Para ver si la a es 1 o 0
		Double nuevoPeso = solutions.get(0).first() + valor;	
		List<Integer> nuevasActions = new ArrayList<>(solutions.get(0).second());
		nuevasActions.add(0, a); // IMPORTANTE: Al principio (0) PARA QUE LA LISTA NO SALGA INVERTIDA
		return new Pair<>(nuevoPeso, nuevasActions);
	}		// Creo que es siempre igual, PREGUNTAR A GEMINI, me dice que no siempre es igual xd


	@Override
	public List<HVEj1> neighbors(Integer a) {									// BASICAMENTE COGEMOS LO SIGUIENTE, ES DECIR, LO QUE TENEMOS AHORA Y EL INDICE +1
		Integer nuevoIndice = this.indice + 1;
		Double nuevoSueldo = this.sueldoAcumulado;
		Integer nuevaValoracion = this.valoracionAcumulada;
		Set<String> nuevasCualidades = new HashSet<>(this.cualidadesPendientes);
		List<Integer> nuevosContratados = new ArrayList<>(this.contratados);

		if (a == 1) { // Si decidimos contratarlo, actualizamos nuestras "mochilas"
			nuevoSueldo += Datos1.getSueldoMin(this.indice);
			nuevaValoracion += Datos1.getValoracion(this.indice);
			nuevasCualidades.removeAll(Datos1.getCualidades(this.indice));		// De las cualidades que nos faltan quitamos las que nos da el nuevo candidato
			nuevosContratados.add(this.indice);
		}

		return List.of(new HVEj1(nuevoIndice, nuevoSueldo, nuevaValoracion, nuevasCualidades, nuevosContratados));
	}

	@Override
	public HEEj1 edge(Integer a) {
		// "Crea una flecha que va desde MÍ (this), hasta mis VECINOS, usando esta ACCIÓN"
		return new HEEj1(this, this.neighbors(a), a);
	}

}
