package ejercicio2.gv;

import java.util.ArrayList;
import java.util.List;

import ejercicio2.Datos2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class MainGreedy {

	public static void main(String[] args) {
		// 1. Cargamos Datos
		Datos2.iniDatos("src/ejercicio2/gv/DatosEntrada1.txt");
		
		// 2. Creamos el Vertice Inicial
		List<Integer> capacidadInicial = new ArrayList<>();
		for (Integer i = 0; i < Datos2.getNumContenedores(); i++) {
			capacidadInicial.add(Datos2.getTamContenedor(i));
		}
		VertexEj02Greedy vI = new VertexEj02Greedy(0, capacidadInicial);
		
		// 3. Creamos el Grafo Virtualç
		var gV = EGraph.virtual(vI).type(Type.Max).pathType(PathType.Sum).heuristic(VertexEj02Greedy::heuristica).build();
		
		// 4. Ejercutamos los ALgortimos
		var aBT = BT.ofGreedy(gV);
		var aS = AStar.ofGreedy(gV);
		
		var solBT = aBT.search();
		var solAS = aS.search();
		
		System.out.println(solBT.get().getEdgeList());
		System.out.println(solAS.get().getEdgeList());

	}

}
