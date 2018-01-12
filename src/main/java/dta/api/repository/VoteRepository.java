package dta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dta.api.entities.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

}
