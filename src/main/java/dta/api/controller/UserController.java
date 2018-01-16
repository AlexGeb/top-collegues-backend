package dta.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dta.api.entities.Account;
import dta.api.repository.AccountRepository;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/sign-up")
	public void signUp(@RequestBody Account user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		accountRepository.save(user);
	}
}
