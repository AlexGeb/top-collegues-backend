package dta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dta.api.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	public Account findByUsername(String username);
}
