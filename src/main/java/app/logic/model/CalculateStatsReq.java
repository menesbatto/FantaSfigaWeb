package app.logic.model;

public class CalculateStatsReq {

	
	private CompetitionBean competition;
	private Boolean light;
	
	
	
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

	
	
}