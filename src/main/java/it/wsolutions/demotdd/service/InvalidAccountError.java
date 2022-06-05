package it.wsolutions.demotdd.service;

public class InvalidAccountError extends RuntimeException {
  public InvalidAccountError(int accountId) {
    super("Invalid  accountId : "+accountId);
  }
}
