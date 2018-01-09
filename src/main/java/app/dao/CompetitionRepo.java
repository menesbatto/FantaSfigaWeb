package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Competition;
import app.dao.entity.League;

@RepositoryRestResource
public interface CompetitionRepo extends JpaRepository<Competition, Long> {

	Competition findByLeagueAndShortName(League league, String shortName);

	List<Competition> findByLeague(League league);

	
	


}
