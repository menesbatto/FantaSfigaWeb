package app.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Permutation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private Integer players;
	
	@Lob
	private String permutations;

	public Permutation() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getPlayers() {
		return players;
	}

	public void setPlayers(Integer players) {
		this.players = players;
	}

	public String getPermutations() {
		return permutations;
	}

	public void setPermutations(String permutations) {
		this.permutations = permutations;
	}
	

	
	
	

}
