package app.logic._1_seasonPatternExtractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.LeagueDao;
import app.dao.RulesDao;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.PlayerEnum;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.utils.AppConstants;
import app.utils.HttpUtils;
import app.utils.IOUtils;

@Service
public class SeasonPatternExtractor {
	
	@Autowired
	private LeagueDao leagueDao;
	
	@Autowired
	private RulesDao rulesDao;
	
	@Autowired
	private UserBean userBean;

	public void calculateSerieAToCompetitionSeasonDaysBinding(String leagueShortName, String competitionShortName) {
		
		List<SeasonDayBean> seasonDays = downloadSeasonDays(leagueShortName, competitionShortName);
		
		SeasonBean season = new SeasonBean();
		season.setSeasonDays(seasonDays);
		
		rulesDao.saveSerieAToCompetitionBinding(season, leagueShortName, competitionShortName, userBean.getUsername());
		
	}
	
	public void saveOnlineSeasonAndTeams(String leagueShortName, String competitionShortName) {
		if (userBean.getUsername() == null)
			return;
		
		List<SeasonDayBean> seasonDays = downloadSeasonDays(leagueShortName, competitionShortName);
		SeasonBean season = new SeasonBean();
		season.setSeasonDays(seasonDays);
		
		List<String> teams = getSortedTeams(season);
//		IOUtils.write(AppConstants.PLAYERS_DIR + AppConstants.PLAYERS_FILE_NAME, teams);
		
		leagueDao.saveTeams(teams, leagueShortName, userBean.getUsername());
		
		
		SeasonBean lightSeason = createLightSeason(teams, season);

		leagueDao.saveOnlineSeason(season, leagueShortName, competitionShortName, userBean.getUsername());
		
		System.out.println(lightSeason);
		
	}

	private List<String> getSortedTeams(SeasonBean season) {
		SeasonDayBean fsd = season.getSeasonDays().get(0);
		List<String> teams = new ArrayList<String>();
		for (MatchBean match : fsd.getMatches()) {
			teams.add(match.getHomeTeam());
			teams.add(match.getAwayTeam());
		}
		Collections.sort(teams);
		return teams;
	}

	
	public void calculateCompetitionPattern(String leagueShortName, String competitionShortName) {
		
		if (userBean.getUsername() == null)
			return;
		
		List<SeasonDayBean> seasonDays = downloadSeasonDays(leagueShortName, competitionShortName);
		SeasonBean season = new SeasonBean();
		season.setSeasonDays(seasonDays);
		
		List<String> teams = getSortedTeams(season);
		
		SeasonBean lightSeason = createLightSeason(teams, season);

		leagueDao.saveCompetitionPattern(season, leagueShortName, competitionShortName, userBean.getUsername());

//		rulesDao.saveCompetitionPattern(season, leagueShortName, competitionShortName, userBean.getUsername());
		
		System.out.println(lightSeason);
		
	}


	private List<SeasonDayBean> downloadSeasonDays(String leagueShortName, String competitionShortName) {
		Elements seasonDayElements = downloadSeasonDayHtmlElements(leagueShortName, competitionShortName);
		
		List<SeasonDayBean> seasonDays = new ArrayList<SeasonDayBean>();
		SeasonDayBean seasonDay;
		
		for (int i = 0; i < seasonDayElements.size(); i++) {
			Element lineUpElem = seasonDayElements.get(i);
			seasonDay = createSeasonDay(lineUpElem, "Giornata " + (i+1));
			seasonDays.add(seasonDay);
		}
		return seasonDays;
	}
	
	public Integer lastCalculatedWebSeasonDay(String leagueShortName, String competitionShortName) {
		Elements seasonDayElements = downloadSeasonDayHtmlElements(leagueShortName, competitionShortName);
	
		for (int i = 0; i < seasonDayElements.size(); i++) {
			Element lineUpElem = seasonDayElements.get(i);
			Elements results = lineUpElem.getElementsByClass("result");
			if (results.isEmpty())
				return i;
		}
		return seasonDayElements.size();
	}


	private Elements downloadSeasonDayHtmlElements(String leagueShortName, String competitionShortName) {
		String url = AppConstants.CALENDAR_URL_TEMPLATE.replace("[LEAGUE_NAME]", leagueShortName).replace("[COMPETITION_ID]", competitionShortName);
//		Credentials c = userDao.retrieveGazzettaCredentials(userBean.getUsername());
//		Document doc = HttpUtils.getHtmlPageNoLogged(url, c.getUsername(), c.getPassword());
		Document doc = HttpUtils.getHtmlPageNoLogged(url);
		

		Elements seasonDayElements = doc.getElementsByTag("table");
		return seasonDayElements;
	}
	
	

	private static SeasonDayBean createSeasonDay(Element seasonDayElements, String seasonDayName) {
		SeasonDayBean seasonDay = new SeasonDayBean(seasonDayName);
		Elements matchesDomElems = seasonDayElements.getElementsByClass("match");
		Elements matchesGoalsDomElems = seasonDayElements.getElementsByClass("result");

		Element serieASeasonDayElem = seasonDayElements.getElementsByTag("tr").get(0).getElementsByClass("thtitle").get(0);
		String serieASeasonDayApp = serieASeasonDayElem.text().split("-")[1];
		String serieASeasonDay = serieASeasonDayApp.substring(1, serieASeasonDayApp.length()-9);
		MatchBean m;
		Elements teamsDomElems;
		String homeTeam;
		String awayTeam;
		Double homeSumTotalPoints;
		Double awaySumTotalPoints;
		for (int i = 0; i < matchesDomElems.size(); i++) {
			Element matchElem = matchesDomElems.get(i);
			Element goalsElem = matchesGoalsDomElems.get(i);

			teamsDomElems = matchElem.getElementsByTag("a");
			homeTeam = teamsDomElems.get(0).text();
			awayTeam = teamsDomElems.get(1).text();
			Elements resultPoints = matchElem.getElementsByClass("point");
			m = new MatchBean(homeTeam, awayTeam);
			if (resultPoints.size() != 0){
				homeSumTotalPoints = Double.valueOf(resultPoints.get(0).text().replace(",", "."));
				awaySumTotalPoints = Double.valueOf(resultPoints.get(1).text().replace(",", "."));
				m.getHomeTeamResult().setSumTotalPoints(homeSumTotalPoints);
				m.getAwayTeamResult().setSumTotalPoints(awaySumTotalPoints);
				
				
				int homeGoals = Integer.valueOf(goalsElem.text().split("-")[0]);
				int awayGoals = Integer.valueOf(goalsElem.text().split("-")[1]);
				m.getHomeTeamResult().setGoals(homeGoals);
				m.getAwayTeamResult().setGoals(awayGoals);
			}
			seasonDay.getMatches().add(m);
		}
		seasonDay.setSerieANumber(Integer.valueOf(serieASeasonDay));
		return seasonDay;
	}

	private static SeasonBean createLightSeason(List<String> teams, SeasonBean season) {


		for (SeasonDayBean sd : season.getSeasonDays()) {
			for (MatchBean m : sd.getMatches()) {
				PlayerEnum homeTeamEnum = PlayerEnum.values()[teams.indexOf(m.getHomeTeam())];
				PlayerEnum awayTeamEnum = PlayerEnum.values()[teams.indexOf(m.getAwayTeam())];
				m.setHomeTeamEnum(homeTeamEnum);
				m.setAwayTeamEnum(awayTeamEnum);
				m.setHomeTeam(m.getHomeTeam());
				m.setAwayTeam(m.getAwayTeam());
			}
		}

		return season;

	}
	
	
//	private static String getCompetitionId() {
//		// Vado sul calendario e prendo i due indirizzi
//		// Provo il primo 
//		//		Se mi torna che ha una scritta del tipo
//		// 		"questo tipo di competizione non prevede un calendario."
//		//		Allora è di tipo racing e non ci posso fare niente quindi pwe le formazioni devo scegliere l'altro
//		//
//		//		Se mi torna che NON ha una scritta del tipo
//		// 		"questo tipo di competizione non prevede un calendario."
//		//		Allora per le formazioni devo scegliere questo
//				
//		
//		//http://leghe.fantagazzetta.com/accaniti-division/formazioni/143826?g=1
//		
//		String calendarUrlDefault = AppConstants.CALENDAR_URL_TEMPLATE.replace("[COMPETITION_ID]", "");
//		String competitionId = null;
//		String calendarCompetitionUrl;
//		try {
//			Document doc = Jsoup.connect(calendarUrlDefault).get();
//			Elements select = doc.select("option");
//			for (Element s :select){
//				if (s.text().equalsIgnoreCase(AppConstants.COMPETITION_NAME)){
//					calendarCompetitionUrl= s.val();
//					competitionId = calendarCompetitionUrl.substring(calendarCompetitionUrl.lastIndexOf("/")+1);
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return competitionId;
//
//	}
	
//	public static SeasonBean getSeasonPattern() {
//		// recupera da file il pattern della stagione recuperata
//		// Dalla combinazione data in ingresso crea il rispettivo calendario
//		SeasonBean seasonPattern = IOUtils.read(AppConstants.SEASON_REASULTS_DIR + AppConstants.SEASON_REASULTS_FILE_NAME, SeasonBean.class);
//		return seasonPattern;
//	}
	
//	public ArrayList<String> getPlayers() {
//		List<String> teams = leagueDao.findTeams(leagueShortName, username);
//		ArrayList<String> players = IOUtils.read(AppConstants.PLAYERS_DIR + AppConstants.PLAYERS_FILE_NAME, ArrayList.class);
//		return players;
//	}
	
}