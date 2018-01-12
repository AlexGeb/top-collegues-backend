package dta.api.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import dta.api.models.Action;

@Entity
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@OneToOne
	private Collegue voteur;

	@OneToOne
	private Collegue voteFor;

	@Column
	private Action action;

	@Column
	private LocalDateTime date;

	public Vote() {
		date = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Collegue getVoteur() {
		return voteur;
	}

	public void setVoteur(Collegue voteur) {
		this.voteur = voteur;
	}

	public Collegue getVoteFor() {
		return voteFor;
	}

	public void setVoteFor(Collegue voteFor) {
		this.voteFor = voteFor;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
