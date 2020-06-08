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

	// variabili per lo stato della ricorsione
	private double pesoMax ;

	private List<String> camminoMax ;
	
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
	
	/*
	[ v1 v2 v3 v4 ] -> lungo N
	Soluzione parziale: un cammino che parte dal vertice iniziale
	Livello: lunghezza del camminio parziale
	Condizione di terminazione: Cammino ha lunghezza N (cioè N+1 vertici)
	Funzione da valutare: peso del cammino
	Generazione delle soluzioni: aggiungere gli adiacenti che non siano ancora
	presenti nel cammino
	Avvio della ricorsione: 1 vertice (di partenza)
		[ partenza ] , livello = 1
		[ partenza, v ] , livello = 2
		[ partenza, v2, v3, v4, .... v(N+1) ], livello = N+1  
	*/
	
	public void cercaCammino(String partenza, int N) {
		this.camminoMax = null ;
		this.pesoMax = 0.0 ;
		
		List<String> parziale = new ArrayList<>() ;
		parziale.add(partenza) ;
		
		search(parziale, 1, N);
	}
	
	private void search(List<String> parziale, int livello, int N) {
		
		if(livello == N+1) {
			double peso = pesoCammino(parziale) ;
			if(peso>this.pesoMax) {
				this.pesoMax=peso ;
				this.camminoMax = new ArrayList<>(parziale);
			}
			return ;
		}
		
		List<String> vicini = Graphs.neighborListOf(this.graph, parziale.get(livello-1)) ;
		for(String v : vicini) {
			if(!parziale.contains(v)) {
				parziale.add(v) ;
				search(parziale, livello+1, N) ;
				parziale.remove(parziale.size()-1) ;
			}
		}
	}

	private double pesoCammino(List<String> parziale) {
		double peso = 0.0 ;
		for(int i=1; i<parziale.size(); i++) {
			double p = this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1), parziale.get(i))) ;
			peso += p ;
		}
		return peso ;
	}
	
	public double getPesoMax() {
		return pesoMax;
	}

	public List<String> getCamminoMax() {
		return camminoMax;
	}


}
