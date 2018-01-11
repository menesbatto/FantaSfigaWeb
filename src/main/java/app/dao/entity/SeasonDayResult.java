package app.dao.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SeasonDayResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<LineUpLight> linesUpLight;

	public SeasonDayResult() {
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


	public List<LineUpLight> getLinesUpLight() {
		return linesUpLight;
	}

	public void setLinesUpLight(List<LineUpLight> linesUpLight) {
		this.linesUpLight = linesUpLight;
	}

	@Override
	public String toString() {
		return "SeasonDayResult [id=" + id + ", name=" + name + ", linesUpLight="
				+ linesUpLight + "]";
	}
	
	
	

}
