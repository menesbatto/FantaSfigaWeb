package app.logic._2_realChampionshipAnalyzer;

import java.io.Serializable;
import java.util.List;

import app.logic.model.CompetitionBean;

public class ReportBean implements Serializable{

	private static final long serialVersionUID = 7432705468162422852L;

	private List<VoteMismatchBean> voteMismatches;
	
	private List<MatchMismatchBean> matchMismatches;
	
	
	private CompetitionBean competition;
	
	
	public List<VoteMismatchBean> getVoteMismatches() {
		return voteMismatches;
	}

	public void setVoteMismatches(List<VoteMismatchBean> voteMismatches) {
		this.voteMismatches = voteMismatches;
	}

	public CompetitionBean getCompetition() {
		return competition;
	}

	public void setCompetition(CompetitionBean competition) {
		this.competition = competition;
	}

	public List<MatchMismatchBean> getMatchMismatches() {
		return matchMismatches;
	}

	public void setMatchMismatches(List<MatchMismatchBean> matchMismatches) {
		this.matchMismatches = matchMismatches;
	}
	
}
