package dta.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dta.api.entities.Collegue;

public interface CollegueRepository extends JpaRepository<Collegue, Integer> {
	Optional<Collegue> findByPseudo(String pseudo);
}
