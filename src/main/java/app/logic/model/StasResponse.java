package app.logic.model;

import app.logic._4_seasonsExecutor.model.RankingBean;

public class StasResponse {
	
	private String competitionShortName;
	private String leagueShortName;
	
	private RankingBean realRanking;
	
	private RankingBean realLightRanking;
	
	
	
	private RankingBean fairRanking;
	
	private RankingBean deltaFairRanking;

	
	
	private RankingBean positionsRanking;
	
	private RankingBean positionsPercentaleRanking;
	
	private RankingBean averagePositionRanking;
	
	private RankingBean deltaPositionRankings;
	
	
	public StasResponse() {
	}
	
	public RankingBean getPositionsPercentaleRanking() {
		return positionsPercentaleRanking;
	}

	public void setPositionsPercentaleRanking(RankingBean positionsPercentaleRanking) {
		this.positionsPercentaleRanking = positionsPercentaleRanking;
	}

	public RankingBean getAveragePositionRanking() {
		return averagePositionRanking;
	}

	public void setAveragePositionRanking(RankingBean averagePositionRanking) {
		this.averagePositionRanking = averagePositionRanking;
	}

	public RankingBean getDeltaPositionRankings() {
		return deltaPositionRankings;
	}

	public void setDeltaPositionRankings(RankingBean deltaPositionRankings) {
		this.deltaPositionRankings = deltaPositionRankings;
	}

	public RankingBean getDeltaFairRanking() {
		return deltaFairRanking;
	}

	public void setDeltaFairRanking(RankingBean deltaFairRanking) {
		this.deltaFairRanking = deltaFairRanking;
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


	public RankingBean getFairRanking() {
		return fairRanking;
	}

	public void setFairRanking(RankingBean fairRanking) {
		this.fairRanking = fairRanking;
	}

	public RankingBean getPositionsRanking() {
		return positionsRanking;
	}

	public void setPositionsRanking(RankingBean positionsRanking) {
		this.positionsRanking = positionsRanking;
	}

	@Override
	public String toString() {
		return "StasResponse [competitionShortName=" + competitionShortName + ", leagueShortName=" + leagueShortName
				+ ", realRanking=" + realRanking + ", positionsRanking=" + positionsRanking + ", fairRanking="
				+ fairRanking + ", positionsPercentaleRanking=" + positionsPercentaleRanking
				+ ", averagePositionRanking=" + averagePositionRanking + ", deltaPositionRankings="
				+ deltaPositionRankings + ", deltaFairRanking=" + deltaFairRanking + "]";
	}

	public RankingBean getRealLightRanking() {
		return realLightRanking;
	}

	public void setRealLightRanking(RankingBean realLightRanking) {
		this.realLightRanking = realLightRanking;
	}
	
	
	
	
	
}
