package it.wsolutions.demotdd.service.impl;

import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.service.api.AccountService;
import it.wsolutions.demotdd.service.InvalidAccountError;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
  @Override
  public BankAccount getBankAccount(int accountId) {
    if (accountId < 1000) {
      throw new InvalidAccountError();
    }
    return new BankAccount("EUR", "Primary Account", BigDecimal.TEN);
  }

}
