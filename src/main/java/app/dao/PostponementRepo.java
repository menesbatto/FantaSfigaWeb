package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Postponement;

@RepositoryRestResource
public interface PostponementRepo extends JpaRepository<Postponement, Long> {

	Postponement findByHomeTeamAndAwayTeamAndSeasonDay(String homeTeam, String awayTeam, Integer seasonDay);
	//findByLastnameIsNullAndFirstnam
	List<Postponement> findByWaitIsNull();

	
	


}
