package app.logic._0_permutationsGenerator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.UtilsDao;

@Service
public class PermutationsGenerator {

	@Autowired
	private UtilsDao utilsDao;
	
	private static Integer i = 0;
	
	public void createPermutations(Integer playersNumber) {
		List<String> calendarPermutations = new ArrayList<String>();
		String base = "ABCDEFGHILMNOPQRSTUV";
		
		String correct = base.substring(0, playersNumber);
		permutation(correct, calendarPermutations);
		
		utilsDao.savePermutations(playersNumber, calendarPermutations);
//		IOUtils.write(AppConstants.ALL_INPUT_PERMUTATIONS_DIR + AppConstants.ALL_INPUT_PERMUTATIONS_FILE_NAME , calendarPermutations);
	}
	
	public static void permutation(String str, List<String> list) { 
	    permutation("", str, list); 
	}

	private static void permutation(String prefix, String str, List<String> list) {
	    int n = str.length();
	    if (n == 0) {
//	    	System.out.println(prefix);
	    	list.add(prefix);
//	    	System.out.println(i++);
	    }
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), list);
	    }
	}
	
	public List<String> getAllPermutations(Integer playersNumber) {
		
		List<String> permutations = utilsDao.findPermutations(playersNumber);

//		ArrayList<String> players = IOUtils.read(AppConstants.ALL_INPUT_PERMUTATIONS_DIR + AppConstants.ALL_INPUT_PERMUTATIONS_FILE_NAME, ArrayList.class);
		return permutations;
	}
}
