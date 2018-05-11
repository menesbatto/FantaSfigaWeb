package app.logic.model;

import java.io.Serializable;
import java.util.List;

import app.RulesType;
import app.logic._0_rulesDownloader.model.RulesBean;

public class IntegrateRulesReq implements Serializable {

	private static final long serialVersionUID = 9192412690485788831L;

	private String leagueShortName;
	private String competitionShortName;

	private RulesBean rules;
	
	public IntegrateRulesReq() {
	}

	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
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


	public RulesBean getRules() {
		return rules;
	}

	public void setRules(RulesBean rules) {
		this.rules = rules;
	}

	

}
