package app.logic._0_votesDownloader_0_rulesDownloader.model;

import java.io.Serializable;

public class CompetitionRules  implements Serializable {

	private static final long serialVersionUID = 2222384220107049801L;

	private Boolean homeBonusActive;
	private Double homeBonus;

	
	
	public CompetitionRules() {
	}

	public Double getHomeBonus() {
		return homeBonus;
	}

	public void setHomeBonus(Double homeBonus) {
		this.homeBonus = homeBonus;
	}

	public Boolean isHomeBonusActive() {
		return homeBonusActive;
	}

	public void setHomeBonusActive(Boolean homeBonusActive) {
		this.homeBonusActive = homeBonusActive;
	}
	
	

	
}
