package app.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class VoteMismatch {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(cascade=CascadeType.ALL)
	private Vote realVote;
	
	private Double voteFromWeb;
	private Double fantaVoteFromWeb;
	private Double fantavote;
	private Double vote;
	private String player;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Vote getRealVote() {
		return realVote;
	}
	public void setRealVote(Vote realVote) {
		this.realVote = realVote;
	}
	public Double getVoteFromWeb() {
		return voteFromWeb;
	}
	public void setVoteFromWeb(Double voteFromWeb) {
		this.voteFromWeb = voteFromWeb;
	}
	public Double getFantaVoteFromWeb() {
		return fantaVoteFromWeb;
	}
	public void setFantaVoteFromWeb(Double fantaVoteFromWeb) {
		this.fantaVoteFromWeb = fantaVoteFromWeb;
	}
	public Double getFantavote() {
		return fantavote;
	}
	public void setFantavote(Double fantavote) {
		this.fantavote = fantavote;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public Double getVote() {
		return vote;
	}
	public void setVote(Double vote) {
		this.vote = vote;
	}
	
	
	
	
}
