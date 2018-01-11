package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Competition;
import app.dao.entity.League;
import app.dao.entity.Rules;
import app.dao.entity.SeasonResult;

@RepositoryRestResource
public interface SeasonResultRepo extends JpaRepository<SeasonResult, Long> {

}
