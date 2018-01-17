package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Postponement;

@RepositoryRestResource
public interface PostponementRepo extends JpaRepository<Postponement, Long> {

	
	


}
