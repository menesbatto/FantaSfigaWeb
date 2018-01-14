package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Ranking;

@RepositoryRestResource
public interface RankingRepo extends JpaRepository<Ranking, Long> {

}
