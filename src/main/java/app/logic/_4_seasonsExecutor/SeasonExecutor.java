package app.logic._4_seasonsExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;
import app.logic._4_seasonsExecutor.model.Pair;
import app.logic._4_seasonsExecutor.model.RankingBean;
import app.logic._4_seasonsExecutor.model.RankingRowBean;
import app.utils.AppConstants;

@Service
public class SeasonExecutor {
	
//	private static ArrayList<SeasonDayResultBean> realChampionshipResults = MainSeasonAnalyzerFINAL.getRealChampionshipResults();
	
//	private static RankingBean ranking;
//	private static RankingBean formulaUnoRanking;
//	private static ArrayList<Integer> serieAseasonToWait;
	
//	static RulesBean rules = RulesExpertMain.getRules();
	private int i = 0;
	
	@Autowired
	private SeasonDayExecutor seasonDayExecutor;
	
	
	public RankingBean execute(SeasonBean season, RulesBean rules, List<SeasonDayResultBean> realChampionshipResults, List<String> teams) {
		List<SeasonDayBean> seasonDays = season.getSeasonDays();
		SeasonDayBean seasonDay = null;
		SeasonDayResultBean seasonDayResult;
		RankingBean ranking = createRanking(teams);
		RankingBean formulaUnoRanking = createRanking(teams);
//		serieAseasonToWait = MainSeasonAnalyzerFINAL.getSeasonDaysToWait();
		
		
		
		for (int i = 0; i < realChampionshipResults.size(); i++) {
//			Integer fantacalcioStartingSerieASeasonDay = MainSeasonAnalyzerFINAL.getFantacalcioStartingSerieASeasonDay();
			
//			if (serieAseasonToWait.contains(i + fantacalcioStartingSerieASeasonDay)){
//				continue;
//			}
//			if (AppConstants.SEASON_DAYS_TO_WAIT.contains(i+1))
			seasonDay = seasonDays.get(i);
			seasonDayResult = realChampionshipResults.get(i);
			if (seasonDayResult.getLinesUpLight().size() > 0) {	//Controllo per gestire le giornate in cui ci sono i rinvii
				seasonDayResult = seasonDayExecutor.execute(seasonDay, seasonDayResult, rules, teams);
	//			System.out.println(seasonDayResult);
				updateRanking(seasonDayResult, ranking);	
				updateFormulaUnoRanking(seasonDayResult, rules, formulaUnoRanking);
			}
			else {
				if (AppConstants.DEBUG_MODE)
					System.out.println( (i + 1) + " Giornata da recuperare");
			}
		}
		
		
//		System.out.println(ranking);
//		System.out.println("");
//		System.out.println(formulaUnoRanking);
//		System.out.println("");
		
//		System.out.println("numero sculate");
//		System.out.println(MatchExecutor.winOrDrewForHalfPointsList);
//		System.out.println("punti sculati");
//		System.out.println(MatchExecutor.rankingPointsWinOrDrewForHalfPointsList);
//		System.out.println("numero  sfigate");
//		System.out.println(MatchExecutor.loseOrDrewForHalfPointList);
//		System.out.println("punti sfigate");
//		System.out.println(MatchExecutor.rankingPointsLoseOrDrewForHalfPointList);
		return ranking;
		

	}

	
	


	private static void updateFormulaUnoRanking(SeasonDayResultBean seasonDayResult, RulesBean rules, RankingBean formulaUnoRanking) {
		List<Pair> seasonDayVoteSumRanking = new ArrayList<Pair>();
		Pair p;
		for (LineUpLightBean lul : seasonDayResult.getLinesUpLight()){
			p = new Pair(lul.getTeamName(), lul.getTotalWithoutGoalkeeperAndMiddlefielderModifiers());
			seasonDayVoteSumRanking.add(p);
		}
		Collections.sort(seasonDayVoteSumRanking, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				int compareTo = o2.getValue().compareTo(o1.getValue());
				return compareTo;
			}
			
		});
		
		List<Pair> seasonDayFormulaUnoRanking = new ArrayList<Pair>();
		Pair formulaUnoPair = new Pair(seasonDayVoteSumRanking.get(0).getName(), rules.getPoints().getFormulaUnoPoints().get(0));
		seasonDayFormulaUnoRanking.add(formulaUnoPair);
		for (int j = 1; j < seasonDayVoteSumRanking.size()-1; j++) {
			Pair voteSumPair = seasonDayVoteSumRanking.get(j);
			if (j > 0){
				Pair previousVoteSumPair = seasonDayVoteSumRanking.get(j - 1);
				if ( previousVoteSumPair.getValue() == voteSumPair.getValue() ){
					Pair previousFormulaUnoPair = seasonDayFormulaUnoRanking.get(j - 1);
					formulaUnoPair = new Pair(voteSumPair.getName(), previousFormulaUnoPair.getValue());
				}
				else
					formulaUnoPair = new Pair(voteSumPair.getName(),rules.getPoints().getFormulaUnoPoints().get(j));
			}
			seasonDayFormulaUnoRanking.add(formulaUnoPair);
		}
//		System.out.println(seasonDayFormulaUnoRanking);
		for( RankingRowBean rr : formulaUnoRanking.getRows()){
			for (Pair ps :seasonDayFormulaUnoRanking){
				if (rr.getName().equalsIgnoreCase((ps.getName()))){
					rr.setPoints(rr.getPoints() + ps.getValue().intValue());
					continue;
				}
			}
//			System.out.println(seasonDayFormulaUnoRanking);
		}
	
		Collections.sort(formulaUnoRanking.getRows(), new Comparator<RankingRowBean>() {
			public int compare(RankingRowBean o1, RankingRowBean o2) {
				if (o2.getPoints() - o1.getPoints() < 0)
					return -1;
				else if (o2.getPoints() - o1.getPoints() > 0)
					return 1;
				return 0;
			}
			
		});
		
	}



	private static void updateRanking(SeasonDayResultBean seasonDayResult, RankingBean ranking) {
		for (LineUpLightBean lul : seasonDayResult.getLinesUpLight()){
			for (RankingRowBean rr : ranking.getRows()){
				if (lul.getTeamName().equalsIgnoreCase(rr.getName())){
					updateRankingRow(rr, lul);
				}
			}
		}
		
	}
	
	
	
	
	private static void updateRankingRow(RankingRowBean rr, LineUpLightBean lul) {
		rr.setPoints(rr.getPoints() + lul.getRankingPoints());
		rr.setScoredGoals(rr.getScoredGoals() + lul.getGoals());
		rr.setSumAllVotes(rr.getSumAllVotes() + lul.getSumTotalPoints());
		rr.setTakenGoals(rr.getTakenGoals() +  lul.getTakenGoals());
		//rr.setTakenGoals(takenGoals);
		//rr.setRankingPosition(rankingPosition);
		
	}



	


	private RankingBean createRanking(List<String> teams) {
		RankingBean ranking = new RankingBean();
		ranking.setName(""+i++);
		
		RankingRowBean rw; 
		for (String p : teams){
			rw = new RankingRowBean(p);
			ranking.getRows().add(rw);
		}
		return ranking;
		
	}
	

	
	
}
