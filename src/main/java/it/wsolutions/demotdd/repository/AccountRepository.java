package it.wsolutions.demotdd.repository;

import it.wsolutions.demotdd.model.Account;
import it.wsolutions.demotdd.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
