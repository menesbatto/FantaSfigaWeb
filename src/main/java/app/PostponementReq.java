package app;

import java.io.Serializable;

public class PostponementReq implements Serializable {

	private static final long serialVersionUID = 9192412690485788831L;

	private String leagueShortName;
	private String competitionShortName;

	
	private String postponementBehaviour;
	
	public PostponementReq() {
	}

	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
	}

	public String getPostponementBehaviour() {
		return postponementBehaviour;
	}

	public void setPostponementBehaviour(String postponementBehaviour) {
		this.postponementBehaviour = postponementBehaviour;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PostponementReq [leagueShortName=" + leagueShortName + ", postponementBehaviour="
				+ postponementBehaviour + "]";
	}

	public String getCompetitionShortName() {
		return competitionShortName;
	}

	public void setCompetitionShortName(String competitionShortName) {
		this.competitionShortName = competitionShortName;
	}
	
	
	

}
