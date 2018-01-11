package app.logic._2_seasonPatternExtractor.model;

import java.io.Serializable;

import app.logic._1_realChampionshipAnalyzer.model.LineUpLightBean;

public class Match  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6875552801573617783L;
	private String homeTeam;
	private String awayTeam;
	private PlayerEnum homeTeamEnum;
	private PlayerEnum awayTeamEnum;
	
	private LineUpLightBean homeTeamResult;
	private LineUpLightBean awayTeamResult;
	
	
	
	
	
	
	
	public Match(String homeTeam, String awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	public Match() {

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
		return "" + getHomeTeam().substring(0,7) + "\t " + getAwayTeam().substring(0,7) + "\t "
				+ getHomeTeamResult().getGoals() + "\t " + getAwayTeamResult().getGoals() + "\n";
//		return "Match [getHomeTeam()=" + getHomeTeam() + "\n getAwayTeam()=" + getAwayTeam() + "\n getHomeTeamEnum()="
//		+ getHomeTeamEnum() + "\n getAwayTeamEnum()=" + getAwayTeamEnum() + "\n getHomeTeamResult()="
//		+ getHomeTeamResult() + "\n getAwayTeamResult()=" + getAwayTeamResult() + "]";
	}


	
	
	
	
}
