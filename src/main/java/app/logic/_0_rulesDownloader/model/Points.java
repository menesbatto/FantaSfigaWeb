package app.logic._0_rulesDownloader.model;


import java.io.Serializable;
import java.util.List;

public class Points  implements Serializable{

	private static final long serialVersionUID = -6475495633212412217L;

	private List<Double> goalPoints;
	private List<Double> formulaUnoPoints;
	
	//Se due squadre totalizzano un punteggio all'interno della stessa fascia, si aggiunge un gol alla squadra che avra' distaccato l'avversario del valore specificato.
	private Boolean fasciaConIntornoActive;
	private Double fasciaConIntorno;
	
	//Se i punteggi delle due squadre cadono in fasce differenti, vince chi distacca l'avversario di almeno del valore specificato
	private Boolean intornoActive;
	private Double intorno;
	
	//Se l'intorno vale anche tra 0 e 1
	private Boolean intorno01Active;
	
	
	//Se due squadre ottengono un punteggio inferiore a Soglia gol specificare lo scarto per far scattare il gol alla squadra con punteggio maggiore
	private Boolean controllaPareggioActive;
	private Double controllaPareggio;
	
	//Se la differenza dei punti-squadra è uguale o superiore al valore impostato, si attribuisce un altro gol alla squadra con piu' punti
	private Boolean differenzaPuntiActive;
	private Double differenzaPunti;
	
	//Se una delle due squadre ottiene un punteggio inferiore al valore impostato, si attribuisce un altro gol alla squadra avversaria
	private Boolean autogolActive;
	private Double autogol;
	
	public List<Double> getGoalPoints() {
		return goalPoints;
	}
	public void setGoalPoints(List<Double> goalPoints) {
		this.goalPoints = goalPoints;
	}
	public List<Double> getFormulaUnoPoints() {
		return formulaUnoPoints;
	}
	public void setFormulaUnoPoints(List<Double> formulaUnoPoints) {
		this.formulaUnoPoints = formulaUnoPoints;
	}
	public Boolean isFasciaConIntornoActive() {
		return fasciaConIntornoActive;
	}
	public void setFasciaConIntornoActive(Boolean fasciaConIntornoActive) {
		this.fasciaConIntornoActive = fasciaConIntornoActive;
	}
	public Double getFasciaConIntorno() {
		return fasciaConIntorno;
	}
	public void setFasciaConIntorno(Double fasciaConIntorno) {
		this.fasciaConIntorno = fasciaConIntorno;
	}
	public Boolean isIntornoActive() {
		return intornoActive;
	}
	public void setIntornoActive(Boolean intornoActive) {
		this.intornoActive = intornoActive;
	}
	public Double getIntorno() {
		return intorno;
	}
	public void setIntorno(Double intorno) {
		this.intorno = intorno;
	}
	public Boolean isControllaPareggioActive() {
		return controllaPareggioActive;
	}
	public void setControllaPareggioActive(Boolean controllaPareggioActive) {
		this.controllaPareggioActive = controllaPareggioActive;
	}
	public Double getControllaPareggio() {
		return controllaPareggio;
	}
	public void setControllaPareggio(Double controllaPareggio) {
		this.controllaPareggio = controllaPareggio;
	}
	public Boolean isDifferenzaPuntiActive() {
		return differenzaPuntiActive;
	}
	public void setDifferenzaPuntiActive(Boolean differenzaPuntiActive) {
		this.differenzaPuntiActive = differenzaPuntiActive;
	}
	public Double getDifferenzaPunti() {
		return differenzaPunti;
	}
	public void setDifferenzaPunti(Double differenzaPunti) {
		this.differenzaPunti = differenzaPunti;
	}

	
	@Override
	public String toString() {
		return "Points [goalPoints=" + goalPoints + "\n formulaUnoPoints=" + formulaUnoPoints
				+ "\n isFasciaConIntornoActive=" + fasciaConIntornoActive + "\n fasciaConIntorno=" + fasciaConIntorno
				+ "\n isIntornoActive=" + intornoActive + "\n intorno=" + intorno + "\n "
				+ "intorno01Active=" + intorno01Active + "\n " + "isControllaPareggioActive="
				+ controllaPareggioActive + "\n controllaPareggio=" + controllaPareggio + "\n isDifferenzaPuntiActive="
				+ differenzaPuntiActive + "\n differenzaPunti=" + differenzaPunti + "]";
	}
	public Boolean getAutogolActive() {
		return autogolActive;
	}
	public void setAutogolActive(Boolean autogolActive) {
		this.autogolActive = autogolActive;
	}
	public Double getAutogol() {
		return autogol;
	}
	public void setAutogol(Double autogol) {
		this.autogol = autogol;
	}
	public Boolean isIntorno01Active() {
		return intorno01Active;
	}
	public void setIntorno01Active(Boolean intorno01Active) {
		this.intorno01Active = intorno01Active;
	}
	

}
