package app.logic._4_seasonsExecutor.model;

import java.io.Serializable;

public class RankingRowBean implements Serializable{

	private static final long serialVersionUID = -8498184099900310703L;

	private String name;
	private Integer points;					// 56, 60, 58
	private Double sumAllVotes;			// 1345, 1200, 1240.5, 
	private Integer scoredGoals;			// 50, 30, 23
	private Integer takenGoals;			// 50, 30, 23
	private Integer rankingPosition;		// 1,2,3,4,5,6,7,8
	
	
	public RankingRowBean() {
		points = 0;
		sumAllVotes = 0.0;
		scoredGoals = 0;
		takenGoals = 0;
	}

	public RankingRowBean(String name) {
		this();
		this.name = name;
	}




	@Override
	public String toString() {
		return  getNameToString() + "\t" + points + "\t" + sumAllVotes + "\t scoredGoals=\t"
				+ scoredGoals + "\t takenGoals=\t" + takenGoals + "\t" + rankingPosition + "\n";
	}


	private String getNameToString() {
		return this.name.length() > 11 ? this.name.substring(0,10) : this.name;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public Integer getPoints() {
		return points;
	}




	public void setPoints(Integer points) {
		this.points = points;
	}




	public Double getSumAllVotes() {
		return sumAllVotes;
	}




	public void setSumAllVotes(Double sumAllVotes) {
		this.sumAllVotes = sumAllVotes;
	}




	public Integer getScoredGoals() {
		return scoredGoals;
	}




	public void setScoredGoals(Integer scoredGoals) {
		this.scoredGoals = scoredGoals;
	}




	public Integer getTakenGoals() {
		return takenGoals;
	}




	public void setTakenGoals(Integer takenGoals) {
		this.takenGoals = takenGoals;
	}




	public Integer getRankingPosition() {
		return rankingPosition;
	}




	public void setRankingPosition(Integer rankingPosition) {
		this.rankingPosition = rankingPosition;
	}

	

}
