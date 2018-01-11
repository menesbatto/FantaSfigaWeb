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
import app.dao.entity.SeasonDayResult;
import app.dao.entity.SeasonResult;
import app.dao.entity.User;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._1_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._1_realChampionshipAnalyzer.model.SeasonDayResultBean;
import app.logic._2_seasonPatternExtractor.model.SeasonResultBean;

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
	
	
	
}
