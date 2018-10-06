package app.logic._0_rulesDownloader.model;

import java.io.Serializable;

import app.logic._0_votesDownloader.model.VotesSourceEnum;

public class DataSources  implements Serializable{

	private static final long serialVersionUID = 2886189272962700751L;

	private VotesSourceEnum votesSource;
	private String yellowRedCardSource;
	
	
	public VotesSourceEnum getVotesSource() {
		return votesSource;
	}
	public void setVotesSource(VotesSourceEnum votesSource) {
		this.votesSource = votesSource;
	}
	@Override
	public String toString() {
		return "DataSources [votesSource=" + votesSource + "\n yellowRedCardSource=" + yellowRedCardSource + "]";
	}
	public String getYellowRedCardSource() {
		return yellowRedCardSource;
	}
	public void setYellowRedCardSource(String yellowRedCardSource) {
		this.yellowRedCardSource = yellowRedCardSource;
	}

	
	
}
