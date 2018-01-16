package dta.api.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dta.api.entities.Collegue;
import dta.api.entities.Vote;
import dta.api.repository.CollegueRepository;
import dta.api.repository.VoteRepository;
import dta.api.services.BackendAvailableService;
import dta.api.services.HistoriqueWebsocketService;
import dta.api.exceptions.CollegueNotFoundException;
import dta.api.models.Action;

@RestController
@RequestMapping("collegues")
public class ColleguesController {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	BackendAvailableService availableService;

	private final CollegueRepository collegueRepo;
	private final VoteRepository voteRepo;
	private final HistoriqueWebsocketService historiqueWebSocketSvc;

	@Autowired
	ColleguesController(CollegueRepository collegueRepo, VoteRepository voteRepo,
			HistoriqueWebsocketService historiqueWebSocketSvc) {
		this.collegueRepo = collegueRepo;
		this.voteRepo = voteRepo;
		this.historiqueWebSocketSvc = historiqueWebSocketSvc;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Collegue> getAllCollegues() {
		List<Collegue> collegues = this.collegueRepo.findAll();
		collegues.sort((c1, c2) -> {
			return -c1.getId().compareTo(c2.getId());
		});
		return collegues;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{pseudo}")
	public Collegue getOneCollegue(@PathVariable("pseudo") String pseudo) {
		return this.collegueRepo.findByPseudo(pseudo).orElse(null);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Collegue addCollegue(@RequestBody Collegue collegue) {
		System.out.println(collegue.getPseudo());
		return this.collegueRepo.save(collegue);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{pseudo}")
	public Collegue likeOrHateAction(@PathVariable String pseudo, @RequestBody Map<String, String> action)
			throws CollegueNotFoundException, JsonProcessingException {
		Optional<Collegue> colOpt = collegueRepo.findByPseudo(pseudo);
		if (colOpt.isPresent()) {
			Vote vote = new Vote();
			Collegue col = colOpt.get();
			if (action.get("action").equals("aimer")) {
				col.setScore(col.getScore() + 10);
				vote.setAction(Action.AIMER);
			} else {
				col.setScore(col.getScore() - 5);
				vote.setAction(Action.DETESTER);
			}
			collegueRepo.save(col);
			vote.setVoteFor(col);
			vote.setActualScore(col.getScore());
			voteRepo.save(vote);
			historiqueWebSocketSvc.sendMessage(new TextMessage(objectMapper.writeValueAsString(vote)));
			return col;
		} else {
			throw new CollegueNotFoundException("collegue " + pseudo + " inconnu");
		}
	}

	@GetMapping("/ping")
	public void ping(HttpServletResponse response) {
		if (availableService.isBackendReady()) {
			response.setStatus(204); // backend ready
		} else {
			response.setStatus(503); // service unavailable
		}
	}
}
