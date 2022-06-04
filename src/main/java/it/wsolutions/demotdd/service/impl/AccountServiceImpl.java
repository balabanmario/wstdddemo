package it.wsolutions.demotdd.service.impl;

import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.repository.AccountRepository;
import it.wsolutions.demotdd.service.AccountNotFoundError;
import it.wsolutions.demotdd.service.InvalidAccountError;
import it.wsolutions.demotdd.service.api.AccountService;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
  private AccountRepository accountRepository;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public BankAccount getBankAccount(int accountId) {
    if (accountId < 1000) {
      throw new InvalidAccountError();
    }

    if (!accountRepository.existsById(accountId)) {
      throw new AccountNotFoundError();
    }

    return new BankAccount("EUR", "Primary Account", BigDecimal.TEN);
  }

}
