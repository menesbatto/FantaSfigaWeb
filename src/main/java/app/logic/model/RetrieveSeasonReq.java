package app.logic.model;

import app.RulesType;

public class RetrieveSeasonReq {

	
	private CompetitionBean competition;
	private String pattern;
	private RulesType rulesType;
	
	
	
	public String getPattern() {
		return pattern;
	}



	public void setPattern(String pattern) {
		this.pattern = pattern;
	}



	public RetrieveSeasonReq() {
	}



	public CompetitionBean getCompetition() {
		return competition;
	}



	public void setCompetition(CompetitionBean competition) {
		this.competition = competition;
	}



	public RulesType getRulesType() {
		return rulesType;
	}



	public void setRulesType(RulesType rulesType) {
		this.rulesType = rulesType;
	}



	
	
}