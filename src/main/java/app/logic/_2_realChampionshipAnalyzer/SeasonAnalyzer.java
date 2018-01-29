package app.logic._2_realChampionshipAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.PostponementBean;
import app.dao.LeagueDao;
import app.dao.RulesDao;
import app.dao.UtilsDao;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic._1_seasonPatternExtractor.model.SeasonResultBean;
import app.logic._2_realChampionshipAnalyzer.model.LineUp;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._2_realChampionshipAnalyzer.model.PostponementBehaviourEnum;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;
import app.utils.AppConstants;

@Service
public class SeasonAnalyzer {

	@Autowired
	private UserBean userBean;
	
	@Autowired
	private RulesDao rulesDao;
	
	@Autowired
	private LeagueDao leagueDao;
	
	@Autowired
	private UtilsDao utilsDao;
	
	@Autowired
	private SeasonDayAnalyzer seasonDayAnalyzer;
	
	
	public SeasonResultBean calculateSeasonResult(String competitionShortName, String leagueShortName){
		return calculateSeasonResult(competitionShortName, leagueShortName, null);
	}
	

	public void cleanSeasonFromWeb(String competitionShortName, String leagueShortName){
		
		RulesBean rules = rulesDao.retrieveRules(competitionShortName, leagueShortName, userBean.getUsername());
		
		if (rules.getCompetitionRules().getPostponementBehaviour().equals(PostponementBehaviourEnum.WAIT_MATCHES)) {
			
			Map<Integer, List<PostponementBean>> postponements = utilsDao.findAllPostponement();
			Map<Integer, Integer> seasonDayBind = rulesDao.findSerieAToCompetitionBinding(leagueShortName, competitionShortName, userBean.getUsername());
			
			List<Integer> competitionSeasonDaysToRemove = new ArrayList<Integer>();
			for (Integer serieASeasonDayPost : postponements.keySet()) {
				competitionSeasonDaysToRemove.add(seasonDayBind.get(serieASeasonDayPost));
			}
			
			leagueDao.removeSeasonDaysFromWebSeasonDays(leagueShortName, competitionShortName, userBean.getUsername(), competitionSeasonDaysToRemove) ;
		}
	}
	
	
	public SeasonResultBean downloadSeasonFromWeb(String competitionShortName, String leagueShortName){
		
		SeasonFromWebBean seasonFromWeb = leagueDao.findSeasonFromWeb(leagueShortName, competitionShortName, userBean.getUsername());

		if (seasonFromWeb == null) {
			seasonFromWeb = new SeasonFromWebBean();
			seasonFromWeb.setSeasonDaysFromWeb(new HashMap<Integer, SeasonDayFromWebBean>());
		}

			
		int seriaAActualSeasonDay = utilsDao.calculateLastSerieASeasonDayCalculated();
		
		//	1 - 4		//	5 - 9 		//	10 - 15		//	15 - 21		//	20 - 27
		//	2 - 5		//	6 - 10		//	11 - 16 	//	16 - 22		//	21 - 28
		//	3 - 6		//	7 - 11		//	12 - 17 	//	17 - 23		//	22 - 29
		//	4 - 7		//	8 - 12		//	13 - 18 	//	18 - 24		//	23 - 30
						//	9 - 13 		//	14 - 19		//	19 - 25		//	24 - 31
		Map<Integer, Integer> seasonDayBind = rulesDao.findCompetitionToSerieABinding(leagueShortName, competitionShortName, userBean.getUsername());
		
		
		String finalSeasonDayUrl = AppConstants.SEASON_DAY_LINES_UP_URL_TEMPLATE.replace("[COMPETITION_ID]", competitionShortName).replace("[LEAGUE_NAME]", leagueShortName);
		
		
		Map<Integer, SeasonDayFromWebBean> seasonDaysFromWeb = new HashMap<Integer, SeasonDayFromWebBean>();
		

		for (Entry<Integer, Integer> entry : seasonDayBind.entrySet()) {
			
			Integer compSeasonDay = entry.getKey();
			Integer serieASeasonDay = entry.getValue();
			
			
			if (serieASeasonDay > seriaAActualSeasonDay) {
				break;
			}
			

			
			if (seasonFromWeb.getSeasonDaysFromWeb().get(compSeasonDay) == null) {
				List<LineUp> lineUpFromWeb = seasonDayAnalyzer.downloadSeasonDayLinesUpFromWeb(finalSeasonDayUrl + compSeasonDay);
				SeasonDayFromWebBean seasonDayFromWeb = new SeasonDayFromWebBean();
				seasonDayFromWeb.setLinesUp(lineUpFromWeb);
				
				seasonDaysFromWeb.put(compSeasonDay, seasonDayFromWeb);
			}
			
			
			
		}
		seasonFromWeb.setSeasonDaysFromWeb(seasonDaysFromWeb);
		leagueDao.saveSeasonFromWeb(leagueShortName, competitionShortName, userBean.getUsername(), seasonFromWeb);
		
		return null;
	}
	
	
	public SeasonResultBean calculateSeasonResult(String competitionShortName, String leagueShortName, RulesBean rulesInput){
		
		Boolean isCustomRules = rulesInput != null;
		
		RulesBean rules;
		if (isCustomRules)
			rules = rulesInput;	
		else
			rules = rulesDao.retrieveRules(competitionShortName, leagueShortName, userBean.getUsername());
			
		
		VotesSourceEnum voteSource = rules.getDataSource().getVotesSource();
		if  (AppConstants.FORCE_VOTE_SOURCE != null){
			voteSource = AppConstants.FORCE_VOTE_SOURCE;
		}
		
		Map<String, Map<String, List<PlayerVoteComplete>>> map = utilsDao.findVotesBySource(voteSource);
		
		int seriaAActualSeasonDay = utilsDao.calculateLastSerieASeasonDayCalculated();

		ArrayList<SeasonDayResultBean> seasonDayResults = new ArrayList<SeasonDayResultBean>();
		
		
		//	1 - 4		//	5 - 9 		//	10 - 15		//	15 - 21		//	20 - 27
		//	2 - 5		//	6 - 10		//	11 - 16 	//	16 - 22		//	21 - 28
		//	3 - 6		//	7 - 11		//	12 - 17 	//	17 - 23		//	22 - 29
		//	4 - 7		//	8 - 12		//	13 - 18 	//	18 - 24		//	23 - 30
						//	9 - 13 		//	14 - 19		//	19 - 25		//	24 - 31
		Map<Integer, Integer> seasonDayBind = rulesDao.findSerieAToCompetitionBinding(leagueShortName, competitionShortName, userBean.getUsername());
		
		
		SeasonDayResultBean seasonDayResult;
		
		Map<Integer, List<PostponementBean>> postponements = utilsDao.findAllPostponement();
		
//		
		SeasonFromWebBean seasonFromWeb = leagueDao.findSeasonFromWeb(leagueShortName, competitionShortName, userBean.getUsername());
		
//		seasonFromWeb = input;
		
		Map<Integer, SeasonDayFromWebBean> seasonDaysFromWeb = seasonFromWeb.getSeasonDaysFromWeb();
		
		System.out.println();
//		for (Integer i = 1; i<38; i++){
		for (Entry<Integer, Integer> entry : seasonDayBind.entrySet()) {
			if (rules.getCompetitionRules().getPostponementBehaviour().equals(PostponementBehaviourEnum.WAIT_MATCHES)) { //Controllo per gestire le giornate in cui ci sono i rinvii
				if (checkIfJumpSeasonDay(postponements, entry)) {
					List<LineUpLightBean> emptyLineUpLight = new ArrayList<LineUpLightBean>();
					seasonDayResults.add(new SeasonDayResultBean(entry.getValue().toString(), emptyLineUpLight)); 
					continue;
				}
			}
			
			Integer compSeasonDay = entry.getValue();
			Integer serieASeasonDay = entry.getKey();
			
			
			if (serieASeasonDay > seriaAActualSeasonDay) {
				break;
			}
			
			SeasonDayFromWebBean currentSeasonDayFromWeb = seasonDaysFromWeb.get(compSeasonDay);
			
			seasonDayResult = seasonDayAnalyzer.calculateSingleSeasonDay(currentSeasonDayFromWeb, serieASeasonDay , rules, map.get(serieASeasonDay+""), postponements);
			
			seasonDayResults.add(seasonDayResult);
			
			
		}
		

		SeasonResultBean seasonResult = new SeasonResultBean();
		seasonResult.setSeasonDayResults(seasonDayResults);
		seasonResult.setName("BASE");
		
		if (!isCustomRules)
			leagueDao.saveCalculatedSeasonResult(seasonResult, leagueShortName, competitionShortName, userBean.getUsername());
		

		
		return seasonResult;
	}


	private boolean checkIfJumpSeasonDay(Map<Integer, List<PostponementBean>> postponements, Entry<Integer, Integer> entry) {
		
		
		List<PostponementBean> list = postponements.get(entry.getKey());
		if (list != null) {
			for (PostponementBean post : list) {
				if (!post.getPlayed())
					return true;
			}
		}
		return false;
	}
	
	
	

//	public static ArrayList<SeasonDayResultBean> getRealChampionshipResults() {
//		ArrayList<SeasonDayResultBean> realChampionshipResults = IOUtils.read(AppConstants.SEASON_DAYS_RESULTS_DIR + AppConstants.SEASON_DAYS_RESULTS_FILE_NAME, ArrayList.class);
//		return realChampionshipResults;
//	}


}