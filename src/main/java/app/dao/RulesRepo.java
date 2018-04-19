package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Competition;
import app.dao.entity.League;
import app.dao.entity.Rules;

@RepositoryRestResource
public interface RulesRepo extends JpaRepository<Rules, Long> {

	List<Rules> findByLeague(League league);
	Rules findByCompetitionAndType(Competition competition, String type);

}
