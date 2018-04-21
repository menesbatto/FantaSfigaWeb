package app.logic.model;

import app.RulesType;

public class CalculateStatsReq {

	
	private CompetitionBean competition;
	private Boolean light;
	private RulesType rulesType;
	
	
	
	public CalculateStatsReq() {
	}



	public CompetitionBean getCompetition() {
		return competition;
	}



	public void setCompetition(CompetitionBean competition) {
		this.competition = competition;
	}



	public Boolean getLight() {
		return light;
	}



	public void setLight(Boolean light) {
		this.light = light;
	}



	public RulesType getRulesType() {
		return rulesType;
	}



	public void setRulesType(RulesType rulesType) {
		this.rulesType = rulesType;
	}

	
	
}