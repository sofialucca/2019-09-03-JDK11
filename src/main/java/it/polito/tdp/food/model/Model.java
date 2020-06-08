package it.polito.tdp.food.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> graph ;
	
	public void creaGrafo(int C) {
		
		FoodDao dao = new FoodDao() ;
		
		List<String> vertici = dao.getPortionDisplayNames(C) ;
		
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class) ;
		
		Graphs.addAllVertices(this.graph, vertici) ;
		
		System.out.println(this.graph) ;
		
	}
	
}
