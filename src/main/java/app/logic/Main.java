package app.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.language.bm.RuleType;
import org.apache.tomcat.util.descriptor.web.ServletDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.RulesType;
import app.dao.LeagueDao;
import app.dao.RankingType;
import app.dao.RulesDao;
import app.dao.UtilsDao;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_rulesDownloader.model.CustomRules;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonResultBean;
import app.logic._2_realChampionshipAnalyzer.SeasonAnalyzer;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;
import app.logic._3_seasonsGenerator.AllSeasonsGenerator;
import app.logic._3_seasonsGenerator.SeasonGenerator;
import app.logic._4_seasonsExecutor.MainSeasonsExecutor;
import app.logic._4_seasonsExecutor.model.RankingBean;
import app.logic._4_seasonsExecutor.model.RankingRowBean;
import app.logic._5_rankingAnalizer.RankingAnalyzer;
import app.logic.model.SeasonAndRankingRes;
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
			// LUCKY EDGE 0.5
			calculateLuckyEdge(RankingType.LUCKY_EDGES_0_5, 0.5, leagueShortName, competitionShortName, allSeasons, rules, calculatedSeasonResult);
			
			// LUCKY EDGE 1.0
			calculateLuckyEdge(RankingType.LUCKY_EDGES_1, 1.0, leagueShortName, competitionShortName, allSeasons, rules, calculatedSeasonResult);
			
			// INVERT HOME AWAY
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


	private void calculateLuckyEdge(RankingType rankingType, Double delta, String leagueShortName, String competitionShortName, List<SeasonBean> allSeasons,
			RulesBean rules, SeasonResultBean calculatedSeasonResult) {
		CustomRules customRulesLuckyEdge = new CustomRules();
		rules.setCustomRules(customRulesLuckyEdge);
		customRulesLuckyEdge.setInvertHomeAway(true);
		customRulesLuckyEdge.setRankingType(rankingType);
		customRulesLuckyEdge.setLuckyEdgePoints(delta);
		
		List<RankingBean> luckyEdgeRankings = mainSeasonsExecutor.execute(allSeasons, leagueShortName, competitionShortName, rules, calculatedSeasonResult);
		RankingBean luckyEdgeRanking = luckyEdgeRankings.get(0);
		luckyEdgeRanking.setRulesType(RulesType.REAL);
		luckyEdgeRanking.setName(rankingType.name());
		leagueDao.saveRanking(luckyEdgeRanking, leagueShortName, competitionShortName, userBean.getUsername());
	}


	@Autowired
	private UtilsDao utilsDao;
	@Autowired
	private SeasonGenerator seasonGenerator;
	
	
	public SeasonAndRankingRes retrieveSeasonFromPattern(String pattern, String leagueShortName, String competitionShortName) {
		Integer serieAActualSeasonDay = utilsDao.calculateLastSerieASeasonDayCalculated();
		Map<Integer, Integer> bindings = rulesDao.findSerieAToCompetitionBinding(leagueShortName, competitionShortName, userBean.getUsername());
		SeasonBean seasonPattern = leagueDao.findSeason(leagueShortName, competitionShortName, userBean.getUsername(), "Pattern");
		List<String> teams = leagueDao.findTeams(leagueShortName, userBean.getUsername());
		Integer fantacalcioActualSeasonDay = bindings.get(serieAActualSeasonDay);
		SeasonBean s = seasonGenerator.generateSingleSeason(pattern, 0, fantacalcioActualSeasonDay, seasonPattern, teams);

		List<SeasonBean> allSeasons = new ArrayList<SeasonBean>();
		allSeasons.add(s);
		
		RulesBean rules = rulesDao.retrieveRules(competitionShortName, leagueShortName, RulesType.REAL, userBean.getUsername());
		SeasonResultBean calculatedSeasonResult = leagueDao.findCalculatedSeasonResult(leagueShortName, competitionShortName, userBean.getUsername());
		
		List<RankingBean> allRankings = mainSeasonsExecutor.execute(allSeasons , leagueShortName, competitionShortName, rules, calculatedSeasonResult);
		RankingBean ranking = allRankings.get(0);
		
		
		SeasonAndRankingRes res = new SeasonAndRankingRes();
		res.setSeason(s);
		res.setRanking(ranking);
		return res;
	}
}

