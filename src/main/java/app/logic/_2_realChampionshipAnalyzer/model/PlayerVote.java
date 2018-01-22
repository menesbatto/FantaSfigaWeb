package app.logic._2_realChampionshipAnalyzer.model;

import app.logic._0_votesDownloader.model.RoleEnum;

public class PlayerVote {
	
	private RoleEnum role;
	private String name;
	private String team;
	private Double vote;
	private Double fantaVote;
	private boolean alreadyUsed;
	private Double goalkeerModifier;
	private Double strikerModifier;
	
	private Double voteFromWeb;
	private Double fantaVoteFromWeb;
	
	
	
	public PlayerVote() {
	}

	public PlayerVote(RoleEnum role, String name, String team, Double vote, Double fantaVote) {
		this.role = role;
		this.name = name;
		this.team = team;
		this.vote = vote;
		this.fantaVote = fantaVote;
	}
	
	public RoleEnum getRole() {
		return role;
	}
	public void setRole(RoleEnum role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public Double getVote() {
		return vote;
	}
	public void setVote(Double vote) {
		this.vote = vote;
	}
	public Double getFantaVote() {
		return fantaVote;
	}
	public void setFantaVote(Double fantaVote) {
		this.fantaVote = fantaVote;
	}

	public boolean isAlreadyUsed() {
		return alreadyUsed;
	}

	public void setAlreadyUsed(boolean alreadyUsed) {
		this.alreadyUsed = alreadyUsed;
	}

	public Double getGoalkeerModifier() {
		return goalkeerModifier;
	}

	public void setGoalkeerModifier(Double goalkeerModifier) {
		this.goalkeerModifier = goalkeerModifier;
	}

	public Double getStrikerModifier() {
		return strikerModifier;
	}

	public void setStrikerModifier(Double strikerModifier) {
		this.strikerModifier = strikerModifier;
	}

	public Double getVoteFromWeb() {
		return voteFromWeb;
	}

	public void setVoteFromWeb(Double voteFromWeb) {
		this.voteFromWeb = voteFromWeb;
	}

	public Double getFantaVoteFromWeb() {
		return fantaVoteFromWeb;
	}

	public void setFantaVoteFromWeb(Double fantaVoteFromWeb) {
		this.fantaVoteFromWeb = fantaVoteFromWeb;
	}

	@Override
	public String toString() {
		return "PlayerVote [role=" + role + ", name=" + name + ", team=" + team + ", vote=" + vote + ", fantaVote="
				+ fantaVote + ", alreadyUsed=" + alreadyUsed + ", goalkeerModifier=" + goalkeerModifier
				+ ", strikerModifier=" + strikerModifier + ", voteFromWeb=" + voteFromWeb + ", fantaVoteFromWeb="
				+ fantaVoteFromWeb + "]";
	}

	
	
	
	
}
