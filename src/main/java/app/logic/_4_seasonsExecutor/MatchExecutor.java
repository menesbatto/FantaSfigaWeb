package app.logic._4_seasonsExecutor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import app.dao.RankingType;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._4_seasonsExecutor.model.LuckyEdgeInfo;
import app.logic._4_seasonsExecutor.model.Pair;
import app.utils.AppConstants;

@Service
public class MatchExecutor {


	public void execute(MatchBean m, RulesBean rules, List<String> teams) {
		
		LineUpLightBean homeTeamResult = m.getHomeTeamResult();
		LineUpLightBean awayTeamResult = m.getAwayTeamResult();

		// 1 - Calcola i punti dei voti + modificatori e casa
		Double homeSumTotalPoints = homeTeamResult.getTotalWithoutGoalkeeperAndMiddlefielderModifiers();
		Double awaySumTotalPoints = awayTeamResult.getTotalWithoutGoalkeeperAndMiddlefielderModifiers();
		
		
		// Modificatore difesa
		if (rules.getModifiers().isGoalkeeperModifierActive()){
			//if (!AppConstants.FORCE_GOALKEEPER_MODIFIER_DISABLED){
				Double homeVarGoalkeeper = homeTeamResult.getGoalkeeperModifier();
				Double awayVarGoalkeeper = awayTeamResult.getGoalkeeperModifier();
				homeSumTotalPoints += awayVarGoalkeeper;
				awaySumTotalPoints += homeVarGoalkeeper;
			//}
		}
		
		// Modificatore centrocampo
		if (rules.getModifiers().isMiddlefielderModifierActive()){
			//if (!AppConstants.FORCE_MIDDLEFIELD_MODIFIER_DISABLED){
				Double homeVarMid = homeTeamResult.getMiddlefieldersVariation();
				Double awayVarMid = awayTeamResult.getMiddlefieldersVariation();
				Double varMid = homeVarMid - awayVarMid;
				
				if (varMid <= -2 || varMid >= 2){
//					if (varMid >= 8){
//						homeSumTotalPoints += rules.getModifiers().getMiddlefielderOver8();
//						awaySumTotalPoints += rules.getModifiers().getMiddlefielderUnderMinus8();
//					} else if (varMid >= 6) {
//						homeSumTotalPoints += rules.getModifiers().getMiddlefielderOver6();
//						awaySumTotalPoints += rules.getModifiers().getMiddlefielderUnderMinus6();
//					} else if (varMid >= 4) {
//						homeSumTotalPoints += rules.getModifiers().getMiddlefielderOver4();
//						awaySumTotalPoints += rules.getModifiers().getMiddlefielderUnderMinus4();
//					} else if (varMid >= 2) {
//						homeSumTotalPoints += rules.getModifiers().getMiddlefielderOver2();
//						awaySumTotalPoints += rules.getModifiers().getMiddlefielderUnderMinus2();
//					} else if (varMid <= -8) {
//						homeSumTotalPoints += rules.getModifiers().getMiddlefielderUnderMinus8();
//						awaySumTotalPoints += rules.getModifiers().getMiddlefielderOver8();
//					} else if (varMid <= -6) {
//						homeSumTotalPoints += rules.getModifiers().getMiddlefielderUnderMinus6();
//						awaySumTotalPoints += rules.getModifiers().getMiddlefielderOver6();
//					} else if (varMid <= -4) {
//						homeSumTotalPoints += rules.getModifiers().getMiddlefielderUnderMinus4();
//						awaySumTotalPoints += rules.getModifiers().getMiddlefielderOver4();
//					} else if (varMid <= -2) {
//						homeSumTotalPoints += rules.getModifiers().getMiddlefielderUnderMinus2();
//						awaySumTotalPoints += rules.getModifiers().getMiddlefielderOver2();
//					} else {
//						System.out.println("C'e' un errore");
//					}
				}
				//}
		}
		if (rules.getCompetitionRules().isHomeBonusActive()) {
			//if (AppConstants.FORCE_INVERT_HOME_AWAY != null){
//				if (!AppConstants.FORCE_INVERT_HOME_AWAY){
				if (rules.getCustomRules() != null && RankingType.INVERT_HOME_AWAY.equals(rules.getCustomRules().getRankingType())){
					if (homeSumTotalPoints != 0.0) {
						awaySumTotalPoints = awaySumTotalPoints + rules.getCompetitionRules().getHomeBonus(); // Chi gioca fuori casa +2
					}
				} else {//if (AppConstants.FORCE_INVERT_HOME_AWAY){
					if (awaySumTotalPoints != 0.0) {
						homeSumTotalPoints = homeSumTotalPoints + rules.getCompetitionRules().getHomeBonus(); // Chi gioca in casa +2
					}
				}
//			}
		}

		homeTeamResult.setSumTotalPoints(homeSumTotalPoints);
		awayTeamResult.setSumTotalPoints(awaySumTotalPoints);
		
		//2 - Calcola gol fatti 			
		Integer homeTeamGoals = calculateGoal(homeSumTotalPoints, rules);
		Integer awayTeamGoals = calculateGoal(awaySumTotalPoints, rules);
		
		

		
		// FASCIA CON INTORNO
		//Se due squadre totalizzano un punteggio all'interno della stessa fascia, si aggiunge un gol alla squadra che avra' distaccato l'avversario del valore specificato.
		// Maggiore di 0 goal
		//Si aggiunge il gol

		Double firstGoalPoints = rules.getPoints().getGoalPoints().get(0);

		if (rules.getPoints().isFasciaConIntornoActive()){
			if (homeTeamGoals == awayTeamGoals) {
				if ( homeSumTotalPoints > firstGoalPoints)
					if (  homeSumTotalPoints > awaySumTotalPoints && homeSumTotalPoints - awaySumTotalPoints > rules.getPoints().getFasciaConIntorno()  ){
						homeTeamGoals++;
					}
					else if (  awaySumTotalPoints > homeSumTotalPoints && awaySumTotalPoints - homeSumTotalPoints > rules.getPoints().getFasciaConIntorno()  ) {
						awayTeamGoals++;
					}
			}
		}

		// INTORNO
		//Se i punteggi delle due squadre cadono in fasce differenti, vince chi distacca l'avversario di almeno del valore specificato
		//Si toglie il gol
		if (rules.getPoints().isIntornoActive()){
			Double difference = homeSumTotalPoints - awaySumTotalPoints;
			if (difference <= rules.getPoints().getIntorno() &&  difference >= - rules.getPoints().getIntorno() ){
				if (homeTeamGoals > awayTeamGoals) {
					homeTeamGoals = awayTeamGoals;
				}
				else {//if (awayTeamGoals > homeTeamGoals)
					awayTeamGoals = homeTeamGoals;
				}
			} 
		}
		
		
		// CONTROLLA PAREGGIO
		//Se due squadre ottengono un punteggio inferiore a Soglia gol specificare lo scarto per far scattare il gol alla squadra con punteggio maggiore
		// Uguale a 0 goal
		//Si aggiunge il gol
		if (rules.getPoints().isControllaPareggioActive()){
			if (homeSumTotalPoints < firstGoalPoints && awaySumTotalPoints < firstGoalPoints) {
				if (  homeSumTotalPoints > awaySumTotalPoints && homeSumTotalPoints - awaySumTotalPoints > rules.getPoints().getControllaPareggio()  ){
					homeTeamGoals++;
				}
				else if (  awaySumTotalPoints > homeSumTotalPoints && awaySumTotalPoints - homeSumTotalPoints > rules.getPoints().getControllaPareggio()  ){
					awayTeamGoals++;
				}
			}
		}
	
		// DIFFERENZA PUNTI
		//Se la differenza dei punti-squadra è uguale o superiore al valore impostato, si attribuisce un altro gol alla squadra con piu' punti
		//Si aggiunge il gol
		if (rules.getPoints().isDifferenzaPuntiActive()){
			if (  homeSumTotalPoints > awaySumTotalPoints && homeSumTotalPoints - awaySumTotalPoints > rules.getPoints().getDifferenzaPunti()  ){
				homeTeamGoals++;
			}
			else if (  awaySumTotalPoints > homeSumTotalPoints && awaySumTotalPoints - homeSumTotalPoints > rules.getPoints().getDifferenzaPunti()  ){
				awayTeamGoals++;
			}
			
		}
		
		// AUTOGOL
		//Se una delle due squadre ottiene un punteggio inferiore al valore impostato, si attribuisce un altro gol alla squadra avversaria
		//Si aggiunge il gol
		if (rules.getPoints().getAutogolActive()) {
			if ( homeSumTotalPoints < rules.getPoints().getAutogol() )
				awayTeamGoals++;
			else if ( awaySumTotalPoints < rules.getPoints().getAutogol() )
				homeTeamGoals++;
		}
		
		
		homeTeamResult.setGoals(homeTeamGoals);
		awayTeamResult.setGoals(awayTeamGoals);
		
		//3 - Calcola punti in classifica guadagnati
		Integer homeRankingPoints;
		Integer awayRankingPoints;
		
		if (homeTeamGoals > awayTeamGoals){
			homeRankingPoints = 3;
			awayRankingPoints = 0;
		} else if (homeTeamGoals < awayTeamGoals){
			homeRankingPoints = 0;
			awayRankingPoints = 3;
		} else {
			homeRankingPoints = 1;
			awayRankingPoints = 1;
		}
		
		homeTeamResult.setRankingPoints(homeRankingPoints);
		awayTeamResult.setRankingPoints(awayRankingPoints);
		
		homeTeamResult.setTakenGoals(awayTeamGoals);
		awayTeamResult.setTakenGoals(homeTeamGoals);
		
		if(		rules.getCustomRules()!= null && 
				( RankingType.LUCKY_EDGES_0_5.equals(rules.getCustomRules().getRankingType()) || RankingType.LUCKY_EDGES_1.equals(rules.getCustomRules().getRankingType())) ) {
			analyzeLuckyEdges(homeTeamResult, awayTeamResult, rules);
		}
	}

	private void analyzeLuckyEdges(LineUpLightBean homeTeamResult, LineUpLightBean awayTeamResult, RulesBean rules) {
		if (homeTeamResult.getLuckyEdge()== null)
			homeTeamResult.setLuckyEdge(new LuckyEdgeInfo());
		if (awayTeamResult.getLuckyEdge()== null)
			awayTeamResult.setLuckyEdge(new LuckyEdgeInfo());		
		
		Integer homeRankingPoints = homeTeamResult.getRankingPoints();
		Integer awayRankingPoints = awayTeamResult.getRankingPoints();
		Integer homeTeamGoals = homeTeamResult.getGoals();
		Integer awayTeamGoals = awayTeamResult.getGoals();
		Double awaySumTotalPoints = awayTeamResult.getSumTotalPoints();
		Double homeSumTotalPoints = homeTeamResult.getSumTotalPoints();
		if (homeTeamGoals - awayTeamGoals <= 1 && homeTeamGoals - awayTeamGoals >= -1){
			//SCULO PER HOME
			if ( rules.getPoints().getGoalPoints().contains(homeSumTotalPoints) || rules.getPoints().getGoalPoints().contains(awaySumTotalPoints + rules.getCustomRules().getLuckyEdgePoints())){
				Double rankingPointsGainedFromHomeTeam = 0.0;
				Double rankingPointsLostFromAwayTeam = 0.0;
				if (homeRankingPoints == 3.0){
					rankingPointsGainedFromHomeTeam = 2.0;
					rankingPointsLostFromAwayTeam = 1.0;
				}
				else if (homeRankingPoints == 1.0){
					rankingPointsGainedFromHomeTeam = 1.0;
					rankingPointsLostFromAwayTeam = 2.0;
				}
				if (homeRankingPoints > 0.0){
					homeTeamResult.getLuckyEdge().setLuckyEdgeGain(rankingPointsGainedFromHomeTeam);
					awayTeamResult.getLuckyEdge().setUnluckyEdgeLose(-rankingPointsLostFromAwayTeam);
				}
			} 
			//SCULO PER AWAY
			if ( rules.getPoints().getGoalPoints().contains(awaySumTotalPoints) || rules.getPoints().getGoalPoints().contains(homeSumTotalPoints + rules.getCustomRules().getLuckyEdgePoints())){
				Double rankingPointsGainedFromAwayTeam = 0.0;
				Double rankingPointsLostFromHomeTeam = 0.0;
				if (awayRankingPoints == 3.0){
					rankingPointsGainedFromAwayTeam = 2.0;
					rankingPointsLostFromHomeTeam = 1.0;
				}
				else if (awayRankingPoints == 1.0){
					rankingPointsGainedFromAwayTeam = 1.0;
					rankingPointsLostFromHomeTeam = 2.0;
				}
				if (awayRankingPoints > 0.0){
					homeTeamResult.getLuckyEdge().setUnluckyEdgeLose( -rankingPointsLostFromHomeTeam);
					awayTeamResult.getLuckyEdge().setLuckyEdgeGain( rankingPointsGainedFromAwayTeam);
				}
			}
		}
		
	}

	private List<Pair> createPairList(List<String> teams) {
		Pair p;
		List<Pair> list = new ArrayList<Pair>();
		for(String s: teams){
			p = new Pair(s, 0.0);
			list.add(p);
		}
		return list;
	}

	private Integer calculateGoal(Double homeTeamPoints, RulesBean rules) {
		for (int i = 0; i< rules.getPoints().getGoalPoints().size(); i++){
			if (homeTeamPoints < rules.getPoints().getGoalPoints().get(i))
				return i;
		}
//		if (homeTeamPoints<66){
//			return 0;
//		} else if (homeTeamPoints >= 66 && homeTeamPoints < 72 ){
//			return 1;
//		} else if (homeTeamPoints >= 72 && homeTeamPoints < 77 ){
//			return 2;
//		} else if (homeTeamPoints >= 77 && homeTeamPoints < 81 ){
//			return 3;
//		} else if (homeTeamPoints >= 81 && homeTeamPoints < 85 ){
//			return 4;
//		} else if (homeTeamPoints >= 85 && homeTeamPoints < 89 ){
//			return 5;
//		} else if (homeTeamPoints >= 89 && homeTeamPoints < 93 ){
//			return 6;
//		} else if (homeTeamPoints >= 93 && homeTeamPoints < 97 ){
//			return 7;
//		} else if (homeTeamPoints >= 97 && homeTeamPoints < 101 ){
//			return 8;
//		} else if (homeTeamPoints >= 101 && homeTeamPoints < 105 ){
//			return 9;
//		}
		
		return 14;
	}
	

}
