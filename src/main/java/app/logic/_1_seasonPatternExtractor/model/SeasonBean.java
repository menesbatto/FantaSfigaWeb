package app.logic._1_seasonPatternExtractor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeasonBean implements Serializable{
	
	private static final long serialVersionUID = -5241896046608966295L;
	private String name;
	private List<SeasonDayBean> seasonDays;
	

	public SeasonBean() {
		this.seasonDays = new ArrayList<SeasonDayBean>();
	}

	public SeasonBean(String name) {
		this.name = name;
		this.seasonDays = new ArrayList<SeasonDayBean>();
	}

	public List<SeasonDayBean> getSeasonDays() {
		return seasonDays;
	}

	public void setSeasonDays(List<SeasonDayBean> seasonDays) {
		this.seasonDays = seasonDays;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "" + name + "\n, " + seasonDays + "\n\n";
	}
	
	

	
}
