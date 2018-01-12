package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Competition;
import app.dao.entity.Season;

@RepositoryRestResource
public interface SeasonRepo extends JpaRepository<Season, Long> {

	Season findByNameAndCompetition(String name, Competition competition);

}
