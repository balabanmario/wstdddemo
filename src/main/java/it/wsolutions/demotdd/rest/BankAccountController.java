package it.wsolutions.demotdd.rest;

import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.service.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

  @Autowired
  private AccountService accountService;

  @GetMapping("/account/{id}")
  ResponseEntity<BankAccount> getBankAccount(@PathVariable int id) {
    BankAccount bankAccount = accountService.getBankAccount(id);

    return new ResponseEntity<>(bankAccount, HttpStatus.OK);
  }
}