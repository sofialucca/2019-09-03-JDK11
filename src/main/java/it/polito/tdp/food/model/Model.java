package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String,DefaultWeightedEdge> grafo;
	private List<String> vertici;
	private List<String> risultatoCammino;
	private int camminoOttimo;
	private FoodDao dao;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(double cal){
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//vertici
		vertici = dao.getPorzioni(cal);
		Graphs.addAllVertices(grafo, vertici);
		
		//archi
		for(Adiacenti a : dao.getArchi(vertici, cal)) {
			Graphs.addEdge(grafo, a.getP1(), a.getP2(), a.getPeso());
		}
		
	}
	
	public List<String> getVertici(){
		return vertici;
	}
	
	public int getEdgeSize() {
		return grafo.edgeSet().size();
	}
	
	public Map<String,Integer> getCorrelate(String partenza){
		Map<String,Integer> result = new HashMap<>();
		for(String s : Graphs.neighborListOf(grafo, partenza)) {
			DefaultWeightedEdge e = grafo.getEdge(s, partenza);
			result.put(s, (int) grafo.getEdgeWeight(e));
		}
		return result;
	}
	
	public List<String> getCammino(int n, String partenza){
		risultatoCammino = new ArrayList<>();
		camminoOttimo = 0;
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		cerca(parziale, partenza, 0, n);
		return risultatoCammino;
	}

	private void cerca(List<String> parziale, String precedente, int costoCammino, int passiMax) {
		if(parziale.size()-1 == passiMax) {
			if(costoCammino>camminoOttimo) {
				camminoOttimo = costoCammino;
				risultatoCammino = new ArrayList<>(parziale);
			}
			return;
		}else {
			for(String s : Graphs.neighborListOf(grafo, precedente)) {
				if(!parziale.contains(s)) {
					parziale.add(s);
					DefaultWeightedEdge e = grafo.getEdge(s, precedente);
					int nuovoCosto = (int) grafo.getEdgeWeight(e) + costoCammino;
					cerca(parziale, s, nuovoCosto, passiMax);
					parziale.remove(s);
				}
			}
		}
		
		
	}
	
	public int getCostoCammino() {
		return this.camminoOttimo;
	}
}
