package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	
	private FoodDAO dao;
	private Graph<Condimento, DefaultWeightedEdge> grafo;
	private Map<Integer, Condimento> idMap;
	private List<Condimento> listaVertici;
	
	public Model() {
		dao = new FoodDAO();
		
	}
	
	public void creaGrafo(Double calorie) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		
		listaVertici = dao.getVertici(calorie, idMap);  // stesso condimento, diversa porzione
		Graphs.addAllVertices(this.grafo, listaVertici); //salva codice !=
		
		
		for(Arco a : dao.archi(idMap)) {
			Graphs.addEdge(this.grafo, a.getC1(), a.getC2(), a.getPeso());
		}
		
		
	}
	
	public Integer nVertici() {
		return this.grafo.vertexSet().size();
		
	}
	public Integer nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Condimento> listaVertici(){
		Collections.sort(this.listaVertici);
		return listaVertici; 
	}
	
	public Integer nCibi(Condimento c) {
		Integer pesoTot = 0;
		
		List<Condimento> vicini = Graphs.neighborListOf(this.grafo, c);
		
		for(Condimento cond : vicini) {
			Integer peso = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(c, cond));
			pesoTot += peso;
			
		}
		
		return pesoTot;
	}

}
