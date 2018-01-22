package app;

import java.io.Serializable;

public class RulesReq implements Serializable {

	private static final long serialVersionUID = 9192412690485788831L;

	private String leagueShortName;
	private String competitionShortName;

	private String maxOfficeVoteBehaviour;
	private String postponementBehaviour;
	private Boolean autogolActive;
	private Double autogol;
	
	public RulesReq() {
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

	public String getCompetitionShortName() {
		return competitionShortName;
	}

	public void setCompetitionShortName(String competitionShortName) {
		this.competitionShortName = competitionShortName;
	}

	public String getMaxOfficeVoteBehaviour() {
		return maxOfficeVoteBehaviour;
	}

	public void setMaxOfficeVoteBehaviour(String maxOfficeVoteBehaviour) {
		this.maxOfficeVoteBehaviour = maxOfficeVoteBehaviour;
	}

	public Boolean getAutogolActive() {
		return autogolActive;
	}

	public void setAutogolActive(Boolean autogolActive) {
		this.autogolActive = autogolActive;
	}

	public Double getAutogol() {
		return autogol;
	}

	public void setAutogol(Double autogol) {
		this.autogol = autogol;
	}

	@Override
	public String toString() {
		return "RulesReq [leagueShortName=" + leagueShortName + ", competitionShortName=" + competitionShortName
				+ ", maxOfficeVoteBehaviour=" + maxOfficeVoteBehaviour + ", postponementBehaviour="
				+ postponementBehaviour + ", autogolActive=" + autogolActive + ", autogol=" + autogol + "]";
	}
	
	
	

}
