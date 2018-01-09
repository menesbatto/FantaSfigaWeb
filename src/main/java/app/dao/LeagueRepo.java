package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.League;
import app.dao.entity.User;

@RepositoryRestResource
public interface LeagueRepo extends JpaRepository<League, Long> {

	League findByUserAndShortName(User user, String name);

	
	


}
