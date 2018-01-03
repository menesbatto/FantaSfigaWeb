package app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.dao.entity.Person;

@Service
@EnableCaching
public class PersonDao {

	@Autowired
	private PersonRepo personRepo;

	
	@Cacheable("person")
	public Person findById(Long id) {
		Person p1 = new Person();
		p1.setFirstName("Alfio");
		p1.setLastName("Ciao");
		personRepo.save(p1);
		Person p = personRepo.findById(id);
		return p;
	}
	
	
	
}
