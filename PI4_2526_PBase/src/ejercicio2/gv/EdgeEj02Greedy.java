package ejercicio2.gv;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record EdgeEj02Greedy(VertexEj02Greedy source, VertexEj02Greedy target, Integer action, Double weight) implements SimpleEdgeAction<VertexEj02Greedy, Integer> {

}
