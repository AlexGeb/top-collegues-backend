package dta.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dta.api.entities.Account;
import dta.api.repository.AccountRepository;

@Service
public class BackendAvailableService {
	private boolean ready = false;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AccountRepository accountRepository;

	public boolean isBackendReady() {
		return ready;
	}

	public void setIsReady(boolean isready) {
		if (accountRepository.findByUsername("user") == null) {
			Account account = new Account();
			account.setUsername("user");
			account.setPassword(bCryptPasswordEncoder.encode("password"));
			accountRepository.save(account);
		}
		ready = isready;
	}
}
  