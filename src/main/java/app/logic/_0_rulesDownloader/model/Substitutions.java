package app.logic._0_rulesDownloader.model;


import java.io.Serializable;
import java.util.List;

public class Substitutions  implements Serializable{

	private static final long serialVersionUID = 5180169100397162838L;

	private Integer substitutionNumber;
	private SubstitutionsModeEnum substitutionMode;
	
	private Boolean playerOfficeDecreasingVotesActive;
	private List<Double> playerOfficeDecreasingVotes;
	
	private MaxOfficeVotesEnum maxOfficeVotes;
	
	private Boolean goalkeeperPlayerOfficeVoteActive;
	private Double goalkeeperPlayerOfficeVote;

	private Boolean movementsPlayerOfficeVoteActive;
	private Double movementsPlayerOfficeVote;
	
	
	public Integer getSubstitutionNumber() {
		return substitutionNumber;
	}
	public void setSubstitutionNumber(Integer substitutionNumber) {
		this.substitutionNumber = substitutionNumber;
	}
	public SubstitutionsModeEnum getSubstitutionMode() {
		return substitutionMode;
	}
	public void setSubstitutionMode(SubstitutionsModeEnum substitutionMode) {
		this.substitutionMode = substitutionMode;
	}
	public Double getGoalkeeperPlayerOfficeVote() {
		return goalkeeperPlayerOfficeVote;
	}
	public void setGoalkeeperPlayerOfficeVote(Double goalkeeperPlayerOfficeVote) {
		this.goalkeeperPlayerOfficeVote = goalkeeperPlayerOfficeVote;
	}
	public Double getMovementsPlayerOfficeVote() {
		return movementsPlayerOfficeVote;
	}
	public void setMovementsPlayerOfficeVote(Double movementsPlayerOfficeVote) {
		this.movementsPlayerOfficeVote = movementsPlayerOfficeVote;
	}
	public Boolean isGoalkeeperPlayerOfficeVoteActive() {
		return goalkeeperPlayerOfficeVoteActive;
	}
	public void setGoalkeeperPlayerOfficeVoteActive(Boolean goalkeeperPlayerOfficeVoteActive) {
		this.goalkeeperPlayerOfficeVoteActive = goalkeeperPlayerOfficeVoteActive;
	}
	public Boolean isMovementsPlayerOfficeVoteActive() {
		return movementsPlayerOfficeVoteActive;
	}
	public void setMovementsPlayerOfficeVoteActive(Boolean movementsPlayerOfficeVoteActive) {
		this.movementsPlayerOfficeVoteActive = movementsPlayerOfficeVoteActive;
	}
	@Override
	public String toString() {
		return "Substitutions [substitutionNumber=" + substitutionNumber + "\n substitutionMode=" + substitutionMode
				+  "\n goalkeeperPlayerOfficeVoteActive=" + goalkeeperPlayerOfficeVoteActive
				+ "\n goalkeeperPlayerOfficeVote=" + goalkeeperPlayerOfficeVote + "\n movementsPlayerOfficeVoteActive="
				+ movementsPlayerOfficeVoteActive + "\n movementsPlayerOfficeVote=" + movementsPlayerOfficeVote
				+ "\n maxOfficeVotes=" + maxOfficeVotes + "\n playerOfficeDecreasingVotesActive="
				+ playerOfficeDecreasingVotesActive + "\n playerOfficeDecreasingVotes=" + playerOfficeDecreasingVotes + 
				
				"]";
	}
	public MaxOfficeVotesEnum getMaxOfficeVotes() {
		return maxOfficeVotes;
	}
	public void setMaxOfficeVotes(MaxOfficeVotesEnum maxOfficeVotes) {
		this.maxOfficeVotes = maxOfficeVotes;
	}
	public Boolean getPlayerOfficeDecreasingVotesActive() {
		return playerOfficeDecreasingVotesActive;
	}
	public void setPlayerOfficeDecreasingVotesActive(Boolean playerOfficeDecreasingVotesActive) {
		this.playerOfficeDecreasingVotesActive = playerOfficeDecreasingVotesActive;
	}
	public List<Double> getPlayerOfficeDecreasingVotes() {
		return playerOfficeDecreasingVotes;
	}
	public void setPlayerOfficeDecreasingVotes(List<Double> playerOfficeDecreasingVotes) {
		this.playerOfficeDecreasingVotes = playerOfficeDecreasingVotes;
	}
	
	
	
}
