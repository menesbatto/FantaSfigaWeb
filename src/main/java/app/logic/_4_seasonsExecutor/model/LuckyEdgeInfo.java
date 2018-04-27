package app.logic._4_seasonsExecutor.model;

public class LuckyEdgeInfo {
	
	private Integer luckyEdgeNumber;

	private Double luckyEdgeGain;
	
	private Integer unluckyEdgeNumber;
	
	private Double unluckyEdgeLose;

	public LuckyEdgeInfo() {
		luckyEdgeNumber = 0;
		luckyEdgeGain = 0.0;
		unluckyEdgeNumber = 0;
		unluckyEdgeLose = 0.0;
	}

	public Double getLuckyEdgeGain() {
		return luckyEdgeGain;
	}

	public void setLuckyEdgeGain(Double luckyEdgeGain) {
		this.luckyEdgeGain = luckyEdgeGain;
	}


	public Double getUnluckyEdgeLose() {
		return unluckyEdgeLose;
	}

	public void setUnluckyEdgeLose(Double unluckyEdgeLose) {
		this.unluckyEdgeLose = unluckyEdgeLose;
	}

	public Integer getLuckyEdgeNumber() {
		return luckyEdgeNumber;
	}

	public void setLuckyEdgeNumber(Integer luckyEdgeNumber) {
		this.luckyEdgeNumber = luckyEdgeNumber;
	}

	public Integer getUnluckyEdgeNumber() {
		return unluckyEdgeNumber;
	}

	public void setUnluckyEdgeNumber(Integer unluckyEdgeNumber) {
		this.unluckyEdgeNumber = unluckyEdgeNumber;
	}
	
	
	
}
