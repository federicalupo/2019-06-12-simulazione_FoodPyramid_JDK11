package it.polito.tdp.food.model;

import java.util.ArrayList;
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
	private List<Condimento> listaMigliore;
	private Double calorieMax;
	
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
	
	/**
	 * parziale in cui metto condimento di partenza
	 * sommo caloria
	 * mi faccio dare gli adiacenti
	 * 
	 * scorro tutti i vertici che ho
	 * vedo se ognuno appartiene ad adiacenti, se non appartiene, aggiungo
	 * sommo calorie 
	 * 
	 * backtracking
	 * 
	 * calorie da passare come parametro
	 * 
	 * terminazione =>
	 * calorie > calorieMax
	 * aggiorno
	 * non metto return
	 * 
	 * @param c
	 */
	
	public List<Condimento> ingredientiIndipendenti(Condimento c) {
		
		this.calorieMax = Double.MIN_VALUE;
		this.listaMigliore = new ArrayList<>();
		
		
		List<Condimento> parziale = new ArrayList<>();
		parziale.add(c);
		
		Double calorie = c.getCalorie();
		
		ricorsiva(parziale, c, calorie);
		
		return listaMigliore;
	
	}
	
	public void ricorsiva(List<Condimento> parziale, Condimento c, Double calorie) {
		
		if(calorie > calorieMax) {
			calorieMax = calorie;
			this.listaMigliore = new ArrayList<>(parziale);
			
		}
		
		
		List<Condimento> vicini = Graphs.neighborListOf(this.grafo, c);
		
		for(Condimento cond : this.grafo.vertexSet()) {  //PROBLEMA : ho a collegato con b,c ; b collegato con altri..... Parto da a
														//nella scansione capita d che non è adiacente ad a, lo aggiungo.. Poi ricomincia la scansione e capita b che non è collegato 
														//a d, lo aggiungo... però è collegato ad a
			if(! vicini.contains(cond) && !parziale.contains(cond)) { //CONTROLLO ANCHE CHE NON SIA IN PARZIALE!!!!!
				parziale.add(cond);
				calorie += cond.getCalorie();
				ricorsiva(parziale, cond, calorie);
				parziale.remove(cond);				
			}
		}
		
		
	}

	public Double getCalorieMax() {
		return calorieMax;
	}
	
	

}
