package app.logic._2_realChampionshipAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.PostponementBean;
import app.dao.LeagueDao;
import app.dao.RulesDao;
import app.dao.UtilsDao;
import app.dao.entity.LineUpLight;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic._1_seasonPatternExtractor.model.SeasonResultBean;
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
		return calculateSeasonResult(competitionShortName, leagueShortName);
	}
	
	public SeasonResultBean calculateSeasonResult(String competitionShortName, String leagueShortName, RulesBean rulesInput){
		
		RulesBean rules;
		if (rulesInput == null)
			rules = rulesDao.retrieveRules(competitionShortName, leagueShortName, userBean.getUsername());
		else
			rules = rulesInput;	
		
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
		
		
		String finalSeasonDayUrl = AppConstants.SEASON_DAY_LINES_UP_URL_TEMPLATE.replace("[COMPETITION_ID]", competitionShortName).replace("[LEAGUE_NAME]", leagueShortName);
		
		
		
		SeasonDayResultBean seasonDayResult;
		
		Map<Integer, List<PostponementBean>> postponements = utilsDao.findAllPostponement();

	
		
		
		for (Entry<Integer, Integer> entry : seasonDayBind.entrySet()) {
			if (rules.getCompetitionRules().getPostponementBehaviour().equals(PostponementBehaviourEnum.WAIT_MATCHES)) //Controllo per gestire le giornate in cui ci sono i rinvii
				if (postponements.get(entry.getValue()) != null) {
					List<LineUpLightBean> emptyLineUpLight = new ArrayList<LineUpLightBean>();
					seasonDayResults.add(new SeasonDayResultBean(entry.getKey().toString(), emptyLineUpLight)); 
					continue;
				}
			
			
			Integer compSeasonDay = entry.getKey();
			Integer serieASeasonDay = entry.getValue();
			
			
			if (serieASeasonDay > seriaAActualSeasonDay) {
				break;
			}
			
			seasonDayResult = seasonDayAnalyzer.calculateSingleSeasonDay(finalSeasonDayUrl + compSeasonDay, map.get(serieASeasonDay+""), serieASeasonDay , rules, postponements) ;
			
			seasonDayResults.add(seasonDayResult);
			
			
		}
		
		SeasonResultBean seasonResult = new SeasonResultBean();
		seasonResult.setSeasonDayResults(seasonDayResults);
		seasonResult.setName("BASE");
		
		leagueDao.saveCalculatedSeasonResult(seasonResult, leagueShortName, competitionShortName, userBean.getUsername());
		
//		###############################
//		Integer fantacalcioSeasonDaysNumber = calculateFantaSeasonDayForSerieASSeasonDay(seasonDayBind, seriaAActualSeasonDay);//seriaAActualSeasonDay - fantacalcioStartingSerieASeasonDay + 1;
//		
//		
//		System.out.println(fantacalcioSeasonDaysNumber);
//		
//		seasonDayResults = new ArrayList<SeasonDayResult>();
//		SeasonDayResult seasonDayResult;
//		for (int i = 1; i <= fantacalcioSeasonDaysNumber; i++) {
//			if (serieAseasonToWait.contains(i - 1 + fantacalcioStartingSerieASeasonDay)){
//				seasonDayResults.add(null);
//				continue;
//			}
//			VotesSourceEnum voteSource = rules.getDataSource().getVotesSource();
//			if  (AppConstants.FORCE_VOTE_SOURCE != null){
//				voteSource = AppConstants.FORCE_VOTE_SOURCE;
//			}
//			
//			String key = calculateSerieASeasonDayForFantaSeasonDay(seasonDayBind, i);
//			
//			
//			seasonDayResult = SeasonDayAnalyzer.execute(finalSeasonDayUrl + i, map.get(key), key ) ;
//			
//			seasonDayResults.add(seasonDayResult);
//
//		}
//		//System.out.println(seasonDayResults);
//		String finalResultSeasonPath= AppConstants.SEASON_DAYS_RESULTS_DIR + AppConstants.SEASON_DAYS_RESULTS_FILE_NAME;
//		IOUtils.write(finalResultSeasonPath, seasonDayResults);

		
		return seasonResult;
	}
	

//	public static ArrayList<SeasonDayResultBean> getRealChampionshipResults() {
//		ArrayList<SeasonDayResultBean> realChampionshipResults = IOUtils.read(AppConstants.SEASON_DAYS_RESULTS_DIR + AppConstants.SEASON_DAYS_RESULTS_FILE_NAME, ArrayList.class);
//		return realChampionshipResults;
//	}


}