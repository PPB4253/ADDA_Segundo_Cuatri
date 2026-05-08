package ejercicio2.gv;

import java.util.ArrayList;
import java.util.List;

import ejercicio2.Datos2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class Main {

	public static void main(String[] args) {
		// 1. Cargamos Datos
		Datos2.iniDatos("src/ejercicio2/gv/DatosEntrada1.txt");
		
		// 2. Vertice Inicia
		List<Integer> capacidadInicial =  new ArrayList<>();
		for (Integer i = 0; i < Datos2.getNumContenedores(); i++) {
			capacidadInicial.add(Datos2.getTamContenedor(i));
		}
		VertexEj02 vI = new VertexEj02(0, capacidadInicial);
		
		// 3. Creamos el Grafo Virtual
		var gV = EGraph.virtual(vI).type(Type.Max).pathType(PathType.Sum).heuristic(VertexEj02::heuristica).build();

		// 4. Usamos Algoritmos
		var aBT = BT.of(gV);
		var aS = AStar.of(gV);
		
		var solBT = aBT.search();
		var solAs = aS.search();
		
		System.out.println(solBT.get().getEdgeList());
		System.out.println(solAs.get().getEdgeList());
	}

}
