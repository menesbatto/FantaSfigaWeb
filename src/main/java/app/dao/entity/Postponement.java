package app.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import app.logic._0_rulesDownloader.model.SubstitutionsModeEnum;

@Entity
public class Postponement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private Integer seasonDay;
	
	private String homeTeam;
	
	private String awayTeam;
	
	private Boolean played;
	
	private Boolean wait;

	public Postponement() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getSeasonDay() {
		return seasonDay;
	}

	public void setSeasonDay(Integer seasonDay) {
		this.seasonDay = seasonDay;
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

	public Boolean getPlayed() {
		return played;
	}

	public void setPlayed(Boolean played) {
		this.played = played;
	}

	@Override
	public String toString() {
		return "Postponement [id=" + id + ", seasonDay=" + seasonDay + ", homeTeam=" + homeTeam + ", awayTeam="
				+ awayTeam + ", played=" + played + "]";
	}

	public Boolean getWait() {
		return wait;
	}

	public void setWait(Boolean wait) {
		this.wait = wait;
	}
	

	
}
