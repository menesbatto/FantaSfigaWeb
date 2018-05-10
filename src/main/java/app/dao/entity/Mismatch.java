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
public class Mismatch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<VoteMismatch> voteMismatches;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<MatchMismatch> matchMismatches;
	
	
	@OneToOne
	private Competition competition;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public List<VoteMismatch> getVoteMismatches() {
		return voteMismatches;
	}

	public void setVoteMismatches(List<VoteMismatch> voteMismatches) {
		this.voteMismatches = voteMismatches;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public List<MatchMismatch> getMatchMismatches() {
		return matchMismatches;
	}

	public void setMatchMismatches(List<MatchMismatch> matchMismatches) {
		this.matchMismatches = matchMismatches;
	}
	
	

}
