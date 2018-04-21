package app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dom4j.rule.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.RulesType;
import app.dao.entity.Competition;
import app.dao.entity.League;
import app.dao.entity.LineUpLight;
import app.dao.entity.Matcho;
import app.dao.entity.Rules;
import app.dao.entity.Season;
import app.dao.entity.SeasonDay;
import app.dao.entity.Vote;
import app.logic._0_rulesDownloader.model.BonusMalus;
import app.logic._0_rulesDownloader.model.CompetitionRules;
import app.logic._0_rulesDownloader.model.DataSources;
import app.logic._0_rulesDownloader.model.MaxOfficeVotesEnum;
import app.logic._0_rulesDownloader.model.Modifiers;
import app.logic._0_rulesDownloader.model.Points;
import app.logic._0_rulesDownloader.model.RulesBean;
import app.logic._0_rulesDownloader.model.Substitutions;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.RoleEnum;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;
import app.logic._2_realChampionshipAnalyzer.model.LineUpLightBean;
import app.logic._2_realChampionshipAnalyzer.model.PostponementBehaviourEnum;
import app.logic.model.IntegrateRulesReq;

@Service
@EnableCaching
public class RulesDao {

	@Autowired
	private RulesRepo rulesRepo;

	@Autowired
	private LeagueDao leagueDao;

	@Autowired
	private UserDao userDao;

	
	private Vote populateVote(PlayerVoteComplete voteBean, Integer serieASeasonDay, VotesSourceEnum voteSource) {
//		Vote ent = new Vote();
//		ent.setSerieASeasonDay(serieASeasonDay);
//		ent.setSource(voteSource.name());
//		
//		ent.setAutogoals(voteBean.getAutogoals());
//		ent.setEvenGoal(voteBean.getEvenGoal());
//		ent.setMissedPenalties(voteBean.getMissedPenalties());
//		ent.setMovementAssists(voteBean.getMovementAssists());
//		ent.setName(voteBean.getName());
//		ent.setRedCard(voteBean.getRedCard());
//		ent.setRole(voteBean.getRole().toString());
//		ent.setSavedPenalties(voteBean.getSavedPenalties());
//		ent.setScoredGoals(voteBean.getScoredGoals());
//		ent.setScoredPenalties(voteBean.getScoredPenalties());
//		ent.setStationaryAssists(voteBean.getStationaryAssists());
//		ent.setSubIn(voteBean.getSubIn());
//		ent.setSubOut(voteBean.getSubOut());
//		ent.setTakenGoals(voteBean.getTakenGoals());
//		ent.setTeam(voteBean.getTeam());
//		ent.setVote(voteBean.getVote());
//		ent.setWinGoal(voteBean.getWinGoal());
//		ent.setYellowCard(voteBean.getYellowCard());
//		
		return null;
	}

	public Boolean existRulesForLeague(String leagueShortName, String username) {
		League league = leagueDao.findByShortNameEnt(leagueShortName, username);
		
		List<Rules> rules = rulesRepo.findByLeague(league);
		
//		rulesRepo.delete(rules);
		Boolean exist = rules.size() > 0;
		return exist;
	}

	public Boolean existRulesForCompetition(String competitionShortName, String leagueShortName, String username) {
		Competition competition = leagueDao.findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		
		Rules rules = rulesRepo.findByCompetitionAndType(competition, RulesType.REAL.name());
		
		boolean alreadyExistRules = rules != null;
		
		return alreadyExistRules;
		
	}

	
//	public Boolean existIntegratedRulesForCompetition(String competitionShortName, String leagueShortName, String username) {
//		Competition competition = leagueDao.findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
//		
//		Rules rules = rulesRepo.findByCompetition(competition);
//		
//		boolean alreadyExistRules = false;
//		
//		if ( rules != null)
//			if (rules.getPostponementBehaviour() != null)	// se è = null significa che già' ho settato le regole aggiuntive
//				alreadyExistRules = true; 
//		
//		
//		return alreadyExistRules;
//		
//	}

	
	private Rules populateRules(RulesBean b, String leagueShortName, String username) {
		Rules e = new Rules();
		
		
		BonusMalus bonusMalus = b.getBonusMalus();
		
		e.setAutogoalP(bonusMalus.getAutogoal().get(RoleEnum.P));
		e.setEvenGoalP(bonusMalus.getEvenGoal().get(RoleEnum.P));
		e.setMissedPenaltyP(bonusMalus.getMissedPenalty().get(RoleEnum.P));
		e.setMovementAssistP(bonusMalus.getMovementAssist().get(RoleEnum.P));
		e.setRedCardP(bonusMalus.getRedCard().get(RoleEnum.P));
		e.setScoredGoalP(bonusMalus.getScoredGoal().get(RoleEnum.P));

		e.setSavedPenaltyP(bonusMalus.getSavedPenalty().get(RoleEnum.P));
		e.setScoredPenaltyP(bonusMalus.getScoredPenalty().get(RoleEnum.P));
		e.setStationaryAssistP(bonusMalus.getStationaryAssist().get(RoleEnum.P));
		e.setTakenGoalP(bonusMalus.getTakenGoal().get(RoleEnum.P));
		e.setWinGoalP(bonusMalus.getWinGoal().get(RoleEnum.P));
		e.setYellowCardP(bonusMalus.getYellowCard().get(RoleEnum.P));
		
		
		e.setAutogoalD(bonusMalus.getAutogoal().get(RoleEnum.D));
		e.setEvenGoalD(bonusMalus.getEvenGoal().get(RoleEnum.D));
		e.setMissedPenaltyD(bonusMalus.getMissedPenalty().get(RoleEnum.D));
		e.setMovementAssistD(bonusMalus.getMovementAssist().get(RoleEnum.D));
		e.setRedCardD(bonusMalus.getRedCard().get(RoleEnum.D));
		e.setScoredGoalD(bonusMalus.getScoredGoal().get(RoleEnum.D));

		e.setSavedPenaltyD(bonusMalus.getSavedPenalty().get(RoleEnum.D));
		e.setScoredPenaltyD(bonusMalus.getScoredPenalty().get(RoleEnum.D));
		e.setStationaryAssistD(bonusMalus.getStationaryAssist().get(RoleEnum.D));
		e.setTakenGoalD(bonusMalus.getTakenGoal().get(RoleEnum.D));
		e.setWinGoalD(bonusMalus.getWinGoal().get(RoleEnum.D));
		e.setYellowCardD(bonusMalus.getYellowCard().get(RoleEnum.D));
		
		
		
		e.setAutogoalC(bonusMalus.getAutogoal().get(RoleEnum.C));
		e.setEvenGoalC(bonusMalus.getEvenGoal().get(RoleEnum.C));
		e.setMissedPenaltyC(bonusMalus.getMissedPenalty().get(RoleEnum.C));
		e.setMovementAssistC(bonusMalus.getMovementAssist().get(RoleEnum.C));
		e.setRedCardC(bonusMalus.getRedCard().get(RoleEnum.C));
		e.setScoredGoalC(bonusMalus.getScoredGoal().get(RoleEnum.C));

		e.setSavedPenaltyC(bonusMalus.getSavedPenalty().get(RoleEnum.C));
		e.setScoredPenaltyC(bonusMalus.getScoredPenalty().get(RoleEnum.C));
		e.setStationaryAssistC(bonusMalus.getStationaryAssist().get(RoleEnum.C));
		e.setTakenGoalC(bonusMalus.getTakenGoal().get(RoleEnum.C));
		e.setWinGoalC(bonusMalus.getWinGoal().get(RoleEnum.C));
		e.setYellowCardC(bonusMalus.getYellowCard().get(RoleEnum.C));
		
		
		
		
		e.setAutogoalA(bonusMalus.getAutogoal().get(RoleEnum.A));
		e.setEvenGoalA(bonusMalus.getEvenGoal().get(RoleEnum.A));
		e.setMissedPenaltyA(bonusMalus.getMissedPenalty().get(RoleEnum.A));
		e.setMovementAssistA(bonusMalus.getMovementAssist().get(RoleEnum.A));
		e.setRedCardA(bonusMalus.getRedCard().get(RoleEnum.A));
		e.setScoredGoalA(bonusMalus.getScoredGoal().get(RoleEnum.A));

		e.setSavedPenaltyA(bonusMalus.getSavedPenalty().get(RoleEnum.A));
		e.setScoredPenaltyA(bonusMalus.getScoredPenalty().get(RoleEnum.A));
		e.setStationaryAssistA(bonusMalus.getStationaryAssist().get(RoleEnum.A));
		e.setTakenGoalA(bonusMalus.getTakenGoal().get(RoleEnum.A));
		e.setWinGoalA(bonusMalus.getWinGoal().get(RoleEnum.A));
		e.setYellowCardA(bonusMalus.getYellowCard().get(RoleEnum.A));

		// #################################################################################
		
		DataSources dataSource = b.getDataSource();
		
		e.setBonusMalusSource(dataSource.getBonusMalusSource().name());
		e.setVotesSource(dataSource.getVotesSource().name());
		e.setYellowRedCardSource(dataSource.getYellowRedCardSource());
		
		// #################################################################################
		
		Modifiers modifiers = b.getModifiers();
		
		
		e.setGoalkeeperModifierActive(modifiers.isGoalkeeperModifierActive());
		e.setGoalkeeperVote3(modifiers.getGoalkeeperVote3());
		e.setGoalkeeperVote3half(modifiers.getGoalkeeperVote3half());
		e.setGoalkeeperVote4(modifiers.getGoalkeeperVote4());
		e.setGoalkeeperVote4half(modifiers.getGoalkeeperVote4half());
		e.setGoalkeeperVote5(modifiers.getGoalkeeperVote5());
		e.setGoalkeeperVote5half(modifiers.getGoalkeeperVote5half());
		e.setGoalkeeperVote6(modifiers.getGoalkeeperVote6());
		e.setGoalkeeperVote6half(modifiers.getGoalkeeperVote6half());
		e.setGoalkeeperVote7(modifiers.getGoalkeeperVote7());
		e.setGoalkeeperVote7half(modifiers.getGoalkeeperVote7half());
		e.setGoalkeeperVote8(modifiers.getGoalkeeperVote8());
		e.setGoalkeeperVote8half(modifiers.getGoalkeeperVote8half());
		e.setGoalkeeperVote9(modifiers.getGoalkeeperVote9());
		
		e.setDefenderModifierActive(modifiers.isDefenderModifierActive());
		e.setDefenderAvgVote6(modifiers.getDefenderAvgVote6());
		e.setDefenderAvgVote6half(modifiers.getDefenderAvgVote6half());
		e.setDefenderAvgVote7(modifiers.getDefenderAvgVote7());
		
		e.setMiddlefielderModifierActive(modifiers.isMiddlefielderModifierActive());
		e.setMiddlefielderNear0(modifiers.getMiddlefielderNear0());
		e.setMiddlefielderOver2(modifiers.getMiddlefielderOver2());
		e.setMiddlefielderOver4(modifiers.getMiddlefielderOver4());
		e.setMiddlefielderOver6(modifiers.getMiddlefielderOver6());
		e.setMiddlefielderOver8(modifiers.getMiddlefielderOver8());
		e.setMiddlefielderUnderMinus2(modifiers.getMiddlefielderUnderMinus2());					
		e.setMiddlefielderUnderMinus4(modifiers.getMiddlefielderUnderMinus4());
		e.setMiddlefielderUnderMinus6(modifiers.getMiddlefielderUnderMinus6());
		e.setMiddlefielderUnderMinus8(modifiers.getMiddlefielderUnderMinus8());
		
		e.setStrikerModifierActive(	modifiers.isStrikerModifierActive());
		e.setStrikerVote6(modifiers.getStrikerVote6());
		e.setStrikerVote6half(modifiers.getStrikerVote6half());
		e.setStrikerVote7(modifiers.getStrikerVote7());
		e.setStrikerVote7half(modifiers.getStrikerVote7half());
		e.setStrikerVote8(modifiers.getStrikerVote8());
		
		e.setPerformanceModifierActive(modifiers.isPerformanceModifierActive());
		e.setPerformance0(modifiers.getPerformance0());
		e.setPerformance1(modifiers.getPerformance1());
		e.setPerformance2(modifiers.getPerformance2());
		e.setPerformance3(modifiers.getPerformance3());
		e.setPerformance4(modifiers.getPerformance4());
		e.setPerformance5(modifiers.getPerformance5());
		e.setPerformance6(modifiers.getPerformance6());
		e.setPerformance7(modifiers.getPerformance7());
		e.setPerformance8(modifiers.getPerformance8());
		e.setPerformance9(modifiers.getPerformance9());
		e.setPerformance10(modifiers.getPerformance10());
		e.setPerformance11(modifiers.getPerformance11());
		
		e.setFairPlayModifierActive(modifiers.isFairPlayModifierActive());
		e.setFairPlay(modifiers.getFairPlay());
		
		
		// #################################################################################

		Points points = b.getPoints();
		
		List<Double> formulaUnoPoints = points.getFormulaUnoPoints();
		String formulaUnoPointsString = "";
		for (Double point : formulaUnoPoints)
			formulaUnoPointsString += point + "-";
		e.setFormulaUnoPoints(formulaUnoPointsString);
		
		List<Double> goalPoints = points.getGoalPoints();
		String goalPointsString = "";
		for (Double point : goalPoints)
			goalPointsString += point + "-";
		e.setGoalPoints(goalPointsString);
		
		e.setControllaPareggioActive(points.isControllaPareggioActive());
		e.setControllaPareggio(points.getControllaPareggio());
		
		e.setDifferenzaPuntiActive(points.isDifferenzaPuntiActive());
		e.setDifferenzaPunti(points.getDifferenzaPunti());
		
		e.setFasciaConIntornoActive(points.isFasciaConIntornoActive());
		e.setFasciaConIntorno(points.getFasciaConIntorno());

		e.setIntornoActive(points.isIntornoActive());
		e.setIntorno(points.getIntorno());
		
		e.setAutogolActive(points.getAutogolActive());
		e.setAutogol(points.getAutogol());
		
		e.setPortiereImbattutoActive(points.isPortiereImbattutoActive());
		e.setPortiereImbattuto(points.getPortiereImbattuto());
		
		// #################################################################################

		Substitutions substitutions = b.getSubstitutions();
		
		e.setSubstitutionNumber(substitutions.getSubstitutionNumber());
		e.setSubstitutionMode(substitutions.getSubstitutionMode());
		
		String maxOfficeVotesString;
		if (substitutions.getMaxOfficeVotes().equals(MaxOfficeVotesEnum.TILL_SUBSTITUTIONS))
			maxOfficeVotesString = "TILL_SUBSTITUTIONS";
		else //	if (substitutions.getMaxOfficeVotes().equals(MaxOfficeVotesEnum.TILL_ALL))
			maxOfficeVotesString = "TILL_ALL";
		e.setMaxOfficeVotes(maxOfficeVotesString);
		
		e.setGoalkeeperPlayerOfficeVoteActive(substitutions.isGoalkeeperPlayerOfficeVoteActive());
		e.setGoalkeeperPlayerOfficeVote(substitutions.getGoalkeeperPlayerOfficeVote());
		e.setMovementsPlayerOfficeVoteActive(substitutions.isMovementsPlayerOfficeVoteActive());
		e.setMovementsPlayerOfficeVote(substitutions.getMovementsPlayerOfficeVote());
		e.setYellowCardSvOfficeVoteActive(substitutions.isYellowCardSvOfficeVoteActive());
		e.setYellowCardSvOfficeVote(substitutions.getYellowCardSvOfficeVote());
		
		e.setType(b.getType().name());
		e.setHomeBonusActive(b.getCompetitionRules().isHomeBonusActive());
		e.setHomeBonus(b.getCompetitionRules().getHomeBonus());
		e.setBinding(b.getCompetitionRules().getBinding());
		if (b.getCompetitionRules().getPostponementBehaviour()!= null)
			e.setPostponementBehaviour(b.getCompetitionRules().getPostponementBehaviour().name());
		
		return e;
	}




//	@PersistenceContext
//	private EntityManager entityManager;
//
//		entityManager.detach(rules);
//		Rules rules = rulesRepo.findByLeagueAndBasic(league, true);
//		rules.setId(0);
//		rules.setCompetition( competition );
//		rules.setHomeBonusActive( rulesBean.getCompetitionRules().isHomeBonusActive());
//		rules.setHomeBonus( rulesBean.getCompetitionRules().getHomeBonus());
//	}
	
	
	public Rules retrieveRulesEnt(String competitionShortName, String leagueShortName, String username) {
			
		Competition competition = leagueDao.findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		Rules e = rulesRepo.findByCompetitionAndType(competition, RulesType.REAL.name());
		return e;
			
	}
	
//	@Cacheable("rules")
	public RulesBean retrieveRules(String competitionShortName, String leagueShortName, RulesType type, String username) {
		
		Competition competition = leagueDao.findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		Rules e = rulesRepo.findByCompetitionAndType(competition, type.name());
		
		RulesBean bean = new RulesBean();
		
		BonusMalus bonusMalus = new BonusMalus();
		bean.setBonusMalus(bonusMalus);
		
		bonusMalus.getAutogoal().put(RoleEnum.P, e.getAutogoalP());
		bonusMalus.getEvenGoal().put(RoleEnum.P, e.getEvenGoalP());
		bonusMalus.getMissedPenalty().put(RoleEnum.P, e.getMissedPenaltyP());
		bonusMalus.getMovementAssist().put(RoleEnum.P, e.getMovementAssistP());
		bonusMalus.getRedCard().put(RoleEnum.P, e.getRedCardP());
		bonusMalus.getScoredGoal().put(RoleEnum.P, e.getScoredGoalP());

		bonusMalus.getSavedPenalty().put(RoleEnum.P, e.getSavedPenaltyP());
		bonusMalus.getScoredPenalty().put(RoleEnum.P, e.getScoredPenaltyP());
		bonusMalus.getStationaryAssist().put(RoleEnum.P, e.getStationaryAssistP());
		bonusMalus.getTakenGoal().put(RoleEnum.P, e.getTakenGoalP());
		bonusMalus.getWinGoal().put(RoleEnum.P, e.getWinGoalP());
		bonusMalus.getYellowCard().put(RoleEnum.P, e.getYellowCardP());
		
		
		bonusMalus.getAutogoal().put(RoleEnum.D, e.getAutogoalD());
		bonusMalus.getEvenGoal().put(RoleEnum.D, e.getEvenGoalD());
		bonusMalus.getMissedPenalty().put(RoleEnum.D, e.getMissedPenaltyD());
		bonusMalus.getMovementAssist().put(RoleEnum.D, e.getMovementAssistD());
		bonusMalus.getRedCard().put(RoleEnum.D, e.getRedCardD());
		bonusMalus.getScoredGoal().put(RoleEnum.D, e.getScoredGoalD());

		bonusMalus.getSavedPenalty().put(RoleEnum.D, e.getSavedPenaltyD());
		bonusMalus.getScoredPenalty().put(RoleEnum.D, e.getScoredPenaltyD());
		bonusMalus.getStationaryAssist().put(RoleEnum.D, e.getStationaryAssistD());
		bonusMalus.getTakenGoal().put(RoleEnum.D, e.getTakenGoalD());
		bonusMalus.getWinGoal().put(RoleEnum.D, e.getWinGoalD());
		bonusMalus.getYellowCard().put(RoleEnum.D, e.getYellowCardD());
		
		
		
		bonusMalus.getAutogoal().put(RoleEnum.C, e.getAutogoalC());
		bonusMalus.getEvenGoal().put(RoleEnum.C, e.getEvenGoalC());
		bonusMalus.getMissedPenalty().put(RoleEnum.C, e.getMissedPenaltyC());
		bonusMalus.getMovementAssist().put(RoleEnum.C, e.getMovementAssistC());
		bonusMalus.getRedCard().put(RoleEnum.C, e.getRedCardC());
		bonusMalus.getScoredGoal().put(RoleEnum.C, e.getScoredGoalC());

		bonusMalus.getSavedPenalty().put(RoleEnum.C, e.getSavedPenaltyC());
		bonusMalus.getScoredPenalty().put(RoleEnum.C, e.getScoredPenaltyC());
		bonusMalus.getStationaryAssist().put(RoleEnum.C, e.getStationaryAssistC());
		bonusMalus.getTakenGoal().put(RoleEnum.C, e.getTakenGoalC());
		bonusMalus.getWinGoal().put(RoleEnum.C, e.getWinGoalC());
		bonusMalus.getYellowCard().put(RoleEnum.C, e.getYellowCardC());
		
		
		
		bonusMalus.getAutogoal().put(RoleEnum.A, e.getAutogoalA());
		bonusMalus.getEvenGoal().put(RoleEnum.A, e.getEvenGoalA());
		bonusMalus.getMissedPenalty().put(RoleEnum.A, e.getMissedPenaltyA());
		bonusMalus.getMovementAssist().put(RoleEnum.A, e.getMovementAssistA());
		bonusMalus.getRedCard().put(RoleEnum.A, e.getRedCardA());
		bonusMalus.getScoredGoal().put(RoleEnum.A, e.getScoredGoalA());

		bonusMalus.getSavedPenalty().put(RoleEnum.A, e.getSavedPenaltyA());
		bonusMalus.getScoredPenalty().put(RoleEnum.A, e.getScoredPenaltyA());
		bonusMalus.getStationaryAssist().put(RoleEnum.A, e.getStationaryAssistA());
		bonusMalus.getTakenGoal().put(RoleEnum.A, e.getTakenGoalA());
		bonusMalus.getWinGoal().put(RoleEnum.A, e.getWinGoalA());
		bonusMalus.getYellowCard().put(RoleEnum.A, e.getYellowCardA());
		
		
		// #################################################################################

		DataSources dataSource = new DataSources();
		bean.setDataSource(dataSource);
		
		dataSource.setBonusMalusSource(VotesSourceEnum.valueOf(e.getBonusMalusSource()));
		dataSource.setVotesSource(VotesSourceEnum.valueOf(e.getVotesSource()));
		dataSource.setYellowRedCardSource(e.getYellowRedCardSource());
		
		// #################################################################################
		
		Modifiers modifiers = new Modifiers();
		bean.setModifiers(modifiers);
		
		modifiers.setGoalkeeperModifierActive(e.isGoalkeeperModifierActive());
		modifiers.setGoalkeeperVote3(e.getGoalkeeperVote3());
		modifiers.setGoalkeeperVote3half(e.getGoalkeeperVote3half());
		modifiers.setGoalkeeperVote4(e.getGoalkeeperVote4());
		modifiers.setGoalkeeperVote4half(e.getGoalkeeperVote4half());
		modifiers.setGoalkeeperVote5(e.getGoalkeeperVote5());
		modifiers.setGoalkeeperVote5half(e.getGoalkeeperVote5half());
		modifiers.setGoalkeeperVote6(e.getGoalkeeperVote6());
		modifiers.setGoalkeeperVote6half(e.getGoalkeeperVote6half());
		modifiers.setGoalkeeperVote7(e.getGoalkeeperVote7());
		modifiers.setGoalkeeperVote7half(e.getGoalkeeperVote7half());
		modifiers.setGoalkeeperVote8(e.getGoalkeeperVote8());
		modifiers.setGoalkeeperVote8half(e.getGoalkeeperVote8half());
		modifiers.setGoalkeeperVote9(e.getGoalkeeperVote9());
		
		modifiers.setDefenderModifierActive(e.isDefenderModifierActive());
		modifiers.setDefenderAvgVote6(e.getDefenderAvgVote6());
		modifiers.setDefenderAvgVote6half(e.getDefenderAvgVote6half());
		modifiers.setDefenderAvgVote7(e.getDefenderAvgVote7());
		
		modifiers.setMiddlefielderModifierActive(e.isMiddlefielderModifierActive());
		modifiers.setMiddlefielderNear0(e.getMiddlefielderNear0());
		modifiers.setMiddlefielderOver2(e.getMiddlefielderOver2());
		modifiers.setMiddlefielderOver4(e.getMiddlefielderOver4());
		modifiers.setMiddlefielderOver6(e.getMiddlefielderOver6());
		modifiers.setMiddlefielderOver8(e.getMiddlefielderOver8());
		modifiers.setMiddlefielderUnderMinus2(e.getMiddlefielderUnderMinus2());					
		modifiers.setMiddlefielderUnderMinus4(e.getMiddlefielderUnderMinus4());
		modifiers.setMiddlefielderUnderMinus6(e.getMiddlefielderUnderMinus6());
		modifiers.setMiddlefielderUnderMinus8(e.getMiddlefielderUnderMinus8());
		
		modifiers.setStrikerModifierActive(	e.isStrikerModifierActive());
		modifiers.setStrikerVote6(e.getStrikerVote6());
		modifiers.setStrikerVote6half(e.getStrikerVote6half());
		modifiers.setStrikerVote7(e.getStrikerVote7());
		modifiers.setStrikerVote7half(e.getStrikerVote7half());
		modifiers.setStrikerVote8(e.getStrikerVote8());
		
		modifiers.setPerformanceModifierActive(e.isPerformanceModifierActive());
		modifiers.setPerformance0(e.getPerformance0());
		modifiers.setPerformance1(e.getPerformance1());
		modifiers.setPerformance2(e.getPerformance2());
		modifiers.setPerformance3(e.getPerformance3());
		modifiers.setPerformance4(e.getPerformance4());
		modifiers.setPerformance5(e.getPerformance5());
		modifiers.setPerformance6(e.getPerformance6());
		modifiers.setPerformance7(e.getPerformance7());
		modifiers.setPerformance8(e.getPerformance8());
		modifiers.setPerformance9(e.getPerformance9());
		modifiers.setPerformance10(e.getPerformance10());
		modifiers.setPerformance11(e.getPerformance11());
		
		modifiers.setFairPlayModifierActive(e.isFairPlayModifierActive());
		modifiers.setFairPlay(e.getFairPlay());
		
		// #################################################################################

		Points points = new Points();
		bean.setPoints(points);
		
		List<Double> formulaUnoPoints = new ArrayList<Double>();
		String formulaUnoPointsString = e.getFormulaUnoPoints();
		String[] split = formulaUnoPointsString.split("-");
		
		for (int i = 0; i < split.length; i++) {
			String pointString = split[i];
			Double point = Double.valueOf(pointString);
			formulaUnoPoints.add(point);
		}
		points.setFormulaUnoPoints(formulaUnoPoints);
		
		List<Double> goalPoints = new ArrayList<Double>();
		String goalPointsString = e.getGoalPoints();
		split = goalPointsString.split("-");
		
		for (int i = 0; i < split.length; i++) {
			String pointString = split[i];
			Double point = Double.valueOf(pointString);
			goalPoints.add(point);
		}
		points.setGoalPoints(goalPoints);
		
		points.setControllaPareggioActive(e.getControllaPareggioActive());
		points.setControllaPareggio(e.getControllaPareggio());
		
		points.setDifferenzaPuntiActive(e.getDifferenzaPuntiActive());
		points.setDifferenzaPunti(e.getDifferenzaPunti());
		
		points.setFasciaConIntornoActive(e.getFasciaConIntornoActive());
		points.setFasciaConIntorno(e.getFasciaConIntorno());

		points.setIntornoActive(e.getIntornoActive());
		points.setIntorno(e.getIntorno());
		
		points.setAutogolActive(e.getAutogolActive());
		points.setAutogol(e.getAutogol());
		
		points.setPortiereImbattutoActive(e.getPortiereImbattutoActive());
		points.setPortiereImbattuto(e.getPortiereImbattuto());

		// #################################################################################
		
		Substitutions substitution = new Substitutions();
		bean.setSubstitutions(substitution);

		substitution.setSubstitutionNumber(e.getSubstitutionNumber());
		substitution.setSubstitutionMode(e.getSubstitutionMode());
		
		MaxOfficeVotesEnum maxOfficeVotesEnum;
		if (e.getMaxOfficeVotes()!= null) {
			if (e.getMaxOfficeVotes().equals("TILL_SUBSTITUTIONS"))
				maxOfficeVotesEnum = MaxOfficeVotesEnum.TILL_SUBSTITUTIONS;
			else //	if (e.getMaxOfficeVotes().equals("TILL_ALL"))
				maxOfficeVotesEnum = MaxOfficeVotesEnum.TILL_ALL;
			substitution.setMaxOfficeVotes(maxOfficeVotesEnum);
		}
		
		substitution.setGoalkeeperPlayerOfficeVoteActive(e.getGoalkeeperPlayerOfficeVoteActive());
		substitution.setGoalkeeperPlayerOfficeVote(e.getGoalkeeperPlayerOfficeVote());
		substitution.setMovementsPlayerOfficeVoteActive(e.getMovementsPlayerOfficeVoteActive());
		substitution.setMovementsPlayerOfficeVote(e.getMovementsPlayerOfficeVote());
		substitution.setYellowCardSvOfficeVoteActive(e.getYellowCardSvOfficeVoteActive());
		substitution.setYellowCardSvOfficeVote(e.getYellowCardSvOfficeVote());
		
		// #################################################################################
		
		CompetitionRules competitionRules = new CompetitionRules();
		competitionRules.setHomeBonus(e.getHomeBonus());
		competitionRules.setHomeBonusActive(e.getHomeBonusActive());
		competitionRules.setBinding(e.getBinding());
		if (e.getPostponementBehaviour() != null)
			competitionRules.setPostponementBehaviour(PostponementBehaviourEnum.valueOf(e.getPostponementBehaviour()));
		
		bean.setCompetitionRules(competitionRules);
		
		bean.setType(RulesType.valueOf(e.getType()));
		
		return bean;
	}
	
	
	//@Transactional
	public RulesBean saveRulesForCompetition(RulesBean rulesBean, String competitionShortName, String leagueShortName, String username) {
		
		
		Boolean alreadyExistRules = existRulesForCompetition(competitionShortName, leagueShortName, username);
		if (alreadyExistRules)
			return null;
		
		Rules rules = populateRules(rulesBean, leagueShortName, username);

		League league = leagueDao.findByShortNameEnt(leagueShortName, username);
		rules.setLeague(league);
		
		Competition competition = leagueDao.findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		rules.setCompetition(competition);
		
		rules.setHomeBonusActive(rulesBean.getCompetitionRules().isHomeBonusActive());
		rules.setHomeBonus(rulesBean.getCompetitionRules().getHomeBonus());
		
		
		rulesRepo.save(rules);
		
		RulesBean bean = new RulesBean();
		return bean; 
		
	}
	
	
	public RulesBean updateRulesForCompetition(RulesBean rulesBean, String competitionShortName, String leagueShortName, String username) {
		
		
		
		Rules newRules = populateRules(rulesBean, leagueShortName, username);

		League league = leagueDao.findByShortNameEnt(leagueShortName, username);
		newRules.setLeague(league);
		
		Competition competition = leagueDao.findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		newRules.setCompetition(competition);

		Rules dbRules = rulesRepo.findByCompetitionAndType(competition, newRules.getType());
		newRules.setId(dbRules.getId());
		rulesRepo.save(newRules);
		
		
		return rulesBean; 
		
	}
	
	
	public void saveSerieAToCompetitionBinding(SeasonBean season, String leagueShortName, String competitionShortName, String username) {
		String seasonDayBinding = "";
		
		Rules rules = retrieveRulesEnt(competitionShortName, leagueShortName,  username);
		
		for (SeasonDayBean sd : season.getSeasonDays()) {
			seasonDayBinding += sd.getNameNumber() + "-" + sd.getSerieANumber() + ",";
		}
		
		rules.setBinding(seasonDayBinding);
		
		rulesRepo.save(rules);
		
	}
	
	public Map<Integer, Integer> findSerieAToCompetitionBinding(String leagueShortName, String competitionShortName, String username) {
		RulesBean rules = retrieveRules(competitionShortName, leagueShortName, RulesType.REAL, username);
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		String[] pairs = rules.getCompetitionRules().getBinding().split(",");
		for (int i = 0; i < pairs.length; i++) {
			String[] seasonDays = pairs[i].split("-");
			Integer comp = Integer.valueOf(seasonDays[0]);
			Integer serieA = Integer.valueOf(seasonDays[1]);
			map.put(serieA, comp);
		}
		
		return map;
	}
	
	
	public Map<Integer, Integer> findCompetitionToSerieABinding(String leagueShortName, String competitionShortName, String username) {
		RulesBean rules = retrieveRules(competitionShortName, leagueShortName, RulesType.REAL, username);
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		String[] pairs = rules.getCompetitionRules().getBinding().split(",");
		for (int i = 0; i < pairs.length; i++) {
			String[] seasonDays = pairs[i].split("-");
			Integer serieA = Integer.valueOf(seasonDays[0]);
			Integer comp = Integer.valueOf(seasonDays[1]);
			map.put(serieA, comp);
		}
		
		return map;
		
		
	}

	public void integrateRules(IntegrateRulesReq req, String username) {
		String competitionShortName = req.getCompetitionShortName();
		String leagueShortName = req.getLeagueShortName();
		String postponementBehaviour = req.getPostponementBehaviour();
		Boolean autogolActive = req.getAutogolActive();
		Double autogol = req.getAutogol();
		String maxOfficeVoteBehaviour = req.getMaxOfficeVoteBehaviour();
		
		Competition competition = leagueDao.findCompetitionByShortNameAndLeagueEnt(competitionShortName, leagueShortName, username);
		Rules ent = rulesRepo.findByCompetitionAndType(competition, RulesType.REAL.name());
		ent.setPostponementBehaviour(postponementBehaviour);
		ent.setAutogol(autogol);
		ent.setAutogolActive(autogolActive);
		ent.setMaxOfficeVotes(maxOfficeVoteBehaviour);
		
		rulesRepo.save(ent);
		
		createCustomRules(ent);
	}

	@PersistenceContext
	private EntityManager entityManager;
	
	private void createCustomRules(Rules ent) {
		//Crea copia delle regole custom
		Rules customRulesEnt = rulesRepo.findByCompetitionAndType(ent.getCompetition(), RulesType.CUSTOM.name());
		if (customRulesEnt != null)
			rulesRepo.delete(customRulesEnt);
		entityManager.detach(ent);
		ent.setId(0);
		ent.setType(RulesType.CUSTOM.name());
		rulesRepo.save(ent);
	}
	
	
	
}
