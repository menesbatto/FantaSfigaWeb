package app.dao.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RankingRow {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	private Double points;					// 56, 60, 58
	
	private Double sumAllVotes;			// 1345, 1200, 1240.5, 
	
	private Integer scoredGoals;			// 50, 30, 23
	
	private Integer takenGoals;			// 50, 30, 23
	
	private Integer rankingPosition;		// 1,2,3,4,5,6,7,8
	
	private String positions;
	
	private Integer luckyEdgeNumber;

	private Double luckyEdgeGain;
	
	private Integer unluckyEdgeNumber;
	
	private Integer bestPosition;
	
	private String bestPattern;
	
	private Integer worstPosition;
	
	public Integer getBestPosition() {
		return bestPosition;
	}

	public void setBestPosition(Integer bestPosition) {
		this.bestPosition = bestPosition;
	}

	public String getBestPattern() {
		return bestPattern;
	}

	public void setBestPattern(String bestPattern) {
		this.bestPattern = bestPattern;
	}

	public Integer getWorstPosition() {
		return worstPosition;
	}

	public void setWorstPosition(Integer worstPosition) {
		this.worstPosition = worstPosition;
	}

	public String getWorstPattern() {
		return worstPattern;
	}

	public void setWorstPattern(String worstPattern) {
		this.worstPattern = worstPattern;
	}

	private String worstPattern;
	
	
	public Integer getLuckyEdgeNumber() {
		return luckyEdgeNumber;
	}

	public void setLuckyEdgeNumber(Integer luckyEdgeNumber) {
		this.luckyEdgeNumber = luckyEdgeNumber;
	}

	public Double getLuckyEdgeGain() {
		return luckyEdgeGain;
	}

	public void setLuckyEdgeGain(Double luckyEdgeGain) {
		this.luckyEdgeGain = luckyEdgeGain;
	}

	public Integer getUnluckyEdgeNumber() {
		return unluckyEdgeNumber;
	}

	public void setUnluckyEdgeNumber(Integer unluckyEdgeNumber) {
		this.unluckyEdgeNumber = unluckyEdgeNumber;
	}

	public Double getUnluckyEdgeLose() {
		return unluckyEdgeLose;
	}

	public void setUnluckyEdgeLose(Double unluckyEdgeLose) {
		this.unluckyEdgeLose = unluckyEdgeLose;
	}

	private Double unluckyEdgeLose;
	
	public RankingRow() {
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
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

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	@Override
	public String toString() {
		return "RankingRow [id=" + id + ", name=" + name + ", points=" + points + ", sumAllVotes=" + sumAllVotes
				+ ", scoredGoals=" + scoredGoals + ", takenGoals=" + takenGoals + ", rankingPosition=" + rankingPosition
				+ ", positions=" + positions + "]";
	}
	
	
	
	

	
}