package app;

public class CompetitionBean {

	
	private String name;
	private String shortName;
	private String url;
	private String leagueShortName;
	private String type;
	
	
	
	public CompetitionBean(String name, String shortName, String url, String leagueShortName, String type) {
		this.name = name;
		this.shortName = shortName;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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
		return "CompetitionBean [name=" + name + ", shortName=" + shortName + ", url=" + url + ", leagueName="
				+ leagueShortName + ", type=" + type + "]";
	}

	
}
