package app.logic._4_seasonsExecutor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RankingBean implements Serializable{

	private static final long serialVersionUID = 8438190796515182835L;

	private int name;
	
	private List<RankingRowBean> rows;

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

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	
	
	
	

}
