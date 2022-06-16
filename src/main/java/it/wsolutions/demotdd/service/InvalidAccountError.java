package it.wsolutions.demotdd.service;

public class InvalidAccountError extends RuntimeException {
  public InvalidAccountError(long accountId) {
    super("Invalid  accountId : "+accountId);
  }
}
