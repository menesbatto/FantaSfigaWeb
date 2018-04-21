package app.logic.model;

import app.RulesType;

public class RetrieveAllRankingsReq {

	
	private CompetitionBean competition;
	private RulesType rulesType;
	
	
	
	public RulesType getRulesType() {
		return rulesType;
	}



	public void setRulesType(RulesType rulesType) {
		this.rulesType = rulesType;
	}



	public RetrieveAllRankingsReq() {
	}



	public CompetitionBean getCompetition() {
		return competition;
	}



	public void setCompetition(CompetitionBean competition) {
		this.competition = competition;
	}





	
	
}