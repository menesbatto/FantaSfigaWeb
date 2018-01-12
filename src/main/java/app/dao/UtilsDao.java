package app.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.dao.entity.Matcho;
import app.dao.entity.Permutation;
import app.dao.entity.Season;
import app.dao.entity.SeasonDay;
import app.logic._1_seasonPatternExtractor.model.MatchBean;
import app.logic._1_seasonPatternExtractor.model.SeasonBean;
import app.logic._1_seasonPatternExtractor.model.SeasonDayBean;

@Service
@EnableCaching
public class UtilsDao {

	@Autowired
	private PermutationRepo permutationRepo;

	public void savePermutations(Integer playersNumber, List<String> calendarPermutations) {
		String permutations = "";
		for (String perm : calendarPermutations) {
			permutations += perm + ",";
		}
		Permutation ent = new Permutation();
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

	
	
}
