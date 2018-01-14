package app.logic._4_seasonsExecutor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;

@Service
public class SeasonDayExecutor {
	
	@Autowired
	private MatchExecutor matchExecutor;
	
	public SeasonDayResultBean execute(SeasonDayBean seasonDay, SeasonDayResultBean seasonDayResult, RulesBean rules, List<String> teams) {
		for (MatchBean m : seasonDay.getMatches()){
			for (LineUpLightBean lul : seasonDayResult.getLinesUpLight()){
				if (m.getHomeTeam().equalsIgnoreCase(lul.getTeamName())){
					m.setHomeTeamResult(lul);	
				}
				if (m.getAwayTeam().equalsIgnoreCase(lul.getTeamName())){
					m.setAwayTeamResult(lul);
				}
			}
			matchExecutor.execute(m, rules, teams); // per riferimento ha modificato il SeasonDayResult
		}
		return seasonDayResult;
	}


	
}
