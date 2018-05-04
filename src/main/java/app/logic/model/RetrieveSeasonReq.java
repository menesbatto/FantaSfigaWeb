package app.logic.model;

public class RetrieveSeasonReq {

	
	private CompetitionBean competition;
	private String pattern;
	
	
	
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



	
	
}