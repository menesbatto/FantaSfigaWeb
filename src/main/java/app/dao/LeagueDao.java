package app.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.CompetitionBean;
import app.dao.entity.Competition;
import app.dao.entity.League;
import app.dao.entity.LineUpLight;
import app.dao.entity.Matcho;
import app.dao.entity.Season;
import app.dao.entity.SeasonDay;
import app.dao.entity.SeasonDayResult;
import app.dao.entity.SeasonResult;
import app.dao.entity.User;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.PlayerEnum;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._1_seasonPatternExtractor.model.SeasonResultBean;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;

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




	public Boolean saveLeagues(List<LeagueBean> beans, String username) {
		
		List<League> ents = new ArrayList<League>();
		User user = userDao.retrieveByUsername(username);
		
		
		League ent;
		League existingLeague;
		for (LeagueBean bean : beans) {
			
			existingLeague = leagueRepo.findByUserAndShortName(user, bean.getName());
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


	public League findByShortNameEnt(String leagueShortName, String username) {
		User user = userDao.retrieveByUsername(username);
		League ent = leagueRepo.findByUserAndShortName(user, leagueShortName);
		return ent;
	}
	


	public LeagueBean findByShortName(String leagueShortName, String username) {
		User user = userDao.retrieveByUsername(username);
		League ent = leagueRepo.findByUserAndShortName(user, leagueShortName);
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
			
			existingCompetition = competitionRepo.findByLeagueAndShortName(league, bean.getShortName());
			if (existingCompetition != null)
				continue;
			
			ent = new Competition();
			ent.setName(bean.getName());
			ent.setShortName(bean.getShortName());
			ent.setUrl(bean.getUrl());
			ent.setLeague(league);
			ent.setType(bean.getType());
			
			ents.add(ent);
			
		}
		
		competitionRepo.save(ents);
		
		return true;
	}
	
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
			bean.setShortName(e.getShortName());
			bean.setUrl(e.getUrl());
			bean.setType(e.getType());
			
			beans.add(bean);
		}
		
		return beans; 		
	}


	public void saveCompetition(Competition competition) {
		competitionRepo.save(competition);
		
	}


	public void saveCalculatedSeason(SeasonResultBean seasonResultBean, String leagueShortName, String competitionShortName, String username) {
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		
		SeasonResult seasonResult = new SeasonResult();
		
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
		
		List<LineUpLight> lineUpLights = new ArrayList<LineUpLight>();
		
		
		for (LineUpLightBean lineUpLightBean : bean.getLinesUpLight()) {
			LineUpLight lineUpLight = createLineUpLight(lineUpLightBean);
			lineUpLights.add(lineUpLight);
		}
		
		ent.setLinesUpLight(lineUpLights);
		
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
	
	
	public void saveOnlineSeason(SeasonBean bean, String leagueShortName, String competitionShortName, String username) {
		Competition competition = findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);

		Season s = seasonRepo.findByNameAndCompetition(null, competition);
		if (s != null)
			return;
		
		s = new Season();
		s.setCompetition(competition);
		s.setName(bean.getName());
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


	private LineUpLightBean createLineUpLightBean(LineUpLight awayTeamResult) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
}
