package app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.dao.entity.User;
import app.dao.entity.Vote;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.RoleEnum;
import app.logic._0_votesDownloader.model.VotesSourceEnum;

@Service
@EnableCaching
public class VoteDao {

	@Autowired
	private VoteRepo voteRepo;


	
	public Map<String, Map<String, List<PlayerVoteComplete>>> findVotesBySource(VotesSourceEnum source) {
		
		List<Vote> votes = voteRepo.findBySourceOrderBySerieASeasonDayAsc(source.name());
		
		Map<String, Map<String, List<PlayerVoteComplete>>> map = new HashMap<String, Map<String, List<PlayerVoteComplete>>>();
		

		
		for (Vote voteEnt : votes) {
			String seasonDay = voteEnt.getSerieASeasonDay().toString();
			Map<String, List<PlayerVoteComplete>> teamMap = map.get(seasonDay);
			
			if (teamMap == null) {
				teamMap = new HashMap<String, List<PlayerVoteComplete>>();
				map.put(seasonDay, teamMap);
			}
			
			String team = voteEnt.getTeam();
			List<PlayerVoteComplete> players = teamMap.get(team);
			
			if (players == null) {
				players = new ArrayList<PlayerVoteComplete>();
				teamMap.put(team, players);
			}
			
			PlayerVoteComplete playerVote = createPlayerVote(voteEnt);
			players.add(playerVote);
			
		}
		
		return map;
	}

	private PlayerVoteComplete createPlayerVote(Vote voteEnt) {
		
		String name = voteEnt.getName();
		String team = voteEnt.getTeam();
		RoleEnum role = RoleEnum.valueOf(voteEnt.getRole());
		Double vote = voteEnt.getVote();
		Boolean yellowCard = voteEnt.getYellowCard();
		Boolean redCard = voteEnt.getRedCard();
		Double scoredGoals = voteEnt.getScoredGoals();
		Double scoredPenalties = voteEnt.getScoredPenalties();
		Double movementAssists = voteEnt.getMovementAssists();
		Double stationaryAssists = voteEnt.getStationaryAssists();
		Double autogoals = voteEnt.getAutogoals();
		Double missedPenalties = voteEnt.getMissedPenalties();
		Double savedPenalties = voteEnt.getSavedPenalties();
		Double takenGoals = voteEnt.getTakenGoals();
		Boolean winGoal = voteEnt.getWinGoal();
		Boolean evenGoal = voteEnt.getEvenGoal();
		Boolean subIn = voteEnt.getSubIn();
		Boolean subOut = voteEnt.getSubOut();
		
		PlayerVoteComplete bean = new PlayerVoteComplete(name, team, role, vote, yellowCard, redCard, scoredGoals, scoredPenalties, 
				movementAssists, stationaryAssists, autogoals, missedPenalties, savedPenalties, takenGoals, winGoal, evenGoal, subIn, subOut); 

		return bean;
	}

	public int calculateLastSeasonDayCalculated() {
		List<Integer> seasonDays = voteRepo.findAllDistinct();
		if (seasonDays.isEmpty())
			return 0;
		
		return seasonDays.get(seasonDays.size()-1);
	}


	public void saveVotesBySeasonDayAndVoteSource(Map<String, List<PlayerVoteComplete>> votesByTeam, Integer serieASeasonDay, VotesSourceEnum voteSource) {
		
		Vote v;
		List<Vote> voteEnts = new ArrayList<Vote>();
		for ( Entry<String, List<PlayerVoteComplete>> entry : votesByTeam.entrySet()) {
//			String team = entry.getKey();
			List<PlayerVoteComplete> votes = entry.getValue();
			for (PlayerVoteComplete voteBean : votes) {
				v = populateVote(voteBean, serieASeasonDay, voteSource);
				voteEnts.add(v);
			}
		}
		
		voteRepo.save(voteEnts);
	}



	private Vote populateVote(PlayerVoteComplete voteBean, Integer serieASeasonDay, VotesSourceEnum voteSource) {
		Vote ent = new Vote();
		ent.setSerieASeasonDay(serieASeasonDay);
		ent.setSource(voteSource.name());
		
		ent.setAutogoals(voteBean.getAutogoals());
		ent.setEvenGoal(voteBean.getEvenGoal());
		ent.setMissedPenalties(voteBean.getMissedPenalties());
		ent.setMovementAssists(voteBean.getMovementAssists());
		ent.setName(voteBean.getName());
		ent.setRedCard(voteBean.getRedCard());
		ent.setRole(voteBean.getRole().toString());
		ent.setSavedPenalties(voteBean.getSavedPenalties());
		ent.setScoredGoals(voteBean.getScoredGoals());
		ent.setScoredPenalties(voteBean.getScoredPenalties());
		ent.setStationaryAssists(voteBean.getStationaryAssists());
		ent.setSubIn(voteBean.getSubIn());
		ent.setSubOut(voteBean.getSubOut());
		ent.setTakenGoals(voteBean.getTakenGoals());
		ent.setTeam(voteBean.getTeam());
		ent.setVote(voteBean.getVote());
		ent.setWinGoal(voteBean.getWinGoal());
		ent.setYellowCard(voteBean.getYellowCard());
		
		return ent;
	}



	public List<String> findAllSerieATeam() {
		List<String> seasonDays = voteRepo.findAllSerieATeam();
		if (seasonDays.isEmpty())
			return null;
		return seasonDays;
	}

	
	
	
}
