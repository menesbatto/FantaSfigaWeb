package app.logic._3_seasonsGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.LeagueDao;
import app.dao.RulesDao;
import app.dao.UtilsDao;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.utils.AppConstants;


//Crea tutti i possibili calendari (40320) e li scrive su file
@Service
public class AllSeasonsGenerator {
	
	@Autowired
	private UtilsDao utilsDao;
	
	@Autowired
	private LeagueDao leagueDao;
	
	@Autowired
	private UserBean userBean;
	
	@Autowired
	private RulesDao rulesDao;
	
	@Autowired
	private SeasonGenerator seasonGenerator;
	
	
	public ArrayList<SeasonBean> generateAllSeasons(String leagueShortName, String competitionShortName){
		return generateAllSeasons(leagueShortName, competitionShortName, false);
	}
	
	public ArrayList<SeasonBean> generateAllSeasons(String leagueShortName, String competitionShortName, Boolean onlyOne){
		long startTime = System.nanoTime();
		System.out.println("Inizio generazione di tutti i calendari");
		SeasonBean s;
		ArrayList<SeasonBean> allSeasons = new ArrayList<SeasonBean>();
		
		List<String> teams = leagueDao.findTeams(leagueShortName, userBean.getUsername());
		Integer playersNumbers = teams.size();
		
		List<String> allInputPermutations = utilsDao.findPermutations(playersNumbers );
//		if (AppConstants.DEBUG_MODE) 
		if (onlyOne)	
			allInputPermutations = allInputPermutations.subList(0, 1);
		
		Integer seasonNumber = 0;
		
		Map<Integer, Integer> bindings = rulesDao.findSerieAToCompetitionBinding(leagueShortName, competitionShortName, userBean.getUsername());
		Integer serieAActualSeasonDay = utilsDao.calculateLastSerieASeasonDayCalculated();
			
		Integer fantacalcioActualSeasonDay = bindings.get(serieAActualSeasonDay);
		if (fantacalcioActualSeasonDay == null)
			fantacalcioActualSeasonDay = bindings.get(serieAActualSeasonDay-1);
		

		
		SeasonBean seasonPattern = leagueDao.findSeason(leagueShortName, competitionShortName, userBean.getUsername(), "Pattern");
		
		for (String permutation : allInputPermutations){
			s = seasonGenerator.generateSingleSeason(permutation, seasonNumber++, fantacalcioActualSeasonDay, seasonPattern, teams);
			allSeasons.add(s);
		}
		
//		if (!AppConstants.FAST_MODE_ACTIVE)
//			IOUtils.write(AppConstants.GENERATED_SEASONS_DIR + AppConstants.GENERATED_SEASONS_FILE_NAME , allSeasons);
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		System.out.println("Fine generazione di tutti i calendari " + duration/1000000);

		
		return allSeasons;
		
	}

//	public static ArrayList<SeasonBean> getAllGeneratedSeasonStructures() {
//		long startTime = System.nanoTime();
//		System.out.println("Inizio caricamento di tutti i calendari");
//		ArrayList<SeasonBean> allSeasons = IOUtils.read(AppConstants.GENERATED_SEASONS_DIR + AppConstants.GENERATED_SEASONS_FILE_NAME, ArrayList.class);
//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
//		System.out.println("Fine caricamento " + duration/1000000);
//		return allSeasons;
//	}
	
}
