package app.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.language.bm.RuleType;
import org.apache.tomcat.util.descriptor.web.ServletDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.RulesType;
import app.dao.LeagueDao;
import app.dao.RankingType;
import app.dao.RulesDao;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_rulesDownloader.model.CustomRules;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonResultBean;
import app.logic._2_realChampionshipAnalyzer.SeasonAnalyzer;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;
import app.logic._3_seasonsGenerator.AllSeasonsGenerator;
import app.logic._4_seasonsExecutor.MainSeasonsExecutor;
import app.logic._4_seasonsExecutor.model.RankingBean;
import app.logic._4_seasonsExecutor.model.RankingRowBean;
import app.logic._5_rankingAnalizer.RankingAnalyzer;
import app.logic.model.StasResponse;

@Service
public class Main {
	
	@Autowired
	private UserBean userBean;
	
	@Autowired
	private RulesDao rulesDao;
	
	@Autowired
	private SeasonAnalyzer seasonAnalyzer;
	
	@Autowired
	private LeagueDao leagueDao;
	
	
	@Autowired
	private AllSeasonsGenerator allSeasonsGenerator;

	@Autowired
	private MainSeasonsExecutor mainSeasonsExecutor;
	
	@Autowired
	private RankingAnalyzer rankingAnalyzer;
	
	
	public StasResponse calculateRealStats(String leagueShortName, String competitionShortName, Boolean onlyOne, RulesType rulesType){
		
	// Genera tutti i possibili calendari (sarebbe inutile farlo sempre ma ci si mette meno ad eseguirlo che a deserializzarli da disco)
		List<SeasonBean> allSeasons = allSeasonsGenerator.generateAllSeasons(leagueShortName, competitionShortName, onlyOne);
		RulesBean rules = rulesDao.retrieveRules(competitionShortName, leagueShortName, rulesType, userBean.getUsername());

		SeasonResultBean calculatedSeasonResult;
		if (rulesType.equals(RulesType.REAL)) {
			calculatedSeasonResult = leagueDao.findCalculatedSeasonResult(leagueShortName, competitionShortName, userBean.getUsername());
		}
		else {
			// se sono regole personalizzate mi ricalcolo tutti risultati alla luce delle regole personalizzate
			calculatedSeasonResult = seasonAnalyzer.calculateSeasonResult(competitionShortName, leagueShortName, rules);
		}

		List<RankingBean> allRankings = mainSeasonsExecutor.execute(allSeasons, leagueShortName, competitionShortName, rules, calculatedSeasonResult);
		
		
		
		
		if (onlyOne && rulesType == RulesType.REAL) {
			

			CustomRules customRulesLuckyEdge = new CustomRules();
			rules.setCustomRules(customRulesLuckyEdge);
			customRulesLuckyEdge.setInvertHomeAway(true);
			customRulesLuckyEdge.setRankingType(RankingType.LUCKY_EDGES);
			customRulesLuckyEdge.setLuckyEdgePoints(0.5);
			
			List<RankingBean> luckyEdgeRankings = mainSeasonsExecutor.execute(allSeasons, leagueShortName, competitionShortName, rules, calculatedSeasonResult);
			RankingBean luckyEdgeRanking = luckyEdgeRankings.get(0);
			luckyEdgeRanking.setRulesType(RulesType.REAL);
			luckyEdgeRanking.setName(RankingType.LUCKY_EDGES.name());
			leagueDao.saveRanking(luckyEdgeRanking, leagueShortName, competitionShortName, userBean.getUsername());
			
			// trova come salvare i punteggi di 0,5 o 1
			
			
			
			
			
			
			
			
			
			
			
			CustomRules customRules = new CustomRules();
			rules.setCustomRules(customRules);
			customRules.setInvertHomeAway(true);
			customRules.setRankingType(RankingType.INVERT_HOME_AWAY);
			
			List<RankingBean> invertHomeAwayRankings = mainSeasonsExecutor.execute(allSeasons, leagueShortName, competitionShortName, rules, calculatedSeasonResult);
			RankingBean invertHomeAwayRanking = invertHomeAwayRankings.get(0);
			invertHomeAwayRanking.setRulesType(RulesType.REAL);
			invertHomeAwayRanking.setName(RankingType.INVERT_HOME_AWAY.name());
			leagueDao.saveRanking(invertHomeAwayRanking, leagueShortName, competitionShortName, userBean.getUsername());
			
			
			
			
			
		}
		
		
		// Salvo il realRanking ovvero quello che se calcolato con le regole REAL dovrebbe essere uguale al ranking del sito
		RankingBean realRanking = allRankings.get(0);
		realRanking.setRulesType(rulesType);
		System.out.println(realRanking);
		leagueDao.saveRanking(realRanking, leagueShortName, competitionShortName, userBean.getUsername());
		
		// Salvo i ranking piu' pesanti da calcolare
		if (!onlyOne) {
			rankingAnalyzer.calculateAndSaveAllRankings(allRankings, leagueShortName, competitionShortName, rulesType);
		}
		
		
		return null;
	}


	public StasResponse calculateStatsWithCustomRules(String leagueShortName, String competitionShortName, Boolean onlyOne, RulesBean customRules) {
		
		//RulesBean rulesDb = rulesDao.retrieveRules(competitionShortName, leagueShortName, RulesType.CUSTOM, userBean.getUsername());
//		rulesDb.getModifiers().setDefenderModifierActive(false);
//		rulesDb.getModifiers().setGoalkeeperModifierActive(false);
//		rulesDb.getModifiers().setMiddlefielderModifierActive(false);
//		rulesDb.getModifiers().setStrikerModifierActive(false);
//		rulesDb.getModifiers().setPerformanceModifierActive(false);
//		rulesDb.getModifiers().setFairPlayModifierActive(false);
//		List<Double> GOAL_POINTS = Arrays.asList(66.0, 72.0, 78.0, 84.0, 90.0, 96.0, 102.0, 108.0, 114.0);;
//		rulesDb.getPoints().setGoalPoints(GOAL_POINTS);
		
		
		

		
//		SeasonResultBean calculatedSeasonResult = seasonAnalyzer.calculateSeasonResult(competitionShortName, leagueShortName, customRules);
//		List<SeasonBean> allSeasons = allSeasonsGenerator.generateAllSeasons(leagueShortName, competitionShortName, onlyOne);
//		List<RankingBean> allRankings = mainSeasonsExecutor.execute(allSeasons, leagueShortName, competitionShortName, calculatedSeasonResult, customRules);
//		
//		// Salvo il ranking REALE che è sempre il primo
//		RankingBean realRanking = allRankings.get(0);
//		realRanking.setRulesType(RulesType.CUSTOM);
//		System.out.println(realRanking);
//		leagueDao.saveRanking(realRanking, leagueShortName, competitionShortName, userBean.getUsername());
//		
//		if (!onlyOne) {
//			rankingAnalyzer.calculateAndSaveAllRankings(allRankings, leagueShortName, competitionShortName, RulesType.CUSTOM);
//		}
//		
		return null;
		
	}
}

