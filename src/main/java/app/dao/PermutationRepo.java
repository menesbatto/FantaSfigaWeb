package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Permutation;

@RepositoryRestResource
public interface PermutationRepo extends JpaRepository<Permutation, Long> {

	Permutation findByPlayers(Integer players);

}
