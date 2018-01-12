package app.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SeasonDay {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;

	@OneToMany
	private List<Matcho> matches;
	
	private Integer serieANumber;
	
	

	public SeasonDay() {
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

	public List<Matcho> getMatches() {
		return matches;
	}

	public void setMatches(List<Matcho> matches) {
		this.matches = matches;
	}

	public Integer getSerieANumber() {
		return serieANumber;
	}

	public void setSerieANumber(Integer serieANumber) {
		this.serieANumber = serieANumber;
	}

	@Override
	public String toString() {
		return "SeasonDay [id=" + id + ", name=" + name + ", matches=" + matches + ", serieANumber=" + serieANumber
				+ "]";
	}
	
	
	
	
	
	

}
