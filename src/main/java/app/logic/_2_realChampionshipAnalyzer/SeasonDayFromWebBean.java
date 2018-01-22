package app.logic._2_realChampionshipAnalyzer;

import java.io.Serializable;
import java.util.List;

import app.logic._2_realChampionshipAnalyzer.model.LineUp;

public class SeasonDayFromWebBean implements Serializable{
	
	private static final long serialVersionUID = 2832069035402420880L;

	private String name;
	
	private List<LineUp> linesUp;

	public SeasonDayFromWebBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LineUp> getLinesUp() {
		return linesUp;
	}

	public void setLinesUp(List<LineUp> linesUp) {
		this.linesUp = linesUp;
	}

	@Override
	public String toString() {
		return "SeasonDayFromWeb [name=" + name + ", linesUp=" + linesUp + "]";
	}
	

	
	
	
	
}
