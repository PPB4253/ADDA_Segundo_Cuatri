package ej01;

import us.lsi.graphs.alg.PD;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class Main {

	public static void main(String[] args) {		// Hyper Grafos Virtuales	(ESTOS NO TIENEN HEURISTICA)
		// 1. Cargar datos del problema
			// 1.2. Crear clase solucion		(todavia no lo hemos hecho)
		DatosMulticonjunto.iniDatos("src/ej01/ejemplo1_1.txt");
		
		// 2. Record hipervertices
		
		// 3. Hyperarista
		
		// 4. Construir el hypergrafo
		var vI = new Ej01HV(0, DatosMulticonjunto.SUMA);
		var hG = SimpleVirtualHyperGraph.simpleVirtualHyperGraph(vI);
		
		// 5. Crear y ejecutar PD (Programacion Dinamica)									// SIEMPRE ES TODO IGUAL
		var pD = PD.dynamicProgrammingSearch(hG, PDType.Min);		// Minimizar, pues Min
		pD.search();
		var arbol = pD.getSolutionsTree();
		
		// 6. Procesar la SOL
		var gT = GraphTree.graphTree(vI, arbol);
		System.out.println(gT.solution());

	}

}
