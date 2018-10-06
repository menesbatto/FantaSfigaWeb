package app.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.logic._0_votesDownloader.model.VotesSourceEnum;

public class AppConstants {
	
	
	//	URL
	public static String LAST_SEASON_DAY_URL = "http://www.fantagazzetta.com/voti-fantacalcio-serie-a";
	
	public static String GAZZETTA_URL = "http://leghe.fantagazzetta.com/";
	
	public static String LOGIN_PAGE_URL = GAZZETTA_URL;
	
	public static String CALENDAR_URL_TEMPLATE = "http://leghe.fantagazzetta.com/[LEAGUE_NAME]/calendario/[COMPETITION_ID]";

	public static String SEASON_DAY_LINES_UP_URL_TEMPLATE = "http://leghe.fantagazzetta.com/[LEAGUE_NAME]/formazioni/[COMPETITION_ID]?g=";


//	public static String RULES_1_BONUS_MALUS_URL = 		"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/visualizza-opzioni-calcolo1";
//	public static String RULES_1_BONUS_MALUS_URL = 		"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/gestione-lega/opzioni-rose";
//	public static String RULES_2_SOURCE_URL =			"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/visualizza-opzioni-calcolo2";
//	public static String RULES_2_SOURCE_URL =			"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/gestione-lega/opzioni-formazioni";
//	public static String RULES_3_SUBSTITUTIONS_URL = 	"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/visualizza-opzioni-calcolo3";
//	public static String RULES_3_SUBSTITUTIONS_URL = 	"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/gestione-lega/opzioni-formazioni";
//	public static String RULES_4_POINTS_URL = 			"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/visualizza-opzioni-calcolo4";
//	public static String RULES_4_POINTS_URL = 			"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/gestione-lega/opzioni-calcolo";
//	public static String RULES_5_MODIFIERS_URL = 		"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/visualizza-opzioni-calcolo5";
//	public static String RULES_5_MODIFIERS_URL = 		"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/gestione-lega/opzioni-calcolo-avanzato";
//	public static String RULES_6_COMPETITION_URL = 		"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/visualizza-competizione-calendario/[COMPETITION_ID]";	
	public static String RULES_6_COMPETITION_URL = 		"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/visualizza-competizione-calendario/[COMPETITION_ID]";	
//
	public static String RULES_CONFIG_URL = 			"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/gestione-lega/opzioni-calcolo";
	public static String RULES_CONFIG_PLUS_URL = 		"http://leghe.fantagazzetta.com/[LEAGUE_NAME]/gestione-lega/opzioni-calcolo-avanzato";

	
	public static VotesSourceEnum FORCE_VOTE_SOURCE = null;//VotesSourceEnum.NAPOLI;
	
	public static String SEASON_DAY_ALL_VOTES_URL = "http://www.fantagazzetta.com/voti-fantacalcio-serie-a/2017-18";
	public static String SEASON_DAY_VOTES_URL_TEMPLATE = "https://www.fantagazzetta.com/Servizi/Voti.ashx?s=2017-18&g=[SEASON_DAY]&tv=[DATE_TIME_MILLIS]&t=[GAZZETTA_TEAM_ID]";
	public static String SERIE_A_TEAMS_IDS_URL = "https://www.fantagazzetta.com/voti-fantacalcio-serie-a/";
	
	
	
	
//	public static String LEAGUE_NAME = "mefaipvopviopenaleague";
//	public static String COMPETITION_NAME = "ME FAI PVOPVIO PENA LEAGUE";
//	public static String LEAGUE_DIR = "ale\\";
//	public static String user = "Ale.Dima.9";
//	public static String password = "9Vucinic";
	
	
//	public static String LEAGUE_NAME = "hppomezialeague";
//	public static String COMPETITION_NAME = "CAMPIONATOSEI";
//	public static String LEAGUE_DIR = "hppomezialeague\\";
//	public static String user = "nick23asr";
//	public static String password = "asroma23";
	
	
//	public static String LEAGUE_NAME = "accaniti-division";
//	public static String COMPETITION_NAME = "CAMPIONATO SERIE A-CCANITA";
//	public static String LEAGUE_DIR = "accanitidivision\\";
//	public static String user = "menesbatto";
//	public static String password = "suppaman";

}
