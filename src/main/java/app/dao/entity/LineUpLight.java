package app.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LineUpLight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String teamName;
	
	private Double middlefieldersVariation;

	private Double goalkeeperModifier;
	
	private Double totalWithoutGoalkeeperAndMiddlefielderModifiers;

	private Double sumTotalPoints;		//punti fatti 66, 75.5;

	private int goals;					//Gol fatti 1,2,3,4,5,		

	private int takenGoals;					//Gol Presi 1,2,3,4,5,		

	private int rankingPoints;			//0, 1, 3
										//X, V, P

	public LineUpLight() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Double getMiddlefieldersVariation() {
		return middlefieldersVariation;
	}

	public void setMiddlefieldersVariation(Double middlefieldersVariation) {
		this.middlefieldersVariation = middlefieldersVariation;
	}

	public Double getGoalkeeperModifier() {
		return goalkeeperModifier;
	}

	public void setGoalkeeperModifier(Double goalkeeperModifier) {
		this.goalkeeperModifier = goalkeeperModifier;
	}

	public Double getTotalWithoutGoalkeeperAndMiddlefielderModifiers() {
		return totalWithoutGoalkeeperAndMiddlefielderModifiers;
	}

	public void setTotalWithoutGoalkeeperAndMiddlefielderModifiers(Double totalWithoutGoalkeeperAndMiddlefielderModifiers) {
		this.totalWithoutGoalkeeperAndMiddlefielderModifiers = totalWithoutGoalkeeperAndMiddlefielderModifiers;
	}

	public Double getSumTotalPoints() {
		return sumTotalPoints;
	}

	public void setSumTotalPoints(Double sumTotalPoints) {
		this.sumTotalPoints = sumTotalPoints;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getTakenGoals() {
		return takenGoals;
	}

	public void setTakenGoals(int takenGoals) {
		this.takenGoals = takenGoals;
	}

	public int getRankingPoints() {
		return rankingPoints;
	}

	public void setRankingPoints(int rankingPoints) {
		this.rankingPoints = rankingPoints;
	}

	@Override
	public String toString() {
		return "LineUpLight [id=" + id + ", teamName=" + teamName + ", middlefieldersVariation="
				+ middlefieldersVariation + ", goalkeeperModifier=" + goalkeeperModifier
				+ ", totalWithoutGoalkeeperAndMiddlefielderModifiers=" + totalWithoutGoalkeeperAndMiddlefielderModifiers
				+ ", sumTotalPoints=" + sumTotalPoints + ", goals=" + goals + ", takenGoals=" + takenGoals
				+ ", rankingPoints=" + rankingPoints + "]";
	}
	
	
	
	
	
	
}
