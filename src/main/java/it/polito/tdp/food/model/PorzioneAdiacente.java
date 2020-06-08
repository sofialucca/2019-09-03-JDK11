package it.polito.tdp.food.model;

public class PorzioneAdiacente {
	
	private String porzione ;
	private Double peso ;
	/**
	 * @param porzione
	 * @param peso
	 */
	public PorzioneAdiacente(String porzione, Double peso) {
		super();
		this.porzione = porzione;
		this.peso = peso;
	}
	public String getPorzione() {
		return porzione;
	}
	public void setPorzione(String porzione) {
		this.porzione = porzione;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	

}
