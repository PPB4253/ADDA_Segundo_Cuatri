package ej02;

import java.util.HashSet;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;

public class Main {

	public static void main(String[] args) {
		// 1. Ver, entender, cargar datos del problema
		DatosSubconjunto.iniDatos("src/ej02/ejemplo2_1.txt");
		
		// 2. Implementar el vértice virtual (LO TOCHO DE ESTO)
		// Es un Record no clase, default, atributos, ...
		
		// 3. Arista Virtual
		
		// 4. Heurística	(es obligatoria, pero puede que los resultados den bien sin ponerla)
		
		// 5. Usar el constructor de grafos para crear el Grafo Virtual (GV)
		Ej02Vertex vI = new Ej02Vertex(0, new HashSet<>(DatosSubconjunto.getUniverso()));	// El inicial empieza en 0 y tiene pendientes tedos los elementos (el universo)
		var gV = EGraph.virtual(vI).type(Type.Min).pathType(PathType.Sum).heuristic(Ej02Vertex::miHeu).build();	// Siempre empieza con .virtual() y acaba con build()
		// Le dices que empiece en vI, que quieres Minimizar el coste (Type.Min), que el coste de la ruta es la suma de los pasos (PathType.Sum) y le pasas la Heurística.
		// vI es el startVertex, nunca usar startVertex pq da un error chungisimo
		// EN WEIGHT PONER LO QUE MAXIMIZAR O MINIMIZAR, y .type le decimos si queremos minimizar o maximizar
		
		// 6. Crear el Algoritmo (tenemos 3 tipos aBT (BackTracking), aS (A estrella) y voraz (que falta por hacer))
		var aBT = BT.of(gV);
		var aS = AStar.of(gV);
		
		// 7. Ejecutar y procesar solución
		var solBT = aBT.search();
		System.out.println(solBT.get().getEdgeList());
		
		var solAS = aS.search();
		System.out.println(solAS.get().getEdgeList());
		
	}

}
