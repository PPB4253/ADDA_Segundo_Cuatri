package ejercicioAdicional.pd;

import ejercicioAdicional.DatosAutovia;
import us.lsi.graphs.alg.PD;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class Main {

	public static void main(String[] args) {
		// 1. Cargar Datos
		DatosAutovia.iniDatos("src/ejercicioAdicional/DatosEntrada1.txt");
		
		// 2. Vertice Inicial
		HVEjAdicional vI = new HVEjAdicional(0, DatosAutovia.getPresupuestoMax());
		
		// 3. Creamos el Grafo Virtual
		var hG = SimpleVirtualHyperGraph.simpleVirtualHyperGraph(vI);
		
		// 4. Usamos el Algoritmo
		var pD = PD.dynamicProgrammingSearch(hG, PDType.Max);		// Pq queremos MAXIMIZAR
		pD.search();
		var arbol = pD.getSolutionsTree();
		
		var gT = GraphTree.graphTree(vI, arbol);
		System.out.println(gT.solution());
		
	}

}
