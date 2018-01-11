package app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.logic._0_credentialsSaver.UserExpert;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_rulesDownloader.RulesExpertMain;
import app.logic._0_votesDownloader.MainSeasonVotesDowloader;
import app.logic._1_realChampionshipAnalyzer.SeasonAnalyzer;
import app.logic._2_seasonPatternExtractor.SeasonPatternExtractor;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class FacadeController {


	@Autowired
	private MainSeasonVotesDowloader mainSeasonVotesDowloader;

	@Autowired
	private RulesExpertMain rulesExpertMain;
	
	@Autowired
	private UserExpert userExpert;
	
	@Autowired
	private SeasonAnalyzer seasonAnalyzer;
	
	@Autowired
	private SeasonPatternExtractor seasonPatternExtractor;
	
	
	// ###################################################
	// ##########            1                ############
	// ###################################################
	
	@RequestMapping(value = "/downloadVotes", method = RequestMethod.GET)
	public ResponseEntity<String> downloadVotes() {
		
		mainSeasonVotesDowloader.execute();
//		User p = personDao.findById(1L);
		String body = "Downloading Votes COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	

	//###################################################################
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserBean user) {
		
		user = userExpert.createUser(user);
		
		String body;
		if (user != null)
			body = "Saving new User COMPLETED";
		else
			body = "Saving new User FAILED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	//###################################################################
	
	@RequestMapping(value = "/confirmUser", params = { "username", "rnd" },  method = RequestMethod.GET)
	public ResponseEntity<String> confirmUser(@RequestParam String username, @RequestParam String rnd) {
		
		ConfirmUser confirm = new ConfirmUser();
		confirm.setUsername(username);
		confirm.setRnd(rnd);
		
		Boolean confirmed = userExpert.confirmUser(confirm);
		String body;
		if (confirmed)
			body = "Saving new User COMPLETED";
		else
			body = "Saving new User ERROR";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}

	
	//###################################################################
	
	@RequestMapping(value = "/saveFantaGazzettaCredentials", method = RequestMethod.POST)
	public ResponseEntity<String> saveFantaGazzettaCredentials(@RequestBody Credentials credentials) {
		
		userExpert.saveGazzettaCredentials(credentials);
		String body = "Saving Gazzeta Credentials COMPLETED";
		
		
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	//###################################################################
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody Credentials credentials) {
		
		Boolean confirmed = userExpert.login(credentials);
		String body;
		if (confirmed)
			body = "Login COMPLETED";
		else
			body = "Login ERROR";
		
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################
	
	@RequestMapping(value = "/downloadLeagues", method = RequestMethod.GET)
	public ResponseEntity<String> downloadLeagues() {
		
		List<LeagueBean> leaguesInserted = userExpert.downloadLeagues();

		String body;
		if (leaguesInserted.isEmpty())
			body = "Downloading Leagues FAILED";
		else
			body = "Downloading Leagues COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	//###################################################################
	
	@RequestMapping(value = "/downloadCompetitions/{leagueName}", method = RequestMethod.GET)
	public ResponseEntity<String> downloadCompetitions(@PathVariable String leagueName) {
		List<CompetitionBean> competitionsInserted= userExpert.downloadCompetitions(leagueName);
		
		String body;
		if (competitionsInserted.isEmpty())
			body = "Downloading Competitions FAILED";
		else
			body = "Downloading Competitions COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	//###################################################################
	
	@RequestMapping(value = "/downloadRules/{leagueShortName}", method = RequestMethod.GET)
	public ResponseEntity<String> downloadRules(@PathVariable String leagueShortName) {
		
		rulesExpertMain.saveRulesForLeague(leagueShortName);
		String body = "Downloading Rules COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################

	@RequestMapping(value = "/extractPattern", method = RequestMethod.POST)
	public ResponseEntity<String> extractPattern(@RequestBody CompetitionBean competition) {
		String competitionShortName = competition.getShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonPatternExtractor.calculateSerieAToCompetitionSeasonDaysBinding(leagueShortName, competitionShortName);

		String body = "Extract pattern COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
		
	//###################################################################
	
		@RequestMapping(value = "/analyzeCompetition", method = RequestMethod.POST)
		public ResponseEntity<String> analyzeChampionship(@RequestBody CompetitionBean competition) {
			
			String competitionShortName = competition.getShortName();
			String leagueShortName = competition.getLeagueShortName();
			
			seasonAnalyzer.calculateSeasonResult(competitionShortName, leagueShortName);
			String body = "Analyze Championship COMPLETED";
			
			ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
			return response;
		}
}