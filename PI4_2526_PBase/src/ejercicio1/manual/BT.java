package ejercicio1.manual;

import java.util.ArrayList;
import java.util.List;

import ejercicio1.Datos1;

public class BT {						// SIEMPRE ES IGUAL (cambiando lo de maximizar y minimizar)
	
	List<Integer> mejorSolucion;
	Double valorMejorSolucion;
	Estado estado;
	
	public BT() {
		mejorSolucion = null;
		// Empezamos en lo más bajo, porque queremos MAXIMIZAR, para MINIMIZAR, al reves, pondriamos MAX_VALUE
		valorMejorSolucion = Double.MIN_VALUE; 
		estado = new Estado();
	}
	
	public List<Integer> mejorSolucion() {
		return mejorSolucion == null ? null : new ArrayList<Integer>(mejorSolucion);
	}
	
	public void bt() {
		// 1. CASO BASE: ¿Hemos llegado al final del árbol (hemos mirado a todos)?
		if (estado.vertice().goal()) {
			// 1.1 ¿Es una solución válida? (¿Cubre todas las cualidades?)
			if (estado.vertice().goalHasSolution()) {
				// 1.2 ¿Es MEJOR (mayor) que la que ya teníamos guardada?
				if (estado.valorSolucion() > valorMejorSolucion) {		// Pq queremos Maximizar
					valorMejorSolucion = estado.valorSolucion();
					mejorSolucion = estado.solucion();
				}
			}
			return; // Como ya hemos llegado al final, cortamos esta rama
		}
		
		// 2. CASO RECURSIVO: Si no hemos llegado al final, miramos qué puertas (acciones) podemos cruzar
		for (Integer action: estado.vertice().actions()) {
			
			// 3. LA PODA (La magia del Backtracking)
			// Si nuestra estimación MÁS OPTIMISTA de este camino (la cota)
			// es menor o igual a lo que ya tenemos guardado en el bolsillo... ¡No perdemos el tiempo!
			if (estado.cota(action) <= valorMejorSolucion) {		// Pq queremos Maximizar
				continue; 	// No nos sirve
			}
			
			// 4. EL RECORRIDO (Avanzamos, seguimos buscando, y luego recogemos cable)
			if (estado.cota(action) > valorMejorSolucion) {
				estado.forward(action); // Damos un paso adelante
				bt();                   // Volvemos a llamar al algoritmo (recursividad)
				estado.backward();      // Borramos las huellas al volver (vuelta atrás)
			}
		}
	}

	public static void main(String[] args) {
		Datos1.iniDatos("src/ejercicio1/gv/DatosEntrada1.txt");
		
		BT bt = new BT();
		bt.bt(); // ¡Arrancamos el motor!
		
		System.out.println("=========================================");
		System.out.println(" SOLUCIÓN BACKTRACKING MANUAL (EJ1)      ");
		System.out.println("=========================================");
		System.out.println("Secuencia de acciones (0=No, 1=Sí): " + bt.mejorSolucion());
		System.out.println("Valoración total conseguida: " + bt.valorMejorSolucion);
		System.out.println("=========================================");
	}
	
}
