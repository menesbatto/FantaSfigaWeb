package app.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Matcho {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String homeTeam;
	private String awayTeam;
	
	@OneToOne
	private LineUpLight homeTeamResult;
	
	@OneToOne
	private LineUpLight awayTeamResult;
	
	
	
	public Matcho() {
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public LineUpLight getHomeTeamResult() {
		return homeTeamResult;
	}
	public void setHomeTeamResult(LineUpLight homeTeamResult) {
		this.homeTeamResult = homeTeamResult;
	}
	public LineUpLight getAwayTeamResult() {
		return awayTeamResult;
	}
	public void setAwayTeamResult(LineUpLight awayTeamResult) {
		this.awayTeamResult = awayTeamResult;
	}
	@Override
	public String toString() {
		return "Matcho [id=" + id + ", homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + ", homeTeamResult="
				+ homeTeamResult + ", awayTeamResult=" + awayTeamResult + "]";
	}
	
	
	
}
