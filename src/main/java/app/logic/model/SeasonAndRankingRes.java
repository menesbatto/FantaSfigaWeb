package app.logic.model;

import java.io.Serializable;

import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._4_seasonsExecutor.model.RankingBean;

public class SeasonAndRankingRes implements Serializable {

	private static final long serialVersionUID = -9074938878778729334L;

	private SeasonBean season;
	
	private RankingBean ranking;
	
	public SeasonAndRankingRes() {
	}

	public SeasonBean getSeason() {
		return season;
	}

	public void setSeason(SeasonBean season) {
		this.season = season;
	}

	public RankingBean getRanking() {
		return ranking;
	}

	public void setRanking(RankingBean ranking) {
		this.ranking = ranking;
	}
	
	
	
}
