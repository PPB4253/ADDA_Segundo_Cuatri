package ejercicioAdicional.gv;

import ejercicioAdicional.DatosAutovia;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class MainEjAdicional {

	public static void main(String[] args) {
		// 1. Cargamos Datos
		DatosAutovia.iniDatos("src/ejercicioAdicional/DatosEntrada1.txt");

		// 2. Vertice Inicia
		AutoviaVertice vI = new AutoviaVertice(0, DatosAutovia.getPresupuestoMax());
		
		// 3. Creamos el Grafo Virtual
		var gV = EGraph.virtual(vI).type(Type.Max).pathType(PathType.Sum).heuristic(AutoviaVertice::heuristica).build();
		
		// 4. Usamos Algoritmos
		var aBT = BT.of(gV);
		var aS = AStar.of(gV);
		
		var solBT = aBT.search();
		var solAS = aS.search();
		
		System.out.println(solBT.get().getEdgeList());
		System.out.println(solAS.get().getEdgeList());
	}

}
