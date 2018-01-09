package app.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.CompetitionBean;
import app.dao.entity.Competition;
import app.dao.entity.League;
import app.dao.entity.User;
import app.logic._0_credentialsSaver.LeagueBean;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.UserBean;

@Service
@EnableCaching
public class LeagueDao {

	@Autowired
	private LeagueRepo leagueRepo;

	@Autowired
	private CompetitionRepo competitionRepo;
	
	@Autowired
	private UserDao userDao;




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
			
			existingCompetition = competitionRepo.findByLeagueAndName(league, bean.getName());
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
	
	public Competition findCompetitionByNameAndLeagueEnt (String competitionName, String leagueShortName, String username) {
	
		League league = findByShortNameEnt(leagueShortName, username);
		Competition competition = competitionRepo.findByLeagueAndName(league, competitionName);
		
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
			bean.setLeagueName(e.getLeague().getName());
			bean.setName(e.getName());
			bean.setShortName(e.getShortName());
			bean.setUrl(e.getUrl());
			bean.setType(e.getType());
			
			beans.add(bean);
		}
		
		return beans; 		
	}
	
	
	
}
