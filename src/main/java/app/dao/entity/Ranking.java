package app.dao.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Ranking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	
	private String rulesType;
	
	@ManyToOne
	private Competition competition;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<RankingRow> rows;

	
	
	public Ranking() {
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

	public List<RankingRow> getRows() {
		return rows;
	}

	public void setRows(List<RankingRow> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "Ranking [id=" + id + ", name=" + name + "]";
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public String getRulesType() {
		return rulesType;
	}

	public void setRulesType(String rulesType) {
		this.rulesType = rulesType;
	}
	
	
	

	
}