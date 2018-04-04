package app.logic._0_credentialsSaver;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.CompetitionBean;
import app.dao.LeagueDao;
import app.dao.UserDao;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_credentialsSaver.model.UserBean;
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
		
		Elements elements = doc.getElementsByClass("llist").get(0).getElementsByTag("a");
		
		
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

	public List<CompetitionBean> downloadCompetitions(String leagueShortName){
		if (userBean.getUsername() == null)
			return null;
		
		Credentials c = userDao.retrieveGazzettaCredentials(userBean.getUsername());
		
		LeagueBean league = leagueDao.findLeagueByShortName(leagueShortName, userBean.getUsername());
		
		String leagueUrl = AppConstants.GAZZETTA_URL + league.getShortName() + "/gestione-competizioni";
		Document doc = HttpUtils.getHtmlPageLogged(leagueUrl , c.getUsername(), c.getPassword());
		
		Element elementsOfGestioneCompetizionePage = doc.getElementById("tbcompattive");
		
		List<CompetitionBean> competitions = new ArrayList<CompetitionBean>();
		
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

				competitionShortName = competitionRulesUrl.substring(competitionRulesUrl.lastIndexOf("/") + 1, competitionRulesUrl.length());	//247720
				
				competitionName = elem.text();		//CAMPIONATO SERIE A-CCANITA
				
				CompetitionBean com = new CompetitionBean(competitionName, competitionShortName, competitionRulesUrl, null, type);
	
				competitions.add(com);
			
			}
		}
		
		else if ( elementsOfGestioneCompetizionePage!= null) {	//AMMINISTRATORE
		
			elements = elementsOfGestioneCompetizionePage.getElementsByTag("tbody").get(0).getElementsByTag("tr");
			
			for (Element elem : elements) {
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
		
		leagueDao.saveCompetitions(competitions, league.getShortName(), userBean.getUsername());
		
		return competitions;
	}
	
	
	
	
	
	public Boolean login(Credentials credentials) {
		UserBean dbUserBean = userDao.login(credentials);
		userBean.setEmail(dbUserBean.getEmail());
		userBean.setUsername(dbUserBean.getUsername());
		userBean.setFirstname(dbUserBean.getFirstname());
		
		if (dbUserBean == null)
			return false;
		return true;
	}

	public Boolean logout() {
		userBean.setUsername(null);
		userBean.setFirstname(null);
		userBean.setLastname(null);
		userBean.setEmail(null);
		userBean.setPassword(null);
		return true;
	}

	
	
}
