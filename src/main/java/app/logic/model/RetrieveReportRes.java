package app.logic.model;

import java.io.Serializable;

import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._2_realChampionshipAnalyzer.ReportBean;
import app.logic._4_seasonsExecutor.model.RankingBean;

public class RetrieveReportRes implements Serializable {

	private static final long serialVersionUID = -9074938878778729335L;

	private ReportBean report;
	private CompetitionBean competition;
	
	
	
	public RetrieveReportRes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReportBean getReport() {
		return report;
	}
	public void setReport(ReportBean report) {
		this.report = report;
	}
	public CompetitionBean getCompetition() {
		return competition;
	}
	public void setCompetition(CompetitionBean competition) {
		this.competition = competition;
	}

	
	
}
