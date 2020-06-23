package it.polito.tdp.food.model;

public class Condimento implements Comparable<Condimento>{
	private Integer codice;
	private Double calorie;
	
	
	public Condimento(Integer codice, Double calorie) {
		super();
		this.codice = codice;
		this.calorie = calorie;
	}


	public Integer getCodice() {
		return codice;
	}


	public Double getCalorie() {
		return calorie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Condimento other = (Condimento) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		return true;
	}


	@Override
	public int compareTo(Condimento o) {
		return -this.calorie.compareTo(o.getCalorie());
	}


	@Override
	public String toString() {
		return  codice + " " + calorie ;
	}
	
	
	
	
}
