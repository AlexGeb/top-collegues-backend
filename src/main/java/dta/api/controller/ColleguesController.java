package dta.api.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dta.api.entities.Collegue;
import dta.api.repository.CollegueRepository;
import dta.api.exceptions.CollegueNotFoundException;

@RestController
@RequestMapping("/api/collegues")
public class ColleguesController {

	private final CollegueRepository collegueRepo;

	/**
	 * @param collaborateursRepo
	 * @param departementRepo
	 */
	@Autowired
	ColleguesController(CollegueRepository collegueRepo) {
		this.collegueRepo = collegueRepo;
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
			throws CollegueNotFoundException {
		Optional<Collegue> colOpt = collegueRepo.findByPseudo(pseudo);
		if (colOpt.isPresent()) {
			Collegue col = colOpt.get();
			if (action.get("action").equals("aimer")) {
				col.setScore(col.getScore() + 10);
			} else {
				col.setScore(col.getScore() - 5);
			}
			collegueRepo.save(col);
			return col;
		} else {
			throw new CollegueNotFoundException("collegue " + pseudo + " inconnu");
		}
	}

}
