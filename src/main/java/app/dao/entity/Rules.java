package app.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import app.logic._0_rulesDownloader.model.SubstitutionsModeEnum;

@Entity
public class Rules {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	private Competition competition;

	@ManyToOne
	private League league;
	
	private String binding;
	
	private String type;
	
	// BonusMalus
	private Double redCardP;
	private Double yellowCardP;
	private Double scoredGoalP;
	private Double scoredPenaltyP;
	private Double movementAssistP;
	private Double stationaryAssistP;
	private Double autogoalP;
	private Double missedPenaltyP;
	private Double savedPenaltyP;
	private Double takenGoalP;
	private Double evenGoalP;
	private Double winGoalP;

	private Double redCardD;
	private Double yellowCardD;
	private Double scoredGoalD;
	private Double scoredPenaltyD;
	private Double movementAssistD;
	private Double stationaryAssistD;
	private Double autogoalD;
	private Double missedPenaltyD;
	private Double savedPenaltyD;
	private Double takenGoalD;
	private Double evenGoalD;
	private Double winGoalD;
	
	private Double redCardC;
	private Double yellowCardC;
	private Double scoredGoalC;
	private Double scoredPenaltyC;
	private Double movementAssistC;
	private Double stationaryAssistC;
	private Double autogoalC;
	private Double missedPenaltyC;
	private Double savedPenaltyC;
	private Double takenGoalC;
	private Double evenGoalC;
	private Double winGoalC;
	
	private Double redCardA;
	private Double yellowCardA;
	private Double scoredGoalA;
	private Double scoredPenaltyA;
	private Double movementAssistA;
	private Double stationaryAssistA;
	private Double autogoalA;
	private Double missedPenaltyA;
	private Double savedPenaltyA;
	private Double takenGoalA;
	private Double evenGoalA;
	private Double winGoalA;
	
	
	
	// Datasource
	private String votesSource;				//Fantagazzetta, Italia, Statistico
	private String bonusMalusSource;		//FISSO A Fantagazzetta
	private String yellowRedCardSource;		//Fantagazzetta o Giudice Sportivo
	
	// Points
	private String goalPoints;
	private String formulaUnoPoints;
	
	private Boolean fasciaConIntornoActive;
	private Double fasciaConIntorno;
	
	private Boolean intornoActive;
	private Double intorno;
	
	private Boolean controllaPareggioActive;
	private Double controllaPareggio;
	
	private Boolean differenzaPuntiActive;
	private Double differenzaPunti;
	
	private Boolean portiereImbattutoActive;
	private Double portiereImbattuto;
	
	private Boolean autogolActive;
	private Double autogol;
	
	// Substitution
	private Integer substitutionNumber;
	private SubstitutionsModeEnum substitutionMode;
	
	private String maxOfficeVotes;
	
	private Boolean yellowCardSvOfficeVoteActive;
	private Double yellowCardSvOfficeVote;
	
	private Boolean goalkeeperPlayerOfficeVoteActive;
	private Double goalkeeperPlayerOfficeVote;

	private Boolean movementsPlayerOfficeVoteActive;
	private Double movementsPlayerOfficeVote;
	
	
	// Modifiers
	private boolean goalkeeperModifierActive;
	private Double goalkeeperVote3;
	private Double goalkeeperVote3half;
	private Double goalkeeperVote4;
	private Double goalkeeperVote4half;
	private Double goalkeeperVote5;
	private Double goalkeeperVote5half;
	private Double goalkeeperVote6;
	private Double goalkeeperVote6half;
	private Double goalkeeperVote7;
	private Double goalkeeperVote7half;
	private Double goalkeeperVote8;
	private Double goalkeeperVote8half;
	private Double goalkeeperVote9;
	
	
	private boolean defenderModifierActive;
	private Double defenderAvgVote6;
	private Double defenderAvgVote6half;
	private Double defenderAvgVote7;
	
	private boolean middlefielderModifierActive;
	private Double middlefielderUnderMinus8;
	private Double middlefielderUnderMinus6;
	private Double middlefielderUnderMinus4;
	private Double middlefielderUnderMinus2;
	private Double middlefielderNear0;
	private Double middlefielderOver2;
	private Double middlefielderOver4;
	private Double middlefielderOver6;
	private Double middlefielderOver8;
	
	
	private boolean strikerModifierActive;
	private Double strikerVote6;
	private Double strikerVote6half;
	private Double strikerVote7;
	private Double strikerVote7half;
	private Double strikerVote8;
	
	private boolean performanceModifierActive;
	private Double performance11;
	private Double performance10;
	private Double performance9;
	private Double performance8;
	private Double performance7;
	private Double performance6;
	private Double performance5;
	private Double performance4;
	private Double performance3;
	private Double performance2;
	private Double performance1;
	private Double performance0;
	
	private boolean fairPlayModifierActive;
	private Double fairPlay;
	
	// CompetitionRules
	private Boolean homeBonusActive;
	private Double homeBonus;
	private String postponementBehaviour;
	
	public Rules() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public Double getRedCardP() {
		return redCardP;
	}

	public void setRedCardP(Double redCardP) {
		this.redCardP = redCardP;
	}

	public Double getYellowCardP() {
		return yellowCardP;
	}

	public void setYellowCardP(Double yellowCardP) {
		this.yellowCardP = yellowCardP;
	}

	public Double getScoredGoalP() {
		return scoredGoalP;
	}

	public void setScoredGoalP(Double scoredGoalP) {
		this.scoredGoalP = scoredGoalP;
	}

	public Double getScoredPenaltyP() {
		return scoredPenaltyP;
	}

	public void setScoredPenaltyP(Double scoredPenaltyP) {
		this.scoredPenaltyP = scoredPenaltyP;
	}

	public Double getMovementAssistP() {
		return movementAssistP;
	}

	public void setMovementAssistP(Double movementAssistP) {
		this.movementAssistP = movementAssistP;
	}

	public Double getStationaryAssistP() {
		return stationaryAssistP;
	}

	public void setStationaryAssistP(Double stationaryAssistP) {
		this.stationaryAssistP = stationaryAssistP;
	}

	public Double getAutogoalP() {
		return autogoalP;
	}

	public void setAutogoalP(Double autogoalP) {
		this.autogoalP = autogoalP;
	}

	public Double getMissedPenaltyP() {
		return missedPenaltyP;
	}

	public void setMissedPenaltyP(Double missedPenaltyP) {
		this.missedPenaltyP = missedPenaltyP;
	}

	public Double getSavedPenaltyP() {
		return savedPenaltyP;
	}

	public void setSavedPenaltyP(Double savedPenaltyP) {
		this.savedPenaltyP = savedPenaltyP;
	}

	public Double getTakenGoalP() {
		return takenGoalP;
	}

	public void setTakenGoalP(Double takenGoalP) {
		this.takenGoalP = takenGoalP;
	}

	public Double getEvenGoalP() {
		return evenGoalP;
	}

	public void setEvenGoalP(Double evenGoalP) {
		this.evenGoalP = evenGoalP;
	}

	public Double getWinGoalP() {
		return winGoalP;
	}

	public void setWinGoalP(Double winGoalP) {
		this.winGoalP = winGoalP;
	}

	public Double getRedCardD() {
		return redCardD;
	}

	public void setRedCardD(Double redCardD) {
		this.redCardD = redCardD;
	}

	public Double getYellowCardD() {
		return yellowCardD;
	}

	public void setYellowCardD(Double yellowCardD) {
		this.yellowCardD = yellowCardD;
	}

	public Double getScoredGoalD() {
		return scoredGoalD;
	}

	public void setScoredGoalD(Double scoredGoalD) {
		this.scoredGoalD = scoredGoalD;
	}

	public Double getScoredPenaltyD() {
		return scoredPenaltyD;
	}

	public void setScoredPenaltyD(Double scoredPenaltyD) {
		this.scoredPenaltyD = scoredPenaltyD;
	}

	public Double getMovementAssistD() {
		return movementAssistD;
	}

	public void setMovementAssistD(Double movementAssistD) {
		this.movementAssistD = movementAssistD;
	}

	public Double getStationaryAssistD() {
		return stationaryAssistD;
	}

	public void setStationaryAssistD(Double stationaryAssistD) {
		this.stationaryAssistD = stationaryAssistD;
	}

	public Double getAutogoalD() {
		return autogoalD;
	}

	public void setAutogoalD(Double autogoalD) {
		this.autogoalD = autogoalD;
	}

	public Double getMissedPenaltyD() {
		return missedPenaltyD;
	}

	public void setMissedPenaltyD(Double missedPenaltyD) {
		this.missedPenaltyD = missedPenaltyD;
	}

	public Double getSavedPenaltyD() {
		return savedPenaltyD;
	}

	public void setSavedPenaltyD(Double savedPenaltyD) {
		this.savedPenaltyD = savedPenaltyD;
	}

	public Double getTakenGoalD() {
		return takenGoalD;
	}

	public void setTakenGoalD(Double takenGoalD) {
		this.takenGoalD = takenGoalD;
	}

	public Double getEvenGoalD() {
		return evenGoalD;
	}

	public void setEvenGoalD(Double evenGoalD) {
		this.evenGoalD = evenGoalD;
	}

	public Double getWinGoalD() {
		return winGoalD;
	}

	public void setWinGoalD(Double winGoalD) {
		this.winGoalD = winGoalD;
	}

	public Double getRedCardC() {
		return redCardC;
	}

	public void setRedCardC(Double redCardC) {
		this.redCardC = redCardC;
	}

	public Double getYellowCardC() {
		return yellowCardC;
	}

	public void setYellowCardC(Double yellowCardC) {
		this.yellowCardC = yellowCardC;
	}

	public Double getScoredGoalC() {
		return scoredGoalC;
	}

	public void setScoredGoalC(Double scoredGoalC) {
		this.scoredGoalC = scoredGoalC;
	}

	public Double getScoredPenaltyC() {
		return scoredPenaltyC;
	}

	public void setScoredPenaltyC(Double scoredPenaltyC) {
		this.scoredPenaltyC = scoredPenaltyC;
	}

	public Double getMovementAssistC() {
		return movementAssistC;
	}

	public void setMovementAssistC(Double movementAssistC) {
		this.movementAssistC = movementAssistC;
	}

	public Double getStationaryAssistC() {
		return stationaryAssistC;
	}

	public void setStationaryAssistC(Double stationaryAssistC) {
		this.stationaryAssistC = stationaryAssistC;
	}

	public Double getAutogoalC() {
		return autogoalC;
	}

	public void setAutogoalC(Double autogoalC) {
		this.autogoalC = autogoalC;
	}

	public Double getMissedPenaltyC() {
		return missedPenaltyC;
	}

	public void setMissedPenaltyC(Double missedPenaltyC) {
		this.missedPenaltyC = missedPenaltyC;
	}

	public Double getSavedPenaltyC() {
		return savedPenaltyC;
	}

	public void setSavedPenaltyC(Double savedPenaltyC) {
		this.savedPenaltyC = savedPenaltyC;
	}

	public Double getTakenGoalC() {
		return takenGoalC;
	}

	public void setTakenGoalC(Double takenGoalC) {
		this.takenGoalC = takenGoalC;
	}

	public Double getEvenGoalC() {
		return evenGoalC;
	}

	public void setEvenGoalC(Double evenGoalC) {
		this.evenGoalC = evenGoalC;
	}

	public Double getWinGoalC() {
		return winGoalC;
	}

	public void setWinGoalC(Double winGoalC) {
		this.winGoalC = winGoalC;
	}

	public Double getRedCardA() {
		return redCardA;
	}

	public void setRedCardA(Double redCardA) {
		this.redCardA = redCardA;
	}

	public Double getYellowCardA() {
		return yellowCardA;
	}

	public void setYellowCardA(Double yellowCardA) {
		this.yellowCardA = yellowCardA;
	}

	public Double getScoredGoalA() {
		return scoredGoalA;
	}

	public void setScoredGoalA(Double scoredGoalA) {
		this.scoredGoalA = scoredGoalA;
	}

	public Double getScoredPenaltyA() {
		return scoredPenaltyA;
	}

	public void setScoredPenaltyA(Double scoredPenaltyA) {
		this.scoredPenaltyA = scoredPenaltyA;
	}

	public Double getMovementAssistA() {
		return movementAssistA;
	}

	public void setMovementAssistA(Double movementAssistA) {
		this.movementAssistA = movementAssistA;
	}

	public Double getStationaryAssistA() {
		return stationaryAssistA;
	}

	public void setStationaryAssistA(Double stationaryAssistA) {
		this.stationaryAssistA = stationaryAssistA;
	}

	public Double getAutogoalA() {
		return autogoalA;
	}

	public void setAutogoalA(Double autogoalA) {
		this.autogoalA = autogoalA;
	}

	public Double getMissedPenaltyA() {
		return missedPenaltyA;
	}

	public void setMissedPenaltyA(Double missedPenaltyA) {
		this.missedPenaltyA = missedPenaltyA;
	}

	public Double getSavedPenaltyA() {
		return savedPenaltyA;
	}

	public void setSavedPenaltyA(Double savedPenaltyA) {
		this.savedPenaltyA = savedPenaltyA;
	}

	public Double getTakenGoalA() {
		return takenGoalA;
	}

	public void setTakenGoalA(Double takenGoalA) {
		this.takenGoalA = takenGoalA;
	}

	public Double getEvenGoalA() {
		return evenGoalA;
	}

	public void setEvenGoalA(Double evenGoalA) {
		this.evenGoalA = evenGoalA;
	}

	public Double getWinGoalA() {
		return winGoalA;
	}

	public void setWinGoalA(Double winGoalA) {
		this.winGoalA = winGoalA;
	}

	public String getVotesSource() {
		return votesSource;
	}

	public void setVotesSource(String votesSource) {
		this.votesSource = votesSource;
	}

	public String getGoalPoints() {
		return goalPoints;
	}

	public void setGoalPoints(String goalPoints) {
		this.goalPoints = goalPoints;
	}

	public String getFormulaUnoPoints() {
		return formulaUnoPoints;
	}

	public void setFormulaUnoPoints(String formulaUnoPoints) {
		this.formulaUnoPoints = formulaUnoPoints;
	}

	public Boolean getFasciaConIntornoActive() {
		return fasciaConIntornoActive;
	}

	public void setFasciaConIntornoActive(Boolean fasciaConIntornoActive) {
		this.fasciaConIntornoActive = fasciaConIntornoActive;
	}

	public Double getFasciaConIntorno() {
		return fasciaConIntorno;
	}

	public void setFasciaConIntorno(Double fasciaConIntorno) {
		this.fasciaConIntorno = fasciaConIntorno;
	}

	public Boolean getIntornoActive() {
		return intornoActive;
	}

	public void setIntornoActive(Boolean intornoActive) {
		this.intornoActive = intornoActive;
	}

	public Double getIntorno() {
		return intorno;
	}

	public void setIntorno(Double intorno) {
		this.intorno = intorno;
	}

	public Boolean getControllaPareggioActive() {
		return controllaPareggioActive;
	}

	public void setControllaPareggioActive(Boolean controllaPareggioActive) {
		this.controllaPareggioActive = controllaPareggioActive;
	}

	public Double getControllaPareggio() {
		return controllaPareggio;
	}

	public void setControllaPareggio(Double controllaPareggio) {
		this.controllaPareggio = controllaPareggio;
	}

	public Boolean getDifferenzaPuntiActive() {
		return differenzaPuntiActive;
	}

	public void setDifferenzaPuntiActive(Boolean differenzaPuntiActive) {
		this.differenzaPuntiActive = differenzaPuntiActive;
	}

	public Double getDifferenzaPunti() {
		return differenzaPunti;
	}

	public void setDifferenzaPunti(Double differenzaPunti) {
		this.differenzaPunti = differenzaPunti;
	}

	public Boolean getPortiereImbattutoActive() {
		return portiereImbattutoActive;
	}

	public void setPortiereImbattutoActive(Boolean portiereImbattutoActive) {
		this.portiereImbattutoActive = portiereImbattutoActive;
	}

	public Double getPortiereImbattuto() {
		return portiereImbattuto;
	}

	public void setPortiereImbattuto(Double portiereImbattuto) {
		this.portiereImbattuto = portiereImbattuto;
	}

	public Integer getSubstitutionNumber() {
		return substitutionNumber;
	}

	public void setSubstitutionNumber(Integer substitutionNumber) {
		this.substitutionNumber = substitutionNumber;
	}

	public SubstitutionsModeEnum getSubstitutionMode() {
		return substitutionMode;
	}

	public void setSubstitutionMode(SubstitutionsModeEnum substitutionMode) {
		this.substitutionMode = substitutionMode;
	}

	public Boolean getYellowCardSvOfficeVoteActive() {
		return yellowCardSvOfficeVoteActive;
	}

	public void setYellowCardSvOfficeVoteActive(Boolean yellowCardSvOfficeVoteActive) {
		this.yellowCardSvOfficeVoteActive = yellowCardSvOfficeVoteActive;
	}

	public Double getYellowCardSvOfficeVote() {
		return yellowCardSvOfficeVote;
	}

	public void setYellowCardSvOfficeVote(Double yellowCardOfficeVote) {
		this.yellowCardSvOfficeVote = yellowCardOfficeVote;
	}

	public Boolean getGoalkeeperPlayerOfficeVoteActive() {
		return goalkeeperPlayerOfficeVoteActive;
	}

	public void setGoalkeeperPlayerOfficeVoteActive(Boolean goalkeeperPlayerOfficeVoteActive) {
		this.goalkeeperPlayerOfficeVoteActive = goalkeeperPlayerOfficeVoteActive;
	}

	public Double getGoalkeeperPlayerOfficeVote() {
		return goalkeeperPlayerOfficeVote;
	}

	public void setGoalkeeperPlayerOfficeVote(Double goalkeeperPlayerOfficeVote) {
		this.goalkeeperPlayerOfficeVote = goalkeeperPlayerOfficeVote;
	}

	public Boolean getMovementsPlayerOfficeVoteActive() {
		return movementsPlayerOfficeVoteActive;
	}

	public void setMovementsPlayerOfficeVoteActive(Boolean movementsPlayerOfficeVoteActive) {
		this.movementsPlayerOfficeVoteActive = movementsPlayerOfficeVoteActive;
	}

	public Double getMovementsPlayerOfficeVote() {
		return movementsPlayerOfficeVote;
	}

	public void setMovementsPlayerOfficeVote(Double movementsPlayerOfficeVote) {
		this.movementsPlayerOfficeVote = movementsPlayerOfficeVote;
	}

	public boolean isGoalkeeperModifierActive() {
		return goalkeeperModifierActive;
	}

	public void setGoalkeeperModifierActive(boolean goalkeeperModifierActive) {
		this.goalkeeperModifierActive = goalkeeperModifierActive;
	}

	public Double getGoalkeeperVote3() {
		return goalkeeperVote3;
	}

	public void setGoalkeeperVote3(Double goalkeeperVote3) {
		this.goalkeeperVote3 = goalkeeperVote3;
	}

	public Double getGoalkeeperVote3half() {
		return goalkeeperVote3half;
	}

	public void setGoalkeeperVote3half(Double goalkeeperVote3half) {
		this.goalkeeperVote3half = goalkeeperVote3half;
	}

	public Double getGoalkeeperVote4() {
		return goalkeeperVote4;
	}

	public void setGoalkeeperVote4(Double goalkeeperVote4) {
		this.goalkeeperVote4 = goalkeeperVote4;
	}

	public Double getGoalkeeperVote4half() {
		return goalkeeperVote4half;
	}

	public void setGoalkeeperVote4half(Double goalkeeperVote4half) {
		this.goalkeeperVote4half = goalkeeperVote4half;
	}

	public Double getGoalkeeperVote5() {
		return goalkeeperVote5;
	}

	public void setGoalkeeperVote5(Double goalkeeperVote5) {
		this.goalkeeperVote5 = goalkeeperVote5;
	}

	public Double getGoalkeeperVote5half() {
		return goalkeeperVote5half;
	}

	public void setGoalkeeperVote5half(Double goalkeeperVote5half) {
		this.goalkeeperVote5half = goalkeeperVote5half;
	}

	public Double getGoalkeeperVote6() {
		return goalkeeperVote6;
	}

	public void setGoalkeeperVote6(Double goalkeeperVote6) {
		this.goalkeeperVote6 = goalkeeperVote6;
	}

	public Double getGoalkeeperVote6half() {
		return goalkeeperVote6half;
	}

	public void setGoalkeeperVote6half(Double goalkeeperVote6half) {
		this.goalkeeperVote6half = goalkeeperVote6half;
	}

	public Double getGoalkeeperVote7() {
		return goalkeeperVote7;
	}

	public void setGoalkeeperVote7(Double goalkeeperVote7) {
		this.goalkeeperVote7 = goalkeeperVote7;
	}

	public Double getGoalkeeperVote7half() {
		return goalkeeperVote7half;
	}

	public void setGoalkeeperVote7half(Double goalkeeperVote7half) {
		this.goalkeeperVote7half = goalkeeperVote7half;
	}

	public Double getGoalkeeperVote8() {
		return goalkeeperVote8;
	}

	public void setGoalkeeperVote8(Double goalkeeperVote8) {
		this.goalkeeperVote8 = goalkeeperVote8;
	}

	public Double getGoalkeeperVote8half() {
		return goalkeeperVote8half;
	}

	public void setGoalkeeperVote8half(Double goalkeeperVote8half) {
		this.goalkeeperVote8half = goalkeeperVote8half;
	}

	public Double getGoalkeeperVote9() {
		return goalkeeperVote9;
	}

	public void setGoalkeeperVote9(Double goalkeeperVote9) {
		this.goalkeeperVote9 = goalkeeperVote9;
	}

	public boolean isDefenderModifierActive() {
		return defenderModifierActive;
	}

	public void setDefenderModifierActive(boolean defenderModifierActive) {
		this.defenderModifierActive = defenderModifierActive;
	}

	public Double getDefenderAvgVote6() {
		return defenderAvgVote6;
	}

	public void setDefenderAvgVote6(Double defenderAvgVote6) {
		this.defenderAvgVote6 = defenderAvgVote6;
	}

	public Double getDefenderAvgVote6half() {
		return defenderAvgVote6half;
	}

	public void setDefenderAvgVote6half(Double defenderAvgVote6half) {
		this.defenderAvgVote6half = defenderAvgVote6half;
	}

	public Double getDefenderAvgVote7() {
		return defenderAvgVote7;
	}

	public void setDefenderAvgVote7(Double defenderAvgVote7) {
		this.defenderAvgVote7 = defenderAvgVote7;
	}

	public boolean isMiddlefielderModifierActive() {
		return middlefielderModifierActive;
	}

	public void setMiddlefielderModifierActive(boolean middlefielderModifierActive) {
		this.middlefielderModifierActive = middlefielderModifierActive;
	}

	public Double getMiddlefielderUnderMinus8() {
		return middlefielderUnderMinus8;
	}

	public void setMiddlefielderUnderMinus8(Double middlefielderUnderMinus8) {
		this.middlefielderUnderMinus8 = middlefielderUnderMinus8;
	}

	public Double getMiddlefielderUnderMinus6() {
		return middlefielderUnderMinus6;
	}

	public void setMiddlefielderUnderMinus6(Double middlefielderUnderMinus6) {
		this.middlefielderUnderMinus6 = middlefielderUnderMinus6;
	}

	public Double getMiddlefielderUnderMinus4() {
		return middlefielderUnderMinus4;
	}

	public void setMiddlefielderUnderMinus4(Double middlefielderUnderMinus4) {
		this.middlefielderUnderMinus4 = middlefielderUnderMinus4;
	}

	public Double getMiddlefielderUnderMinus2() {
		return middlefielderUnderMinus2;
	}

	public void setMiddlefielderUnderMinus2(Double middlefielderUnderMinus2) {
		this.middlefielderUnderMinus2 = middlefielderUnderMinus2;
	}

	public Double getMiddlefielderNear0() {
		return middlefielderNear0;
	}

	public void setMiddlefielderNear0(Double middlefielderNear0) {
		this.middlefielderNear0 = middlefielderNear0;
	}

	public Double getMiddlefielderOver2() {
		return middlefielderOver2;
	}

	public void setMiddlefielderOver2(Double middlefielderOver2) {
		this.middlefielderOver2 = middlefielderOver2;
	}

	public Double getMiddlefielderOver4() {
		return middlefielderOver4;
	}

	public void setMiddlefielderOver4(Double middlefielderOver4) {
		this.middlefielderOver4 = middlefielderOver4;
	}

	public Double getMiddlefielderOver6() {
		return middlefielderOver6;
	}

	public void setMiddlefielderOver6(Double middlefielderOver6) {
		this.middlefielderOver6 = middlefielderOver6;
	}

	public Double getMiddlefielderOver8() {
		return middlefielderOver8;
	}

	public void setMiddlefielderOver8(Double middlefielderOver8) {
		this.middlefielderOver8 = middlefielderOver8;
	}

	public boolean isStrikerModifierActive() {
		return strikerModifierActive;
	}

	public void setStrikerModifierActive(boolean strikerModifierActive) {
		this.strikerModifierActive = strikerModifierActive;
	}

	public Double getStrikerVote6() {
		return strikerVote6;
	}

	public void setStrikerVote6(Double strikerVote6) {
		this.strikerVote6 = strikerVote6;
	}

	public Double getStrikerVote6half() {
		return strikerVote6half;
	}

	public void setStrikerVote6half(Double strikerVote6half) {
		this.strikerVote6half = strikerVote6half;
	}

	public Double getStrikerVote7() {
		return strikerVote7;
	}

	public void setStrikerVote7(Double strikerVote7) {
		this.strikerVote7 = strikerVote7;
	}

	public Double getStrikerVote7half() {
		return strikerVote7half;
	}

	public void setStrikerVote7half(Double strikerVote7half) {
		this.strikerVote7half = strikerVote7half;
	}

	public Double getStrikerVote8() {
		return strikerVote8;
	}

	public void setStrikerVote8(Double strikerVote8) {
		this.strikerVote8 = strikerVote8;
	}

	public boolean isPerformanceModifierActive() {
		return performanceModifierActive;
	}

	public void setPerformanceModifierActive(boolean performanceModifierActive) {
		this.performanceModifierActive = performanceModifierActive;
	}

	public Double getPerformance11() {
		return performance11;
	}

	public void setPerformance11(Double performance11) {
		this.performance11 = performance11;
	}

	public Double getPerformance10() {
		return performance10;
	}

	public void setPerformance10(Double performance10) {
		this.performance10 = performance10;
	}

	public Double getPerformance9() {
		return performance9;
	}

	public void setPerformance9(Double performance9) {
		this.performance9 = performance9;
	}

	public Double getPerformance8() {
		return performance8;
	}

	public void setPerformance8(Double performance8) {
		this.performance8 = performance8;
	}

	public Double getPerformance7() {
		return performance7;
	}

	public void setPerformance7(Double performance7) {
		this.performance7 = performance7;
	}

	public Double getPerformance6() {
		return performance6;
	}

	public void setPerformance6(Double performance6) {
		this.performance6 = performance6;
	}

	public Double getPerformance5() {
		return performance5;
	}

	public void setPerformance5(Double performance5) {
		this.performance5 = performance5;
	}

	public Double getPerformance4() {
		return performance4;
	}

	public void setPerformance4(Double performance4) {
		this.performance4 = performance4;
	}

	public Double getPerformance3() {
		return performance3;
	}

	public void setPerformance3(Double performance3) {
		this.performance3 = performance3;
	}

	public Double getPerformance2() {
		return performance2;
	}

	public void setPerformance2(Double performance2) {
		this.performance2 = performance2;
	}

	public Double getPerformance1() {
		return performance1;
	}

	public void setPerformance1(Double performance1) {
		this.performance1 = performance1;
	}

	public Double getPerformance0() {
		return performance0;
	}

	public void setPerformance0(Double performance0) {
		this.performance0 = performance0;
	}

	public boolean isFairPlayModifierActive() {
		return fairPlayModifierActive;
	}

	public void setFairPlayModifierActive(boolean fairPlayModifierActive) {
		this.fairPlayModifierActive = fairPlayModifierActive;
	}

	public Double getFairPlay() {
		return fairPlay;
	}

	public void setFairPlay(Double fairPlay) {
		this.fairPlay = fairPlay;
	}

	public String getYellowRedCardSource() {
		return yellowRedCardSource;
	}

	public void setYellowRedCardSource(String yellowRedCardSource) {
		this.yellowRedCardSource = yellowRedCardSource;
	}

	public String getBonusMalusSource() {
		return bonusMalusSource;
	}

	public void setBonusMalusSource(String bonusMalusSource) {
		this.bonusMalusSource = bonusMalusSource;
	}

	public void setGoalPoints(List<Double> goalPoints2) {
		// TODO Auto-generated method stub
		
	}

	public String getMaxOfficeVotes() {
		return maxOfficeVotes;
	}

	public void setMaxOfficeVotes(String maxOfficeVotes) {
		this.maxOfficeVotes = maxOfficeVotes;
	}

	public Boolean getAutogolActive() {
		return autogolActive;
	}

	public void setAutogolActive(Boolean autogolActive) {
		this.autogolActive = autogolActive;
	}

	public Double getAutogol() {
		return autogol;
	}

	public void setAutogol(Double autogol) {
		this.autogol = autogol;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public Competition getCompetition() {
		return competition;
	}

	public Boolean getHomeBonusActive() {
		return homeBonusActive;
	}

	public void setHomeBonusActive(Boolean homeBonusActive) {
		this.homeBonusActive = homeBonusActive;
	}

	public Double getHomeBonus() {
		return homeBonus;
	}

	public void setHomeBonus(Double homeBonus) {
		this.homeBonus = homeBonus;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getPostponementBehaviour() {
		return postponementBehaviour;
	}

	public void setPostponementBehaviour(String postponementBehaviour) {
		this.postponementBehaviour = postponementBehaviour;
	}

	@Override
	public String toString() {
		return "Rules [id=" + id + ", competition=" + competition + ", league=" + league + ", binding=" + binding
				+ ", redCardP=" + redCardP + ", yellowCardP=" + yellowCardP + ", scoredGoalP=" + scoredGoalP
				+ ", scoredPenaltyP=" + scoredPenaltyP + ", movementAssistP=" + movementAssistP + ", stationaryAssistP="
				+ stationaryAssistP + ", autogoalP=" + autogoalP + ", missedPenaltyP=" + missedPenaltyP
				+ ", savedPenaltyP=" + savedPenaltyP + ", takenGoalP=" + takenGoalP + ", evenGoalP=" + evenGoalP
				+ ", winGoalP=" + winGoalP + ", redCardD=" + redCardD + ", yellowCardD=" + yellowCardD
				+ ", scoredGoalD=" + scoredGoalD + ", scoredPenaltyD=" + scoredPenaltyD + ", movementAssistD="
				+ movementAssistD + ", stationaryAssistD=" + stationaryAssistD + ", autogoalD=" + autogoalD
				+ ", missedPenaltyD=" + missedPenaltyD + ", savedPenaltyD=" + savedPenaltyD + ", takenGoalD="
				+ takenGoalD + ", evenGoalD=" + evenGoalD + ", winGoalD=" + winGoalD + ", redCardC=" + redCardC
				+ ", yellowCardC=" + yellowCardC + ", scoredGoalC=" + scoredGoalC + ", scoredPenaltyC=" + scoredPenaltyC
				+ ", movementAssistC=" + movementAssistC + ", stationaryAssistC=" + stationaryAssistC + ", autogoalC="
				+ autogoalC + ", missedPenaltyC=" + missedPenaltyC + ", savedPenaltyC=" + savedPenaltyC
				+ ", takenGoalC=" + takenGoalC + ", evenGoalC=" + evenGoalC + ", winGoalC=" + winGoalC + ", redCardA="
				+ redCardA + ", yellowCardA=" + yellowCardA + ", scoredGoalA=" + scoredGoalA + ", scoredPenaltyA="
				+ scoredPenaltyA + ", movementAssistA=" + movementAssistA + ", stationaryAssistA=" + stationaryAssistA
				+ ", autogoalA=" + autogoalA + ", missedPenaltyA=" + missedPenaltyA + ", savedPenaltyA=" + savedPenaltyA
				+ ", takenGoalA=" + takenGoalA + ", evenGoalA=" + evenGoalA + ", winGoalA=" + winGoalA
				+ ", votesSource=" + votesSource + ", bonusMalusSource=" + bonusMalusSource + ", yellowRedCardSource="
				+ yellowRedCardSource + ", goalPoints=" + goalPoints + ", formulaUnoPoints=" + formulaUnoPoints
				+ ", fasciaConIntornoActive=" + fasciaConIntornoActive + ", fasciaConIntorno=" + fasciaConIntorno
				+ ", intornoActive=" + intornoActive + ", intorno=" + intorno + ", controllaPareggioActive="
				+ controllaPareggioActive + ", controllaPareggio=" + controllaPareggio + ", differenzaPuntiActive="
				+ differenzaPuntiActive + ", differenzaPunti=" + differenzaPunti + ", portiereImbattutoActive="
				+ portiereImbattutoActive + ", portiereImbattuto=" + portiereImbattuto + ", autogolActive="
				+ autogolActive + ", autogol=" + autogol + ", substitutionNumber=" + substitutionNumber
				+ ", substitutionMode=" + substitutionMode + ", maxOfficeVotes=" + maxOfficeVotes
				+ ", yellowCardSvOfficeVoteActive=" + yellowCardSvOfficeVoteActive + ", yellowCardSvOfficeVote="
				+ yellowCardSvOfficeVote + ", goalkeeperPlayerOfficeVoteActive=" + goalkeeperPlayerOfficeVoteActive
				+ ", goalkeeperPlayerOfficeVote=" + goalkeeperPlayerOfficeVote + ", movementsPlayerOfficeVoteActive="
				+ movementsPlayerOfficeVoteActive + ", movementsPlayerOfficeVote=" + movementsPlayerOfficeVote
				+ ", goalkeeperModifierActive=" + goalkeeperModifierActive + ", goalkeeperVote3=" + goalkeeperVote3
				+ ", goalkeeperVote3half=" + goalkeeperVote3half + ", goalkeeperVote4=" + goalkeeperVote4
				+ ", goalkeeperVote4half=" + goalkeeperVote4half + ", goalkeeperVote5=" + goalkeeperVote5
				+ ", goalkeeperVote5half=" + goalkeeperVote5half + ", goalkeeperVote6=" + goalkeeperVote6
				+ ", goalkeeperVote6half=" + goalkeeperVote6half + ", goalkeeperVote7=" + goalkeeperVote7
				+ ", goalkeeperVote7half=" + goalkeeperVote7half + ", goalkeeperVote8=" + goalkeeperVote8
				+ ", goalkeeperVote8half=" + goalkeeperVote8half + ", goalkeeperVote9=" + goalkeeperVote9
				+ ", defenderModifierActive=" + defenderModifierActive + ", defenderAvgVote6=" + defenderAvgVote6
				+ ", defenderAvgVote6half=" + defenderAvgVote6half + ", defenderAvgVote7=" + defenderAvgVote7
				+ ", middlefielderModifierActive=" + middlefielderModifierActive + ", middlefielderUnderMinus8="
				+ middlefielderUnderMinus8 + ", middlefielderUnderMinus6=" + middlefielderUnderMinus6
				+ ", middlefielderUnderMinus4=" + middlefielderUnderMinus4 + ", middlefielderUnderMinus2="
				+ middlefielderUnderMinus2 + ", middlefielderNear0=" + middlefielderNear0 + ", middlefielderOver2="
				+ middlefielderOver2 + ", middlefielderOver4=" + middlefielderOver4 + ", middlefielderOver6="
				+ middlefielderOver6 + ", middlefielderOver8=" + middlefielderOver8 + ", strikerModifierActive="
				+ strikerModifierActive + ", strikerVote6=" + strikerVote6 + ", strikerVote6half=" + strikerVote6half
				+ ", strikerVote7=" + strikerVote7 + ", strikerVote7half=" + strikerVote7half + ", strikerVote8="
				+ strikerVote8 + ", performanceModifierActive=" + performanceModifierActive + ", performance11="
				+ performance11 + ", performance10=" + performance10 + ", performance9=" + performance9
				+ ", performance8=" + performance8 + ", performance7=" + performance7 + ", performance6=" + performance6
				+ ", performance5=" + performance5 + ", performance4=" + performance4 + ", performance3=" + performance3
				+ ", performance2=" + performance2 + ", performance1=" + performance1 + ", performance0=" + performance0
				+ ", fairPlayModifierActive=" + fairPlayModifierActive + ", fairPlay=" + fairPlay + ", homeBonusActive="
				+ homeBonusActive + ", homeBonus=" + homeBonus + ", postponementBehaviour=" + postponementBehaviour
				+ "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
