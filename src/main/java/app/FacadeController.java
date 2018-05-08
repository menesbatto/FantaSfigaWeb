package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import app.logic.Main;
import app.logic._0_credentialsSaver.UserExpert;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_permutationsGenerator.PermutationsGenerator;
import app.logic._0_rulesDownloader.RulesExpertMain;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._0_votesDownloader.MainSeasonVotesDowloader;
import app.logic._1_seasonPatternExtractor.SeasonPatternExtractor;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._2_realChampionshipAnalyzer.SeasonAnalyzer;
import app.logic._2_realChampionshipAnalyzer.model.PostponementBehaviourEnum;
import app.logic._3_seasonsGenerator.AllSeasonsGenerator;
import app.logic._5_rankingAnalizer.RankingAnalyzer;
import app.logic.model.CalculateStatsReq;
import app.logic.model.CompetitionBean;
import app.logic.model.CustomRulesReq;
import app.logic.model.PostponementBean;
import app.logic.model.RetrieveAllRankingsReq;
import app.logic.model.RetrieveRules;
import app.logic.model.RetrieveSeasonReq;
import app.logic.model.SeasonAndRankingRes;
import app.logic.model.StasResponse;
import app.logic.model.IntegrateRulesReq;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class FacadeController {

	private static final Gson gson = new Gson();
	
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
	
	@Autowired
	private RankingAnalyzer rankingAnalyzer;
	
	
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
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}
	

	//###################################################################
	
	// Rimuove i voti di tutte le giornate da recuperare e li riscarica
	// Utile per le league in cui si attendono i recuperi.
	// Da richiamare quando viene giocato un recupero e la giornata si conclude
	 
	@RequestMapping(value = "/cleanVotes", method = RequestMethod.GET)
	public ResponseEntity<String> cleanVotes() {
		
		mainSeasonVotesDowloader.cleanVotes();
//		User p = personDao.findById(1L);
		String body = "Clean Votes COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}
	

	//###################################################################
	
	// Inserisce un rinvio
	@RequestMapping(value = "/insertPostponement", method = RequestMethod.POST)
	public ResponseEntity<String> insertPostponement(@RequestBody PostponementBean m) {
		
		mainSeasonVotesDowloader.insertPostponement(m);
//		User p = personDao.findById(1L);
		String body = "Insert Postponemen COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################
	
	// Inserisce un rinvio
	@RequestMapping(value = "/removePostponement", method = RequestMethod.POST)
	public ResponseEntity<String> removePostponement(@RequestBody PostponementBean m) {
		
		mainSeasonVotesDowloader.removePostponement(m);
//			User p = personDao.findById(1L);d
		String body = "Remove Postponemen COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}
	//###################################################################
	
	// Inserisce un rinvio
	@RequestMapping(value = "/retrievePostponements", method = RequestMethod.GET)
	public ResponseEntity<List<PostponementBean>> retrievePostponements() {
		
		List<PostponementBean> postponements = mainSeasonVotesDowloader.retrievePostponements();
//			User p = personDao.findById(1L);
		String body = "Retrieve Postponemen COMPLETED";
		
		ResponseEntity<List<PostponementBean>> response = new ResponseEntity<List<PostponementBean>>(postponements, HttpStatus.OK);
		return response;
	}
	

	//###################################################################
	
	// Crea un nuovo user dell'applicativo da validare
	// Viene richiamato solo una volta all'inizio

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<UserBean> createUser(@RequestBody UserBean user) {
		
		user = userExpert.createUser(user);
		
		String body;
		ResponseEntity<UserBean> response;

		if (user != null) {
			body = "{message : \"Saving new User COMPLETED\"}";
			response = new ResponseEntity<UserBean>(user, HttpStatus.OK);
		}
		else {
			body = "{message : \"User already exist\"}";
			response = new ResponseEntity<UserBean>(user, HttpStatus.CONFLICT);
		}
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

	
	@RequestMapping(value = "/saveFantaGazzettaCredentials", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> saveFantaGazzettaCredentials(@RequestBody Credentials credentials) {
		
		userExpert.saveGazzettaCredentials(credentials);
		String body = "Saving Gazzetta Credentials COMPLETED";
		
		
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}
	
	//###################################################################
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserBean> login(@RequestBody Credentials credentials) {
		
		UserBean userBean = userExpert.login(credentials);
		String body;
		if (userBean != null)
			body = "Login COMPLETED";
		else
			body = "Login ERROR";
		
		ResponseEntity<UserBean> response = new ResponseEntity<UserBean>(userBean, HttpStatus.OK);
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
	
	// RICHIAMATO DA USER 1 volta all'inizio
	
	
	@RequestMapping(value = "/downloadLeagues", method = RequestMethod.GET)
	public ResponseEntity<List<LeagueBean>> downloadLeagues() {
		
		List<LeagueBean> leaguesInserted = userExpert.downloadLeagues();

		String body;
		if (leaguesInserted.isEmpty())
			body = "Downloading Leagues FAILED";
		else
			body = "Downloading Leagues COMPLETED";
		
		ResponseEntity<List<LeagueBean>> response = new ResponseEntity<List<LeagueBean>>(leaguesInserted, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################
		// Recupera le league del cliente messo in sessione
		// RICHIAMATO DA USER ogni volta che accede alla app
	
		

		@RequestMapping(value = "/retrieveLeagues", method = RequestMethod.GET)
		public ResponseEntity<List<LeagueBean>> retrieveLeagues() {
			
			List<LeagueBean> leaguesInserted = userExpert.retrieveLeagues();

			String body;
			ResponseEntity<List<LeagueBean>> response;
			if (leaguesInserted == null) {
				response = new ResponseEntity<List<LeagueBean>>(leaguesInserted, HttpStatus.UNAUTHORIZED);
				body = "Retrieve Leagues FAILED";
			}
			else {
				response = new ResponseEntity<List<LeagueBean>>(leaguesInserted, HttpStatus.OK);
				body = "Retrieve Leagues COMPLETED";
			}
			
			return response;
		}
		
		
		
	//###################################################################
	
	// Per la lega inviata vengono scaricate le competizioni contenute
	// RICHIAMATO DA USER 1 volta all'inizio

	@RequestMapping(value = "/downloadCompetitions/{leagueShortName}", method = RequestMethod.GET)
	public ResponseEntity<List<CompetitionBean>> downloadCompetitions(@PathVariable String leagueShortName) {
		List<CompetitionBean> competitionsInserted= userExpert.downloadCompetitions(leagueShortName);
		
		String body;
		if (competitionsInserted.isEmpty())
			body = "Downloading Competitions FAILED";
		else
			body = "Downloading Competitions COMPLETED";
		
		ResponseEntity<List<CompetitionBean>> response = new ResponseEntity<List<CompetitionBean>>(competitionsInserted, HttpStatus.OK);
		return response;
	}
	
	//###################################################################
	
	// Per la lega inviata vengono scaricate le competizioni contenute
	// RICHIAMATO DA USER ogni volta che accede ad una lega

	@RequestMapping(value = "/retrieveCompetitions/{leagueShortName}", method = RequestMethod.GET)
	public ResponseEntity<List<CompetitionBean>> retrieveCompetitions(@PathVariable String leagueShortName) {
		List<CompetitionBean> competitionsInserted= userExpert.retrieveCompetitions(leagueShortName);
		
		String body;
		ResponseEntity<List<CompetitionBean>> response;
		if (competitionsInserted == null) {
			response = new ResponseEntity<List<CompetitionBean>>(competitionsInserted, HttpStatus.UNAUTHORIZED);
			body = "Retrieve Competitions FAILED";
		}
		else {
			response = new ResponseEntity<List<CompetitionBean>>(competitionsInserted, HttpStatus.OK);
			body = "Retrieve Competitions COMPLETED";
		}
		
		return response;
	}
	
	//###################################################################

	// Scarica tutte le regole scaricabili per tutte le competizioni di una data league
	// RICHIAMATO DA USER 1 volta all'inizio
	
	@RequestMapping(value = "/downloadRules/{leagueShortName}", method = RequestMethod.GET)
	public ResponseEntity<RulesBean> downloadRules(@PathVariable String leagueShortName) {
		
		RulesBean rules = rulesExpertMain.saveRulesForLeague(leagueShortName);

		ResponseEntity<RulesBean> response;
		String body;
		if (rules == null) {
			response = new ResponseEntity<RulesBean>(rules, HttpStatus.UNAUTHORIZED);
			body = "Download Rules FAILED";
		}
		else {
			response = new ResponseEntity<RulesBean>(rules, HttpStatus.OK);
			body = "Download Rules COMPLETED";
		}
		
		return response;
	}
	
	//###################################################################

	// Recupera a DB le regole della competizione, usato spesso per le customRules
	
	@RequestMapping(value = "/retrieveRules", method = RequestMethod.POST)
	public ResponseEntity<RulesBean> retrieveRules(@RequestBody RetrieveRules req) {
		
		RulesType type = RulesType.valueOf(req.getType());
		RulesBean rules = rulesExpertMain.retrieveRules(req.getCompetition(), type);

		ResponseEntity<RulesBean> response;
		String body;
		if (rules == null) {
			response = new ResponseEntity<RulesBean>(rules, HttpStatus.UNAUTHORIZED);
			body = "Retrieve Rules FAILED";
		}
		else {
			response = new ResponseEntity<RulesBean>(rules, HttpStatus.OK);
			body = "Retrieve Rules COMPLETED";
		}
		
		return response;
	}
	
	//###################################################################
	
	// Per la specifica competizione integra le regole non scaricabili.
	// RICHIAMATO DA USER 1 volta all'inizio

	@RequestMapping(value = "/integrateRules", method = RequestMethod.POST)
	public ResponseEntity<IntegrateRulesReq> integrateRules(@RequestBody IntegrateRulesReq req) {
		
		IntegrateRulesReq rules = rulesExpertMain.integrateRules(req);
		
		ResponseEntity<IntegrateRulesReq> response;
		String body;
		if (rules == null) {
			response = new ResponseEntity<IntegrateRulesReq>(rules, HttpStatus.UNAUTHORIZED);
			body = "Integrate Rules FAILED";
		}
		else {
			response = new ResponseEntity<IntegrateRulesReq>(rules, HttpStatus.OK);
			body = "Integrate Rules COMPLETED";
		}
		
		return response;
	}
		
	
	//###################################################################

	// Salva a DB le regole della competizione, usato spesso per le customRules
	
	@RequestMapping(value = "/saveRules", method = RequestMethod.POST)
	public ResponseEntity<RulesBean> saveCustomRules(@RequestBody IntegrateRulesReq req) {
		
		
		RulesBean rules = rulesExpertMain.saveRules(req.getRules(), req.getLeagueShortName(), req.getCompetitionShortName());

		ResponseEntity<RulesBean> response;
		String body;
		if (rules == null) {
			response = new ResponseEntity<RulesBean>(rules, HttpStatus.UNAUTHORIZED);
			body = "Save Rules FAILED";
		}
		else {
			response = new ResponseEntity<RulesBean>(rules, HttpStatus.OK);
			body = "Save Rules COMPLETED";
		}
		
		return response;
	}
	
	// Calcola il legame tra le giornate della Serie A e le giornate della specifica competizione
	// RICHIAMATO DA USER 1 volta all'inizio
	
	@RequestMapping(value = "/calculateBinding", method = RequestMethod.POST)
	public ResponseEntity<String> calculateBinding(@RequestBody CompetitionBean competition) {
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonPatternExtractor.calculateSerieAToCompetitionSeasonDaysBinding(leagueShortName, competitionShortName);

		
		String body = "Calculate Binding COMPLETED";
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		
		return response;
	}
	
	
	//###################################################################

	// Calcola il pattern della competizione, ovvero il calendario, tutti gli scontri giornata per giornata
	// Utile per generare poi i 40k calendari
	// RICHIAMATO DA USER 1 volta all'inizio
	
	@RequestMapping(value = "/calculateCompetitionPattern", method = RequestMethod.POST)
	public ResponseEntity<String> calculateCompetitionPattern(@RequestBody CompetitionBean competition) {
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonPatternExtractor.calculateCompetitionPattern(leagueShortName, competitionShortName);

		String body = "Calculate Competition Pattern COMPLETED";
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		
		return response;
	}
	
	
	
	//###################################################################

	// Scarica il calendario della competizione passata in input
	// Salva anche i team di una certa competizione

	// RICHIAMATO DA USER 1 volta all'inizio
	
	@RequestMapping(value = "/saveOnlineSeasonAndTeams", method = RequestMethod.POST)
	public ResponseEntity<String> saveOnlineSeasonAndTeams(@RequestBody CompetitionBean competition) {
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonPatternExtractor.saveOnlineSeasonAndTeams(leagueShortName, competitionShortName);

		String body = "Save Online Season And Teams COMPLETED";
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);

		return response;
	}
	
	
		
	//###################################################################
	
	// Calcola le permutazioni del numero di team inseriti
	
	// RICHIAMATO dall'admin soltanto 3 volte all'inizio e mai piu'
	
	@RequestMapping(value = "/createPermutations/{playersNumber}", method = RequestMethod.GET)
	public ResponseEntity<String> createPermutations(@PathVariable Integer playersNumber) {
		
		permutationsGenerator.createPermutations(playersNumber);

		String body = "Creating Permutation COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################

	// Rimuove dalla season salvata scaricandola dal web le giornate che concidono con i recuperi.
	
	// utile solo per gli user delle competizioni in cui si attende
	
	// Richiamato DALL USER solo quando viene recupeata per intero una giornata. UNA VOLTA PER OGNI COMPETIZIONE, POICHE RIPULISCE SOLO QUELLA
	
	
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
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}

	//###################################################################
	
	// Sulla base delle formazioni schierate calcola i risultati con la base dati che ha scaricato in precendenza
	
	// Richiamato una volta a settimana, come preludio per il calculateRealStats
	
	@RequestMapping(value = "/calculateSeasonResult", method = RequestMethod.POST)
	public ResponseEntity<String> calculateSeasonResult(@RequestBody CompetitionBean competition) {
		
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		
		seasonAnalyzer.calculateSeasonResult(competitionShortName, leagueShortName);
		String body = "Calculate Season Result From Web COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}
	
		
	//###################################################################
	
	// NON RICHIAMATO MAI POICHE ACCORPATO NEL MAIN	
	
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
	
	// Richiamato ogni volta che si vogliono calcolare le statistiche di una competizione (1 volta a settimana)
	
	@RequestMapping(value = "/retrieveAllRankings", method = RequestMethod.POST)
	public ResponseEntity<StasResponse> retrieveAllRankings(@RequestBody RetrieveAllRankingsReq req) {
		CompetitionBean competition = req.getCompetition();
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		RulesType rulesType = req.getRulesType();
		
		StasResponse stats = rankingAnalyzer.retrieveAllRankings(leagueShortName, competitionShortName, rulesType);
		String body = "Create Stats COMPLETED";
		
		ResponseEntity<StasResponse> response = new ResponseEntity<StasResponse>(stats, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################
	
	// Calcola le statistiche di una certa competizione
	
	// Richiamato ogni volta che si vogliono calcolare le statistiche di una competizione (1 volta a settimana)
	
	@Autowired
	private Main main;
	@RequestMapping(value = "/calculateRealStats", method = RequestMethod.POST)
	public ResponseEntity<String> calculateRealStats(@RequestBody CalculateStatsReq req) {
		CompetitionBean competition = req.getCompetition();
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		Boolean light = req.getLight();
		RulesType rulesType = req.getRulesType();
		
		main.calculateRealStats(leagueShortName, competitionShortName, light, rulesType);
		String body = "Create Stats COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(gson.toJson(body), HttpStatus.OK);
		return response;
	}
		
		
	//###################################################################
	
	// Recupera la stagione dato il pattern
	@RequestMapping(value = "/retrieveSeason", method = RequestMethod.POST)
	public ResponseEntity<SeasonAndRankingRes> calculateRealStats(@RequestBody RetrieveSeasonReq req) {
		CompetitionBean competition = req.getCompetition();
		String competitionShortName = competition.getCompetitionShortName();
		String leagueShortName = competition.getLeagueShortName();
		String pattern = req.getPattern();
		RulesType rulesType = req.getRulesType();
		
		SeasonAndRankingRes res = main.retrieveSeasonFromPattern(pattern, leagueShortName, competitionShortName, rulesType);
		
		
		String body = "Retrieve Season COMPLETED";
		
		ResponseEntity<SeasonAndRankingRes> response = new ResponseEntity<SeasonAndRankingRes>(res, HttpStatus.OK);
		return response;
	}
		
		
		
}