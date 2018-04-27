package app.logic._0_rulesDownloader.model;

import java.io.Serializable;

import app.dao.RankingType;

public class CustomRules implements Serializable{
	
	private static final long serialVersionUID = -8424516479545346363L;
	
	private boolean invertHomeAway;
	private Double luckyEdgePoints;
	
	private RankingType rankingType;
	
	public CustomRules() {
	}

	
	public Boolean getInvertHomeAway() {
		return invertHomeAway;
	}
	public void setInvertHomeAway(Boolean invertHomeAway) {
		this.invertHomeAway = invertHomeAway;
	}


	@Override
	public String toString() {
		return "CustomRules [invertHomeAway=" + invertHomeAway + "";
	}


	public RankingType getRankingType() {
		return rankingType;
	}


	public void setRankingType(RankingType rankingType) {
		this.rankingType = rankingType;
	}


	public Double getLuckyEdgePoints() {
		return luckyEdgePoints;
	}


	public void setLuckyEdgePoints(Double luckyEdgePoints) {
		this.luckyEdgePoints = luckyEdgePoints;
	}


	
	
	
}
