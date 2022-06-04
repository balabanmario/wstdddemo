package it.wsolutions.demotdd.service.impl;

import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.service.AccountService;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
  @Override
  public BankAccount getBankAccount(int accountId) {
    return new BankAccount("EUR", "Primary Account", BigDecimal.TEN);
  }

}
