package it.wsolutions.demotdd.service.impl;

import it.wsolutions.demotdd.gateway.PayPallService;
import it.wsolutions.demotdd.model.Account;
import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.repository.AccountRepository;
import it.wsolutions.demotdd.service.AccountNotFoundError;
import it.wsolutions.demotdd.service.InvalidAccountError;
import it.wsolutions.demotdd.service.api.AccountService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final PayPallService payPallService;
  private final AccountRepository accountRepository;

  @Override
  public BankAccount getBankAccount(int accountId) {
    if (accountId < 1000) {
      throw new InvalidAccountError();
    }

    Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundError(accountId));
    BigDecimal balance = payPallService.getBalance(accountId);
    return new BankAccount(account.getCurrency(), account.getName(), balance);
  }

}
