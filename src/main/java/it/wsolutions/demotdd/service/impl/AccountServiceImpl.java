package it.wsolutions.demotdd.service.impl;

import it.wsolutions.demotdd.gateway.PayPallService;
import it.wsolutions.demotdd.model.Account;
import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.repository.AccountRepository;
import it.wsolutions.demotdd.service.AccountNotFoundError;
import it.wsolutions.demotdd.service.InvalidAccountError;
import it.wsolutions.demotdd.service.api.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
  private final PayPallService payPallService;
  private final AccountRepository accountRepository;

  @Override
  public BankAccount getBankAccount(int accountId) {

    validate(accountId);

    Account account = getAccount(accountId);

    BigDecimal balance = getBalance(accountId, account);

    return new BankAccount(account.getCurrency(), account.getName(), balance);
  }

  private Account getAccount(int accountId) {
    return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundError(accountId));
  }

  private void validate(int accountId) {
    if (accountId < 1000) {
      throw new InvalidAccountError(accountId);
    }
  }

  private BigDecimal getBalance(int accountId, Account account) {
    BigDecimal balance = BigDecimal.ZERO;
    try {
      balance = payPallService.getBalance(accountId);
    }
    catch (AccountNotFoundError error) {
      log.error("Error retrieving account: " + account, error);
    }
    return balance;
  }

}
