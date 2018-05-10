package app.logic._2_realChampionshipAnalyzer;

import java.io.Serializable;

import app.logic._0_votesDownloader.model.PlayerVoteComplete;

public class VoteMismatchBean implements Serializable{

	private static final long serialVersionUID = 7181258422299195006L;

	
	private PlayerVoteComplete pvcVote;
	private Integer serieASeasonDay;
	private String player;
	
	public VoteMismatchBean(Integer serieASeasonDay, PlayerVoteComplete pvcVote, Double vote, Double voteFromWeb, Double fantaVote, Double fantaVoteFromWeb) {
		this.serieASeasonDay = serieASeasonDay;
		this.pvcVote = pvcVote;
		this.pvcVote.setFantavote(fantaVote);
		this.pvcVote.setFantaVoteFromWeb(fantaVoteFromWeb);
		this.pvcVote.setVote(vote);
		this.pvcVote.setVoteFromWeb(voteFromWeb);
	}
	
	public VoteMismatchBean() {
		// TODO Auto-generated constructor stub
	}

	public PlayerVoteComplete getPvcVote() {
		return pvcVote;
	}
	public void setPvcVote(PlayerVoteComplete pvcVote) {
		this.pvcVote = pvcVote;
	}
	public Integer getSerieASeasonDay() {
		return serieASeasonDay;
	}
	public void setSerieASeasonDay(Integer serieASeasonDay) {
		this.serieASeasonDay = serieASeasonDay;
	}
	@Override
	public String toString() {
		return "VoteMismatchBean [pvcVote=" + pvcVote + ", serieASeasonDay=" + serieASeasonDay + "]";
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}
	
	
	
	
}
