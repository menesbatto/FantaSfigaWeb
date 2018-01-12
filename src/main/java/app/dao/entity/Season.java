package app.dao.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;

@Entity
public class Season {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<SeasonDay> seasonDays;
	
	@OneToOne
	private Competition competition;
	
	

	public Season() {
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

	public List<SeasonDay> getSeasonDays() {
		return seasonDays;
	}

	public void setSeasonDays(List<SeasonDay> seasonDay) {
		this.seasonDays = seasonDay;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@Override
	public String toString() {
		return "Season [id=" + id + ", name=" + name + ", seasonDay=" + seasonDays + ", competition=" + competition
				+ "]";
	}
	
	

}
