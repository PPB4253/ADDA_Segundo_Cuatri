package ejercicioAdicional.gv;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record AutoviaEdge(AutoviaVertice source, AutoviaVertice target, Integer action, Double weight) implements SimpleEdgeAction<AutoviaVertice, Integer> {

}
