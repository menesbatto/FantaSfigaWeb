package app.logic._4_seasonsExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.LeagueDao;
import app.dao.RankingType;
import app.dao.RulesDao;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._1_seasonPatternExtractor.model.SeasonResultBean;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;
import app.logic._4_seasonsExecutor.model.RankingBean;
import app.logic._4_seasonsExecutor.model.RankingRowBean;
import app.utils.AppConstants;
import app.utils.IOUtils;

@Service
public class MainSeasonsExecutor {
	
//	private static ArrayList<SeasonBean> allSeasons;
//	private static int i = 0;
	@Autowired
	private SeasonExecutor seasonExecutor;
	
	@Autowired
	private RulesDao rulesDao;

	@Autowired
	private LeagueDao leagueDao;
	
	
	@Autowired
	private UserBean userBean;
	
	
	public List<RankingBean> execute(List<SeasonBean> allSeasons, String leagueShortName, String competitionShortName) {
		return execute(allSeasons, leagueShortName, competitionShortName, null, null);
	}
	
	public List<RankingBean> execute(List<SeasonBean> allSeasons, String leagueShortName, String competitionShortName, SeasonResultBean seasonResultBeanInput,  RulesBean rulesInput ){
//		allSeasons = allSeasonsInput;
		
		RulesBean rules;
		SeasonResultBean seasonResultBean;
		if (rulesInput == null) {
			rules = rulesDao.retrieveRules(competitionShortName, leagueShortName, userBean.getUsername());
			seasonResultBean = leagueDao.findCalculatedSeasonResult(leagueShortName, competitionShortName, userBean.getUsername());
		}
		else {
			rules = rulesInput;	
			seasonResultBean = seasonResultBeanInput;
		}
		
		long startTime = System.nanoTime();
		System.out.println("Inizio esecuzione di tutti i calendari");
		
		ArrayList<RankingBean> rankings = new ArrayList<RankingBean>();
	

		List<String> teams = leagueDao.findTeams(leagueShortName, userBean.getUsername());

		
		for ( SeasonBean season : allSeasons){
//			i++;
			
			
			RankingBean ranking = seasonExecutor.execute(season, rules, seasonResultBean.getSeasonDayResults(), teams);
		
			
			Collections.sort(ranking.getRows(), new Comparator<RankingRowBean>() {

				public int compare(RankingRowBean o1, RankingRowBean o2) {
					if (o2.getPoints() - o1.getPoints() < 0)
						return -1;
					else if (o2.getPoints() - o1.getPoints() > 0)
						return 1;
					return 0;
				}
			});
			List<RankingRowBean> rows = ranking.getRows();
			
			rows.get(0).setRankingPosition(1); 
			for (int j = 1; j < rows.size(); j++) {
				RankingRowBean row = rows.get(j);
				RankingRowBean prevRow = rows.get(j-1);
				if (row.getPoints()== prevRow.getPoints())
					row.setRankingPosition(prevRow.getRankingPosition());
				else 
					row.setRankingPosition(j+1);
				
			}
			rankings.add(ranking);
//			if (ranking.getRows().get(1).getName().equals("Hawkins"))
//				System.out.println(season);
		} 
		
		if(AppConstants.DEBUG_MODE)
			checkRealSeasonCorrect(seasonResultBean.getSeasonDayResults(),  leagueShortName, competitionShortName);
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		System.out.println("Fine esecuzione di tutti i calendari " + duration/1000000);
		
		
		rankings.get(0).setName(RankingType.REAL.name());
		leagueDao.saveRanking(rankings.get(0), leagueShortName, competitionShortName, userBean.getUsername());
		System.out.println(rankings.get(0));
//		IOUtils.write(AppConstants.REAL_RANKING_DIR + AppConstants.REAL_RANKING_FILE_NAME , rankings.get(0));
		
//		if (!AppConstants.FAST_MODE_ACTIVE)
//			IOUtils.write(AppConstants.RANKING_DIR + AppConstants.RANKING_FILE_NAME , rankings);
		
		return rankings;
	}
	
	
	
	private void checkRealSeasonCorrect(List<SeasonDayResultBean> realChampionshipResults, String leagueShortName, String competitionShortName) {
		
		SeasonBean seasonPattern = leagueDao.findSeason(leagueShortName, competitionShortName, userBean.getUsername(), null);
//		SeasonBean seasonPattern = MainSeasonPatternExtractorNEW.getSeasonPattern();
		
		List<SeasonDayBean> seasonDays = seasonPattern.getSeasonDays();
		for (int j = 0; j < seasonDays.size(); j++) {
			SeasonDayBean seasonDayFromWeb = seasonDays.get(j);
			if (j < realChampionshipResults.size()){
				SeasonDayResultBean seasonDayFromApp = realChampionshipResults.get(j);
				for (int k = 0; k < seasonDayFromWeb.getMatches().size(); k++){
					MatchBean matchFromWeb = seasonDayFromWeb.getMatches().get(k);
					LineUpLightBean homeTeamResultFromWeb = matchFromWeb.getHomeTeamResult();
					LineUpLightBean awayTeamResultFromWeb = matchFromWeb.getAwayTeamResult();
					
					LineUpLightBean homeTeamFromApp = getTeamFromApp(seasonDayFromApp.getLinesUpLight(), matchFromWeb.getHomeTeam());
					LineUpLightBean awayTeamFromApp = getTeamFromApp(seasonDayFromApp.getLinesUpLight(), matchFromWeb.getAwayTeam());
					
					if (homeTeamFromApp != null) {
						if (!homeTeamFromApp.getSumTotalPoints().equals(homeTeamResultFromWeb.getSumTotalPoints())){
							System.out.println("ERRORE \t Giornata:\t" + (Integer)(Integer.valueOf(j)+1) + "\tPartita:\t" +  matchFromWeb.getHomeTeam() + " - " + matchFromWeb.getAwayTeam() + "\tSquadra\t" +  matchFromWeb.getHomeTeam() + "\tPunteggio da web:\t" + homeTeamResultFromWeb.getSumTotalPoints() + "\tPunteggio da app:\t" +  homeTeamFromApp.getSumTotalPoints());
		
						}
		
						if (!awayTeamFromApp.getSumTotalPoints().equals(awayTeamResultFromWeb.getSumTotalPoints())){
							System.out.println("ERRORE \t Giornata:\t" + (Integer)(Integer.valueOf(j)+1) + "\tPartita:\t" +  matchFromWeb.getHomeTeam() + " - " + matchFromWeb.getAwayTeam() + "\tSquadra\t" +  matchFromWeb.getAwayTeam() + "\tPunteggio da web:\t" + awayTeamResultFromWeb.getSumTotalPoints() + "\tPunteggio da app:\t" +  awayTeamFromApp.getSumTotalPoints());
						}
					}
				}
			}
		}
		
		
	}
	private static LineUpLightBean getTeamFromApp(List<LineUpLightBean> linesUpLight, String teamName) {
		for (LineUpLightBean lul : linesUpLight){
			if (lul.getTeamName().equals(teamName))
				return lul;
		}
		return null;
	}


//	public static ArrayList<RankingBean> getAllGeneratedRankings() {
//		long startTime = System.nanoTime();
//		System.out.println("Inizio caricamento di tutte le classifiche");
//		ArrayList<RankingBean> allRankings = IOUtils.read(AppConstants.RANKING_DIR + AppConstants.RANKING_FILE_NAME, ArrayList.class);
//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime); // divide by 1000000 to get
//												// milliseconds.
//		System.out.println("Fine caricamento " + duration / 1000000);
//		return allRankings;
//	}
	
//	public static RankingBean getRealRanking() {
//		RankingBean ranking = IOUtils.read(AppConstants.REAL_RANKING_DIR + AppConstants.REAL_RANKING_FILE_NAME, RankingBean.class);
//		return ranking;
//	}
}
