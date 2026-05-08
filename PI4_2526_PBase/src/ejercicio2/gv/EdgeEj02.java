package ejercicio2.gv;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record EdgeEj02(VertexEj02 source, VertexEj02 target, Integer action, Double weight) implements SimpleEdgeAction<VertexEj02, Integer> {

}
