package app.logic._4_seasonsExecutor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.RulesType;

public class RankingBean implements Serializable{

	private static final long serialVersionUID = 8438190796515182835L;

	private String name;
	
	private List<RankingRowBean> rows;
	
	private RulesType rulesType;
	
	private String pattern;

	public RankingBean() {
		this.rows = new ArrayList<RankingRowBean>();
	}

	public List<RankingRowBean> getRows() {
		return rows;
	}

	public void setRows(List<RankingRowBean> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return rows.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RulesType getRulesType() {
		return rulesType;
	}

	public void setRulesType(RulesType rulesType) {
		this.rulesType = rulesType;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	
	
	
	

}
