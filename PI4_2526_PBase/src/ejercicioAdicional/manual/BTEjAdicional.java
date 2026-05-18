package ejercicioAdicional.manual;

import java.util.ArrayList;
import java.util.List;

import ejercicioAdicional.DatosAutovia;

public class BTEjAdicional {
	
	List<Integer> mejorSolucion;
	Double valorMejorSolucion;
	EstadoEjercicioAdicional estado;
	
	
	public BTEjAdicional() {
		mejorSolucion = null;
		valorMejorSolucion = -Double.MAX_VALUE;			// Pq queremos Maximizar
		estado = new EstadoEjercicioAdicional();
	}
	
	public List<Integer> mejorSolucion() {
		return mejorSolucion == null ? null : new ArrayList<Integer>(mejorSolucion);
	}
	
	public void bt() {
		// 1. CASO BASE
		if (estado.verticeActual.goal()) {
			if (estado.verticeActual.goalHasSolution()) {
				if (estado.valorSolucion() > valorMejorSolucion) {			// Pq queremos Maximizar
					valorMejorSolucion = estado.valorSolucion();
					mejorSolucion = estado.solucion();
				}
			}
			return;
		}
		
		// 2. Caso Recursivo
		for (Integer action: estado.verticeActual().actions()) {
			// PODA
			if (estado.cota(action) <= valorMejorSolucion) {			// Pq queremos Maximizar
				continue;		// Pq queremos que sea mayot que valorMejorSolucion
			}
			
			// Recorrido
			estado.forward(action);
			bt();
			estado.backward();
		}
	}

	
	public static void main(String[] args) {
		DatosAutovia.iniDatos("src/ejercicioAdicional/DatosEntrada1.txt");
		
		BTEjAdicional bt = new BTEjAdicional();
		bt.bt();
		
		System.out.println(bt.mejorSolucion());
		System.out.println(bt.valorMejorSolucion);
	}
}
