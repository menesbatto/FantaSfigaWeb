package app.logic.model;

import app.logic._4_seasonsExecutor.model.RankingBean;

public class StasResponse {
	
	private String competitionShortName;
	private String leagueShortName;
	
	private RankingBean realRanking;
	private RankingBean positionRanking;
	private RankingBean fairRanking;
	
	public StasResponse() {
	}

	public String getCompetitionShortName() {
		return competitionShortName;
	}

	public void setCompetitionShortName(String competitionShortName) {
		this.competitionShortName = competitionShortName;
	}

	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
	}

	public RankingBean getRealRanking() {
		return realRanking;
	}

	public void setRealRanking(RankingBean realRanking) {
		this.realRanking = realRanking;
	}

	public RankingBean getPositionRanking() {
		return positionRanking;
	}

	public void setPositionRanking(RankingBean positionRanking) {
		this.positionRanking = positionRanking;
	}

	public RankingBean getFairRanking() {
		return fairRanking;
	}

	public void setFairRanking(RankingBean fairRanking) {
		this.fairRanking = fairRanking;
	}

	@Override
	public String toString() {
		return "StasResponse [competitionShortName=" + competitionShortName + ", leagueShortName=" + leagueShortName
				+ ", realRanking=" + realRanking + ", positionRanking=" + positionRanking + ", fairRanking="
				+ fairRanking + "]";
	}
	
	
	
	
	
}
