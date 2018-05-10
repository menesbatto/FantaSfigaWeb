package app.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;

@Entity
public class MatchMismatch {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(cascade=CascadeType.ALL)
	private LineUpLight homeLulApp;
	@OneToOne(cascade=CascadeType.ALL)
	private LineUpLight awayLulApp;
	@OneToOne(cascade=CascadeType.ALL)
	private LineUpLight homeLulWeb;
	@OneToOne(cascade=CascadeType.ALL)
	private LineUpLight awayLulWeb;
	
	private Integer serieASeasonDay;
	private Integer competitonSeasonDay;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LineUpLight getHomeLulApp() {
		return homeLulApp;
	}
	public void setHomeLulApp(LineUpLight homeLulApp) {
		this.homeLulApp = homeLulApp;
	}
	public LineUpLight getAwayLulApp() {
		return awayLulApp;
	}
	public void setAwayLulApp(LineUpLight awayLulApp) {
		this.awayLulApp = awayLulApp;
	}
	public LineUpLight getHomeLulWeb() {
		return homeLulWeb;
	}
	public void setHomeLulWeb(LineUpLight homeLulWeb) {
		this.homeLulWeb = homeLulWeb;
	}
	public LineUpLight getAwayLulWeb() {
		return awayLulWeb;
	}
	public void setAwayLulWeb(LineUpLight awayLulWeb) {
		this.awayLulWeb = awayLulWeb;
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
	@Override
	public String toString() {
		return "MatchMismatch [id=" + id + ", homeLulApp=" + homeLulApp + ", awayLulApp=" + awayLulApp + ", homeLulWeb="
				+ homeLulWeb + ", awayLulWeb=" + awayLulWeb + ", serieASeasonDay=" + serieASeasonDay
				+ ", competitonSeasonDay=" + competitonSeasonDay + "]";
	}
	public MatchMismatch() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
