package app.logic.model;

import java.io.Serializable;

import app.logic._0_rulesDownloader.model.RulesBean;

public class RetrieveRulesRes implements Serializable {

	private static final long serialVersionUID = -9074938878778729334L;

	private CompetitionBean competition;
	
	private RulesBean realRules;
	
	private RulesBean customRules;

	public RetrieveRulesRes() {
	}
	
	public CompetitionBean getCompetition() {
		return competition;
	}
	public void setCompetition(CompetitionBean competition) {
		this.competition = competition;
	}

	public RulesBean getRealRules() {
		return realRules;
	}

	public void setRealRules(RulesBean realRules) {
		this.realRules = realRules;
	}

	public RulesBean getCustomRules() {
		return customRules;
	}

	public void setCustomRules(RulesBean customRules) {
		this.customRules = customRules;
	}

	@Override
	public String toString() {
		return "CustomRulesReq [competition=" + competition + ", realRules=" + realRules + ", customRules="
				+ customRules + "]";
	}

	
}
