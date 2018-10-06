package app.logic._0_rulesDownloader.model;


import java.io.Serializable;

public class Modifiers  implements Serializable {
	private static final long serialVersionUID = 2359384220107049801L;

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
	private boolean goalkeeperModifierPenaltySavedActive;
	
	
	private boolean defenderModifierActive;
	private boolean defenderGoalkeeperIncluded;
	private DefenderModeEnum defenderMode;
	

	private Double defenderAvgVoteUnder5;

	private Double defenderAvgVote5;
	private Double defenderAvgVote5quart;
	private Double defenderAvgVote5half;
	private Double defenderAvgVote5sept;
	
	private Double defenderAvgVote6;
	private Double defenderAvgVote6quart;
	private Double defenderAvgVote6half;
	private Double defenderAvgVote6sept;
	
	private Double defenderAvgVote7;
	private Double defenderAvgVote7quart;
	private Double defenderAvgVote7half;
	private Double defenderAvgVote7sept;
	
	private Double defenderAvgVote8;
	
	
	private boolean middlefielderModifierActive;
	private Double middlefielder2;
	private Double middlefielder2half;
	private Double middlefielder3;
	private Double middlefielder3half;
	private Double middlefielder4;
	private Double middlefielder4half;
	private Double middlefielder5;
	private Double middlefielder5half;
	private Double middlefielder6;
	private Double middlefielder6half;
	private Double middlefielder7;
	private Double middlefielder7half;
	private Double middlefielder8;
	
	private Double middlefielderMinus2;
	private Double middlefielderMinus2half;
	private Double middlefielderMinus3;
	private Double middlefielderMinus3half;
	private Double middlefielderMinus4;
	private Double middlefielderMinus4half;
	private Double middlefielderMinus5;
	private Double middlefielderMinus5half;
	private Double middlefielderMinus6;
	private Double middlefielderMinus6half;
	private Double middlefielderMinus7;
	private Double middlefielderMinus7half;
	private Double middlefielderMinus8;

	
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
	
	private boolean captainModifierActive;
	private boolean captainDuplicateBonus;
	private boolean captainDuplicateMalus;
	private Double captainVote3;
	private Double captainVote3half;
	private Double captainVote4;
	private Double captainVote4half;
	private Double captainVote5;
	private Double captainVote5half;
	private Double captainVote6;
	private Double captainVote6half;
	private Double captainVote7;
	private Double captainVote7half;
	private Double captainVote8;
	private Double captainVote8half;
	private Double captainVote9;
	
	
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
	@Override
	public String toString() {
		return "Modifiers ["
				+ " goalkeeperModifierPenaltySavedActive=" + goalkeeperModifierPenaltySavedActive + "\n"
				+ " goalkeeperVote3=" + goalkeeperVote3 + "\n goalkeeperVote3half=" + goalkeeperVote3half
				+ "\n goalkeeperVote4=" + goalkeeperVote4 + "\n goalkeeperVote4half=" + goalkeeperVote4half
				+ "\n goalkeeperVote5=" + goalkeeperVote5 + "\n goalkeeperVote5half=" + goalkeeperVote5half
				+ "\n goalkeeperVote6=" + goalkeeperVote6 + "\n goalkeeperVote6half=" + goalkeeperVote6half
				+ "\n goalkeeperVote7=" + goalkeeperVote7 + "\n goalkeeperVote7half=" + goalkeeperVote7half
				+ "\n goalkeeperVote8=" + goalkeeperVote8 + "\n goalkeeperVote8half=" + goalkeeperVote8half
				+ "\n goalkeeperVote9=" + goalkeeperVote9 
				
				+ "\n\n defenderModifierActive=" + defenderModifierActive
				+ "\n defenderGoalkeeperIncluded=" + defenderGoalkeeperIncluded
				+ "\n defenderMode=" + defenderMode
				+ "\n defenderAvgVoteUnder5=" + defenderAvgVoteUnder5
				+ "\n defenderAvgVote5=" + defenderAvgVote5
				+ "\n defenderAvgVote5quart=" + defenderAvgVote5quart
				+ "\n defenderAvgVote5half=" + defenderAvgVote5half 
				+ "\n defenderAvgVote5sept=" + defenderAvgVote5sept
				+ "\n defenderAvgVote6=" + defenderAvgVote6
				+ "\n defenderAvgVote6quart=" + defenderAvgVote6quart
				+ "\n defenderAvgVote6half=" + defenderAvgVote6half 
				+ "\n defenderAvgVote6sept=" + defenderAvgVote6sept
				+ "\n defenderAvgVote7=" + defenderAvgVote7
				+ "\n defenderAvgVote7quart=" + defenderAvgVote7quart
				+ "\n defenderAvgVote7half=" + defenderAvgVote7half 
				+ "\n defenderAvgVote7sept=" + defenderAvgVote7sept
				+ "\n defenderAvgVote8=" + defenderAvgVote8
				
				+ "\n\n middlefielderModifierActive=" + middlefielderModifierActive
				+ "\n middlefielder2=" + middlefielder2 
				+ "\n middlefielder2half=" + middlefielder2half 
				+ "\n middlefielder3=" + middlefielder3
				+ "\n middlefielder3half=" + middlefielder3half
				+ "\n middlefielder4=" + middlefielder4
				+ "\n middlefielder4half=" + middlefielder4half 
				+ "\n middlefielder5=" + middlefielder5
				+ "\n middlefielder5half=" + middlefielder5half 
				+ "\n middlefielder6=" + middlefielder6
				+ "\n middlefielder6half=" + middlefielder6half 
				+ "\n middlefielder7=" + middlefielder7
				+ "\n middlefielder7half=" + middlefielder7half 
				+ "\n middlefielder8=" + middlefielder8
				+ "\n\n middlefielderMinus2=" + middlefielderMinus2 
				+ "\n middlefielderMinus2half=" + middlefielderMinus2half 
				+ "\n middlefielderMinus3=" + middlefielderMinus3
				+ "\n middlefielderMinus3half=" + middlefielderMinus3half
				+ "\n middlefielderMinus4=" + middlefielderMinus4
				+ "\n middlefielderMinus4half=" + middlefielderMinus4half 
				+ "\n middlefielderMinus5=" + middlefielderMinus5
				+ "\n middlefielderMinus5half=" + middlefielderMinus5half 
				+ "\n middlefielderMinus6=" + middlefielderMinus6
				+ "\n middlefielderMinus6half=" + middlefielderMinus6half 
				+ "\n middlefielderMinus7=" + middlefielderMinus7
				+ "\n middlefielderMinus8half=" + middlefielderMinus7half 
				+ "\n middlefielderMinus8=" + middlefielder8
				 
				
//				+ "\n middlefielderNear0=" + middlefielderNear0 
//				+ "\n middlefielderOver2=" + middlefielderOver2 
//				+ "\n middlefielderOver4=" + middlefielderOver4 
//				+ "\n middlefielderOver6=" + middlefielderOver6 
//				+ "\n middlefielderOver8=" + middlefielderOver8 
				
				+ "\n\n strikerModifierActive=" + strikerModifierActive
				+ "\n strikerVote6half=" + strikerVote6half
				+ "\n strikerVote7=" + strikerVote7
				+ "\n strikerVote7half=" + strikerVote7half 
				+ "\n strikerVote8=" + strikerVote8  
				
				+ "\n\n performanceModifierActive=" + performanceModifierActive
				+ "\n performance0=" + performance0
				+ "\n performance1=" + performance1
				+ "\n performance2=" + performance2
				+ "\n performance3=" + performance3
				+ "\n performance4=" + performance4
				+ "\n performance5=" + performance5
				+ "\n performance6=" + performance6
				+ "\n performance7=" + performance7
				+ "\n performance8=" + performance8
				+ "\n performance9=" + performance9
				+ "\n performance10=" + performance10
				+ "\n performance11=" + performance11
				
				+ "\n\n fairPlayModifierActive=" + fairPlayModifierActive
				+ "\n fairPlay=" + fairPlay
				
				+ "\n\n captainModifierActive=" + captainModifierActive
				+ "\n captainDuplicateBonus=" + isCaptainDuplicateBonus()
				+ "\n captainDuplicateMalus=" + isCaptainDuplicateMalus()
				+ "\n captainVote3=" + captainVote3 + "\n captainVote3half=" + captainVote3half
				+ "\n captainVote4=" + captainVote4 + "\n captainVote4half=" + captainVote4half
				+ "\n captainVote5=" + captainVote5 + "\n captainVote5half=" + captainVote5half
				+ "\n captainVote6=" + captainVote6 + "\n captainVote6half=" + captainVote6half
				+ "\n captainVote7=" + captainVote7 + "\n captainVote7half=" + captainVote7half
				+ "\n captainVote8=" + captainVote8 + "\n captainVote8half=" + captainVote8half
				+ "\n captainVote9=" + captainVote9 +
				
				
				"]";
	}
	public Double getStrikerVote6() {
		return strikerVote6;
	}
	public void setStrikerVote6(Double strikerVote6) {
		this.strikerVote6 = strikerVote6;
	}
	public boolean isGoalkeeperModifierActive() {
		return goalkeeperModifierActive;
	}
	public void setGoalkeeperModifierActive(boolean goalkeeperModifierActive) {
		this.goalkeeperModifierActive = goalkeeperModifierActive;
	}
	public boolean isDefenderModifierActive() {
		return defenderModifierActive;
	}
	public void setDefenderModifierActive(boolean defenderModifierActive) {
		this.defenderModifierActive = defenderModifierActive;
	}
	public boolean isMiddlefielderModifierActive() {
		return middlefielderModifierActive;
	}
	public void setMiddlefielderModifierActive(Boolean middlefielderModifierActive) {
		this.middlefielderModifierActive = middlefielderModifierActive;
	}
	public boolean isStrikerModifierActive() {
		return strikerModifierActive;
	}
	public void setStrikerModifierActive(boolean strikerModifierActive) {
		this.strikerModifierActive = strikerModifierActive;
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
	public boolean isGoalkeeperModifierPenaltySavedActive() {
		return goalkeeperModifierPenaltySavedActive;
	}
	public void setGoalkeeperModifierPenaltySavedActive(boolean goalkeeperModifierPenaltySavedActive) {
		this.goalkeeperModifierPenaltySavedActive = goalkeeperModifierPenaltySavedActive;
	}
	public Double getMiddlefielderMinus8() {
		return middlefielderMinus8;
	}
	public void setMiddlefielderMinus8(Double middlefielderMinus8) {
		this.middlefielderMinus8 = middlefielderMinus8;
	}
	public Double getMiddlefielder2() {
		return middlefielder2;
	}
	public void setMiddlefielder2(Double middlefielder2) {
		this.middlefielder2 = middlefielder2;
	}
	public Double getMiddlefielder2half() {
		return middlefielder2half;
	}
	public void setMiddlefielder2half(Double middlefielder2half) {
		this.middlefielder2half = middlefielder2half;
	}
	public Double getMiddlefielder3() {
		return middlefielder3;
	}
	public void setMiddlefielder3(Double middlefielder3) {
		this.middlefielder3 = middlefielder3;
	}
	public Double getMiddlefielder3half() {
		return middlefielder3half;
	}
	public void setMiddlefielder3half(Double middlefielder3half) {
		this.middlefielder3half = middlefielder3half;
	}
	public Double getMiddlefielder4() {
		return middlefielder4;
	}
	public void setMiddlefielder4(Double middlefielder4) {
		this.middlefielder4 = middlefielder4;
	}
	public Double getMiddlefielder4half() {
		return middlefielder4half;
	}
	public void setMiddlefielder4half(Double middlefielder4half) {
		this.middlefielder4half = middlefielder4half;
	}
	public Double getMiddlefielder5() {
		return middlefielder5;
	}
	public void setMiddlefielder5(Double middlefielder5) {
		this.middlefielder5 = middlefielder5;
	}
	public Double getMiddlefielder5half() {
		return middlefielder5half;
	}
	public void setMiddlefielder5half(Double middlefielder5half) {
		this.middlefielder5half = middlefielder5half;
	}
	public Double getMiddlefielder6() {
		return middlefielder6;
	}
	public void setMiddlefielder6(Double middlefielder6) {
		this.middlefielder6 = middlefielder6;
	}
	public Double getMiddlefielder6half() {
		return middlefielder6half;
	}
	public void setMiddlefielder6half(Double middlefielder6half) {
		this.middlefielder6half = middlefielder6half;
	}
	public Double getMiddlefielder7() {
		return middlefielder7;
	}
	public void setMiddlefielder7(Double middlefielder7) {
		this.middlefielder7 = middlefielder7;
	}
	public Double getMiddlefielder7half() {
		return middlefielder7half;
	}
	public void setMiddlefielder7half(Double middlefielder7half) {
		this.middlefielder7half = middlefielder7half;
	}
	public Double getMiddlefielder8() {
		return middlefielder8;
	}
	public void setMiddlefielder8(Double middlefielder8) {
		this.middlefielder8 = middlefielder8;
	}
	public Double getMiddlefielderMinus2() {
		return middlefielderMinus2;
	}
	public void setMiddlefielderMinus2(Double middlefielderMinus2) {
		this.middlefielderMinus2 = middlefielderMinus2;
	}
	public Double getMiddlefielderMinus2half() {
		return middlefielderMinus2half;
	}
	public void setMiddlefielderMinus2half(Double middlefielderMinus2half) {
		this.middlefielderMinus2half = middlefielderMinus2half;
	}
	public Double getMiddlefielderMinus3() {
		return middlefielderMinus3;
	}
	public void setMiddlefielderMinus3(Double middlefielderMinus3) {
		this.middlefielderMinus3 = middlefielderMinus3;
	}
	public Double getMiddlefielderMinus3half() {
		return middlefielderMinus3half;
	}
	public void setMiddlefielderMinus3half(Double middlefielderMinus3half) {
		this.middlefielderMinus3half = middlefielderMinus3half;
	}
	public Double getMiddlefielderMinus4() {
		return middlefielderMinus4;
	}
	public void setMiddlefielderMinus4(Double middlefielderMinus4) {
		this.middlefielderMinus4 = middlefielderMinus4;
	}
	public Double getMiddlefielderMinus4half() {
		return middlefielderMinus4half;
	}
	public void setMiddlefielderMinus4half(Double middlefielderMinus4half) {
		this.middlefielderMinus4half = middlefielderMinus4half;
	}
	public Double getMiddlefielderMinus5() {
		return middlefielderMinus5;
	}
	public void setMiddlefielderMinus5(Double middlefielderMinus5) {
		this.middlefielderMinus5 = middlefielderMinus5;
	}
	public Double getMiddlefielderMinus5half() {
		return middlefielderMinus5half;
	}
	public void setMiddlefielderMinus5half(Double middlefielderMinus5half) {
		this.middlefielderMinus5half = middlefielderMinus5half;
	}
	public Double getMiddlefielderMinus6() {
		return middlefielderMinus6;
	}
	public void setMiddlefielderMinus6(Double middlefielderMinus6) {
		this.middlefielderMinus6 = middlefielderMinus6;
	}
	public Double getMiddlefielderMinus6half() {
		return middlefielderMinus6half;
	}
	public void setMiddlefielderMinus6half(Double middlefielderMinus6half) {
		this.middlefielderMinus6half = middlefielderMinus6half;
	}
	public Double getMiddlefielderMinus7() {
		return middlefielderMinus7;
	}
	public void setMiddlefielderMinus7(Double middlefielderMinus7) {
		this.middlefielderMinus7 = middlefielderMinus7;
	}
	public Double getMiddlefielderMinus7half() {
		return middlefielderMinus7half;
	}
	public void setMiddlefielderMinus7half(Double middlefielderMinus7half) {
		this.middlefielderMinus7half = middlefielderMinus7half;
	}
	public void setMiddlefielderModifierActive(boolean middlefielderModifierActive) {
		this.middlefielderModifierActive = middlefielderModifierActive;
	}
	public boolean isCaptainModifierActive() {
		return captainModifierActive;
	}
	public void setCaptainModifierActive(boolean captainModifierActive) {
		this.captainModifierActive = captainModifierActive;
	}
	public Double getCaptainVote3() {
		return captainVote3;
	}
	public void setCaptainVote3(Double captainVote3) {
		this.captainVote3 = captainVote3;
	}
	public Double getCaptainVote3half() {
		return captainVote3half;
	}
	public void setCaptainVote3half(Double captainVote3half) {
		this.captainVote3half = captainVote3half;
	}
	public Double getCaptainVote4() {
		return captainVote4;
	}
	public void setCaptainVote4(Double captainVote4) {
		this.captainVote4 = captainVote4;
	}
	public Double getCaptainVote4half() {
		return captainVote4half;
	}
	public void setCaptainVote4half(Double captainVote4half) {
		this.captainVote4half = captainVote4half;
	}
	public Double getCaptainVote5() {
		return captainVote5;
	}
	public void setCaptainVote5(Double captainVote5) {
		this.captainVote5 = captainVote5;
	}
	public Double getCaptainVote5half() {
		return captainVote5half;
	}
	public void setCaptainVote5half(Double captainVote5half) {
		this.captainVote5half = captainVote5half;
	}
	public Double getCaptainVote6() {
		return captainVote6;
	}
	public void setCaptainVote6(Double captainVote6) {
		this.captainVote6 = captainVote6;
	}
	public Double getCaptainVote6half() {
		return captainVote6half;
	}
	public void setCaptainVote6half(Double captainVote6half) {
		this.captainVote6half = captainVote6half;
	}
	public Double getCaptainVote7() {
		return captainVote7;
	}
	public void setCaptainVote7(Double captainVote7) {
		this.captainVote7 = captainVote7;
	}
	public Double getCaptainVote7half() {
		return captainVote7half;
	}
	public void setCaptainVote7half(Double captainVote7half) {
		this.captainVote7half = captainVote7half;
	}
	public Double getCaptainVote8() {
		return captainVote8;
	}
	public void setCaptainVote8(Double captainVote8) {
		this.captainVote8 = captainVote8;
	}
	public Double getCaptainVote8half() {
		return captainVote8half;
	}
	public void setCaptainVote8half(Double captainVote8half) {
		this.captainVote8half = captainVote8half;
	}
	public Double getCaptainVote9() {
		return captainVote9;
	}
	public void setCaptainVote9(Double captainVote9) {
		this.captainVote9 = captainVote9;
	}
	public boolean isCaptainDuplicateBonus() {
		return captainDuplicateBonus;
	}
	public void setCaptainDuplicateBonus(boolean captainDuplicateBonus) {
		this.captainDuplicateBonus = captainDuplicateBonus;
	}
	public boolean isCaptainDuplicateMalus() {
		return captainDuplicateMalus;
	}
	public void setCaptainDuplicateMalus(boolean captainDuplicateMalus) {
		this.captainDuplicateMalus = captainDuplicateMalus;
	}
	public boolean isDefenderGoalkeeperIncluded() {
		return defenderGoalkeeperIncluded;
	}
	public void setDefenderGoalkeeperIncluded(boolean defenderGoalkeeperIncluded) {
		this.defenderGoalkeeperIncluded = defenderGoalkeeperIncluded;
	}
	public DefenderModeEnum getDefenderMode() {
		return defenderMode;
	}
	public void setDefenderMode(DefenderModeEnum defenderMode) {
		this.defenderMode = defenderMode;
	}
	public Double getDefenderAvgVote5() {
		return defenderAvgVote5;
	}
	public void setDefenderAvgVote5(Double defenderAvgVote5) {
		this.defenderAvgVote5 = defenderAvgVote5;
	}
	public Double getDefenderAvgVote5quart() {
		return defenderAvgVote5quart;
	}
	public void setDefenderAvgVote5quart(Double defenderAvgVote5quart) {
		this.defenderAvgVote5quart = defenderAvgVote5quart;
	}
	public Double getDefenderAvgVote5half() {
		return defenderAvgVote5half;
	}
	public void setDefenderAvgVote5half(Double defenderAvgVote5half) {
		this.defenderAvgVote5half = defenderAvgVote5half;
	}
	public Double getDefenderAvgVote5sept() {
		return defenderAvgVote5sept;
	}
	public void setDefenderAvgVote5sept(Double defenderAvgVote5sept) {
		this.defenderAvgVote5sept = defenderAvgVote5sept;
	}
	public Double getDefenderAvgVote6quart() {
		return defenderAvgVote6quart;
	}
	public void setDefenderAvgVote6quart(Double defenderAvgVote6quart) {
		this.defenderAvgVote6quart = defenderAvgVote6quart;
	}
	public Double getDefenderAvgVote6sept() {
		return defenderAvgVote6sept;
	}
	public void setDefenderAvgVote6sept(Double defenderAvgVote6sept) {
		this.defenderAvgVote6sept = defenderAvgVote6sept;
	}
	public Double getDefenderAvgVote7quart() {
		return defenderAvgVote7quart;
	}
	public void setDefenderAvgVote7quart(Double defenderAvgVote7quart) {
		this.defenderAvgVote7quart = defenderAvgVote7quart;
	}
	public Double getDefenderAvgVote7half() {
		return defenderAvgVote7half;
	}
	public void setDefenderAvgVote7half(Double defenderAvgVote7half) {
		this.defenderAvgVote7half = defenderAvgVote7half;
	}
	public Double getDefenderAvgVote7sept() {
		return defenderAvgVote7sept;
	}
	public void setDefenderAvgVote7sept(Double defenderAvgVote7sept) {
		this.defenderAvgVote7sept = defenderAvgVote7sept;
	}
	public Double getDefenderAvgVote8() {
		return defenderAvgVote8;
	}
	public void setDefenderAvgVote8(Double defenderAvgVote8) {
		this.defenderAvgVote8 = defenderAvgVote8;
	}
	public Double getDefenderAvgVoteUnder5() {
		return defenderAvgVoteUnder5;
	}
	public void setDefenderAvgVoteUnder5(Double defenderAvgVoteUnder5) {
		this.defenderAvgVoteUnder5 = defenderAvgVoteUnder5;
	}
	
	
	
	
	
	
	
	



}
