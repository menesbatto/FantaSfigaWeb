package app.dao.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SeasonDayFromWeb {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private Integer competitionSeasonDay;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<LineUpFromWeb> linesUpFromWeb;

	public SeasonDayFromWeb() {
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

	public List<LineUpFromWeb> getLinesUpFromWeb() {
		return linesUpFromWeb;
	}

	public void setLinesUpFromWeb(List<LineUpFromWeb> linesUpFromWeb) {
		this.linesUpFromWeb = linesUpFromWeb;
	}

	@Override
	public String toString() {
		return "SeasonDayFromWeb [id=" + id + ", name=" + name + ", linesUpFromWeb=" + linesUpFromWeb + "]";
	}

	public Integer getCompetitionSeasonDay() {
		return competitionSeasonDay;
	}

	public void setCompetitionSeasonDay(Integer competitionSeasonDay) {
		this.competitionSeasonDay = competitionSeasonDay;
	}


	
	
}
