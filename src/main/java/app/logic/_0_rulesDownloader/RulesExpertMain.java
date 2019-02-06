package app.logic._0_rulesDownloader;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.RulesType;
import app.dao.LeagueDao;
import app.dao.RulesDao;
import app.dao.UserDao;
import app.dao.UtilsDao;
import app.dao.entity.Competition;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.LeagueBean;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_rulesDownloader.model.BonusMalus;
import app.logic._0_rulesDownloader.model.CompetitionRules;
import app.logic._0_rulesDownloader.model.DataSources;
import app.logic._0_rulesDownloader.model.DefenderModeEnum;
import app.logic._0_rulesDownloader.model.MaxOfficeVotesEnum;
import app.logic._0_rulesDownloader.model.Modifiers;
import app.logic._0_rulesDownloader.model.Points;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._0_rulesDownloader.model.Substitutions;
import app.logic._0_rulesDownloader.model.SubstitutionsModeEnum;
import app.logic._0_votesDownloader.model.RoleEnum;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic.model.CompetitionBean;
import app.logic.model.IntegrateRulesReq;
import app.logic.model.PostponementBean;
import app.utils.AppConstants;
import app.utils.HttpUtils;
import app.utils.IOUtils;

@Service
public class RulesExpertMain {
	
	@Autowired
	private UserBean userBean; 
	
	@Autowired
	private UserDao userDao; 

	@Autowired
	private RulesDao rulesDao; 
	
	@Autowired
	private LeagueDao leagueDao; 
	
	@Autowired
	private UtilsDao utilsDao; 
	
	
	public RulesBean retrieveRules(CompetitionBean competition, RulesType type) {
		
		if (userBean.getUsername() == null)
			return null;
		
		RulesBean retrieveRules = rulesDao.retrieveRules(competition.getCompetitionShortName(), competition.getLeagueShortName(), type, userBean.getUsername());
		
		return retrieveRules;
	}
	
	
	
	public RulesBean saveRulesForLeague(String leagueShortName) {
		if (userBean.getUsername() == null)
			return null;
		
		List<CompetitionBean> competitions = leagueDao.findCompetitionsByLeague(leagueShortName, userBean.getUsername());
		if (competitions.size() == 0) {
			return new RulesBean();
		}

		
		Boolean alreadyDownloadRules = rulesDao.existRulesForLeague(leagueShortName, userBean.getUsername());
		if (alreadyDownloadRules)
			return new RulesBean();;
			
		BonusMalus bonusMalus = analyzeRulesPageBonusMalus(leagueShortName);
		
		DataSources dataSources = analyzeRulesPageDataSources(leagueShortName);
		
		Substitutions substitutions = analyzeRulesPageSubstitutions(leagueShortName);

		Points points = analyzeRulesPagePoints(leagueShortName);

		Modifiers modifiers = analyzeRulesPageModifiers(leagueShortName);
		
		RulesBean rules = new RulesBean();
		rules.setBonusMalus(bonusMalus);
		rules.setDataSource(dataSources);
		rules.setSubstitutions(substitutions);
		rules.setPoints(points);
		rules.setModifiers(modifiers);
		
		calculateCompetitionsRules(leagueShortName, rules, competitions);
		HttpUtils.closeDrivers(userBean.getUsername());
		
		return rules;

	}

	private void calculateCompetitionsRules(String leagueShortName, RulesBean rules, List<CompetitionBean> competitions) {
		Map<Integer, List<PostponementBean>> postponementMap = utilsDao.findGeneralPostponementMap();
		for ( Entry<Integer, List<PostponementBean>> entry : postponementMap.entrySet())
			for (PostponementBean post : entry.getValue()) 
				post.setWait(false);
			
	
		for (CompetitionBean competition : competitions) {
			//http://leghe.fantagazzetta.com/accaniti-division/visualizza-competizioni
			//http://leghe.fantagazzetta.com/accaniti-division/visualizza-competizione-calendario/247720
			//http://leghe.fantagazzetta.com/accaniti-division/visualizza-competizione-formula1/247741
			//http://leghe.fantagazzetta.com/accaniti-division/visualizza-competizione-gironi/288971
			CompetitionRules rulesComp = analyzeRulesForCompetition(competition);
			rulesComp.setPostponementMap(postponementMap);
			
			rules.setCompetitionRules(rulesComp);
			rules.setType(RulesType.REAL);
			rulesDao.saveRulesForCompetition(rules, competition.getCompetitionShortName(), leagueShortName, userBean.getUsername());
			
		}
	}

	private CompetitionRules analyzeRulesForCompetition(CompetitionBean competition) {
		
		String url = competition.getUrl();
		//String url = AppConstants.RULES_6_COMPETITION_URL.replace("[LEAGUE_NAME]", competition.getLeagueShortName()).replace("[COMPETITION_ID]", competition.getCompetitionShortName());
		Document doc = getLoggedPage(url, "");
		
		CompetitionRules cr = new CompetitionRules();
		Boolean homeBonusActive = false;
				
		Elements fattoreCampoCurr = null;
		Elements fattoreCampoElements = doc.getElementsByAttributeValue("data-field-key","fattore_campo");
		for (Element fattoreCampo : fattoreCampoElements) {
			fattoreCampoCurr = fattoreCampo.getElementsByAttributeValue("data-h","false");
			if (fattoreCampoCurr.size()>0) {
				homeBonusActive = true; 
				break;
			}
		}
		
		cr.setHomeBonusActive(homeBonusActive);
		
		if (homeBonusActive) {
			Elements elems = fattoreCampoCurr.get(0).getElementsByClass("value-type-message");
			if (elems.size()>0) {	//questo il è per le coppe a gironi...vanno aggiunte bene le regole per tali coppe
				String homeBonusString = elems.get(0).text();
				homeBonusString = homeBonusString.replace(" punti", "");
				Double homeBonus = Double.valueOf(homeBonusString);
			
				cr.setHomeBonus(homeBonus);
			}
			else 
				cr.setHomeBonusActive(false);

		}
		
		return cr;
	}

	private Modifiers analyzeRulesPageModifiers(String leagueName) {
//		Document doc = getLoggedPage(AppConstants.RULES_5_MODIFIERS_URL, leagueName);
		Document doc = getLoggedPage(AppConstants.RULES_CONFIG_PLUS_URL, leagueName);

		
		Modifiers m = new Modifiers();
		
		
		m = getGoalKeeperModifiers(m, doc);
		
		m = getDefenderModifiers(m, doc);
		
		m = getMiddlefielderModifiers(m, doc);
		
		m = getStrickerModifiers(m, doc);
		
		m = getPerformanceModifiers(m, doc);
		
		m = getFairPlayModifiers(m, doc);
		
		m = getCaptainModifiers(m, doc);
		
		System.out.println(m);
		
		return m;
	}

	private Document getLoggedPage(String url, String leagueName) {
		url = url.replace("[LEAGUE_NAME]", leagueName);//url = "http://leghe.fantagazzetta.com/accaniti-division/gestione-lega/opzioni-rose";
		Credentials c = userDao.retrieveGazzettaCredentials(userBean.getUsername());
		Document doc = HttpUtils.getHtmlPageLogged(url, c.getUsername(), c.getPassword());
		return doc;
	}

	private Modifiers getPerformanceModifiers(Modifiers m, Document doc) {
		String performanceString = doc.getElementsByAttributeValue("data-field-key","modificatori.rendimento_attivo").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isPerformanceActive =  performanceString.equals("sì");
		
		if (!isPerformanceActive)
			return m;
		
		m.setPerformanceModifierActive(isPerformanceActive);
		
		String performanceVotesString = doc.getElementById("modificatori.rendimento").getElementsByClass("range-col-type-input").get(0).getElementsByClass("range-label").text();
		String[] performanceVotesSplit = performanceVotesString.split(" ");
		
		List<Double> performanceVotes = new ArrayList<Double>();
		
		int i = 0;
		for (i = 0; i < performanceVotesSplit.length; i++) {
			performanceVotes.add(new Double(performanceVotesSplit[i]));
		}	
		
		Double tot11 = Double.valueOf(performanceVotes.get(0));
		m.setPerformance11(tot11);
		
		Double tot10 = Double.valueOf(performanceVotes.get(1));
		m.setPerformance10(tot10);
		
		Double tot9 = Double.valueOf(performanceVotes.get(2));
		m.setPerformance9(tot9);
		
		Double tot8 = Double.valueOf(performanceVotes.get(3));
		m.setPerformance8(tot8);
		
		Double tot7 = Double.valueOf(performanceVotes.get(4));
		m.setPerformance7(tot7);
		
		Double tot6 = Double.valueOf(performanceVotes.get(5));
		m.setPerformance6(tot6);
		
		Double tot5 = Double.valueOf(performanceVotes.get(6));
		m.setPerformance5(tot5);
		
		Double tot4 = Double.valueOf(performanceVotes.get(7));
		m.setPerformance4(tot4);
		
		Double tot3 = Double.valueOf(performanceVotes.get(8));
		m.setPerformance3(tot3);
		
		Double tot2 = Double.valueOf(performanceVotes.get(9));
		m.setPerformance2(tot2);
		
		Double tot1 = Double.valueOf(performanceVotes.get(10));
		m.setPerformance1(tot1);
		
//		m.setPerformance0(-5.0);
//		m.setPerformance1(-3.0);
//		m.setPerformance2(-2.0);
//		m.setPerformance3(-1.0);
//		m.setPerformance4(0.0);
//		m.setPerformance5(0.0);
//		m.setPerformance6(0.0);
//		m.setPerformance7(0.0);
//		m.setPerformance8(1.0);
//		m.setPerformance9(2.0);
//		m.setPerformance10(3.0);
//		m.setPerformance11(5.0);
		
		return m;
		
	}
	

	private Modifiers getCaptainModifiers(Modifiers m, Document doc) {
		String isCaptainActiveString = doc.getElementsByAttributeValue("data-field-key","modificatori.capitano_attivo").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isCaptainActive =  isCaptainActiveString.equals("sì");
		
		if (!isCaptainActive)
			return m;
		
		m.setCaptainModifierActive(isCaptainActive);
		
		String isCaptainDuplicateBonusString = doc.getElementsByAttributeValue("data-field-key","modificatori.capitano_bonus_doppio").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isCaptainDuplicateBonus=  isCaptainDuplicateBonusString.equals("sì");
		m.setCaptainDuplicateBonus(isCaptainDuplicateBonus);

		
		String isCaptainDuplicateMalusString = doc.getElementsByAttributeValue("data-field-key","modificatori.capitano_malus_doppio").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isCaptainDuplicateMalus =  isCaptainDuplicateMalusString.equals("sì");
		m.setCaptainDuplicateMalus(isCaptainDuplicateMalus);
		
		
		String captainVotesString = doc.getElementById("modificatori.capitano_valori_fasce").getElementsByClass("range-col-type-input").get(0).getElementsByClass("range-label").text();
		String[] captainVotesSplit = captainVotesString.split(" ");
		
		List<Double> captainVotes = new ArrayList<Double>();
		
		int i = 0;
		for (i = 0; i < captainVotesSplit.length; i++) {
			captainVotes.add(new Double(captainVotesSplit[i]));
		}	
		
		Double vote3 = captainVotes.get(0);
		m.setCaptainVote3(vote3);
		
		Double vote3half = captainVotes.get(1);
		m.setCaptainVote3half(vote3half);
		
		Double vote4 = captainVotes.get(2);
		m.setCaptainVote4(vote4);
		
		Double vote4half = captainVotes.get(3);
		m.setCaptainVote4half(vote4half);
		
		Double vote5 = captainVotes.get(4);
		m.setCaptainVote5(vote5);
		
		Double vote5half = captainVotes.get(5);
		m.setCaptainVote5half(vote5half);
		
		Double vote6 = captainVotes.get(6);
		m.setCaptainVote6(vote6);
		
		Double vote6half = captainVotes.get(7);
		m.setCaptainVote6half(vote6half);
		
		Double vote7 = captainVotes.get(8);
		m.setCaptainVote7(vote7);
		
		Double vote7half = captainVotes.get(9);
		m.setCaptainVote7half(vote7half);
		
		Double vote8 = captainVotes.get(10);
		m.setCaptainVote8(vote8);
		
		Double vote8half = captainVotes.get(11);
		m.setCaptainVote8half(vote8half);
		
		Double vote9 = captainVotes.get(12);
		m.setCaptainVote9(vote9);
		
		return m;
	}
	
	
	private Modifiers getFairPlayModifiers(Modifiers m, Document doc) {
		String isFairPlayActiveString = doc.getElementsByAttributeValue("data-field-key","modificatori.fairplay_attivo").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isFairPlayActive =  isFairPlayActiveString.equals("sì");
		
		if (!isFairPlayActive)
			return m;
		
		m.setFairPlayModifierActive(isFairPlayActive);
		
		String fairPlayString = doc.getElementsByAttributeValue("data-field-key", "modificatori.fairplay").get(0).getElementsByClass("value-type-number").get(0).text();
		fairPlayString = fairPlayString.replaceAll(" punti", "");
		Double fairPlay =  Double.valueOf(fairPlayString);
		
		m.setFairPlay(fairPlay);
		
		return m;
	}
	
	private Modifiers getStrickerModifiers(Modifiers m, Document doc) {
		String isStrikerActiveString = doc.getElementsByAttributeValue("data-field-key","modificatori.attacco_attivo").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isStrikerActive =  isStrikerActiveString.equals("sì");
		
		if (!isStrikerActive)
			return m;
		
		m.setStrikerModifierActive(isStrikerActive);
		
		String stickerVotesString = doc.getElementById("modificatori.attacco").getElementsByClass("range-col-type-input").get(0).getElementsByClass("range-label").text();
		String[] strickerVotesSplit = stickerVotesString.split(" ");
		
		List<Double> strickerVotes = new ArrayList<Double>();
		
		int i = 0;
		for (i = 0; i < strickerVotesSplit.length; i++) {
			strickerVotes.add(new Double(strickerVotesSplit[i]));
		}	
		
		Double vote6 = Double.valueOf(strickerVotes.get(0));
		m.setStrikerVote6(vote6);
	
		Double vote6half = Double.valueOf(strickerVotes.get(1));
		m.setStrikerVote6half(vote6half);
		
		Double vote7 = Double.valueOf(strickerVotes.get(2));
		m.setStrikerVote7(vote7);
		
		Double vote7half = Double.valueOf(strickerVotes.get(3));
		m.setStrikerVote7half(vote7half);
		
		Double vote8 = Double.valueOf(strickerVotes.get(4));
		m.setStrikerVote8(vote8);
		
		return m;
		
	}

	private Modifiers getMiddlefielderModifiers(Modifiers m, Document doc) {
		String isMidActiveString = doc.getElementsByAttributeValue("data-field-key","modificatori.centrocampo_attivo").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isMidActive =  !isMidActiveString.equals("no");
		
		if (!isMidActive)
			return m;
		
		m.setMiddlefielderModifierActive(isMidActive);
		
		String midVotesString = doc.getElementById("modificatori.centrocampo").getElementsByClass("range-col-type-input").get(0).getElementsByClass("range-label").text();
		String[] midVotesSplit = midVotesString.split(" ");
		
		List<Double> midVotes = new ArrayList<Double>();
		
		int i = 0;
		for (i = 0; i < midVotesSplit.length; i++) {
			midVotes.add(new Double(midVotesSplit[i]));
		}	
		
		Double diff2 =  midVotes.get(1);
		m.setMiddlefielder2(diff2);
		Double diffMinus2 =  midVotes.get(0);
		m.setMiddlefielderMinus2(diffMinus2);
		Double diff2half =  midVotes.get(3);
		m.setMiddlefielder2half(diff2half);
		Double diffMinus2half =  midVotes.get(2);
		m.setMiddlefielderMinus2half(diffMinus2half);
		
		Double diff3 =  midVotes.get(5);
		m.setMiddlefielder3(diff3);
		Double diffMinus3 =  midVotes.get(4);
		m.setMiddlefielderMinus3(diffMinus3);
		Double diff3half =  midVotes.get(7);
		m.setMiddlefielder3half(diff3half);
		Double diffMinus3half =  midVotes.get(6);
		m.setMiddlefielderMinus3half(diffMinus3half);
		
		Double diff4 =  midVotes.get(9);
		m.setMiddlefielder4(diff4);
		Double diffMinus4 =  midVotes.get(8);
		m.setMiddlefielderMinus4(diffMinus4);
		Double diff4half =  midVotes.get(11);
		m.setMiddlefielder4half(diff4half);
		Double diffMinus4half =  midVotes.get(10);
		m.setMiddlefielderMinus4half(diffMinus4half);
		
		Double diff5 =  midVotes.get(13);
		m.setMiddlefielder5(diff5);
		Double diffMinus5 =  midVotes.get(12);
		m.setMiddlefielderMinus5(diffMinus5);
		Double diff5half =  midVotes.get(15);
		m.setMiddlefielder5half(diff5half);
		Double diffMinus5half =  midVotes.get(14);
		m.setMiddlefielderMinus5half(diffMinus5half);
		
		Double diff6 =  midVotes.get(17);
		m.setMiddlefielder6(diff6);
		Double diffMinus6 =  midVotes.get(16);
		m.setMiddlefielderMinus6(diffMinus6);
		Double diff6half =  midVotes.get(19);
		m.setMiddlefielder6half(diff6half);
		Double diffMinus6half =  midVotes.get(18);
		m.setMiddlefielderMinus6half(diffMinus6half);
		
		Double diff7 =  midVotes.get(21);
		m.setMiddlefielder7(diff7);
		Double diffMinus7 =  midVotes.get(20);
		m.setMiddlefielderMinus7(diffMinus7);
		Double diff7half =  midVotes.get(23);
		m.setMiddlefielder7half(diff7half);
		Double diffMinus7half =  midVotes.get(22);
		m.setMiddlefielderMinus7half(diffMinus7half);
		
		Double diff8 =  midVotes.get(25);
		m.setMiddlefielder8(diff8);
		Double diffMinus8 =  midVotes.get(24);
		m.setMiddlefielderMinus8(diffMinus8);
		
		
		
//		Double over2 =  midVotes.get(0);
//		m.setMiddlefielderOver2(over2);
//		
//		Double underMinus2 = Double.valueOf(underMinus2String);
//		m.setMiddlefielderUnderMinus2(underMinus2);
//		
//		Double over4 = Double.valueOf(over4String);
//		m.setMiddlefielderOver4(over4);
//		
//		Double underMinus4 = Double.valueOf(underMinus4String);
//		m.setMiddlefielderUnderMinus4(underMinus4);
//		
//		Double over6 = Double.valueOf(over6String);
//		m.setMiddlefielderOver6(over6);
//		
//		Double underMinus6 = Double.valueOf(underMinus6String);
//		m.setMiddlefielderUnderMinus6(underMinus6);
//		
//		Double over8 = Double.valueOf(over8String);
//		m.setMiddlefielderOver8(over8);
//		
//		Double underMinus8 = Double.valueOf(underMinus8String);
//		m.setMiddlefielderUnderMinus8(underMinus8);
		
		return m;
		
		
		
	}

	private Modifiers getDefenderModifiers(Modifiers m, Document doc) {
		String isDefenderActiveString = doc.getElementsByAttributeValue("data-field-key","modificatori.difesa_attivo").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isDefenderActive =  !isDefenderActiveString.equals("no");
		
		if (!isDefenderActive)
			return m;
		
		m.setDefenderModifierActive(isDefenderActive);
		
		
		String isGoalkeeperIncludedString = doc.getElementsByAttributeValue("data-field-key","modificatori.difesa_includi_portiere").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isGoalkeeperIncluded =  isGoalkeeperIncludedString.equals("sì");
		m.setDefenderGoalkeeperIncluded(isGoalkeeperIncluded);
		
		String modeString = doc.getElementsByAttributeValue("data-field-key","modificatori.difesa_modalita").get(0).getElementsByClass("value-type-select").get(0).text();
		if (modeString.equals("Assegna bonus/malus all'avversario"))
			m.setDefenderMode(DefenderModeEnum.TO_OTHER);
		else if (modeString.equals("Assegna bonus/malus alla squadra"))
			m.setDefenderMode(DefenderModeEnum.TO_HIMSELF);
		
        
		
		
		
		String firstLowEdgeString = doc.getElementById("modificatori.difesa").getElementsByClass("range-col-type-labels").get(0).getElementsByClass("min-val").get(0).text();
		
		Double firstLowEdge = Double.valueOf(firstLowEdgeString);
		
		Double indexToStartDouble = (firstLowEdge - 5.0) / 0.25;
		Integer indexToStart = indexToStartDouble.intValue() ;
		
		
		
		Integer rangesNumber = doc.getElementById("modificatori.difesa").getElementsByClass("range-col-type-labels").get(0).getElementsByClass("min-val").size() + 1;
		
		String rangeModifiersString = doc.getElementById("modificatori.difesa").getElementsByClass("range-col-type-input").get(0).getElementsByClass("range-label").text();
		
		String[] rangeModifiersSplit = rangeModifiersString.split(" ");
				
		List<Double> rangeModifiers = new ArrayList<Double>();
		
		
		
		int i = 0;
		int elementsBefore = 0;
		for (i = 0; i < 14; i++) {
			if (i<indexToStart) {
				rangeModifiers.add(new Double(rangeModifiersSplit[0]));
				elementsBefore++;
			}
			else if (i >= indexToStart + rangesNumber)
				rangeModifiers.add(new Double(rangeModifiersSplit[rangesNumber-1]));
			else 
				rangeModifiers.add(new Double(rangeModifiersSplit[i - elementsBefore]));
		}	
		
		
		
		
		
		
		Double pointUnder5 =  rangeModifiers.get(0);
		m.setDefenderAvgVoteUnder5(pointUnder5);
		
		Double pointTo5 =  rangeModifiers.get(1);
		m.setDefenderAvgVote5(pointTo5);
		Double pointTo5quart =  rangeModifiers.get(2);
		m.setDefenderAvgVote5quart(pointTo5quart);
		Double pointTo5half =  rangeModifiers.get(3);
		m.setDefenderAvgVote5half(pointTo5half);
		Double pointTo5sept =  rangeModifiers.get(4);
		m.setDefenderAvgVote5sept(pointTo5sept);
		Double pointTo6 =  rangeModifiers.get(5);
		m.setDefenderAvgVote6(pointTo6);
		Double pointTo6quart =  rangeModifiers.get(6);
		m.setDefenderAvgVote6quart(pointTo6quart);
		Double pointTo6half =  rangeModifiers.get(7);
		m.setDefenderAvgVote6half(pointTo6half);
		Double pointTo6sept =  rangeModifiers.get(8);
		m.setDefenderAvgVote6sept(pointTo6sept);
		Double pointTo7 =  rangeModifiers.get(9);
		m.setDefenderAvgVote7(pointTo7);
		Double pointTo7quart =  rangeModifiers.get(10);
		m.setDefenderAvgVote7quart(pointTo7quart);
		Double pointTo7half =  rangeModifiers.get(11);
		m.setDefenderAvgVote7half(pointTo7half);
		Double pointTo7sept =  rangeModifiers.get(12);
		m.setDefenderAvgVote7sept(pointTo7sept);
		Double pointTo8 =  rangeModifiers.get(13);
		m.setDefenderAvgVote8(pointTo8);
		
		return m;
		
	}

	private Modifiers getGoalKeeperModifiers(Modifiers m, Document doc) {
		String isGoalKeeperActiveString = doc.getElementsByAttributeValue("data-field-key","modificatori.portiere_attivo").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isGoalKeeperActive =  !isGoalKeeperActiveString.equals("no");
		
		if (!isGoalKeeperActive)
			return m;
		
		m.setGoalkeeperModifierActive(isGoalKeeperActive);

		
		String isGoalkeeperModifierPenaltySavedActiveString = doc.getElementsByAttributeValue("data-field-key","modificatori.portiere_rigori").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isGoalkeeperModifierPenaltySavedActive =  isGoalkeeperModifierPenaltySavedActiveString.equals("sì");
		m.setGoalkeeperModifierPenaltySavedActive(isGoalkeeperModifierPenaltySavedActive);
		
		
		
		String goalKeeperVotesString = doc.getElementById("modificatori.portiere").getElementsByClass("range-col-type-input").get(0).getElementsByClass("range-label").text();
		String[] goalVotesSplit = goalKeeperVotesString.split(" ");
		
		List<Double> goalKeeperVotes = new ArrayList<Double>();
		
		int i = 0;
		for (i = 0; i < goalVotesSplit.length; i++) {
			goalKeeperVotes.add(new Double(goalVotesSplit[i]));
		}	
		
		
		Double vote3 = goalKeeperVotes.get(0);
		m.setGoalkeeperVote3(vote3);
		
		Double vote3half = goalKeeperVotes.get(1);
		m.setGoalkeeperVote3half(vote3half);
		
		Double vote4 = goalKeeperVotes.get(2);
		m.setGoalkeeperVote4(vote4);
		
		Double vote4half = goalKeeperVotes.get(3);
		m.setGoalkeeperVote4half(vote4half);
		
		Double vote5 = goalKeeperVotes.get(4);
		m.setGoalkeeperVote5(vote5);
		
		Double vote5half = goalKeeperVotes.get(5);
		m.setGoalkeeperVote5half(vote5half);
		
		Double vote6 = goalKeeperVotes.get(6);
		m.setGoalkeeperVote6(vote6);
		
		Double vote6half = goalKeeperVotes.get(7);
		m.setGoalkeeperVote6half(vote6half);
		
		Double vote7 = goalKeeperVotes.get(8);
		m.setGoalkeeperVote7(vote7);
		
		Double vote7half = goalKeeperVotes.get(9);
		m.setGoalkeeperVote7half(vote7half);
		
		Double vote8 = goalKeeperVotes.get(10);
		m.setGoalkeeperVote8(vote8);
		
		Double vote8half = goalKeeperVotes.get(11);
		m.setGoalkeeperVote8half(vote8half);
		
		Double vote9 = goalKeeperVotes.get(12);
		m.setGoalkeeperVote9(vote9);
		
		
		return m;
		
	}

	private Substitutions analyzeRulesPageSubstitutions(String leagueName) {
//		Document doc = getLoggedPage(AppConstants.RULES_3_SUBSTITUTIONS_URL, leagueName);
		Document doc = getLoggedPage(AppConstants.RULES_CONFIG_URL, leagueName);

//		String isFasciaConIntornoActiveString = doc.getElementsMatchingOwnText("Fascia con intorno:").parents().get(0).getElementsByAttribute("checked").val();
//		Boolean isFasciaConIntornoActive = isFasciaConIntornoActiveString.equals("0");
//		p.setFasciaConIntornoActive(isFasciaConIntornoActive);
//		String fasciaConIntornoString = doc.getElementsByAttributeValue("id", "valintorno").val();
//		Double fasciaConIntorno = Double.valueOf(fasciaConIntornoString);
//		p.setFasciaConIntorno(fasciaConIntorno);
		Substitutions s = new Substitutions();
		
		
		Element substitutionsPanel = doc.getElementById("panel_3");
		
		String numeroSostituzioniString = substitutionsPanel.getElementsByAttributeValue("data-field-key","sostituzioni.numero").get(0).getElementsByClass("value-type-number").get(0).text();
		Integer numeroSostituzioni = Integer.valueOf(numeroSostituzioniString);
		s.setSubstitutionNumber(numeroSostituzioni);
		
		String effettuaSostituzioni = substitutionsPanel.getElementsByAttributeValue("data-field-key","sostituzioni.modalita_sostituzioni").get(0).getElementsByClass("value-type-select").get(0).text();
		if (effettuaSostituzioni.equals("Cambio modulo, se il ruolo è incompatibile"))
			s.setSubstitutionMode(SubstitutionsModeEnum.CHANGE_MODULE);
		else if (effettuaSostituzioni.equals("Ruolo per ruolo, prioritario su cambio modulo"))
			s.setSubstitutionMode(SubstitutionsModeEnum.CHANGE_ROLE_THEN_CHANGE_MODULE);
		else// if (effettuaSostituzioni.equals("Solo con cambio ruolo (nessun cambio modulo)")
			s.setSubstitutionMode(SubstitutionsModeEnum.CHANGE_ROLE);
		
		
		
		
		
		
		String goalkeeperPlayerOfficeVoteActiveString = substitutionsPanel.getElementsByAttributeValue("data-field-key","sostituzioni.riserva").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean goalkeeperPlayerOfficeVoteActive =  goalkeeperPlayerOfficeVoteActiveString.equals("sì");
		s.setGoalkeeperPlayerOfficeVoteActive(goalkeeperPlayerOfficeVoteActive);
		
		String goalkeeperPlayerOfficeVoteString = substitutionsPanel.getElementsByAttributeValue("data-field-key","sostituzioni.riserva_portiere").get(0).getElementsByClass("value-type-input").get(0).text();
		Double goalkeeperPlayerOfficeVote = Double.valueOf(goalkeeperPlayerOfficeVoteString.substring(0, 1).replace(",", "."));
		s.setGoalkeeperPlayerOfficeVote(goalkeeperPlayerOfficeVote);
		
		
		
		
		String movementsPlayerOfficeVoteActiveString =  substitutionsPanel.getElementsByAttributeValue("data-field-key","sostituzioni.riserva").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean movementsPlayerOfficeVoteActive = movementsPlayerOfficeVoteActiveString.equals("sì");
		s.setMovementsPlayerOfficeVoteActive(movementsPlayerOfficeVoteActive);
		
		String movementsPlayerOfficeVoteString = substitutionsPanel.getElementsByAttributeValue("data-field-key","ru_scalar").get(0).getElementsByClass("value-type-bool").get(0).text();
		Double movementsPlayerOfficeVote = Double.valueOf(movementsPlayerOfficeVoteString.substring(0, 1).replace(",", "."));
		s.setMovementsPlayerOfficeVote(movementsPlayerOfficeVote);;
		
		
		
		
		
		Boolean playerOfficeVoteDecreaseString = !substitutionsPanel.getElementsByAttributeValue("data-field-key","sostituzioni.riserva_a_scalare").get(0).hasClass("hidden");
		s.setPlayerOfficeDecreasingVotesActive(playerOfficeVoteDecreaseString);
		if (playerOfficeVoteDecreaseString) {
			List<Double> playerOfficeVoteDecreasingList = new ArrayList<Double>();
			
			
			Elements playerOfficeVoteDecreasingElements = substitutionsPanel.getElementsByAttributeValue("data-field-key","sostituzioni.riserva_a_scalare").get(0).getElementsByClass("range-col-type-input").get(0).getElementsByClass("range-cell");
			for (Element elem : playerOfficeVoteDecreasingElements) {
				String voteString = elem.getElementsByTag("span").get(0).text();
				Double voteDouble = Double.valueOf(voteString);
				playerOfficeVoteDecreasingList.add(voteDouble);
			}
			
			s.setPlayerOfficeDecreasingVotes(playerOfficeVoteDecreasingList);
		}
		
		String maxOfficeVotes = substitutionsPanel.getElementsByAttributeValue("data-field-key","sostituzioni.riserva_ufficio").get(0).getElementsByClass("value-type-select").get(0).text();
		if ( maxOfficeVotes != null ) {
			if (maxOfficeVotes.equals("fino al raggiungimento del numero di sostituzioni impostate"))
				s.setMaxOfficeVotes(MaxOfficeVotesEnum.TILL_SUBSTITUTIONS);
			if (maxOfficeVotes.equals("ad una sola riserva senza voto"))
				s.setMaxOfficeVotes(MaxOfficeVotesEnum.ONLY_ONE);
			else// if (maxOfficeVotes.equals("fino al raggiungimento degli 11 calciatori "))
				s.setMaxOfficeVotes(MaxOfficeVotesEnum.TILL_ALL);
		}
		
		
		return s;
	}

	private Points analyzeRulesPagePoints(String leagueName) {
//		Document doc = getLoggedPage(AppConstants.RULES_4_POINTS_URL, leagueName);
		Document doc = getLoggedPage(AppConstants.RULES_CONFIG_URL, leagueName);

				
		Points p = new Points();
		

		Element pointsPanel = doc.getElementById("panel_2");
		
//		String firstGoalPointsString = pointsPanel.getElementsByAttributeValue("data-field-key","goal_ranges.0.punti_tot").get(0).getElementsByClass("val-points").get(0).text();
//		Double firstGoalPoints = Double.valueOf(firstGoalPointsString.replace(" punti", ""));
		
		String goalsRangesString = pointsPanel.getElementsByAttributeValue("data-key","puntiTot").get(0).getElementsByClass("range-cell").text();
		goalsRangesString = goalsRangesString.replaceAll(" punti", "");
		String[] goalsRangesSplit = goalsRangesString.split(" ");
		
		
		List<Double> goalPoints = new ArrayList<Double>();
		
		
		int i = 0;
		for (i = 0; i < goalsRangesSplit.length; i++) {
			goalPoints.add(new Double(goalsRangesSplit[i]));
		}
		
		String lastRangeEndString = pointsPanel.getElementsByAttributeValue("data-key","punti").get(0).getElementsByClass("range-cell").last().text();
		lastRangeEndString = lastRangeEndString.replaceAll(" punti", "");
		Double lastRangeEnd = Double.valueOf(lastRangeEndString);
		lastRangeEnd = Math.ceil(lastRangeEnd);
		
		Double lastRange =  lastRangeEnd - goalPoints.get(goalPoints.size()-1);
		for (; i< 15; i++) {
			goalPoints.add(goalPoints.get(i-1) + lastRange);
		}
		
		
//		goalPoints.add(firstGoalPoints);
//		for (i = 0; i < goalsRangesSplit.length; i++)
//			goalPoints.add(goalPoints.get(i) + new Double(goalsRangesSplit[i]));
//		Double lastRange = goalPoints.get(i) - goalPoints.get(i-1);
//		for (; i < 15; i++){
//			goalPoints.add(goalPoints.get(i) + lastRange);
//		}
//		List<Double> GOAL_POINTS = Arrays.asList(66.0, 72.0, 78.0, 84.0, 90.0, 96.0, 102.0, 108.0, 114.0);
		p.setGoalPoints(goalPoints);
		
		
		String formulaUnoPointsString = pointsPanel.getElementById("soglie.formula_uno").getElementsByClass("range-col-type-input").get(0).getElementsByClass("range-cell").text();
		String[] formulaUnoSplit = formulaUnoPointsString.split(" ");

		List<Double> formulaUnoPoints = new ArrayList<Double>();
		for (i = 0; i < formulaUnoSplit.length; i++)
			formulaUnoPoints.add(new Double(formulaUnoSplit[i]));
		p.setFormulaUnoPoints(formulaUnoPoints);
		//p.setFormulaUnoPoints(Arrays.asList(8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0));
		
		
		
		String fasciaConIntornoString = pointsPanel.getElementsByAttributeValue("data-field-key","soglie.intorno_interno").get(0).getElementsByClass("value-type-input").get(0).text();
		Boolean isFasciaConIntornoActive = !fasciaConIntornoString.equals("NO");
		p.setFasciaConIntornoActive(isFasciaConIntornoActive);
		if (isFasciaConIntornoActive) {
			fasciaConIntornoString = fasciaConIntornoString.replace(" punti", "");
			Double fasciaConIntorno = Double.valueOf(fasciaConIntornoString);
			p.setFasciaConIntorno(fasciaConIntorno);
		}
		
		
		String intornoString = pointsPanel.getElementsByAttributeValue("data-field-key","soglie.intorno").get(0).getElementsByClass("value-type-input").get(0).text();
		Boolean isIntornoActive = !intornoString.equals("NO");
		p.setIntornoActive(isIntornoActive);
		if (isIntornoActive) {
			intornoString = intornoString.replace(" punti", "");
			Double intorno = Double.valueOf(intornoString);
			p.setIntorno(intorno);
		}
		
		String intorno01String = pointsPanel.getElementsByAttributeValue("data-field-key","soglie.intorno_zerouno").get(0).getElementsByClass("value-type-bool").get(0).text();
		Boolean isIntorno01Active = intorno01String.equals("sì");
		p.setIntorno01Active(isIntorno01Active);
		// da impostare
		
		
		String controllaPareggioString = pointsPanel.getElementsByAttributeValue("data-field-key","soglie.controlla_pareggio").get(0).getElementsByClass("value-type-input").get(0).text();
		Boolean isControllaPareggioActive = !controllaPareggioString.equals("NO");
		p.setControllaPareggioActive(isControllaPareggioActive);
		if (isControllaPareggioActive) {
			controllaPareggioString = controllaPareggioString.replace(" punti", "");
			Double controllaPareggio = Double.valueOf(controllaPareggioString);
			p.setControllaPareggio(controllaPareggio);
		}
		
		
		String differenzaPuntiString = pointsPanel.getElementsByAttributeValue("data-field-key","soglie.gol_extra").get(0).getElementsByClass("value-type-input").get(0).text();
		Boolean isDifferenzaPuntiActive = !differenzaPuntiString.equals("NO");
		p.setDifferenzaPuntiActive(isDifferenzaPuntiActive);
		if (isDifferenzaPuntiActive) {
			differenzaPuntiString = differenzaPuntiString.replace(" punti", "");
			Double differenzaPunti = Double.valueOf(differenzaPuntiString);
			p.setDifferenzaPunti(differenzaPunti);
		}
		
		
		String autogolString = pointsPanel.getElementsByAttributeValue("data-field-key","soglie.autogol").get(0).getElementsByClass("value-type-input").get(0).text();
		Boolean isAutogolActive = !autogolString.equals("NO");
		p.setAutogolActive(isAutogolActive);
		if (isAutogolActive) {
			autogolString = autogolString.replace(" punti", "");
			Double autogol = Double.valueOf(autogolString);
			p.setAutogol(autogol);
		}
		
//		Elements autogolElems = doc.getElementsMatchingOwnText("Autogol:");
//		if (autogolElems.isEmpty()) {
//			p.setAutogolActive(false);
//			p.setAutogol(0.0);
//		}
//		else {
//			String isAutogolActiveString = autogolElems.parents().get(0).getElementsByAttribute("checked").val();
//			Boolean isAutogolActive = isAutogolActiveString.equals("1");
//			p.setAutogolActive(isAutogolActive);
//			String autogolString = doc.getElementsByAttributeValue("id", "vdautogol").val();
//			Double autogol = Double.valueOf(autogolString);
//			p.setAutogol(autogol);
//		}
//		
		
		
		
		
//		String isPortiereImbattutoActiveString = doc.getElementsMatchingOwnText("Bonus portiere imbattuto:").parents().get(0).getElementsByAttribute("checked").val();
//		Boolean isPortiereImbattutoActive = isPortiereImbattutoActiveString.equals("1");
//		p.setPortiereImbattutoActive(isPortiereImbattutoActive);
//		String portiereImbattutoString = doc.getElementsByAttributeValue("id", "vpimbattuto").val();
//		Double portiereImbattuto = Double.valueOf(portiereImbattutoString);
//		p.setPortiereImbattuto(portiereImbattuto);
		
		System.out.println(p);
		
		
		
		
		return p;
	}


	private DataSources analyzeRulesPageDataSources(String leagueName) {
//		Document doc = getLoggedPage(AppConstants.RULES_2_SOURCE_URL, leagueName);
		Document doc = getLoggedPage(AppConstants.RULES_CONFIG_URL, leagueName);

		
		DataSources ds = new DataSources();
		Element dataSourcePanel = doc.getElementById("panel_1");
		
		String votesSourceString = dataSourcePanel.getElementsByAttributeValue("data-field-key","fonte.fonte_voti").get(0).getElementsByClass("value-type-select").get(0).text();
		VotesSourceEnum voteSource = getVoteSource(votesSourceString);
		ds.setVotesSource(voteSource);
		
//		String bonusMalusSourceString = doc.getElementsMatchingOwnText("Fonte bonus/malus:").parents().get(0).getElementsByAttribute("selected").text();
//		VotesSourceEnum bonusMalusSource = getVoteSource(bonusMalusSourceString);
//		ds.setBonusMalusSource(bonusMalusSource);
		
		
		String cardsSourceString = dataSourcePanel.getElementsByAttributeValue("data-field-key","fonte.fonte_ammo_espu").get(0).getElementsByClass("value-type-select").get(0).text();
		ds.setYellowRedCardSource(cardsSourceString.substring(0, 1));
		
		
		System.out.println(ds);
		
		
		return ds;
	}

	
	private static VotesSourceEnum getVoteSource(String bonusMalusSourceString) {
		VotesSourceEnum bonusMalusSource;
		if (bonusMalusSourceString.equals("Fantagazzetta"))
			bonusMalusSource = VotesSourceEnum.FANTAGAZZETTA;
		else if (bonusMalusSourceString.equals("Statistico Alvin482"))
			bonusMalusSource = VotesSourceEnum.STATISTICO;
		else //if (bonusMalusSourceString.equals("Italia"))
			bonusMalusSource = VotesSourceEnum.ITALIA;
		return bonusMalusSource;
	}
	
	private BonusMalus analyzeRulesPageBonusMalus(String leagueName) {
//		Document doc = getLoggedPage(AppConstants.RULES_1_BONUS_MALUS_URL, leagueName);
		Document doc = getLoggedPage(AppConstants.RULES_CONFIG_URL, leagueName);
		
		BonusMalus bm = new BonusMalus();
		
//		Elements scoredGoalElements = doc.getElementsMatchingOwnText("Gol segnato:").parents().get(0).select("input");
		Element bonusMalusPanel = doc.getElementById("panel_0");
		Elements currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","gol_segnato").get(0).getElementsByTag("td");
		bm.setScoredGoal(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","gol_subito").get(0).getElementsByTag("td");
		bm.setTakenGoal(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","rigore_segnato").get(0).getElementsByTag("td");
		bm.setScoredPenalty(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","rigore_sbagliato").get(0).getElementsByTag("td");
		bm.setMissedPenalty(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","rigore_parato").get(0).getElementsByTag("td");
		bm.setSavedPenalty(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","ammonizione").get(0).getElementsByTag("td");
		bm.setYellowCard(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","espulsione").get(0).getElementsByTag("td");
		bm.setRedCard(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","assist").get(0).getElementsByTag("td");
		bm.setMovementAssist(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","assist_fermo").get(0).getElementsByTag("td");
		bm.setStationaryAssist(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","autogol").get(0).getElementsByTag("td");
		bm.setAutogoal(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","gol_decisivo_pareggio").get(0).getElementsByTag("td");
		bm.setEvenGoal(getRolesMap(currentElements));
		
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","gol_decisivo_vittoria").get(0).getElementsByTag("td");
		bm.setWinGoal(getRolesMap(currentElements));
		
		//portiere imbattuto
		currentElements = bonusMalusPanel.getElementsByAttributeValue("data-key","portiere_imbattuto").get(0).getElementsByTag("td");
		String portiereImbattutoString = currentElements.get(1).text();
		Double portiereImbattutoNumber = Double.valueOf(portiereImbattutoString);
		Boolean portiereImbattutoNumberActive = portiereImbattutoNumber != 0.0;
		bm.setPortiereImbattutoActive(portiereImbattutoNumberActive);
		if (portiereImbattutoNumberActive) {
			bm.setPortiereImbattuto(portiereImbattutoNumber);
		}
		
		
//		String isPortiereImbattutoActiveString = doc.getElementsMatchingOwnText("Bonus portiere imbattuto:").parents().get(0).getElementsByAttribute("checked").val();
//		Boolean isPortiereImbattutoActive = isPortiereImbattutoActiveString.equals("1");
//		p.setPortiereImbattutoActive(isPortiereImbattutoActive);
//		String portiereImbattutoString = doc.getElementsByAttributeValue("id", "vpimbattuto").val();
//		Double portiereImbattuto = Double.valueOf(portiereImbattutoString);
//		p.setPortiereImbattuto(portiereImbattuto);
		
		//ammonito sv
		
		
		
		String isAssegnaVotoAdAmmonitoSvActiveString = bonusMalusPanel.getElementsByAttributeValue("data-field-key","assegna_voto_sv.ammonito").get(0).getElementsByClass("value-type-number").get(0).text();
		Boolean isAssegnaVotoAdAmmonitoSvActive = !isAssegnaVotoAdAmmonitoSvActiveString.equals("NO");
		bm.setYellowCardSvOfficeVoteActive(isAssegnaVotoAdAmmonitoSvActive);
		if (isAssegnaVotoAdAmmonitoSvActive) {
			String votoAdAmmonitoSvString = isAssegnaVotoAdAmmonitoSvActiveString.replace(" punti", "");
			Double votoAdAmmonitoSv = Double.valueOf(votoAdAmmonitoSvString);
			bm.setYellowCardSvOfficeVote(votoAdAmmonitoSv);
		}
		
		
//		String isAssegnaVotoAdAmmonitoSvActiveString = doc.getElementsByAttributeValue("id", "ammonitosv1").attr("checked");
//		Boolean isAssegnaVotoAdAmmonitoSvActive = isAssegnaVotoAdAmmonitoSvActiveString.equals("checked");
//		s.setYellowCardSvOfficeVoteActive(isAssegnaVotoAdAmmonitoSvActive);
//		
//		String votoAdAmmonitoSvString = doc.getElementsByAttributeValue("id", "vammonitosv").val();
//		Double votoAdAmmonitoSv = Double.valueOf(votoAdAmmonitoSvString.replace(",", "."));
//		s.setYellowCardSvOfficeVote(votoAdAmmonitoSv);
		
		
		
		System.out.println(bm);
		return bm;
	}

	private Map<RoleEnum, Double> getRolesMap(Elements elements) {
		Map<RoleEnum, Double> map = new HashMap<RoleEnum, Double>();
		String valString = elements.get(1).text();
		Double val = Double.valueOf(valString); 
		map.put(RoleEnum.P, val);
		
		valString = elements.get(2).text();
		val = Double.valueOf(valString); 
		map.put(RoleEnum.D, val);
		
		valString = elements.get(3).text();
		val = Double.valueOf(valString); 
		map.put(RoleEnum.C, val);
		
		valString = elements.get(4).text();
		val = Double.valueOf(valString); 
		map.put(RoleEnum.A, val);
		
		return map;
	}

	public IntegrateRulesReq integrateRules(IntegrateRulesReq req) {
		if (userBean.getUsername() == null)
			return null;
		
		rulesDao.integrateRules(req, userBean.getUsername());
		return req;
		
	}



	public RulesBean saveRules(RulesBean rules, String leagueShortName, String competitionShortName) {
		if (userBean.getUsername() == null)
			return null;
		
		List<CompetitionBean> competitions = leagueDao.findCompetitionsByLeague(leagueShortName, userBean.getUsername());
		if (competitions.size() == 0) {
			return new RulesBean();
		}

		rulesDao.updateRulesForCompetition(rules, competitionShortName, leagueShortName, userBean.getUsername());
		
		return rules;
	}



	
}
