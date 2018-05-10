package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Competition;
import app.dao.entity.League;
import app.dao.entity.Mismatch;
import app.dao.entity.User;

@RepositoryRestResource
public interface MismatchRepo extends JpaRepository<Mismatch, Long> {

	Mismatch findByCompetition(Competition competition);
	
	


}
