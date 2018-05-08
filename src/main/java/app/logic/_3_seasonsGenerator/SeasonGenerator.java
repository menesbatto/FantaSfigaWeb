package app.logic._3_seasonsGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.PlayerEnum;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._3_seasonsGenerator.model.PlayerOrder;

@Service
public class SeasonGenerator {
	
	public SeasonBean generateSingleSeason(String combination, Integer seasonNumber, Integer fantacalcioActualSeasonDay, SeasonBean seasonPattern, List<String> players){
		
		ArrayList<PlayerOrder> playerOrderList = new ArrayList<PlayerOrder>();
		
		for (int i = 0; i< players.size(); i++) {
			playerOrderList.add(new PlayerOrder(players.get(i), String.valueOf(combination.charAt(i))));
		}
		
		Collections.sort(playerOrderList, new Comparator<PlayerOrder>() {
			public int compare(PlayerOrder o1, PlayerOrder o2) {
				return o1.getLetter().compareTo(o2.getLetter());
			}
		});
		
		SeasonBean s = createSeason(combination, playerOrderList, fantacalcioActualSeasonDay, seasonPattern); 
//		SeasonBean s = createSeason(seasonNumber + " - " + combination, playerOrderList, fantacalcioActualSeasonDay, seasonPattern); 
		
		return s;
		
	}

	private SeasonBean createSeason(String seasonName, ArrayList<PlayerOrder> playerOrderList, Integer fantacalcioActualSeasonDay, SeasonBean seasonPattern) {
		
		SeasonBean season = new SeasonBean(seasonName);
		SeasonDayBean seasonDay;
		MatchBean m;
		PlayerEnum playerEnum;
		List<SeasonDayBean> seasonDays = seasonPattern.getSeasonDays();
		for (int i = 0; i < seasonDays.size(); i++) {
			if(i <= fantacalcioActualSeasonDay){
				SeasonDayBean currentSeasonDay = seasonDays.get(i);
				seasonDay = new SeasonDayBean(currentSeasonDay.getName());
				seasonDay.setSerieANumber(currentSeasonDay.getSerieANumber());
				season.getSeasonDays().add(seasonDay);
				for (MatchBean currentMatch : currentSeasonDay.getMatches()){
					m = new MatchBean();
					for (PlayerOrder po : playerOrderList){
						playerEnum = PlayerEnum.valueOf(po.getLetter());
						if (currentMatch.getHomeTeamEnum().equals(playerEnum)) {
							m.setHomeTeam(po.getName());
						}
						if (currentMatch.getAwayTeamEnum().equals(playerEnum)) {
							m.setAwayTeam(po.getName());
						}
					}
					seasonDay.getMatches().add(m);
				}
			}
		}
		
		return season;
	}

}
