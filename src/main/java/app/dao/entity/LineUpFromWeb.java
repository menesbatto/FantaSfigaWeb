package app.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LineUpFromWeb {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private Double goalkeeperModifierFromWeb;
	private Double defenderModifierFromWeb;
	private Double strickerModifierFromWeb;
	private Double middlefieldersModifierFromWeb;
	private Double performanceModifierFromWeb;
	private Double fairPlayModifierFromWeb;
	
	private String info;

	public LineUpFromWeb() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Double getFairPlayModifierFromWeb() {
		return fairPlayModifierFromWeb;
	}

	public void setFairPlayModifierFromWeb(Double fairPlayModifierFromWeb) {
		this.fairPlayModifierFromWeb = fairPlayModifierFromWeb;
	}

	public Double getGoalkeeperModifierFromWeb() {
		return goalkeeperModifierFromWeb;
	}

	public void setGoalkeeperModifierFromWeb(Double goalkeeperModifierFromWeb) {
		this.goalkeeperModifierFromWeb = goalkeeperModifierFromWeb;
	}

	public Double getDefenderModifierFromWeb() {
		return defenderModifierFromWeb;
	}

	public void setDefenderModifierFromWeb(Double defenderModifierFromWeb) {
		this.defenderModifierFromWeb = defenderModifierFromWeb;
	}

	public Double getStrickerModifierFromWeb() {
		return strickerModifierFromWeb;
	}

	public void setStrickerModifierFromWeb(Double strickerModifierFromWeb) {
		this.strickerModifierFromWeb = strickerModifierFromWeb;
	}

	public Double getMiddlefieldersModifierFromWeb() {
		return middlefieldersModifierFromWeb;
	}

	public void setMiddlefieldersModifierFromWeb(Double middlefieldersModifierFromWeb) {
		this.middlefieldersModifierFromWeb = middlefieldersModifierFromWeb;
	}

	public Double getPerformanceModifierFromWeb() {
		return performanceModifierFromWeb;
	}

	public void setPerformanceModifierFromWeb(Double performanceModifierFromWeb) {
		this.performanceModifierFromWeb = performanceModifierFromWeb;
	}

	@Override
	public String toString() {
		return "LineUpFromWeb [id=" + id + ", name=" + name + ", goalkeeperModifierFromWeb=" + goalkeeperModifierFromWeb
				+ ", defenderModifierFromWeb=" + defenderModifierFromWeb + ", strickerModifierFromWeb="
				+ strickerModifierFromWeb + ", middlefieldersModifierFromWeb=" + middlefieldersModifierFromWeb
				+ ", performanceModifierFromWeb=" + performanceModifierFromWeb + ", fairPlayModifierFromWeb="
				+ fairPlayModifierFromWeb + ", info=" + info + "]";
	}

	
	
	
	
}
