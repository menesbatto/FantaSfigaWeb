package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Competition;
import app.dao.entity.SeasonFromWeb;

@RepositoryRestResource
public interface SeasonFromWebRepo extends JpaRepository<SeasonFromWeb, Long> {

	SeasonFromWeb findByCompetition(Competition competition);

	void deleteByCompetition(Competition competition);

//	League findByUserAndShortName(User user, String name);

	
	


}
