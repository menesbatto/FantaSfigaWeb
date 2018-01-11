package app.logic._2_seasonPatternExtractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.RulesDao;
import app.dao.UserDao;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._2_seasonPatternExtractor.model.Match;
import app.logic._2_seasonPatternExtractor.model.PlayerEnum;
import app.logic._2_seasonPatternExtractor.model.SeasonBean;
import app.logic._2_seasonPatternExtractor.model.SeasonDay;
import app.utils.AppConstants;
import app.utils.HttpUtils;
import app.utils.IOUtils;

@Service
public class SeasonPatternExtractor {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RulesDao rulesDao;
	
	@Autowired
	private UserBean userBean;

	public void calculateSerieAToCompetitionSeasonDaysBinding(String leagueShortName, String competitionShortName) {
		
		List<SeasonDay> seasonDays = createSeasonDays(leagueShortName, competitionShortName);
		
		SeasonBean season = new SeasonBean();
		season.setSeasonDays(seasonDays);
		
		rulesDao.saveSerieAToCompetitionBinding(season, leagueShortName, competitionShortName, userBean.getUsername());
		
	}
	
	public void calculateCompetitionPattern(String leagueShortName, String competitionShortName) {
		
		List<SeasonDay> seasonDays = createSeasonDays(leagueShortName, competitionShortName);
		SeasonBean season = new SeasonBean();
		season.setSeasonDays(seasonDays);
		
		SeasonBean lightSeason = createLightSeason(season);

		rulesDao.saveCompetitionPattern(season, leagueShortName, competitionShortName, userBean.getUsername());
		
		System.out.println(lightSeason);
		
	}


	private List<SeasonDay> createSeasonDays(String leagueShortName, String competitionShortName) {
		String url = AppConstants.CALENDAR_URL_TEMPLATE.replace("[LEAGUE_NAME]", leagueShortName).replace("[COMPETITION_ID]", competitionShortName);
//		Credentials c = userDao.retrieveGazzettaCredentials(userBean.getUsername());
//		Document doc = HttpUtils.getHtmlPageNoLogged(url, c.getUsername(), c.getPassword());
		Document doc = HttpUtils.getHtmlPageNoLogged(url);
		

		Elements seasonDayElements = doc.getElementsByTag("table");
		
		List<SeasonDay> seasonDays = new ArrayList<SeasonDay>();
		SeasonDay seasonDay;
		
		for (int i = 0; i < seasonDayElements.size(); i++) {
			Element lineUpElem = seasonDayElements.get(i);
			seasonDay = createSeasonDay(lineUpElem, "Giornata " + (i+1));
			seasonDays.add(seasonDay);
		}
		return seasonDays;
	}
	
	

	private static SeasonDay createSeasonDay(Element seasonDayElements, String seasonDayName) {
		SeasonDay seasonDay = new SeasonDay(seasonDayName);
		Elements matchesDomElems = seasonDayElements.getElementsByClass("match");
		Element serieASeasonDayElem = seasonDayElements.getElementsByTag("tr").get(0).getElementsByClass("thtitle").get(0);
		String serieASeasonDayApp = serieASeasonDayElem.text().split("-")[1];
		String serieASeasonDay = serieASeasonDayApp.substring(1, serieASeasonDayApp.length()-9);
		Match m;
		Elements teamsDomElems;
		String homeTeam;
		String awayTeam;
		Double homeSumTotalPoints;
		Double awaySumTotalPoints;
		for (Element matchElem : matchesDomElems) {
			teamsDomElems = matchElem.getElementsByTag("a");
			homeTeam = teamsDomElems.get(0).text();
			awayTeam = teamsDomElems.get(1).text();
			Elements resultPoints = matchElem.getElementsByClass("point");
			m = new Match(homeTeam, awayTeam);
			if (resultPoints.size() != 0){
				homeSumTotalPoints = Double.valueOf(resultPoints.get(0).text().replace(",", "."));
				awaySumTotalPoints = Double.valueOf(resultPoints.get(1).text().replace(",", "."));
				m.getHomeTeamResult().setSumTotalPoints(homeSumTotalPoints);
				m.getAwayTeamResult().setSumTotalPoints(awaySumTotalPoints);
			}
			seasonDay.getMatches().add(m);
		}
		seasonDay.setSerieANumber(Integer.valueOf(serieASeasonDay));
		return seasonDay;
	}

	private static SeasonBean createLightSeason(SeasonBean season) {
		SeasonDay fsd = season.getSeasonDays().get(0);
		List<String> players = new ArrayList<String>();
		for (Match match : fsd.getMatches()) {
			players.add(match.getHomeTeam());
			players.add(match.getAwayTeam());
		}
		Collections.sort(players);
		IOUtils.write(AppConstants.PLAYERS_DIR + AppConstants.PLAYERS_FILE_NAME, players);

		for (SeasonDay sd : season.getSeasonDays()) {
			for (Match m : sd.getMatches()) {
				PlayerEnum homeTeamEnum = PlayerEnum.values()[players.indexOf(m.getHomeTeam())];
				PlayerEnum awayTeamEnum = PlayerEnum.values()[players.indexOf(m.getAwayTeam())];
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
	
	public static SeasonBean getSeasonPattern() {
		// recupera da file il pattern della stagione recuperata
		// Dalla combinazione data in ingresso crea il rispettivo calendario
		SeasonBean seasonPattern = IOUtils.read(AppConstants.SEASON_REASULTS_DIR + AppConstants.SEASON_REASULTS_FILE_NAME, SeasonBean.class);
		return seasonPattern;
	}
	
	public static ArrayList<String> getPlayers() {
		ArrayList<String> players = IOUtils.read(AppConstants.PLAYERS_DIR + AppConstants.PLAYERS_FILE_NAME, ArrayList.class);
		return players;
	}
	
}