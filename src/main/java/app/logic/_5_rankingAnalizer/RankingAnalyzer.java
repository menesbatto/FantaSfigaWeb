package app.logic._5_rankingAnalizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.LeagueDao;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._4_seasonsExecutor.model.Pair;
import app.logic._4_seasonsExecutor.model.RankingBean;
import app.logic._4_seasonsExecutor.model.RankingRowBean;
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
	
	
	public void analyzeRankings(List<RankingBean> allRankings, String leagueShortName, String competitionShortName) {
		List<String> teams = leagueDao.findTeams(leagueShortName, userBean.getUsername());
		RankingBean realRanking = leagueDao.findRealRanking(leagueShortName, competitionShortName, userBean.getUsername());
		
		
		initStaticFields();
		
		System.out.println("CLASSIFICA ATTUALE");
		for(RankingRowBean rr: realRanking.getRows()){
			System.out.println(rr);
		}
		int playerNumber = realRanking.getRows().size();
		
		List<Pair> calculateAllPosition = calculateAllPosition(teams, allRankings, playerNumber);
		calculateAverageRankingPositions(calculateAllPosition, realRanking, playerNumber);
		calculateAvgRankingPoints(teams, allRankings, realRanking, playerNumber);
	}

	
	
	
	

	private void initStaticFields() {
//		if (allRankings == null){
//			allRankings = MainSeasonsExecutor.getAllGeneratedRankings();
//		}
//		realRanking = MainSeasonsExecutor.getRealRanking();
//		playerNumber = realRanking.getRows().size();;
	}

	private void calculateAvgRankingPoints(List<String> teams, List<RankingBean> allRankings, RankingBean realRanking, int playerNumber) {
		System.out.println("\n\n\nCALCOLO DEI PUNTI MEDI");
		List<Pair> results = createListTeams(teams);

		RankingRowBean rr;
		List<RankingRowBean> rows;
		Double listPoints = 0.0;
		for (RankingBean ranking : allRankings) {
			rows = ranking.getRows();
			for (int i = 0; i < rows.size(); i++) {
				rr = rows.get(i);
				for (Pair result : results) {
					if (rr.getName().equals(result.getName())){
						listPoints = result.getValue();
						result.setValue(listPoints + rr.getPoints() );
					}
				}
			}
		}
		int combinations = UsefulMethods.factorial(playerNumber);

		Collections.sort(results, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		for(Pair r : results){
			r.setValue( r.getValue()/combinations);
		}
		System.out.println(results);
		
		

		List<Pair> pointsVariation = new ArrayList<Pair>();
		for (int i = 0; i < results.size(); i++) {
			rr = realRanking.getRows().get(i);
			for(Pair avgPoints : results){
				if (avgPoints.getName().equals(rr.getName())) {
					pointsVariation.add(new Pair(rr.getName(), rr.getPoints() - avgPoints.getValue() ));
				}
			}
		}
		Collections.sort(pointsVariation, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		System.out.println("\n\n\nCALCOLO DELLA DIFFERENZA DAI PUNTI GIUSTI (+ alto, + culo)");
		System.out.println(pointsVariation);
		
	}

	private void calculateAverageRankingPositions(List<Pair> allPositions, RankingBean realRanking, int playerNumber) {
		System.out.println("\n\n\nCALCOLO DELLA POSIZIONE MEDIA");
		List<Double> playerPositions;
		Double sum;
		Double avgPosition;
		Double checkSum = 0.0;;
		Pair avgPositionResult;
		Double pp;
		List<Pair> avgPositions = new ArrayList<Pair>();
		for(Pair positionInput : allPositions) {
			sum = 0.0;
			playerPositions = positionInput.getValueList();
			for (int i = 0; i < playerPositions.size(); i++) {
				pp = playerPositions.get(i);
				sum += pp * (i+1);
			}
			int combinations = UsefulMethods.factorial(playerNumber);
			
			avgPosition = sum / combinations;
			
			String name = positionInput.getName();
//			System.out.println((name.length()>10 ? name.substring(0,10) : name) + ":\t" + avgPosition);
			
			avgPositionResult = new Pair(name, avgPosition);
			avgPositions.add(avgPositionResult);
			checkSum += avgPosition;
		}
		
		Collections.sort(avgPositions, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		
		System.out.println(avgPositions);
		System.out.println(checkSum/playerNumber);
		
		
		
		List<Pair> positionVariation = new ArrayList<Pair>();
		for (int i = 0; i < avgPositions.size(); i++) {
			RankingRowBean rr = realRanking.getRows().get(i);
			for(Pair avgPos :avgPositions){
				if (avgPos.getName().equals(rr.getName())) {
					positionVariation.add(new Pair(rr.getName(), avgPos.getValue()-(i+1)));
				}
			}
		}
		Collections.sort(positionVariation, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		System.out.println("\n\n\nCALCOLO DELLA DIFFERENZA DALLA POSIZIONE GIUSTA (+ alto, + culo)");
		System.out.println(positionVariation);
	}

	private List<Pair> calculateAllPosition(List<String> teams, List<RankingBean> allRankings, int playerNumber) {
		System.out.println("\n\n\nCALCOLO DI TUTTE LE POSIZIONI");
		
		List<Pair> results = createListTeams(teams);
		
		RankingRowBean rr;
		List<RankingRowBean> rows;
		List<Double> listPositions;
		for (RankingBean ranking : allRankings) {
			rows = ranking.getRows();
			for (int i = 0; i < rows.size(); i++) {
				rr = rows.get(i);
				for (Pair result : results) {
					if (rr.getName().equals(result.getName())){
						listPositions = result.getValueList();
						int rankingPosition = rr.getRankingPosition()-1;
						listPositions.set(rankingPosition, listPositions.get(rankingPosition) + 1);
					}
				}
			}
		}
		
		Collections.sort(results, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				return o2.getValueList().get(0).compareTo(o1.getValueList().get(0));
			}
		});
		System.out.println(results);
		System.out.println("\n\n\nCALCOLO DI TUTTE LE POSIZIONI IN PERCENTUALE");
		List<Pair> percentagePairList = new ArrayList<Pair>(); 
		Pair percentagePair;
		int combinations = UsefulMethods.factorial(playerNumber);
		List<Double> percentages = null;
		for( Pair r : results ){
			percentages = new ArrayList<Double>();
			for (Double vl : r.getValueList()){
				double e = vl / combinations * 100;
				double shorT = ((int)(e * 100))/100.0;
				percentages.add(shorT);
			}
			percentagePair = new Pair(r.getName(), percentages);
			percentagePairList.add(percentagePair);
//			r.setValueList(percentages);
		}
		
		System.out.println(percentagePairList);
		
		return results;
	}

	private List<Pair> createListTeams(List<String> teams) {
		
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
