package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Person;

@RepositoryRestResource
public interface PersonRepo extends JpaRepository<Person, Long> {

	Person findById(Long id);

}
