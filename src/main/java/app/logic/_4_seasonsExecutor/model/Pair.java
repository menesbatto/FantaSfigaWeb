package app.logic._4_seasonsExecutor.model;

import java.io.Serializable;
import java.util.List;

public class Pair implements Serializable{

	private static final long serialVersionUID = -1183201598168181426L;
	

	private String name;
	private Double value;
	private List<Double> valueList;
	
	private Integer bestPosition;
	private String bestPattern;
	private Double bestPoints;
	
	private Integer worstPosition;
	private String worstPattern;
	private Double worstPoints;
	
	
	

	public Pair(String name, Double value) {
		this.name = name;
		this.value = value;
	}
	public Pair(String name, List<Double> valueList) {
		this.name = name;
		this.setValueList(valueList);
		this.value=0.0;
	}


	public Double getBestPoints() {
		return bestPoints;
	}
	public void setBestPoints(Double bestPoints) {
		this.bestPoints = bestPoints;
	}
	public Double getWorstPoints() {
		return worstPoints;
	}
	public void setWorstPoints(Double worstPoints) {
		this.worstPoints = worstPoints;
	}
	
	public Integer getBestPosition() {
		return bestPosition;
	}
	public void setBestPosition(Integer bestPosition) {
		this.bestPosition = bestPosition;
	}
	public String getBestPattern() {
		return bestPattern;
	}
	public void setBestPattern(String bestPattern) {
		this.bestPattern = bestPattern;
	}
	public Integer getWorstPosition() {
		return worstPosition;
	}
	public void setWorstPosition(Integer worstPosition) {
		this.worstPosition = worstPosition;
	}
	public String getWorstPattern() {
		return worstPattern;
	}
	public void setWorstPattern(String worstPattern) {
		this.worstPattern = worstPattern;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public List<Double> getValueList() {
		return valueList;
	}
	public void setValueList(List<Double> valueList) {
		this.valueList = valueList;
	}
	@Override
	public String toString() {
		return "\n" + name + "\t" + getFormattedValue(value) + "\t " + getFormattedValueList(valueList);
	}
	
	private String getFormattedValueList(List<Double> valueList) {
		String s= "";
		if (valueList!= null)
			for(Double value : valueList){
				s += getFormattedValue(value) + "\t";
			} 	
		return s;
	}
	private String getFormattedValue(Double value2) {
		String result = (value2 + "").replace('.', ',');
		if (result.length()>5)
			 result= result.substring(0, 6);
		return result;
	}
	private String getNameToString() {
		return this.name.length() > 11 ? this.name.substring(0,10) : this.name;
	}

	 

	
	
}
