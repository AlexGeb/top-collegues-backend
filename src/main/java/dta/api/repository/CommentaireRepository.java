package dta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dta.api.entities.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {

}
