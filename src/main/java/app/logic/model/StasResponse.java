package app.logic.model;

import app.dao.RankingType;
import app.logic._4_seasonsExecutor.model.RankingBean;

public class StasResponse {
	
	private String competitionShortName;
	private String leagueShortName;
	private CompetitionBean competition;

	
	private RankingBean realRanking;
	
	private RankingBean realLightRanking;
	
	
	
	private RankingBean fairRanking;
	
	private RankingBean deltaFairRanking;

	
	
	private RankingBean positionsRanking;
	
	private RankingBean positionsPercentaleRanking;
	
	private RankingBean averagePositionRanking;
	
	private RankingBean deltaPositionRankings;
	
	
	
	private RankingBean invertHomeAwayRanking;
	
	private RankingBean luckyEdgeRanking05;
	
	private RankingBean luckyEdgeRanking1;

	
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

	public RankingBean getInvertHomeAwayRanking() {
		return invertHomeAwayRanking;
	}

	public void setInvertHomeAwayRanking(RankingBean invertHomeAwayRanking) {
		this.invertHomeAwayRanking = invertHomeAwayRanking;
	}

	public RankingBean getLuckyEdgeRanking05() {
		return luckyEdgeRanking05;
	}

	public void setLuckyEdgeRanking05(RankingBean luckyEdgeRanking05) {
		this.luckyEdgeRanking05 = luckyEdgeRanking05;
	}

	public RankingBean getLuckyEdgeRanking1() {
		return luckyEdgeRanking1;
	}

	public void setLuckyEdgeRanking1(RankingBean luckyEdgeRanking1) {
		this.luckyEdgeRanking1 = luckyEdgeRanking1;
	}

	public CompetitionBean getCompetition() {
		return competition;
	}

	public void setCompetition(CompetitionBean competition) {
		this.competition = competition;
	}
	
	
	
	
	
}
