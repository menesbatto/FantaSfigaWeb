package app.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._2_realChampionshipAnalyzer.model.SeasonDayResultBean;
import app.logic._3_seasonsGenerator.AllSeasonsGenerator;
import app.logic._4_seasonsExecutor.MainSeasonsExecutor;
import app.logic._4_seasonsExecutor.model.RankingBean;
import app.logic._5_rankingAnalizer.RankingAnalyzer;

@Service
public class Main {
	
	@Autowired
	private AllSeasonsGenerator allSeasonsGenerator;

	@Autowired
	private MainSeasonsExecutor mainSeasonsExecutor;
	
	@Autowired
	private RankingAnalyzer rankingAnalyzer;
	
	
	public void execute(String leagueShortName, String competitionShortName ){
		
	
	// Genera tutti i possibili calendari (sarebbe inutile farlo sempre ma ci si mette meno ad eseguirlo che a deserializzarli da disco)
		List<SeasonBean> allSeasons = allSeasonsGenerator.generateAllSeasons(leagueShortName, competitionShortName);
		List<RankingBean> allRankings = mainSeasonsExecutor.execute(allSeasons, leagueShortName, competitionShortName);
		rankingAnalyzer.analyzeRankings(allRankings, leagueShortName, competitionShortName);
		
		System.out.println("execute");
	}
}
