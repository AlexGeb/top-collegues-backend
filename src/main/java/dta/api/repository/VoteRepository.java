package dta.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dta.api.entities.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT v FROM Vote v WHERE v.id>:since")
    public List<Vote> findVoteSince(@Param("since") Integer since);
}
