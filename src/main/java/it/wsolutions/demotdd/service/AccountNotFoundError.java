package it.wsolutions.demotdd.service;

public class AccountNotFoundError extends RuntimeException {
  private final int accountId;

  public AccountNotFoundError(int accountId) {
    this.accountId = accountId;
  }
}
