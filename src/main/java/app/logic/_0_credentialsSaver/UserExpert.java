package app.logic._0_credentialsSaver;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.RulesType;
import app.dao.LeagueDao;
import app.dao.RulesDao;
import app.dao.UserDao;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic.model.CompetitionBean;
import app.utils.AppConstants;
import app.utils.HttpUtils;

@Service
public class UserExpert {
		
	@Autowired
	private UserBean userBean;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LeagueDao leagueDao;
	
	@Autowired
	private RulesDao rulesDao;
	

	public void saveGazzettaCredentials(Credentials credentials){
		
		userDao.saveGazzettaCredentials(credentials, userBean.getUsername());
	}

	public UserBean createUser(UserBean userBean){
		
		userBean = userDao.createUser(userBean);
		
		return userBean;
	}
	
	public boolean confirmUser(ConfirmUser confirmUser){
		
		Boolean confirmed = userDao.confirmUser(confirmUser);
		return confirmed;
	}
	
	public List<LeagueBean> downloadLeagues(){
		if (userBean.getUsername() == null)
			return null;
		Credentials c = userDao.retrieveGazzettaCredentials(userBean.getUsername());
		Document doc = HttpUtils.getHtmlPageLogged(AppConstants.LOGIN_PAGE_URL, c.getUsername(), c.getPassword());
		Elements elements = null;
		try {
			elements = doc.getElementsByClass("llist").get(0).getElementsByTag("a");
		}
		catch(Exception e) {
			HttpUtils.getLoggedWebDriver().close();
			HttpUtils.setLoggedWebDriver(null);
		}
		
		String leagueName;
		String leagueShortName;
		String leagueUrl;
		List<LeagueBean> leagues = new ArrayList<LeagueBean>();

		for (Element elem : elements) {
			leagueUrl = elem.attr("href");
			
			leagueShortName = leagueUrl.substring(leagueUrl.lastIndexOf("/") + 1, leagueUrl.length());	//accaniti-division
			if (leagueShortName.equals("iscrizione"))
				continue;
			
			leagueName = elem.text();	//accaniti division
			
			LeagueBean l = new LeagueBean();
			l.setName(leagueName);
			l.setShortName(leagueShortName);
			l.setUrl(leagueUrl);
			leagues.add(l);
			
		}
		
		leagueDao.saveLeagues(leagues, userBean.getUsername());
		
		return leagues;
	}

	public List<LeagueBean> retrieveLeagues(){
		if (userBean.getUsername() == null)
			return null;
		
		List<LeagueBean> leagues = new ArrayList<LeagueBean>();
		leagues = leagueDao.findLeaguesByUsername(userBean.getUsername());
		for (LeagueBean league : leagues) {
			List<CompetitionBean> competitionsByLeague = leagueDao.findCompetitionsByLeague(league.getShortName(), userBean.getUsername());
			Boolean existRulesForLeague = rulesDao.existRulesForLeague(league.getShortName(), userBean.getUsername());
			league.setRulesDownloaded(existRulesForLeague);
			if (competitionsByLeague.isEmpty()) {
				league.setCompetitionsDownloaded(false);
			}
			else {
				league.setCompetitionsDownloaded(true);
			}
		}
		
		return leagues;
	}

	
	
	public List<CompetitionBean> downloadCompetitions(String leagueShortName){
		if (userBean.getUsername() == null)
			return null;
		
		Credentials c = userDao.retrieveGazzettaCredentials(userBean.getUsername());
		
		LeagueBean league = leagueDao.findLeagueByShortName(leagueShortName, userBean.getUsername());
		
		String leagueUrl = AppConstants.GAZZETTA_URL + league.getShortName() + "/visualizza-competizioni";
		System.out.println();
		Document doc = HttpUtils.getHtmlPageLogged(leagueUrl , c.getUsername(), c.getPassword());
		
		List<CompetitionBean> competitions = new ArrayList<CompetitionBean>();

		Element elementsOfGestioneCompetizionePage = doc.getElementById("tbcompattive");
		createCompetitions(doc, elementsOfGestioneCompetizionePage, competitions);
		
		elementsOfGestioneCompetizionePage = doc.getElementById("tbcompterminate");
		createCompetitions(doc, elementsOfGestioneCompetizionePage, competitions);
		
		leagueDao.saveCompetitions(competitions, league.getShortName(), userBean.getUsername());
		
		return competitions;
	}

	private void createCompetitions(Document doc, Element elementsOfGestioneCompetizionePage,
			List<CompetitionBean> competitions) {
		Elements elements;
		String competitionShortName;
		String competitionName;
		String competitionRulesUrl;
		String type;
		
		
		if ( elementsOfGestioneCompetizionePage == null) {	//NON AMMINISTRATORE
			elements = doc.getElementById("dropCompetizione").getElementsByTag("option");
			
			for (Element elem : elements) {
				type = null;
				
				competitionRulesUrl = elem.attr("value");		//http://leghe.fantagazzetta.com/accaniti-division/modifica-competizione-gironi/288971
																//http://leghe.fantagazzetta.com/sisisicertocerto-league/visualizza-competizioni
				competitionShortName = competitionRulesUrl.substring(competitionRulesUrl.lastIndexOf("/") + 1, competitionRulesUrl.length());	//247720
				
				competitionName = elem.text();		//CAMPIONATO SERIE A-CCANITA
				
				CompetitionBean com = new CompetitionBean(competitionName, competitionShortName, competitionRulesUrl, null, type);
	
				competitions.add(com);
			
			}
		}
		
		else if ( elementsOfGestioneCompetizionePage!= null) {	//AMMINISTRATORE
		
			elements = elementsOfGestioneCompetizionePage.getElementsByTag("tbody").get(0).getElementsByTag("tr");
			
			for (Element elem : elements) {
				if (elem.getElementsByTag("a").isEmpty())
					continue;
				competitionRulesUrl = elem.getElementsByTag("a").get(0).attr("href");		//http://leghe.fantagazzetta.com/accaniti-division/modifica-competizione-gironi/288971
																							//http://leghe.fantagazzetta.com/accaniti-division/home/247720
				
				type = elem.getElementsByTag("img").get(0).attr("data-original-title");
				if (type.equals("Competizione a gruppi stile champions league")) 
					type = "CL";
				else if  (type.equals("Competizione in stile formula 1"))
					type = "F1";
				else if  (type.equals("Competizione a calendario"))
					type = "CA";
				else if  (type.equals("Competizione a calendario"))
					type = "CA";
				
				
				competitionShortName = competitionRulesUrl.substring(competitionRulesUrl.lastIndexOf("/") + 1, competitionRulesUrl.length());	//247720
	
				competitionName = elem.getElementsByTag("span").get(0).text();		//CAMPIONATO SERIE A-CCANITA
				
				CompetitionBean com = new CompetitionBean(competitionName, competitionShortName, competitionRulesUrl, null, type);

				competitions.add(com);
			}
			
		}
	}
	
	public List<CompetitionBean> retrieveCompetitions(String leagueShortName){
		if (userBean.getUsername() == null)
			return null;
				
		LeagueBean league = leagueDao.findLeagueByShortName(leagueShortName, userBean.getUsername());
		
		List<CompetitionBean> competitions = new ArrayList<CompetitionBean>();
		
		competitions = leagueDao.findCompetitionsByLeague(league.getShortName(), userBean.getUsername());
		
		for (CompetitionBean comp : competitions) {
			comp.setLeagueName(league.getName());
			RulesBean rules = rulesDao.retrieveRules(comp.getCompetitionShortName(), leagueShortName, RulesType.REAL, userBean.getUsername());
			
			Boolean existIntegratedRulesForCompetition = rules.getCompetitionRules().getPostponementBehaviour() != null;
			comp.setRulesIntegrated(existIntegratedRulesForCompetition);
			
			// calculateBinding
			Boolean binding = rules.getCompetitionRules().getBinding() != null;
			
			Boolean initialOnlineInfoDownloaded = false;
			if (binding) {
				//calculateCompetitionPattern
				SeasonBean findSeasonPatter = leagueDao.findSeason(leagueShortName, comp.getCompetitionShortName(),  userBean.getUsername(), "Pattern");
				Boolean calculateCompetitionPattern = findSeasonPatter != null; 
				
				if (calculateCompetitionPattern) {
					//saveOnlineSeasonAndTeams
					SeasonBean findSeason = leagueDao.findSeason(leagueShortName, comp.getCompetitionShortName(),  userBean.getUsername(), null);
					//Boolean saveOnlineSeasonAndTeams = findSeason != null;
					initialOnlineInfoDownloaded= true;
				}
				
			}
			//Boolean initialOnlineInfoDownloaded = binding && calculateCompetitionPattern && saveOnlineSeasonAndTeams;
			
			comp.setInitialOnlineInfoDownloaded(initialOnlineInfoDownloaded);
			
		}
		return competitions;
	}
	
	
	
	
	
	public UserBean login(Credentials credentials) {
		UserBean dbUserBean  = userDao.login(credentials);
		userBean.setEmail(dbUserBean.getEmail());
		userBean.setUsername(dbUserBean.getUsername());
		userBean.setFirstname(dbUserBean.getFirstname());
		
		
		return dbUserBean;
	}

	public Boolean logout() {
		userBean.setUsername(null);
		userBean.setFirstname(null);
		userBean.setLastname(null);
		userBean.setEmail(null);
		userBean.setPassword(null);
		return true;
	}

	@Transactional
	public LeagueBean resetLeague(String leagueShortName) {
		List<CompetitionBean> competitions = leagueDao.findCompetitionsByLeague(leagueShortName, userBean.getUsername());
		for (CompetitionBean comp : competitions) {
			leagueDao.deleteCompetition(comp, userBean.getUsername());
//			rulesDao.deleteByCompetition(comp, userBean.getUsername());
		}
		
		return null;
	}

	
	
}
