package it.wsolutions.demotdd.service;

public class AccountNotFoundError extends RuntimeException {

  public AccountNotFoundError(int accountId) {
    super("No Account found with accountId : "+accountId);
  }
}
