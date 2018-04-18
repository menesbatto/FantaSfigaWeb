package app.logic._5_rankingAnalizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.LeagueDao;
import app.dao.RankingType;
import app.dao.entity.Competition;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._4_seasonsExecutor.model.Pair;
import app.logic._4_seasonsExecutor.model.RankingBean;
import app.logic._4_seasonsExecutor.model.RankingRowBean;
import app.logic.model.StasResponse;
import app.utils.UsefulMethods;

@Service
public class RankingAnalyzer {

	@Autowired
	private LeagueDao leagueDao;
	
	@Autowired
	private UserBean userBean;
	
//	private static ArrayList<RankingBean> allRankings;
//	private static RankingBean realRanking;
//	private static int playerNumber;
	
	
	public StasResponse retrieveAllRankings(String leagueShortName, String competitionShortName) {
		System.out.println();
		// Ranking reale
		RankingBean realRanking = leagueDao.findRanking(leagueShortName, competitionShortName, userBean.getUsername(), RankingType.REAL);
		
		RankingBean realLightRanking = createRealLightRanking(realRanking);
		
		
		// Ranking con numero di evenienze per ogni posizione
		RankingBean positionsRanking = leagueDao.findRanking(leagueShortName, competitionShortName, userBean.getUsername(), RankingType.POSITIONS);
		
		// Ranking con Percentuale per ogni posizione
		RankingBean positionsPercentaleRanking =  calculatePositionsPercentageRanking(positionsRanking);
		
		// Ranking con Posizione Media
		RankingBean averagePositionRanking = calculateAveragePositionRankings(positionsRanking);

		// Ranking con Differenza tra la Posizione Reale e la Posizione Media
		RankingBean deltaPositionRankings = calculateDeltaPositionRankings(averagePositionRanking, realRanking);
		
		
		

		// Ranking giusto
		RankingBean fairRanking = leagueDao.findRanking(leagueShortName, competitionShortName, userBean.getUsername(), RankingType.FAIR);
		
		// Ranking con Differenza tra i punti giusti e i punti reali
		RankingBean deltaFairRanking = calculateDeltaRankingPoints(fairRanking, realRanking);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		StasResponse res = new StasResponse();
		res.setRealRanking(realRanking);
		res.setRealLightRanking(realLightRanking);
		
		res.setFairRanking(fairRanking);
		res.setDeltaFairRanking(deltaFairRanking);

		res.setPositionsRanking(positionsRanking);
		res.setAveragePositionRanking(averagePositionRanking);
		res.setDeltaPositionRankings(deltaPositionRankings);
		res.setPositionsPercentaleRanking(positionsPercentaleRanking);
		
		
		
		res.setCompetitionShortName(competitionShortName);
		res.setLeagueShortName(leagueShortName);
		
		return res;
		
	}
	
	private RankingBean createRealLightRanking(RankingBean realRanking) {
		RankingBean light = new RankingBean();
		RankingRowBean rrLight;
		List<RankingRowBean> rows = new ArrayList<RankingRowBean>();
		for (RankingRowBean rr : realRanking.getRows()) {
			rrLight = new RankingRowBean();
			rrLight.setName(rr.getName());
			rrLight.setPoints(rr.getPoints());
			rows.add(rrLight);
		}
		light.setRows(rows);
		light.setName(RankingType.LIGHT.name());
		return light;
	}

	public StasResponse calculateAllStats(List<RankingBean> allRankings, String leagueShortName, String competitionShortName) {
		
		List<String> teams = leagueDao.findTeams(leagueShortName, userBean.getUsername());
		RankingBean realRanking = leagueDao.findRanking(leagueShortName, competitionShortName, userBean.getUsername(), RankingType.REAL);
		
		System.out.println("CLASSIFICA ATTUALE");
		for(RankingRowBean rr: realRanking.getRows()){
			System.out.println(rr);
		}
		
		int playerNumber = realRanking.getRows().size();
		
		List<Pair> positionsList = calculatePositionsList(teams, allRankings);
		
		// Ranking con numero di evenienze per ogni posizione
		RankingBean positionsRanking = calculatePositionsRanking(positionsList, playerNumber);
		leagueDao.saveRanking(positionsRanking, leagueShortName, competitionShortName, userBean.getUsername());

		// Ranking con Percentuale per ogni posizione
		RankingBean positionsPercentaleRanking =  calculatePositionsPercentageRanking(positionsRanking);
		
		// Ranking con Posizione Media
		RankingBean averagePositionRanking = calculateAveragePositionRankings(positionsRanking);

		// Ranking con Differenza tra la Posizione Reale e la Posizione Media
		RankingBean deltaPositionRankings = calculateDeltaPositionRankings(averagePositionRanking, realRanking);
		
		
		
		RankingBean fairRanking = calculateAvgRankingPoints(positionsList, playerNumber);
		leagueDao.saveRanking(fairRanking, leagueShortName, competitionShortName, userBean.getUsername());
		
		RankingBean deltaFairRanking = calculateDeltaRankingPoints(fairRanking, realRanking);
		
		
		StasResponse res = new StasResponse();
		
		return res;
		
	}

	
	
	private RankingBean calculateAvgRankingPoints(List<Pair> results, int playerNumber) {

		System.out.println("\n\n\nCALCOLO DEI PUNTI MEDI");

		int combinations = UsefulMethods.factorial(playerNumber);
		for(Pair r : results){
			r.setValue( r.getValue()/combinations);
		}
		
		
		List<RankingRowBean> fairRanking = new ArrayList<RankingRowBean>();
		for (int i = 0; i < results.size(); i++) {
			Pair pair = results.get(i);
			RankingRowBean current = new RankingRowBean();
			current.setName(pair.getName());
			current.setPoints(pair.getValue());
			current.setRankingPosition(i);
			fairRanking.add(current);
		}
		
		RankingBean rankingBean = new RankingBean();
		rankingBean.setName(RankingType.FAIR.name());
		rankingBean.setRows(fairRanking);
		
		System.out.println(results);
		
		return rankingBean;
	}
	
	private RankingBean calculateDeltaRankingPoints(RankingBean fairRanking, RankingBean realRanking) {

		System.out.println("\n\n\nCALCOLO DELLA DIFFERENZA DAI PUNTI GIUSTI IN CORSO...");

		List<RankingRowBean> inputRankingRows = fairRanking.getRows();
		RankingRowBean rr;
		
		List<RankingRowBean> resultRankingRows = new ArrayList<RankingRowBean>();
		for (int i = 0; i < inputRankingRows.size(); i++) {
			rr = realRanking.getRows().get(i);
			for(RankingRowBean avgPoints : inputRankingRows){
				if (avgPoints.getName().equals(rr.getName())) {
					RankingRowBean resultRankingRow = new RankingRowBean();
					resultRankingRow.setName(rr.getName());
					resultRankingRow.setPoints(rr.getPoints() - avgPoints.getPoints());
					resultRankingRows.add(resultRankingRow);
				}
			}
		}
		Collections.sort(resultRankingRows, new Comparator<RankingRowBean>() {
			public int compare(RankingRowBean o1, RankingRowBean o2) {
				return o1.getPoints().compareTo(o2.getPoints());
			}
		});
		
		System.out.println("\n\n\nCALCOLO DELLA DIFFERENZA DAI PUNTI GIUSTI (+ alto, + culo)");
		System.out.println(resultRankingRows);
		
		RankingBean rankingBean = new RankingBean();
		rankingBean.setName(RankingType.DELTA_FAIR.name());
		rankingBean.setRows(resultRankingRows);
		
		
		return rankingBean;
		
	}

	private RankingBean calculateAveragePositionRankings(RankingBean positionsRanking) {
		System.out.println("\n\n\nCALCOLO DELLA POSIZIONE GIUSTA IN CORSO...");
		
		int playerNumber = positionsRanking.getRows().size();
		List<RankingRowBean> inputRankingRows = positionsRanking.getRows();
		
		List<Double> playerPositions;
		Double sum;
		Double avgPosition;
		Double checkSum = 0.0;;
		RankingRowBean avgPositionResult;
		Double pp;
		List<RankingRowBean> avgPositions = new ArrayList<RankingRowBean>();
		for(RankingRowBean positionInput : inputRankingRows) {
			sum = 0.0;
			playerPositions = positionInput.getPositions();
			for (int i = 0; i < playerPositions.size(); i++) {
				pp = playerPositions.get(i);
				sum += pp * (i+1);
			}
			int combinations = UsefulMethods.factorial(playerNumber);
			
			avgPosition = sum / combinations;
			
			String name = positionInput.getName();
//			System.out.println((name.length()>10 ? name.substring(0,10) : name) + ":\t" + avgPosition);
			
			avgPositionResult = new RankingRowBean();
			avgPositionResult.setName(name);
			avgPositionResult.setPoints(avgPosition);
			avgPositions.add(avgPositionResult);
			checkSum += avgPosition;
		}
		
		Collections.sort(avgPositions, new Comparator<RankingRowBean>() {
			public int compare(RankingRowBean o1, RankingRowBean o2) {
				return o1.getPoints().compareTo(o2.getPoints());
			}
		});
		
		RankingBean ranking = new RankingBean();
		ranking.setName(RankingType.AVG_POSITION.name());
		ranking.setRows(avgPositions);
		
		System.out.println("\n\n\nCALCOLO DELLA POSIZIONE GIUSTA");
		System.out.println(avgPositions);
		System.out.println(checkSum/playerNumber);
		
		return ranking;
	}
		
		
	private RankingBean calculateDeltaPositionRankings(RankingBean avgPositionRanking, RankingBean realRanking) {
		System.out.println("\n\n\nCALCOLO DELLA DIFFERENZA DALLA POSIZIONE GIUSTA IN CORSO...");
		List<RankingRowBean> avgPositions  = avgPositionRanking.getRows();
		
		List<RankingRowBean> positionVariation = new ArrayList<RankingRowBean>();
		for (int i = 0; i < avgPositions.size(); i++) {
			RankingRowBean rr = realRanking.getRows().get(i);
			for(RankingRowBean avgPos :avgPositions){
				if (avgPos.getName().equals(rr.getName())) {
					
					RankingRowBean deltaRR = new RankingRowBean();
					deltaRR.setName(rr.getName());
					deltaRR.setPoints(avgPos.getPoints()-(i+1));
					positionVariation.add(deltaRR);
				}
			}
		}
		Collections.sort(positionVariation, new Comparator<RankingRowBean>() {
			public int compare(RankingRowBean o1, RankingRowBean o2) {
				return o1.getPoints().compareTo(o2.getPoints());
			}
		});
		
		RankingBean ranking = new RankingBean();
		ranking.setName(RankingType.DELTA_POSITION.name());
		ranking.setRows(positionVariation);
		
		System.out.println("\n\n\nCALCOLO DELLA DIFFERENZA DALLA POSIZIONE GIUSTA (+ alto, + culo)");
		System.out.println(positionVariation);
		
		return ranking;
		
	}

	private RankingBean calculatePositionsRanking(List<Pair> positionsList, int playerNumber) {
		System.out.println("\n\n\nCALCOLO DI TUTTE LE POSIZIONI");
		
		List<RankingRowBean> positionsRanking = new ArrayList<RankingRowBean>();
		for (int i = 0; i < positionsList.size(); i++) {
			Pair pair = positionsList.get(i);
			RankingRowBean current = new RankingRowBean();
			current.setName(pair.getName());
			current.setPositions(pair.getValueList());
			current.setRankingPosition(i);
			positionsRanking.add(current);
		}
		
		RankingBean rankingBean = new RankingBean();
		rankingBean.setName(RankingType.POSITIONS.name());
		rankingBean.setRows(positionsRanking);
//		leagueDao.saveRanking(rankingBean, leagueShortName, competitionShortName, userBean.getUsername());
		
		return rankingBean;
		
	}
		
	private RankingBean calculatePositionsPercentageRanking(RankingBean positionsRanking) {
		
		System.out.println(positionsRanking.getRows());
		List<RankingRowBean> positionsList = positionsRanking.getRows();
		
		System.out.println("\n\n\nCALCOLO DI TUTTE LE POSIZIONI IN PERCENTUALE");
//		List<Pair> percentagePairList = new ArrayList<Pair>(); 
		RankingRowBean currentRankingRow;
		Integer playerNumber = positionsRanking.getRows().size();
		int combinations = UsefulMethods.factorial(playerNumber);
		List<Double> percentagePositions = null;

		List<RankingRowBean> resultRankingRows = new ArrayList<RankingRowBean>();
		for( RankingRowBean r : positionsList ){
			percentagePositions = new ArrayList<Double>();
			for (Double vl : r.getPositions()){
				double sum = vl / combinations * 100;
				double perc = ((int)(sum * 100))/100.0;
				percentagePositions.add(perc);
			}
			currentRankingRow = new RankingRowBean();
			currentRankingRow.setName(r.getName());
			currentRankingRow.setPositions(percentagePositions);
			resultRankingRows.add(currentRankingRow);
//			r.setValueList(percentages);
		}
		
		RankingBean ranking = new RankingBean();
		ranking.setName(RankingType.PERC_POSITIONS.name());
		ranking.setRows(resultRankingRows);

		return ranking;
	}

	private List<Pair> calculatePositionsList(List<String> teams, List<RankingBean> allRankings) {
		List<Pair> results = createListPairTeams(teams);
		
		RankingRowBean rr;
		List<RankingRowBean> rows;
		List<Double> listPositions;
		Double listPoints = 0.0;
		for (RankingBean ranking : allRankings) {
			rows = ranking.getRows();
			for (int i = 0; i < rows.size(); i++) {
				rr = rows.get(i);
				for (Pair result : results) {
					if (rr.getName().equals(result.getName())){
						listPositions = result.getValueList();
						int rankingPosition = rr.getRankingPosition()-1;
						listPositions.set(rankingPosition, listPositions.get(rankingPosition) + 1);
						
						listPoints = result.getValue();
						result.setValue(listPoints + rr.getPoints() );
					}
				}
			}
		}
		
		Collections.sort(results, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				//return o2.getValueList().get(0).compareTo(o1.getValueList().get(0));
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return results;
	}

	private List<Pair> createListPairTeams(List<String> teams) {
		
//		ArrayList<String> players = MainSeasonPatternExtractorNEW.getPlayers();
		List<Pair> results = new ArrayList<Pair>();
		Pair p;
		for (String player : teams) {
			 List<Double> positions = getArrayOfSize(teams.size());

			p = new Pair(player , positions);
			results.add(p);
			
		}
		return results;
	}

	private List<Double> getArrayOfSize(int size) {
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < size; i++){
			list.add(0.0);
		}
		return list;
	}


}
