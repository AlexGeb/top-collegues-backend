package dta.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dta.api.entities.Vote;
import dta.api.repository.VoteRepository;

@RestController
@RequestMapping("/api/votes")
public class VotesController {
	private final VoteRepository voteRepo;

	@Autowired
	VotesController(VoteRepository voteRepo) {
		this.voteRepo = voteRepo;
	}

	@GetMapping
	public List<Vote> getAllVotes() {
		return voteRepo.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "since" })
	public List<Vote> getVotes(@RequestParam("since") Optional<Integer> since) {
		if (since.isPresent()) {
			return voteRepo.findVoteSince(since.get());
		} else {
			return voteRepo.findAll();
		}
	}
}
