package app;

import java.io.Serializable;

public class PostponementBean implements Serializable {

	private static final long serialVersionUID = 4496723549867825674L;

	private String homeTeam;
	private String awayTeam;
	private Integer seasonDay;
	private Boolean played;
	
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

	
	
}
