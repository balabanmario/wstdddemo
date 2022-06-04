package it.wsolutions.demotdd.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BankAccount {
  String currency;
  String name;
  BigDecimal balance;
}
