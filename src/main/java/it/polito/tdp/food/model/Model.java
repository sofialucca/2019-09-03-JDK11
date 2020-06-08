package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private Graph<String, DefaultWeightedEdge> graph;
	private List<String> vertici;

	public String creaGrafo(int C) {

		FoodDao dao = new FoodDao();

		this.vertici = dao.getPortionDisplayNames(C);

		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		Graphs.addAllVertices(this.graph, vertici);

		List<InfoArco> archi = dao.getTuttiGliArchi();

		for (InfoArco a : archi) {
			if (this.graph.vertexSet().contains(a.getVertice1()) && 
					this.graph.vertexSet().contains(a.getVertice2())) {
				Graphs.addEdge(this.graph, a.getVertice1(), a.getVertice2(), a.getPeso());
			}
		}
		
		return String.format("Grafo creato (%d vertici, %d archi)\n", 
				this.graph.vertexSet().size(), this.graph.edgeSet().size()); 

//		System.out.println(this.graph);

	}
	
	public List<PorzioneAdiacente> getAdiacenti(String porzione) {
		List<String> vicini = Graphs.neighborListOf(this.graph, porzione) ;
		List<PorzioneAdiacente> result = new ArrayList<>();
		for(String v: vicini) {
			double peso = this.graph.getEdgeWeight(this.graph.getEdge(porzione, v)) ;
			result.add(new PorzioneAdiacente(v, peso)) ;
		}
		return result ;
	}

	public List<String> getVerticiGrafo() {
		return this.vertici;
	}

}
