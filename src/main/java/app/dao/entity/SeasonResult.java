package app.dao.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class SeasonResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<SeasonDayResult> seasonDayResults;
	
	@OneToOne
	private Competition competition;
	
	

	public SeasonResult() {
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

	public List<SeasonDayResult> getSeasonDayResults() {
		return seasonDayResults;
	}

	public void setSeasonDayResults(List<SeasonDayResult> seasonDayResults) {
		this.seasonDayResults = seasonDayResults;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	
	

}
