package app.logic.model;

import java.io.Serializable;

public class PostponementBean implements Serializable {

	private static final long serialVersionUID = 4496723549867825674L;

	private String homeTeam;
	private String awayTeam;
	private Integer seasonDay;
	private Boolean played;
	private Boolean wait;
	
	public PostponementBean() {
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

	public Integer getSeasonDay() {
		return seasonDay;
	}

	public void setSeasonDay(Integer seasonDay) {
		this.seasonDay = seasonDay;
	}

	@Override
	public String toString() {
		return "SerieAMatch [homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + ", seasonDay=" + seasonDay + "]";
	}

	public Boolean getPlayed() {
		return played;
	}

	public void setPlayed(Boolean played) {
		this.played = played;
	}

	public Boolean getWait() {
		return wait;
	}

	public void setWait(Boolean wait) {
		this.wait = wait;
	}

	
	
}
