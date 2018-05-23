package app.logic._1_seasonPatternExtractor.model;

import java.io.Serializable;

import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;

public class MatchBean  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6875552801573617783L;
	private String homeTeam;
	private String awayTeam;
	private PlayerEnum homeTeamEnum;
	private PlayerEnum awayTeamEnum;
	
	
	// utile solo nella 5 fase per verificare se il risultato calcolato è uguale al risultato sul sito
	private LineUpLightBean homeTeamResult;
	private LineUpLightBean awayTeamResult;
	
	
	
	
	
	
	
	public MatchBean(String homeTeam, String awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	public MatchBean() {

	}
	
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public PlayerEnum getHomeTeamEnum() {
		return homeTeamEnum;
	}
	public void setHomeTeamEnum(PlayerEnum homeTeamEnum) {
		this.homeTeamEnum = homeTeamEnum;
	}
	public PlayerEnum getAwayTeamEnum() {
		return awayTeamEnum;
	}
	public void setAwayTeamEnum(PlayerEnum awayTeamEnum) {
		this.awayTeamEnum = awayTeamEnum;
	}
	public LineUpLightBean getHomeTeamResult() {
		if (homeTeamResult == null){
			homeTeamResult = new LineUpLightBean();
		}
		return homeTeamResult;
	}
	public void setHomeTeamResult(LineUpLightBean homeTeamResult) {
		this.homeTeamResult = homeTeamResult;
	}
	public LineUpLightBean getAwayTeamResult() {
		if (awayTeamResult == null){
			awayTeamResult = new LineUpLightBean();
		}
		return awayTeamResult;
	}
	public void setAwayTeamResult(LineUpLightBean awayTeamResult) {
		this.awayTeamResult = awayTeamResult;
	}
	@Override
	public String toString() {
		return "" + getHomeTeam() + "\t " + getAwayTeam() + "\t "
				+ getHomeTeamResult().getGoals() + "\t " + getAwayTeamResult().getGoals() + "\n";
//		return "Match [getHomeTeam()=" + getHomeTeam() + "\n getAwayTeam()=" + getAwayTeam() + "\n getHomeTeamEnum()="
//		+ getHomeTeamEnum() + "\n getAwayTeamEnum()=" + getAwayTeamEnum() + "\n getHomeTeamResult()="
//		+ getHomeTeamResult() + "\n getAwayTeamResult()=" + getAwayTeamResult() + "]";
	}


	
	
	
	
}
