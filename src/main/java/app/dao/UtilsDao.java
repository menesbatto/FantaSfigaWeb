package app.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.entity.Permutation;
import app.dao.entity.Postponement;
import app.dao.entity.Vote;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.RoleEnum;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic.model.PostponementBean;

@Service
@EnableCaching
public class UtilsDao {

	@Autowired
	private PermutationRepo permutationRepo;
	
	@Autowired
	private VoteRepo voteRepo;

	@Autowired
	private PostponementRepo postponementRepo;

	public void savePermutations(Integer playersNumber, List<String> calendarPermutations) {
		Permutation ent = permutationRepo.findByPlayers(playersNumber);
		if (ent != null) {
			return;
		}
		
		String permutations = "";
		for (String perm : calendarPermutations) {
			permutations += perm + ",";
		}
		ent = new Permutation();
		ent.setPlayers(playersNumber);
		ent.setPermutations(permutations);
		permutationRepo.save(ent);
	}
	
	public List<String> findPermutations(Integer playersNumbers) {
		List<String> permutations = new ArrayList<String>();
		Permutation ent = permutationRepo.findByPlayers(playersNumbers);
		String permutationsString = ent.getPermutations();
		String[] split = permutationsString.split(",");
		for (int i = 0; i < split.length; i++) {
			permutations.add(split[i]);
		}
		
		return permutations;
	}

	
	public Map<String, Map<String, List<PlayerVoteComplete>>> findVotesBySource(VotesSourceEnum source) {
		
		List<Vote> votes = voteRepo.findBySourceOrderBySerieASeasonDayAsc(source.name());
//		List<Vote> votesToRemove = new ArrayList<Vote>();
//		for (Vote v : votes) {
//			if (v.getSerieASeasonDay().equals(21) || v.getSerieASeasonDay().equals(20)) {
//				votesToRemove.add(v);
//			}
//		}
//		System.out.println();
//		voteRepo.delete(votesToRemove);
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

	public PlayerVoteComplete createPlayerVote(Vote voteEnt) {
		
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
	
	@Transactional
	public int calculateLastSerieASeasonDayCalculated() {
//		voteRepo.deleteBySerieASeasonDay(21);

		List<Integer> seasonDays = voteRepo.findAllDistinct();
		if (seasonDays.isEmpty())
			return 0;
		
		return Collections.max(seasonDays);
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



	public Vote populateVote(PlayerVoteComplete voteBean, Integer serieASeasonDay, VotesSourceEnum voteSource) {
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


	public void insertPostponement(PostponementBean bean) {
		
		Postponement ent = postponementRepo.findByHomeTeamAndAwayTeamAndSeasonDay(bean.getHomeTeam(), bean.getAwayTeam(), bean.getSeasonDay());
		if (ent == null) {
			ent = new Postponement();
			ent.setAwayTeam(bean.getAwayTeam());
			ent.setHomeTeam(bean.getHomeTeam());
			ent.setSeasonDay(bean.getSeasonDay());
		}
		ent.setPlayed(bean.getPlayed());
		postponementRepo.save(ent);
		
			
	}
	
	public void removePostponement(PostponementBean bean) {
		
		Postponement ent = postponementRepo.findByHomeTeamAndAwayTeamAndSeasonDay(bean.getHomeTeam(), bean.getAwayTeam(), bean.getSeasonDay());
		
		postponementRepo.delete(ent);
		
			
	}
	
	public List<PostponementBean> retrievePostponements() {
		List<PostponementBean> postponements = new ArrayList<PostponementBean>();
		
		List<Postponement> ents = postponementRepo.findAll();
		PostponementBean bean;
		for (Postponement ent: ents) {
			bean = new PostponementBean();
			bean.setAwayTeam(ent.getAwayTeam());
			bean.setHomeTeam(ent.getHomeTeam());
			bean.setSeasonDay(ent.getSeasonDay());
			bean.setPlayed(ent.getPlayed());
			postponements.add(bean);
		}
		
		return postponements;
	}
	
	public Map<Integer, List<PostponementBean>> findAllPostponement() {
		Map<Integer, List<PostponementBean>> map = new HashMap<Integer, List<PostponementBean>>();
		
		List<Postponement> ents = postponementRepo.findAll();
		for (Postponement ent : ents) {
			
			PostponementBean bean = createPostponementBean(ent);
			Integer seasonDay = ent.getSeasonDay();
			List<PostponementBean> beans =  map.get(seasonDay);
			if (beans == null) {
				beans = new ArrayList<PostponementBean>();
				map.put(seasonDay, beans);
			}
			beans.add(bean);
			
		}
		return map;
		
	}

	private PostponementBean createPostponementBean(Postponement ent) {
		PostponementBean bean = new PostponementBean();
		bean.setAwayTeam(ent.getAwayTeam());
		bean.setHomeTeam(ent.getHomeTeam());
		bean.setSeasonDay(ent.getSeasonDay());
		bean.setPlayed(ent.getPlayed());
		return bean;
	}

	@Transactional
	public void removeSeasonDaysVotes(Set<Integer> keySet) {
		for (Integer serieASeasonDay : keySet)
			voteRepo.deleteBySerieASeasonDay(serieASeasonDay);
		
	}


	
	
}
