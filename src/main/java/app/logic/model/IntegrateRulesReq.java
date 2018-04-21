package app.logic.model;

import java.io.Serializable;

import app.RulesType;
import app.logic._0_rulesDownloader.model.RulesBean;

public class IntegrateRulesReq implements Serializable {

	private static final long serialVersionUID = 9192412690485788831L;

	private String leagueShortName;
	private String competitionShortName;

	private String maxOfficeVoteBehaviour;
	private String postponementBehaviour;
	private Boolean autogolActive;
	private Double autogol;
	
	private RulesBean rules;
	
	public IntegrateRulesReq() {
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

	public RulesBean getRules() {
		return rules;
	}

	public void setRules(RulesBean rules) {
		this.rules = rules;
	}
	
	
	

}
