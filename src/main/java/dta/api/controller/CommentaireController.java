package dta.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dta.api.entities.Commentaire;
import dta.api.repository.CollegueRepository;
import dta.api.repository.CommentaireRepository;

@RestController
@RequestMapping("commentaires")
public class CommentaireController {
	@Autowired
	CommentaireRepository commRepo;

	@Autowired
	CollegueRepository colRepo;
	
	@GetMapping
	public List<Commentaire> getComments() {
		return commRepo.findAll();
	}

	@PostMapping
	public Commentaire addComment(@RequestBody Map<String,String> comObj) {
		Commentaire com = new Commentaire();
		com.setText(comObj.get("text"));
		com.setCollegue(colRepo.findByPseudo(comObj.get("pseudo")).orElse(null));
		return commRepo.save(com);
	}
}
