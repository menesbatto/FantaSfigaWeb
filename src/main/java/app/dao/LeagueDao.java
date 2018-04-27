package app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.RulesType;
import app.dao.entity.Competition;
import app.dao.entity.League;
import app.dao.entity.LineUpFromWeb;
import app.dao.entity.LineUpLight;
import app.dao.entity.Matcho;
import app.dao.entity.Ranking;
import app.dao.entity.RankingRow;
import app.dao.entity.Season;
import app.dao.entity.SeasonDay;
import app.dao.entity.SeasonDayFromWeb;
import app.dao.entity.SeasonDayResult;
import app.dao.entity.SeasonFromWeb;
import app.dao.entity.SeasonResult;
import app.dao.entity.User;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_votesDownloader.model.RoleEnum;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.PlayerEnum;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._1_seasonPatternExtractor.model.SeasonResultBean;
import app.logic._2_realChampionshipAnalyzer.SeasonDayFromWebBean;
import app.logic._2_realChampionshipAnalyzer.SeasonFromWebBean;
import app.logic._2_realChampionshipAnalyzer.model.LineUp;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._2_realChampionshipAnalyzer.model.PlayerVote;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;
import app.logic._4_seasonsExecutor.model.LuckyEdgeInfo;
import app.logic._4_seasonsExecutor.model.RankingBean;
import app.logic._4_seasonsExecutor.model.RankingRowBean;
import app.logic.model.CompetitionBean;

@Service
@EnableCaching
public class LeagueDao {

	@Autowired
	private LeagueRepo leagueRepo;

	@Autowired
	private CompetitionRepo competitionRepo;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private SeasonResultRepo seasonResultRepo;
	
	@Autowired
	private SeasonRepo seasonRepo;
	
	@Autowired
	private RankingRepo rankingRepo;

	@Autowired
	private SeasonFromWebRepo seasonFromWebRepo;




	public Boolean saveLeagues(List<LeagueBean> beans, String username) {
		
		List<League> ents = new ArrayList<League>();
		User user = userDao.retrieveByUsername(username);
		
		
		League ent;
		League existingLeague;
		for (LeagueBean bean : beans) {
			
			existingLeague = leagueRepo.findByUserAndShortName(user, bean.getShortName());
			if (existingLeague != null)
				continue;
			
			ent = new League();
			ent.setName(bean.getName());
			ent.setShortName(bean.getShortName());
			ent.setUrl(bean.getUrl());
			ent.setUser(user);
			ents.add(ent);
			
		}
		
		leagueRepo.save(ents);
		
		return true;
	}

	
	public List<LeagueBean> findLeaguesByUsername(String username) {
		User user = userDao.retrieveByUsername(username);
		List<League> entList = leagueRepo.findByUser(user);
		
		List<LeagueBean> beans = new ArrayList<LeagueBean>();
		LeagueBean bean;
		for ( League ent : entList) {
			bean = new LeagueBean();
			bean.setName(ent.getName());
			bean.setShortName(ent.getShortName());
			bean.setUrl(ent.getUrl());
			beans.add(bean);
		}
		
		return beans;
	}



	

	public League findByShortNameEnt(String leagueShortName, String username) {
		User user = userDao.retrieveByUsername(username);
		League ent = leagueRepo.findByUserAndShortName(user, leagueShortName);
		return ent;
	}
	


	public LeagueBean findLeagueByShortName(String leagueShortName, String username) {
		User user = userDao.retrieveByUsername(username);
		League ent = leagueRepo.findByUserAndShortName(user, leagueShortName);
		if (ent == null)
			return null;
		LeagueBean bean = new LeagueBean();
		bean.setName(ent.getName());
		bean.setShortName(ent.getShortName());
		bean.setUrl(ent.getUrl());
		return bean;
	}




	public Boolean saveCompetitions(List<CompetitionBean> beans, String leagueShortName, String username) {
		List<Competition> ents = new ArrayList<Competition>();
		
		User user = userDao.retrieveByUsername(username);
		League league = leagueRepo.findByUserAndShortName(user, leagueShortName);
		
		Competition ent;
		Competition existingCompetition;
		for (CompetitionBean bean : beans) {
			
			existingCompetition = competitionRepo.findByLeagueAndShortName(league, bean.getCompetitionShortName());
			if (existingCompetition != null)
				continue;
			
			ent = new Competition();
			ent.setName(bean.getName());
			ent.setShortName(bean.getCompetitionShortName());
			ent.setUrl(bean.getUrl());
			ent.setLeague(league);
			ent.setType(bean.getType());
			
			ents.add(ent);
			
		}
		
		competitionRepo.save(ents);
		
		return true;
	}
	@Cacheable("competition")
	public Competition findCompetitionByShortNameAndLeagueEnt (String competitionShortName, String leagueShortName, String username) {
	
		League league = findByShortNameEnt(leagueShortName, username);
		Competition competition = competitionRepo.findByLeagueAndShortName(league, competitionShortName);
		
		return competition;
	}
	
	
	public List<CompetitionBean> findCompetitionsByLeague(String leagueShortName, String username) {
		User user = userDao.retrieveByUsername(username);
		League league = leagueRepo.findByUserAndShortName(user, leagueShortName);

		List<Competition> ents = competitionRepo.findByLeague(league);

		List<CompetitionBean> beans = new ArrayList<CompetitionBean>();
		CompetitionBean bean;
		for (Competition e : ents) {
			bean = new CompetitionBean();
			bean.setLeagueShortName(e.getLeague().getShortName());
			bean.setName(e.getName());
			bean.setCompetitionShortName(e.getShortName());
			bean.setUrl(e.getUrl());
			bean.setType(e.getType());
			
			beans.add(bean);
		}
		
		return beans; 		
	}


	public void saveCompetition(Competition competition) {
		competitionRepo.save(competition);
		
	}


	public void saveCalculatedSeasonResult(SeasonResultBean seasonResultBean, String leagueShortName, String competitionShortName, String username) {
		

		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		SeasonResult seasonResult = seasonResultRepo.findByCompetition(competition);
		
		if (seasonResult != null)
			seasonResultRepo.delete(seasonResult);
		
		seasonResult = new SeasonResult();
		
		seasonResult.setName(seasonResultBean.getName());
		List<SeasonDayResult> seasonDayResults = new ArrayList<SeasonDayResult>();
		
		for (SeasonDayResultBean seasonDayResultBean : seasonResultBean.getSeasonDayResults()) {
			SeasonDayResult seasonDayResult = createSeasonDayResult(seasonDayResultBean);
			seasonDayResults.add(seasonDayResult);
		}
		seasonResult.setSeasonDayResults(seasonDayResults);
		
		seasonResult.setCompetition(competition);
		
		seasonResultRepo.save(seasonResult);
		
	}


	private SeasonDayResult createSeasonDayResult(SeasonDayResultBean bean) {
		
		SeasonDayResult ent = new SeasonDayResult();
		
		ent.setName(bean.getName());
		
		if (bean.getLinesUpLight()!= null) {
			List<LineUpLight> lineUpLights = new ArrayList<LineUpLight>();
			
			
			for (LineUpLightBean lineUpLightBean : bean.getLinesUpLight()) {
				LineUpLight lineUpLight = createLineUpLight(lineUpLightBean);
				lineUpLights.add(lineUpLight);
			}
			
			ent.setLinesUpLight(lineUpLights);
		}
		
		return ent;
	}


	private LineUpLight createLineUpLight(LineUpLightBean bean) {
		
		LineUpLight ent = new LineUpLight();
		
		ent.setGoalkeeperModifier(bean.getGoalkeeperModifier());
		ent.setGoals(bean.getGoals());
		ent.setMiddlefieldersVariation(bean.getMiddlefieldersVariation());
		ent.setRankingPoints(bean.getRankingPoints());
		
		ent.setSumTotalPoints(bean.getSumTotalPoints());
		ent.setTakenGoals(bean.getTakenGoals());
		ent.setTeamName(bean.getTeamName());
		ent.setTotalWithoutGoalkeeperAndMiddlefielderModifiers(bean.getTotalWithoutGoalkeeperAndMiddlefielderModifiers());
		
		return ent;
	}
	
	
	
	
	public SeasonResultBean findCalculatedSeasonResult(String leagueShortName, String competitionShortName, String username) {
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);

		SeasonResultBean bean = new SeasonResultBean();	
		SeasonResult ent = seasonResultRepo.findByCompetition(competition);
		
		bean.setName(ent.getName());
		List<SeasonDayResultBean> seasonDayResultBeans = new ArrayList<SeasonDayResultBean>();
		for (SeasonDayResult sdEnt : ent.getSeasonDayResults()) {
			
			SeasonDayResultBean sdBean = createSeasonDayResultBean(sdEnt);
			seasonDayResultBeans.add(sdBean);
		}
			
		bean.setSeasonDayResults(seasonDayResultBeans);
	
		return bean;	
	}
	
	
	private SeasonDayResultBean createSeasonDayResultBean(SeasonDayResult ent) {
		
		String name = ent.getName();

		if (ent.getLinesUpLight() == null) {
			SeasonDayResultBean bean = new SeasonDayResultBean();
			bean.setName(name);
			return bean;
		}
		
		List<LineUpLightBean> linesUpLightBeans = new ArrayList<LineUpLightBean>();
		for (LineUpLight lulEnt : ent.getLinesUpLight()) {
			LineUpLightBean lulBean = createLineUpLightBean(lulEnt);
			linesUpLightBeans.add(lulBean);
		}
			
		SeasonDayResultBean bean = new SeasonDayResultBean(name, linesUpLightBeans);
		
		return bean;
	}


	public void saveOnlineSeason(SeasonBean bean, String leagueShortName, String competitionShortName, String username) {
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		Season s = seasonRepo.findByNameAndCompetition(null, competition);
		if (s == null) {
			s = new Season();
			s.setCompetition(competition);
			s.setName(bean.getName());
		}
		List<SeasonDay> seasonDayEnts = new ArrayList<SeasonDay>();
		for (SeasonDayBean seasonDayBean: bean.getSeasonDays()) {
			
			SeasonDay seasonDayEnt = createSeasonDayEnt(seasonDayBean);
			seasonDayEnts.add(seasonDayEnt );
			
		}
			
			
		s.setSeasonDays(seasonDayEnts);
		
		seasonRepo.save(s);
		
	}

	private SeasonDay createSeasonDayEnt(SeasonDayBean bean) {
		SeasonDay ent = new SeasonDay();
		ent.setName(bean.getName());
		ent.setSerieANumber(bean.getSerieANumber());
		List<Matcho> matchEnts = new ArrayList<Matcho>();
		for (MatchBean matchBean : bean.getMatches()) {
			Matcho match = new Matcho();
			LineUpLight homeTeamResult = createLineUpLight(matchBean.getHomeTeamResult());
			match.setHomeTeamResult(homeTeamResult);
			match.setHomeTeam(matchBean.getHomeTeam());

			LineUpLight awayTeamResult = createLineUpLight(matchBean.getAwayTeamResult());
			match.setAwayTeamResult(awayTeamResult);
			match.setAwayTeam(matchBean.getAwayTeam());

			matchEnts.add(match);
			
		}
		
		ent.setMatches(matchEnts);
		return ent;
	}


	public void saveTeams(List<String> teams, String leagueShortName, String username) {
		League league = findByShortNameEnt(leagueShortName, username);
		
		String teamsString = "";
		for (String t : teams)
			teamsString += t + ",";
		league.setTeams(teamsString);
		
		leagueRepo.save(league);
	}


	public List<String> findTeams(String leagueShortName, String username) {
		League league = findByShortNameEnt(leagueShortName, username);
		String teamsString = league.getTeams();
		List<String> teams = new ArrayList<String>();
		String[] split = teamsString.split(",");
		for (int i = 0; i<split.length; i++)
			teams.add(split[i]);
		
		return teams;
	}
	
	
	// SIMILE A LEAGUE DAO
	public void saveCompetitionPattern(SeasonBean bean, String leagueShortName, String competitionShortName, String username) {
		
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		Season s = seasonRepo.findByNameAndCompetition("Pattern", competition);
		if (s != null)
			return;
		
		s = new Season();
		s.setName("Pattern");
		List<SeasonDay> seasonDays = new ArrayList<SeasonDay>();
		
		for (SeasonDayBean seasonDayBean : bean.getSeasonDays()) {
			SeasonDay seasonDay = createSeasonDayPatternEnt(seasonDayBean);
			seasonDays.add(seasonDay);
			
		}
		s.setSeasonDays(seasonDays);
		s.setCompetition(competition);
		
		seasonRepo.save(s);
		
	}

	// SIMILE A LEAGUE DAO
	private SeasonDay createSeasonDayPatternEnt(SeasonDayBean bean) {
		SeasonDay ent = new SeasonDay();
		ent.setName(bean.getName());
		ent.setSerieANumber(bean.getSerieANumber());
		List<Matcho> matches = new ArrayList<Matcho>();
		for (MatchBean matchBean : bean.getMatches()) {
			Matcho match = new Matcho();
			match.setHomeTeam(matchBean.getHomeTeamEnum().name());
			match.setAwayTeam(matchBean.getAwayTeamEnum().name());
			matches.add(match);
		}
		ent.setMatches(matches);
		return ent;
	}

	public SeasonBean findSeason(String leagueShortName, String competitionShortName, String username, String seasonName) {
		
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		Season ent = seasonRepo.findByNameAndCompetition(seasonName, competition);
		if (ent == null)
			return null;
		
		SeasonBean seasonBean = new SeasonBean();
		seasonBean.setName(ent.getName());
		List<SeasonDayBean> seasonDayBeans = new ArrayList<SeasonDayBean>();

		for (SeasonDay seasonDayEnt : ent.getSeasonDays()) {
			SeasonDayBean seasonDayBean = createSeasonDayBean(seasonDayEnt);
			seasonDayBeans.add(seasonDayBean);
		}
		seasonBean.setSeasonDays(seasonDayBeans);
		
		return seasonBean;
	}


	private SeasonDayBean createSeasonDayBean(SeasonDay ent) {
		SeasonDayBean bean = new SeasonDayBean();
		bean.setName(ent.getName());
		
		List<MatchBean> matchesBean = new ArrayList<MatchBean>();
		for(Matcho match : ent.getMatches()) {
			MatchBean matchBean = createMatchBean(match);
			matchesBean.add(matchBean);
		}
		
		bean.setMatches(matchesBean);
		
		return bean;
	}


	private MatchBean createMatchBean(Matcho ent) {
		MatchBean bean = new MatchBean();
		
		bean.setHomeTeam(ent.getHomeTeam());
		bean.setAwayTeam(ent.getAwayTeam());
		
		try {
			bean.setHomeTeamEnum(PlayerEnum.valueOf(ent.getHomeTeam()));
			bean.setAwayTeamEnum(PlayerEnum.valueOf(ent.getAwayTeam()));
		}
		catch (Exception e) {
			
		}
		
		LineUpLightBean homeTeamResult = createLineUpLightBean(ent.getHomeTeamResult());
		bean.setHomeTeamResult(homeTeamResult);
		LineUpLightBean awayTeamResult = createLineUpLightBean(ent.getAwayTeamResult());
		bean.setAwayTeamResult(awayTeamResult);
		
		return bean;
	}


	private LineUpLightBean createLineUpLightBean(LineUpLight ent) {
		if (ent == null)
			return null;
		LineUpLightBean bean = new LineUpLightBean();
		bean.setGoalkeeperModifier(ent.getGoalkeeperModifier());
		bean.setGoals(ent.getGoals());
		bean.setMiddlefieldersVariation(ent.getMiddlefieldersVariation());
		bean.setRankingPoints(ent.getRankingPoints());
		
		bean.setSumTotalPoints(ent.getSumTotalPoints());
		bean.setTakenGoals(ent.getTakenGoals());
		bean.setTeamName(ent.getTeamName());
		bean.setTotalWithoutGoalkeeperAndMiddlefielderModifiers(ent.getTotalWithoutGoalkeeperAndMiddlefielderModifiers());
		
		return bean;
	}


	public void saveRanking(RankingBean bean, String leagueShortName, String competitionShortName, String username) {
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		
		String rankingName = bean.getName();
		String rulesType = bean.getRulesType().name();
		Ranking ent = rankingRepo.findByCompetitionAndNameAndRulesType(competition, rankingName, rulesType);
		
		if (ent != null)
			rankingRepo.delete(ent);
		
		
		ent = new Ranking();
		ent.setCompetition(competition);
		ent.setName(rankingName);
		ent.setRulesType(bean.getRulesType().name());
		List<RankingRow> rows = new ArrayList<RankingRow>();
		for (RankingRowBean rrb : bean.getRows()) {
			String positions = "";
			RankingRow rre = createRankingRowEnt(rrb);
			if ( rrb.getPositions() != null)
				for (Double pos : rrb.getPositions())
					positions += pos + "-";
			rre.setPositions(positions);
			rows.add(rre);
		}
		ent.setRows(rows);
		
		rankingRepo.save(ent);
		
	}


	private RankingRow createRankingRowEnt(RankingRowBean bean) {
		RankingRow ent = new RankingRow();
		ent.setName(bean.getName());
		ent.setPoints(bean.getPoints());
		ent.setRankingPosition(ent.getRankingPosition());
		ent.setScoredGoals(bean.getScoredGoals());
		ent.setSumAllVotes(bean.getSumAllVotes());
		ent.setTakenGoals(bean.getTakenGoals());
		
		if (bean.getLuckyEdge()!= null) {
			ent.setLuckyEdgeGain(bean.getLuckyEdge().getLuckyEdgeGain());
			ent.setUnluckyEdgeLose(bean.getLuckyEdge().getUnluckyEdgeLose());
			ent.setLuckyEdgeNumber(bean.getLuckyEdge().getLuckyEdgeNumber());
			ent.setUnluckyEdgeNumber(bean.getLuckyEdge().getUnluckyEdgeNumber());
		}
		
		return ent;
	}



	public RankingBean findRanking(String leagueShortName, String competitionShortName, String username, RankingType name, RulesType rulesType) {
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		RankingBean bean = new RankingBean();
		
		Ranking ent = rankingRepo.findByCompetitionAndNameAndRulesType(competition, name.name(), rulesType.name());
		if (ent == null)
			return null;
		
		bean.setName(ent.getName());
		
		
		List<RankingRowBean> rows = new ArrayList<RankingRowBean>();
		for (RankingRow rrEnt : ent.getRows()) {
			RankingRowBean rrBean = createRankingRowBean(rrEnt);
			rows.add(rrBean);
		}
		bean.setRows(rows);
		
		return bean;
	}


	private RankingRowBean createRankingRowBean(RankingRow ent) {
		RankingRowBean bean = new RankingRowBean();
		bean.setName(ent.getName());
		bean.setPoints(ent.getPoints());
		bean.setRankingPosition(ent.getRankingPosition());
		bean.setScoredGoals(ent.getScoredGoals());
		bean.setSumAllVotes(ent.getSumAllVotes());
		bean.setTakenGoals(ent.getTakenGoals());
		
		String positionsString = ent.getPositions();
		if (positionsString!= null && !positionsString.equals("")) {
			String[] split = positionsString.split("-");
			List<Double> positions = new ArrayList<Double>();
			for (int i=0; i< split.length; i++)
				positions.add(new Double(split[i]));
			bean.setPositions(positions);
		}
		
		if (ent.getLuckyEdgeGain()!= null) {
			LuckyEdgeInfo luckyEdge = new LuckyEdgeInfo();
			luckyEdge.setLuckyEdgeGain(ent.getLuckyEdgeGain());
			luckyEdge.setLuckyEdgeNumber(ent.getLuckyEdgeNumber());
			luckyEdge.setUnluckyEdgeLose(ent.getUnluckyEdgeLose());
			luckyEdge.setUnluckyEdgeNumber(ent.getUnluckyEdgeNumber());
			bean.setLuckyEdge(luckyEdge);
		}
		
		return bean;
	}


	public void saveSeasonFromWeb(String leagueShortName, String competitionShortName, String username, SeasonFromWebBean seasonFromWeb) {
		
		if (seasonFromWeb.getSeasonDaysFromWeb().isEmpty())
			return;
		
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);

		// Se esiste e ha meno giornate cancello tutto e salvo quella nuova....da ottimizzare
		SeasonFromWeb ent = seasonFromWebRepo.findByCompetition(competition);
		
		if (ent == null) {
			ent = new SeasonFromWeb();
			ent.setCompetition(competition);
			ent.setSeasonDaysFromWeb(new ArrayList<SeasonDayFromWeb>());
			ent.setName(seasonFromWeb.getName());
		}
		
		
		List<SeasonDayFromWeb> seasonDayFromWebEntList = ent.getSeasonDaysFromWeb();
			
		for (Integer key : seasonFromWeb.getSeasonDaysFromWeb().keySet()) {
			if (isNotAlreadySaved(ent.getSeasonDaysFromWeb(), key)) {
		
				Integer competitionSeasonDay = key;
				SeasonDayFromWebBean seasonDayFromWebBean = seasonFromWeb.getSeasonDaysFromWeb().get(key);
				
				SeasonDayFromWeb seasonDayFromWebEnt = createSeasonDayFromWebEnt(competitionSeasonDay, seasonDayFromWebBean);
				seasonDayFromWebEntList.add(seasonDayFromWebEnt);
			}
		}
		
		seasonFromWebRepo.save(ent);
		
	}


	private boolean isNotAlreadySaved(List<SeasonDayFromWeb> seasonDaysFromWeb, Integer key) {
		for (SeasonDayFromWeb seasonDay : seasonDaysFromWeb)
			if (seasonDay.getCompetitionSeasonDay() == key){
				return false;
			}
		return true;
	}


	private SeasonDayFromWeb createSeasonDayFromWebEnt(Integer competitionSeasonDay, SeasonDayFromWebBean bean) {
		
		SeasonDayFromWeb ent = new SeasonDayFromWeb();
		
		ent.setName(bean.getName());
		List<LineUpFromWeb> lineUpFromWebEntList = new ArrayList<LineUpFromWeb>();
		LineUpFromWeb lineUpFromWebEnt;
		for (LineUp lineUpBean : bean.getLinesUp() ){
			lineUpFromWebEnt = createLineUpFromWeb(lineUpBean);
			lineUpFromWebEntList.add(lineUpFromWebEnt);
		}
		
		ent.setCompetitionSeasonDay(competitionSeasonDay);
		ent.setLinesUpFromWeb(lineUpFromWebEntList);
		
		return ent;
		
	}


	private LineUpFromWeb createLineUpFromWeb(LineUp lineUp) {
		
		LineUpFromWeb ent = new LineUpFromWeb();
		String info = "";
		
		info += "#" + RoleEnum.P.name() + "@" + getPlayerVotesString(lineUp.getGoalKeeper());
		info += "#" + RoleEnum.D.name() + "@" + getPlayerVotesString(lineUp.getDefenders());
		info += "#" + RoleEnum.C.name() + "@" + getPlayerVotesString(lineUp.getMidfielders());
		info += "#" + RoleEnum.A.name() + "@" + getPlayerVotesString(lineUp.getStrikers());
		info += "#" + "R" 				+ "@" + getPlayerVotesString(lineUp.getReserves());
		
		ent.setName(lineUp.getTeamName());
		ent.setInfo(info);
		
		//#G#BUFFON_JUV_P_6.5_5.5;#D#DE VRIJ_LAZ_D_6.5_6.5;CALDARA_ATA_D_7.0_10.0;...
		
		
		ent.setGoalkeeperModifierFromWeb(lineUp.getGoalkeeperModifierFromWeb());
		ent.setDefenderModifierFromWeb(lineUp.getDefenderModifierFromWeb());
		ent.setMiddlefieldersModifierFromWeb(lineUp.getMiddlefieldersModifierFromWeb());
		ent.setStrickerModifierFromWeb(lineUp.getStrickerModifierFromWeb());
		ent.setPerformanceModifierFromWeb(lineUp.getPerformanceModifier());
		ent.setFairPlayModifierFromWeb(lineUp.getFairPlayModifierFromWeb());
		
		lineUp.setTeamName(lineUp.getTeamName());
		
		return ent;
	}


	private String getPlayerVotesString(List<PlayerVote> list) {
		String result = "";
		for (PlayerVote p : list) {
			result += p.getName() + "_" + p.getTeam() + "_" + p.getRole() + "_" + p.getVoteFromWeb() + "_" + p.getFantaVoteFromWeb() + ";";
		}
		return result;
	}


	public SeasonFromWebBean findSeasonFromWeb(String leagueShortName, String competitionShortName, String username) {
		
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		
		SeasonFromWeb ent = seasonFromWebRepo.findByCompetition(competition);
		//seasonFromWebRepo.delete(ent)
		if (ent == null)
			return null;
		
		SeasonFromWebBean bean = new SeasonFromWebBean();
		bean.setName(ent.getName());
		
		Map<Integer, SeasonDayFromWebBean> seasonDaysFromWebBeanList = new HashMap<Integer, SeasonDayFromWebBean>();
		
		for (SeasonDayFromWeb seasonDayFromWebEnt: ent.getSeasonDaysFromWeb()) {
			
			Integer competitionSeasonDayKey = seasonDayFromWebEnt.getCompetitionSeasonDay();
			
			SeasonDayFromWebBean seasonDayFromWebBean = createSeasonDayFromWebEnt(seasonDayFromWebEnt);
			seasonDaysFromWebBeanList.put(competitionSeasonDayKey, seasonDayFromWebBean);
			
			
		}
		
		
		bean.setSeasonDaysFromWeb(seasonDaysFromWebBeanList);
		
		
		return bean;
	}


	private SeasonDayFromWebBean createSeasonDayFromWebEnt(SeasonDayFromWeb ent) {
		
		SeasonDayFromWebBean bean = new SeasonDayFromWebBean();
		List<LineUp> lineUpList = new ArrayList<LineUp>();
		LineUp lineUpBean;
		for (LineUpFromWeb lineUpEnt : ent.getLinesUpFromWeb()) {
			lineUpBean = createLineUpBean(lineUpEnt);
			lineUpList.add(lineUpBean);
		}
		
		bean.setLinesUp(lineUpList);
		bean.setName(ent.getName());
		
		return bean;
	}


	private LineUp createLineUpBean(LineUpFromWeb ent) {
		LineUp bean = new LineUp();
		bean.setTeamName(ent.getName());
		
		bean.setGoalkeeperModifierFromWeb(ent.getGoalkeeperModifierFromWeb());
		bean.setDefenderModifierFromWeb(ent.getDefenderModifierFromWeb());
		bean.setMiddlefieldersModifierFromWeb(ent.getMiddlefieldersModifierFromWeb());
		bean.setStrickerModifierFromWeb(ent.getStrickerModifierFromWeb());
		bean.setFairPlayModifierFromWeb(ent.getFairPlayModifierFromWeb());
		bean.setPerformanceModifierFromWeb(ent.getPerformanceModifierFromWeb());
		
		
		populateLineUpWithPlayers(bean, ent.getInfo());
		
		
		
		
		return bean;
	}

	//#P@BUFFON_JUV_P_6.5_5.5;#D#DE VRIJ_LAZ_D_6.5_6.5;CALDARA_ATA_D_7.0_10.0;...
	private void populateLineUpWithPlayers(LineUp bean, String allPlayersString) {
		String[] groupByRoleString = allPlayersString.split("#");
		
		List<PlayerVote> players = null;
		for (String group : groupByRoleString) {
			if (group.equals(""))
				continue;
			String role = group.split("@")[0];
			if (group.split("@").length != 2) {			// se un giocatore non ha dato la formazione e si ha la regola che chi non da la formazione gioca con 0 giocatori
				players = new ArrayList<PlayerVote>();
			}
			else {
				String rolePlayersString = group.split("@")[1];
				players = createPlayersFromString(rolePlayersString);
			}
			
			
			if (role.equals("P")) {
				bean.setGoalKeeper(players);
			} else if (role.equals("D")) {
				bean.setDefenders(players);
			} else if (role.equals("C")) {
				bean.setMidfielders(players);
			}  else if (role.equals("A")) {
				bean.setStrikers(players);
			}  else if (role.equals("R")) {
				bean.setReserves(players);
			} 
		}
	}


	private List<PlayerVote> createPlayersFromString(String playersString) {
		List<PlayerVote> listBean = new ArrayList<PlayerVote>();
		String[] players = playersString.split(";");
		for (int i=0; i< players.length; i++) {
			String[] playerData = players[i].split("_");
			String name = playerData[0];
			String team = playerData[1];
			String roleString = playerData[2];
			RoleEnum role = RoleEnum.valueOf(roleString);
			
			String voteFromWebString = playerData[3];
			Double voteFromWeb = null;
			if ( !voteFromWebString.equals("null") )
				voteFromWeb = Double.valueOf(voteFromWebString);
			
			String fantaVoteFromWebString = playerData[4];
			Double fantaVoteFromWeb = null;
			if ( !fantaVoteFromWebString.equals("null") )
				fantaVoteFromWeb = Double.valueOf(fantaVoteFromWebString);
			
			
			PlayerVote playerVoteBean = new PlayerVote();
			listBean.add(playerVoteBean);
			playerVoteBean.setName(name);
			playerVoteBean.setTeam(team);
			playerVoteBean.setRole(role);
			playerVoteBean.setVoteFromWeb(voteFromWeb);
			playerVoteBean.setFantaVoteFromWeb(fantaVoteFromWeb);
		}
		return listBean;
	}


	public void removeSeasonDaysFromWebSeasonDays(String leagueShortName, String competitionShortName, String username,	List<Integer> competitionSeasonDaysToRemove) {
		

		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		SeasonFromWeb ent = seasonFromWebRepo.findByCompetition(competition);
		List<SeasonDayFromWeb> seasonDayToRemove = new ArrayList<SeasonDayFromWeb>();
		
		for (SeasonDayFromWeb seasonDayEnt : ent.getSeasonDaysFromWeb()) {
			if (    competitionSeasonDaysToRemove.contains( seasonDayEnt.getCompetitionSeasonDay() )   ){
				seasonDayToRemove.add(seasonDayEnt);
			}
		}
		ent.getSeasonDaysFromWeb().removeAll(seasonDayToRemove);
		seasonFromWebRepo.save(ent);
		
		
	}


}
