package app.logic.model;

import java.io.Serializable;

import app.logic._0_rulesDownloader.model.RulesBean;

public class CustomRules implements Serializable {

	private static final long serialVersionUID = -9074938878778729334L;

	private CompetitionBean competition;
	
	private RulesBean rules;

	public CustomRules() {
	}
	
	public CompetitionBean getCompetition() {
		return competition;
	}
	public void setCompetition(CompetitionBean competition) {
		this.competition = competition;
	}
	public RulesBean getRules() {
		return rules;
	}
	public void setRules(RulesBean rules) {
		this.rules = rules;
	}
	@Override
	public String toString() {
		return "CustomRules [competition=" + competition + ", rules=" + rules + "]";
	}
	
	
}
