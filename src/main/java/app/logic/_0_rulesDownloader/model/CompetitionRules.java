package app.logic._0_rulesDownloader.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import app.logic._2_realChampionshipAnalyzer.model.PostponementBehaviourEnum;
import app.logic.model.PostponementBean;

public class CompetitionRules  implements Serializable {

	private static final long serialVersionUID = 2222384220107049801L;

	private Boolean homeBonusActive;
	private Double homeBonus;
	private String binding;
	private PostponementBehaviourEnum postponementBehaviour;
	private Map<Integer, List<PostponementBean>> postponementMap;
	private List<Integer> seasonDaysToJump;

	
	
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

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public PostponementBehaviourEnum getPostponementBehaviour() {
		return postponementBehaviour;
	}

	public void setPostponementBehaviour(PostponementBehaviourEnum postponementBehaviour) {
		this.postponementBehaviour = postponementBehaviour;
	}

	public Map<Integer, List<PostponementBean>> getPostponementMap() {
		return postponementMap;
	}

	public void setPostponementMap(Map<Integer, List<PostponementBean>> postponementsMap) {
		this.postponementMap = postponementsMap;
	}

	public List<Integer> getSeasonDaysToJump() {
		return seasonDaysToJump;
	}

	public void setSeasonDaysToJump(List<Integer> seasonDaysToJump) {
		this.seasonDaysToJump = seasonDaysToJump;
	}

	
	
	

	
}
