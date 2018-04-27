package app.logic._0_rulesDownloader.model;


import java.io.Serializable;

import app.RulesType;

public class RulesBean  implements Serializable{

	private static final long serialVersionUID = -8460127535161295547L;

	private BonusMalus bonusMalus;
	private DataSources dataSource;
	private Points points;
	private Substitutions substitutions;
	private Modifiers modifiers;
	private CompetitionRules competitionRules;
	
	private RulesType type;
	private CustomRules customRules;
	
	public BonusMalus getBonusMalus() {
		return bonusMalus;
	}
	public void setBonusMalus(BonusMalus bonusMalus) {
		this.bonusMalus = bonusMalus;
	}
	public DataSources getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSources dataSource) {
		this.dataSource = dataSource;
	}
	public Points getPoints() {
		return points;
	}
	public void setPoints(Points points) {
		this.points = points;
	}
	public Substitutions getSubstitutions() {
		return substitutions;
	}
	public void setSubstitutions(Substitutions substitution) {
		this.substitutions = substitution;
	}
	public Modifiers getModifiers() {
		return modifiers;
	}
	public void setModifiers(Modifiers modifiers) {
		this.modifiers = modifiers;
	}
	public CompetitionRules getCompetitionRules() {
		return competitionRules;
	}
	public void setCompetitionRules(CompetitionRules competitionRules) {
		this.competitionRules = competitionRules;
	}
	public RulesType getType() {
		return type;
	}
	public void setType(RulesType type) {
		this.type = type;
	}
	public CustomRules getCustomRules() {
		return customRules;
	}
	public void setCustomRules(CustomRules customRules) {
		this.customRules = customRules;
	}
	
	
		
}
