package app.logic._2_realChampionshipAnalyzer;

import java.io.Serializable;
import java.util.Map;

public class SeasonFromWebBean implements Serializable{

	private static final long serialVersionUID = 7181258409299195006L;

	private Map<Integer, SeasonDayFromWebBean> seasonDaysFromWeb;
	
	private String name;
	
	public SeasonFromWebBean() {
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SeasonFromWeb [seasonDaysFromWeb=" + seasonDaysFromWeb + ", name=" + name + "]";
	}


	public Map<Integer,SeasonDayFromWebBean> getSeasonDaysFromWeb() {
		return seasonDaysFromWeb;
	}


	public void setSeasonDaysFromWeb(Map<Integer, SeasonDayFromWebBean> seasonDaysFromWeb) {
		this.seasonDaysFromWeb = seasonDaysFromWeb;
	}
	
	

	
	
	
}
