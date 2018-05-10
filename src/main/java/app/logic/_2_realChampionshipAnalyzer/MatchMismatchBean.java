package app.logic._2_realChampionshipAnalyzer;

import java.io.Serializable;

import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;

public class MatchMismatchBean implements Serializable{

	private static final long serialVersionUID = 7181258422299195006L;

	
	private LineUpLightBean homeLulApp;
	private LineUpLightBean awayLulApp;
	private LineUpLightBean homeLulWeb;
	private LineUpLightBean awayLulWeb;
	
	private Integer serieASeasonDay;
	private Integer competitonSeasonDay;
	
	
	
	
	
	public MatchMismatchBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getSerieASeasonDay() {
		return serieASeasonDay;
	}
	public void setSerieASeasonDay(Integer serieASeasonDay) {
		this.serieASeasonDay = serieASeasonDay;
	}
	public Integer getCompetitonSeasonDay() {
		return competitonSeasonDay;
	}
	public void setCompetitonSeasonDay(Integer competitonSeasonDay) {
		this.competitonSeasonDay = competitonSeasonDay;
	}
	public LineUpLightBean getHomeLulApp() {
		return homeLulApp;
	}
	public void setHomeLulApp(LineUpLightBean homeLulApp) {
		this.homeLulApp = homeLulApp;
	}
	public LineUpLightBean getAwayLulApp() {
		return awayLulApp;
	}
	public void setAwayLulApp(LineUpLightBean awayLulApp) {
		this.awayLulApp = awayLulApp;
	}
	public LineUpLightBean getHomeLulWeb() {
		return homeLulWeb;
	}
	public void setHomeLulWeb(LineUpLightBean homeLulWeb) {
		this.homeLulWeb = homeLulWeb;
	}
	public LineUpLightBean getAwayLulWeb() {
		return awayLulWeb;
	}
	public void setAwayLulWeb(LineUpLightBean awayLulWeb) {
		this.awayLulWeb = awayLulWeb;
	}
	
}
