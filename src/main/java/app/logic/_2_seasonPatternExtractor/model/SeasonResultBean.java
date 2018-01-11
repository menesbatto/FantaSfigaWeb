package app.logic._2_seasonPatternExtractor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.logic._1_realChampionshipAnalyzer.model.SeasonDayResultBean;

public class SeasonResultBean implements Serializable{
	
	private static final long serialVersionUID = -3341896046608966295L;
	
	private String name;
	
	private List<SeasonDayResultBean> seasonDayResults;
	
	
	public SeasonResultBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SeasonDayResultBean> getSeasonDayResults() {
		return seasonDayResults;
	}
	public void setSeasonDayResults(List<SeasonDayResultBean> seasonDayResults) {
		this.seasonDayResults = seasonDayResults;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "SeasonResult [name=" + name + ", seasonDayResults=" + seasonDayResults + "]";
	}
	

	
	
}
