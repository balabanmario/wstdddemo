package it.wsolutions.demotdd.service;

public class AccountNotFoundError extends RuntimeException {

  public AccountNotFoundError(long accountId) {
    super("No Account found with accountId : "+accountId);
  }
}
