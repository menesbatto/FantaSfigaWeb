package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.dao.PersonDao;
import app.dao.entity.Person;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class FacadeController {


	@Autowired
	private PersonDao personDao;

	
	// ###################################################
	// ##########            1                ############
	// ###################################################
	
	@RequestMapping(value = "/findPerson", method = RequestMethod.GET)
	public ResponseEntity<String> findPerson() {
		Person p = personDao.findById(1L);
		String body = "Find Person COMPLETED";

		ResponseEntity<String> response = new ResponseEntity<String>(p.toString(), HttpStatus.OK);
		return response;
	}
	
	
}