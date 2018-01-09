package app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.entity.Competition;
import app.dao.entity.League;
import app.dao.entity.Rules;
import app.dao.entity.User;
import app.dao.entity.Vote;
import app.logic._0_credentialsSaver.LeagueBean;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.RoleEnum;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic._0_votesDownloader_0_rulesDownloader.model.BonusMalus;
import app.logic._0_votesDownloader_0_rulesDownloader.model.DataSources;
import app.logic._0_votesDownloader_0_rulesDownloader.model.MaxOfficeVotesEnum;
import app.logic._0_votesDownloader_0_rulesDownloader.model.Modifiers;
import app.logic._0_votesDownloader_0_rulesDownloader.model.Points;
import app.logic._0_votesDownloader_0_rulesDownloader.model.RulesBean;
import app.logic._0_votesDownloader_0_rulesDownloader.model.Substitutions;

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

	public RulesBean findByShortName(String leagueShortName, String username) {
		League league = leagueDao.findByShortNameEnt(leagueShortName, username);
		Rules rules = rulesRepo.findByLeague(league);
		if (rules == null)
			return null;

		RulesBean b = new RulesBean();
		
		return b;
	}



	public RulesBean saveRulesForLeague(RulesBean b, String leagueShortName, String username) {
		Rules e = new Rules();
		

		
		League league = leagueDao.findByShortNameEnt(leagueShortName, username);
		Rules dbRules = rulesRepo.findByLeague(league);
		if (dbRules != null)
			return null;
		
		
		e.setLeague(league);
		
		
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
			maxOfficeVotesString = "S";
		else //	if (substitutions.getMaxOfficeVotes().equals(MaxOfficeVotesEnum.TILL_ALL))
			maxOfficeVotesString = "A";
		e.setMaxOfficeVotes(maxOfficeVotesString);
		
		e.setGoalkeeperPlayerOfficeVoteActive(substitutions.isGoalkeeperPlayerOfficeVoteActive());
		e.setGoalkeeperPlayerOfficeVote(substitutions.getGoalkeeperPlayerOfficeVote());
		e.setMovementsPlayerOfficeVoteActive(substitutions.isMovementsPlayerOfficeVoteActive());
		e.setMovementsPlayerOfficeVote(substitutions.getMovementsPlayerOfficeVote());
		e.setYellowCardSvOfficeVoteActive(substitutions.isYellowCardSvOfficeVoteActive());
		e.setYellowCardOfficeVote(substitutions.getYellowCardSvOfficeVote());
		
		
		e.setBasic(true);
		
		rulesRepo.save(e);
		
		return b;
	}




	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void saveRulesForCompetition(RulesBean rulesBean, String competitionName, String leagueShortName, String username) {
		League league = leagueDao.findByShortNameEnt(leagueShortName, username);
		
		
		Rules rules = rulesRepo.findByLeagueAndBasic(league, true);

		Competition competition = leagueDao.findCompetitionByNameAndLeagueEnt(competitionName, leagueShortName, username);
		
		entityManager.detach(rules);
		
		rules.setId(0);
		rules.setCompetition( competition );
		rules.setHomeBonusActive( rulesBean.getCompetitionRules().isHomeBonusActive());
		rules.setHomeBonus( rulesBean.getCompetitionRules().getHomeBonus());
		rules.setBasic(false);

		rules = entityManager.merge(rules);
		
		
//		rulesRepo.save(rules);
		
		
		
	}

	
	
	
}
