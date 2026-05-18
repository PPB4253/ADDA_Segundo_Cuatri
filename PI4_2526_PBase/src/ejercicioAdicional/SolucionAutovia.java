package ejercicioAdicional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import ejercicioAdicional.gv.AutoviaEdge;
import ejercicioAdicional.gv.AutoviaVertice;

/**
 * Clase que representa una solución al problema de la Autovía A-92.
 * Almacena qué áreas de servicio han sido seleccionadas y permite 
 * el cálculo de métricas de calidad (beneficio y coste).
 */
public class SolucionAutovia {
    
    // Lista de índices de las áreas seleccionadas (donde x_i = 1)
    private List<Integer> indicesSeleccionados;
    private double beneficioTotal;
    private double costeTotal;

    /**
     * Constructor privado para forzar el uso de métodos de factoría.
     */
    private SolucionAutovia() {
        this.indicesSeleccionados = new ArrayList<>();
        this.beneficioTotal = 0;
        this.costeTotal = 0;
    }

    /**
     * Crea una solución a partir de una lista binaria (cromosoma).
     * Útil para Algoritmos Genéticos.
     * @param cromosoma Lista de enteros (0 o 1)
     */
    public static SolucionAutovia deCromosoma(List<Integer> cromosoma) {
        SolucionAutovia sol = new SolucionAutovia();
        for (int i = 0; i < cromosoma.size(); i++) {
            if (cromosoma.get(i) == 1) {
                sol.agregarPunto(i);
            }
        }
        return sol;
    }

    /**
     * Crea una solución a partir de la salida de Gurobi (valores de variables x_i).
     * Útil para Programación Lineal Entera.
     * @param valoresGurobi Lista de valores reales devueltos por el solver
     */
    public static SolucionAutovia deGurobi(List<Double> valoresGurobi) {
        SolucionAutovia sol = new SolucionAutovia();
        for (int i = 0; i < valoresGurobi.size(); i++) {
            // Gurobi devuelve doubles; usamos un umbral para determinar si es 1
            if (valoresGurobi.get(i) > 0.5) {
                sol.agregarPunto(i);
            }
        }
        return sol;
    }

    public static SolucionAutovia deGraphPath(GraphPath<AutoviaVertice, AutoviaEdge> camino) {
    	List<Integer> aristasCamino = camino.getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList());
        SolucionAutovia sol = deCromosoma(aristasCamino);
        return sol;
    }
	
    /**
     * Agrega un índice a la solución y actualiza los totales consultando DatosAutovia.
     */
    private void agregarPunto(int i) {
        indicesSeleccionados.add(i);
        // Se asume que DatosAutovia tiene estos métodos estáticos disponibles
        beneficioTotal += DatosAutovia.getBeneficio(i);
        costeTotal += DatosAutovia.getCosteConstruccion(i);
    }

    // --- OBSERVADORES ---

    public List<Integer> getIndicesSeleccionados() {
        return new ArrayList<>(indicesSeleccionados);
    }

    public double getBeneficioTotal() {
        return beneficioTotal;
    }

    public double getCosteTotal() {
        return costeTotal;
    }

    @Override
    public String toString() {
        return String.format("Solución [Beneficio: %.2f, Coste: %.2f, Áreas: %s]", 
                             beneficioTotal, costeTotal, indicesSeleccionados.toString());
    }
}