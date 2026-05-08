package ejercicio1.pd;

import java.util.ArrayList;

import ejercicio1.Datos1;
import us.lsi.graphs.alg.PD;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class Main {

	public static void main(String[] args) {		// Hyper Grafos Virtuales	(ESTOS NO TIENEN HEURISTICA)
		// 1. Cargar datos del problema
			// 1.2. Crear clase solucion		(todavia no lo hemos hecho)
		Datos1.iniDatos("src/ejercicio1/pd/DatosEntrada1.txt");
		
		// 2. Record hipervertices
		
		// 3. Hyperarista
		
		// 4. Construir el hypergrafo
		HVEj1 vI = new HVEj1(0, 0.0, 0, Datos1.getCualidades(), new ArrayList<>());
		var hG = SimpleVirtualHyperGraph.simpleVirtualHyperGraph(vI);
		
		// 5. Crear y ejecutar PD (Programacion Dinamica)									// SIEMPRE ES TODO IGUAL
		var pD = PD.dynamicProgrammingSearch(hG, PDType.Max);		// Maximizar, pues Max
		pD.search();
		var arbol = pD.getSolutionsTree();
		
		// 6. Procesar la SOL
		var gT = GraphTree.graphTree(vI, arbol);
		System.out.println(gT.solution());

	}

}
