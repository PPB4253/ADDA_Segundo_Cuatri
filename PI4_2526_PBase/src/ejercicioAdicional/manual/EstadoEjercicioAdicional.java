package ejercicioAdicional.manual;

import java.util.ArrayList;
import java.util.List;

import ejercicioAdicional.DatosAutovia;
import ejercicioAdicional.gv.AutoviaVertice;

public class EstadoEjercicioAdicional {
	
	AutoviaVertice verticeActual;
	List<Integer> solucion;
	Double valorSolucion;
	List<AutoviaVertice> verticesAnteriores;
	
	
	public EstadoEjercicioAdicional() {
		verticeActual = new AutoviaVertice(0, DatosAutovia.getPresupuestoMax());
		solucion = new ArrayList<>();
		valorSolucion = 0.0;
		verticesAnteriores = new ArrayList<>();
	}
	
	public AutoviaVertice verticeActual() {
		return verticeActual;
	}
	
	public List<Integer> solucion() {
		return new ArrayList<>(solucion);
	}
	
	public Double valorSolucion() {
		return valorSolucion;
	}
	
	public Double cota(Integer a) {
		Double peso = 0.0;
		if (a == 1) {			// Solo si es elegido tenemos beneficio
			peso = DatosAutovia.getBeneficio(verticeActual.indice());					// METODO DE LA FUNCION OBJETIVO: beneficio
		}
		return this.valorSolucion + peso + AutoviaVertice.heuristica(verticeActual.neighbor(a), null, null);
	}
	
	public void forward(Integer a) {
		Double peso = 0.0;
		if (a == 1) {			// Solo si es elegido tenemos beneficio
			peso = DatosAutovia.getBeneficio(verticeActual.indice());
		}
		
		solucion.add(a);
		valorSolucion += peso;
		verticesAnteriores.add(verticeActual);
		verticeActual = verticeActual.neighbor(a);
	}
	
	public void backward() {
		verticeActual = verticesAnteriores.remove(verticesAnteriores.size() - 1);
		Integer ultimaAccion = solucion.remove(solucion.size() - 1);
		
		if (ultimaAccion == 1) {			// Solo si es elegido restamos beneficio
			valorSolucion -= DatosAutovia.getBeneficio(verticeActual.indice());
		}
	}

}
