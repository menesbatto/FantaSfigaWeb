package app;

public class CompetitionBean {

	
	private String name;
	private String competitionShortName;
	private String url;
	private String leagueShortName;
	private String type;
	
	
	
	public CompetitionBean(String name, String competitionShortName, String url, String leagueShortName, String type) {
		this.name = name;
		this.setCompetitionShortName(competitionShortName);
		this.url = url;
		this.leagueShortName = leagueShortName;
		this.type = type;
	}

	public CompetitionBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueName) {
		this.leagueShortName = leagueName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CompetitionBean [name=" + name + ", shortName=" + getCompetitionShortName() + ", url=" + url + ", leagueName="
				+ leagueShortName + ", type=" + type + "]";
	}

	public String getCompetitionShortName() {
		return competitionShortName;
	}

	public void setCompetitionShortName(String competitionShortName) {
		this.competitionShortName = competitionShortName;
	}

	
}
