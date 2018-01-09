package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.League;
import app.dao.entity.Rules;

@RepositoryRestResource
public interface RulesRepo extends JpaRepository<Rules, Long> {

	Rules findByLeague(League league);

	Rules findByLeagueAndBasic(League league, Boolean basic);
}
