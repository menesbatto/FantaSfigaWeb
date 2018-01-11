package app.logic._1_realChampionshipAnalyzer.model;

import java.io.Serializable;
import java.util.List;

public class SeasonDayResultBean implements Serializable {
	
	private static final long serialVersionUID = 242853354626694539L;

	
	private String name;
	private Integer nameNumber;
	private List<LineUpLightBean> linesUpLight;
	

	
	
	public SeasonDayResultBean(String name, List<LineUpLightBean> linesUpLight) {
		this.name = name;
		this.linesUpLight = linesUpLight;
		Integer nameNumber = Integer.valueOf(name.substring(name.indexOf(" ")+1));
		this.nameNumber = nameNumber;
	}

	public List<LineUpLightBean> getLinesUpLight() {
		return linesUpLight;
	}

	public void setLinesUpLight(List<LineUpLightBean> linesUpLight) {
		this.linesUpLight = linesUpLight;
	}

	public Integer getNameNumber() {
		return nameNumber;
	}

	public void setNameNumber(Integer nameNumber) {
		this.nameNumber = nameNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "\n\nSeasonDayResult [name=" + name + "\n\n nameNumber=" + nameNumber + "\n\n linesUpLight=" + linesUpLight + "]";
	}
	
	
	
	
	
}

