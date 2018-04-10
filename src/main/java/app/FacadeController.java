package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.logic.Main;
import app.logic._0_credentialsSaver.UserExpert;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_permutationsGenerator.PermutationsGenerator;
import app.logic._0_rulesDownloader.RulesExpertMain;
import app.logic._0_votesDownloader.MainSeasonVotesDowloader;
import app.logic._1_seasonPatternExtractor.SeasonPatternExtractor;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._2_realChampionshipAnalyzer.SeasonAnalyzer;
import app.logic._2_realChampionshipAnalyzer.model.PostponementBehaviourEnum;
import app.logic._3_seasonsGenerator.AllSeasonsGenerator;
import app.logic.model.CompetitionBean;
import app.logic.model.CustomRules;
import app.logic.model.PostponementBean;
import app.logic.model.RulesReq;

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
	private SeasonPatternExtractor seasonPatternExtractor;
	
	@Autowired
	private PermutationsGenerator permutationsGenerator;
	
	@Autowired
	private SeasonAnalyzer seasonAnalyzer;
	
	@Autowired
	private AllSeasonsGenerator allSeasonsGenerator;
	
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> sayHello() {
		
		ResponseEntity<String> response = new ResponseEntity<String>("{\"message\": \"Hello, World dal server pere in diretta o no!\"}", HttpStatus.OK);
		return response;
    }
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserBean>> getUsers() {
		
		List<UserBean> userList = new ArrayList<UserBean>();
		UserBean u1 = new UserBean();
		u1.setEmail("prova1@prova.it");
		u1.setFirstname("Valerio");
		u1.setLastname("Mene");
		userList.add(u1);
		
		UserBean u2 = new UserBean();
		u2.setEmail("prova2@prova.it");
		u2.setFirstname("Gigi");
		u2.setLastname("Rossi");
		userList.add(u2);
		
		
		
		ResponseEntity<List<UserBean>> response = new ResponseEntity<List<UserBean>>(userList, HttpStatus.OK);
		//("{\"message\": \"Hello, World dal server pere in diretta o no!\"}", HttpStatus.OK);
		return response;
    }

	
	
	// ###################################################
	// ##########            1                ############
	// ###################################################
	
	
	// Scarica tutti i voti di tutte le giornate dall'inizio fino alla giornata odierna.
	// Se sono gia' stati scaricati salta la giornata.
	
	@RequestMapping(value = "/downloadVotes", method = RequestMethod.GET)
	public ResponseEntity<String> downloadVotes() {
		
		mainSeasonVotesDowloader.execute();
//		User p = personDao.findById(1L);
		String body = "Downloading Votes COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	

	//###################################################################
	
	// Rimuove i voti di tutte le giornate da recuperare.
	// Utile per le league in cui si attendono i recuperi.
	// Da richiamare quando viene giocato un recupero e la giornata si conclude, cosi' facendo la prossima volta che vengono scaricati i voti
	// verranno scaricati anche quelli della partita/partite recuperate
	 
	@RequestMapping(value = "/cleanVotes", method = RequestMethod.GET)
	public ResponseEntity<String> cleanVotes() {
		
		mainSeasonVotesDowloader.cleanVotes();
//		User p = personDao.findById(1L);
		String body = "Clean Votes COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	

	//###################################################################
	
	// Inserisce un rinvio
	@RequestMapping(value = "/insertPostponement", method = RequestMethod.POST)
	public ResponseEntity<String> insertPostponement(@RequestBody PostponementBean m) {
		
		mainSeasonVotesDowloader.insertPostponement(m);
//		User p = personDao.findById(1L);
		String body = "Insert Postponemen COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	

	//###################################################################
	
	// Crea un nuovo user dell'applicativo da validare
	// Viene richiamato solo una volta all'inizio

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
	// Conferma uno user precendetemente creato
	// Viene richiamato solo una volta all'inizio

	
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
	// Salva le credenziali di FantaGazzetta per il cliente in sessione
	// Viene richiamato solo una volta all'inizio

	
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
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logout() {
		
		Boolean confirmed = userExpert.logout();
		String body;
		if (confirmed)
			body = "logout COMPLETED";
		else
			body = "logout ERROR";
		
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################
	// Scrive le league dell'utente in sessione
	// Viene richiamato solo una volta all'inizio
	
	
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
	
	// Per la lega inviata vengono scaricate le competizioni contenute
	// Viene richiamato solo una volta all'inizio

	@RequestMapping(value = "/downloadCompetitions/{leagueShortName}", method = RequestMethod.GET)
	public ResponseEntity<String> downloadCompetitions(@PathVariable String leagueShortName) {
		List<CompetitionBean> competitionsInserted= userExpert.downloadCompetitions(leagueShortName);
		
		String body;
		if (competitionsInserted.isEmpty())
			body = "Downloading Competitions FAILED";
		else
			body = "Downloading Competitions COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	//###################################################################

	// Scarica tutte le regole scaricabili per tutte le competizioni di una data league
	// Viene richiamato solo una volta all'inizio
	
	@RequestMapping(value = "/downloadRules/{leagueShortName}", method = RequestMethod.GET)
	public ResponseEntity<String> downloadRules(@PathVariable String leagueShortName) {
		
		rulesExpertMain.saveRulesForLeague(leagueShortName);
		String body = "Downloading Rules COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	//###################################################################
	
	// Per la specifica competizione integra le regole non scaricabili.
	// Viene richiamato solo una volta all'inizio

	@RequestMapping(value = "/integrateRules", method = RequestMethod.POST)
	public ResponseEntity<String> integrateRules(@RequestBody RulesReq req) {
		
		rulesExpertMain.integrateRules(req);
		String body = "Integrate Rules COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
		
	//###################################################################

	// Calcola il legame tra le giornate della Serie A e le giornate della specifica competizione
	// Viene richiamato solo una volta all'inizio
	
	@RequestMapping(value = "/calculateBinding", method = RequestMethod.POST)
	public ResponseEntity<String> calculateBinding(@RequestBody CompetitionBean competition) {
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonPatternExtractor.calculateSerieAToCompetitionSeasonDaysBinding(leagueShortName, competitionShortName);

		String body = "Calculate binding COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################

	// Calcola il pattern della competizione, ovvero il calendario, tutti gli scontro giornata per giornata
	// Utile per generare poi i 40k calendari
	// Viene richiamato solo una volta all'inizio
	
	@RequestMapping(value = "/calculateCompetitionPattern", method = RequestMethod.POST)
	public ResponseEntity<String> calculateCompetitionPattern(@RequestBody CompetitionBean competition) {
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonPatternExtractor.calculateCompetitionPattern(leagueShortName, competitionShortName);

		String body = "Calculate Competition Pattern COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	
	//###################################################################

	// Scarica il calendario della competizione passata in input
	// Salva anche i team di una certa competizione

	// Viene richiamato solo all'inizio
	
	@RequestMapping(value = "/saveOnlineSeasonAndTeams", method = RequestMethod.POST)
	public ResponseEntity<String> saveOnlineSeasonAndTeams(@RequestBody CompetitionBean competition) {
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonPatternExtractor.saveOnlineSeasonAndTeams(leagueShortName, competitionShortName);

		String body = "Save Online Season And Teams COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
		
	//###################################################################
	
	// Calcola le permutazioni del numero di team inseriti
	// Viene richiamato solo una volta
	
	@RequestMapping(value = "/createPermutations/{playersNumber}", method = RequestMethod.GET)
	public ResponseEntity<String> createPermutations(@PathVariable Integer playersNumber) {
		
		permutationsGenerator.createPermutations(playersNumber);

		String body = "Creating Permutation COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################

	// Rimuove dalla season salvata scaricandola dal web le giornate che concidono con i recuperi (solo per le competizioni in cui si attende)
	
	
	@RequestMapping(value = "/cleanSeasonFromWeb", method = RequestMethod.POST)
	public ResponseEntity<String> cleanSeasonFromWeb(@RequestBody CompetitionBean competition) {
		
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonAnalyzer.cleanSeasonFromWeb(competitionShortName, leagueShortName);
		String body = "Clean Season From Web COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################
	
	// Scarica la competizione online e i risultati appena calcolati da FantaGazzetta
	// Sono utili per fare confronti con quelli calcolati dalla applicazione FantaSfiga
	// Scarica anche le formazioni scherate
	// Richiamata ad ogni giornata
	
	@RequestMapping(value = "/downloadSeasonFromWeb", method = RequestMethod.POST)
	public ResponseEntity<String> downloadSeasonFromWeb(@RequestBody CompetitionBean competition) {
		
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonAnalyzer.downloadSeasonFromWeb(competitionShortName, leagueShortName);
		String body = "Download Season From Web COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}

	//###################################################################
	
	// Sulla base delle formazioni schierate calcola i risultati con la base dati che ha scaricato in precendenza
	@RequestMapping(value = "/calculateSeasonResult", method = RequestMethod.POST)
	public ResponseEntity<String> calculateSeasonResult(@RequestBody CompetitionBean competition) {
		
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonAnalyzer.calculateSeasonResult(competitionShortName, leagueShortName);
		String body = "Calculate Season Result From Web COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
		
	//###################################################################
	
	// NON CHIAMATO DAL CLIENT
	
	@RequestMapping(value = "/generateAllSeason", method = RequestMethod.POST)
	public ResponseEntity<String> generateAllSeason(@RequestBody CompetitionBean competition) {
		
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		allSeasonsGenerator.generateAllSeasons(leagueShortName, competitionShortName);
		String body = "Generate All Seasons COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
		
		
	//###################################################################
	
	// Calcola le statistiche di una certa competizione
	
	@Autowired
	private Main main;
	@RequestMapping(value = "/calculateRealStats", method = RequestMethod.POST)
	public ResponseEntity<String> createStats(@RequestBody CompetitionBean competition) {
		
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		main.calculateRealStats(leagueShortName, competitionShortName);
		String body = "Create Stats COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
		
		
	//###################################################################
	
	// Calcola le statistiche di una certa competizione con regole personalizzate
	
	@RequestMapping(value = "/calculateStatsWithCustomRules", method = RequestMethod.POST)
	public ResponseEntity<String> calculateRankingWithCustomRules(@RequestBody CustomRules req) {
		
		String competitionShortName = req.getCompetition().getCompetitionShortName();
		String leagueShortName = req.getCompetition().getLeagueShortName();
		
		main.calculateStatsWithCustomRules(leagueShortName, competitionShortName, req.getRules());
		String body = "Calculate Ranking With Custom Rules COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
		
		
		
}