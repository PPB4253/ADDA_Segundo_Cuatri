package ejercicio2.manual;

import java.util.ArrayList;
import java.util.List;

import ejercicio2.Datos2;

public class BTEj02 {
	
	List<Integer> mejorSolucion;
	Double valorMejorSolucion;
	EstadoEj02 estado;
	
	public BTEj02() {								// Inicializamos valores
		mejorSolucion = null;
		valorMejorSolucion = Double.MIN_VALUE;										// PARA MAXIMIZAR EMPEZAMOS EN EL VALOR MINIMO
		estado = new EstadoEj02();
	}
	
	public List<Integer> mejorSolucion() {
		return mejorSolucion == null ? null : new ArrayList<>(mejorSolucion);		// Si es null devuelve null, y si no pues la nueva lista
	}
	
	public void bt() {
		// 1. CASO BASE
		if (estado.verticeActual().goal()) {								// Si hemos llegado al final
			if (estado.verticeActual().goalHasSolution()) {					// y tiene solucion
				if (estado.valorSolucion() > valorMejorSolucion) {		// Pq queremos Maximizar
					valorMejorSolucion = estado.valorSolucion();
					mejorSolucion = estado.solucion();
				}
			}
			return;
		}
		
		// 2. Caso Recursivo
		for (Integer action: estado.verticeActual().actions()) {
			// PODA
			if (estado.cota(action) <= valorMejorSolucion) {		// Pq queremos Maximizar
				continue;			// No nos sirve, pq queremos que sea mayot que valorMejorSolucion
			}
			
			// RECORRIDO
			estado.forward(action);
			bt();
			estado.backward();
		}

	}

	public static void main(String[] args) {
		Datos2.iniDatos("src/ejercicio2/gv/DatosEntrada1.txt");
		
		BTEj02 bt = new BTEj02();
		bt.bt();
		
		System.out.println(bt.mejorSolucion());
		System.out.println(bt.valorMejorSolucion);
	}
}
