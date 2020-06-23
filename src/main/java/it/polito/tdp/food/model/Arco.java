package it.polito.tdp.food.model;

public class Arco {

	
	private Condimento c1;
	private Condimento c2;
	private Integer peso;
	
	public Arco(Condimento c1, Condimento c2, int peso) {
		this.c1 = c1;
		this.c2 = c2;
		this.peso = peso;
	}

	public Condimento getC1() {
		return c1;
	}

	public Condimento getC2() {
		return c2;
	}

	public Integer getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return  c1 + " " + c2 + " " + peso;
	}
	
	
}
