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
public class SeasonFromWeb {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@OneToOne
	private Competition competition;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<SeasonDayFromWeb> seasonDaysFromWeb;

	public SeasonFromWeb() {
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

	public List<SeasonDayFromWeb> getSeasonDaysFromWeb() {
		return seasonDaysFromWeb;
	}

	public void setSeasonDaysFromWeb(List<SeasonDayFromWeb> seasonDaysFromWeb) {
		this.seasonDaysFromWeb = seasonDaysFromWeb;
	}

	@Override
	public String toString() {
		return "SeasonFromWeb [id=" + id + ", name=" + name + ", seasonDaysFromWeb=" + seasonDaysFromWeb + "]";
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}


	
	
}
