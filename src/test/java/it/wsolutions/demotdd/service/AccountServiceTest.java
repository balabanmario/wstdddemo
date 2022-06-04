package it.wsolutions.demotdd.service;

import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.service.impl.AccountServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * UC1:
 *
 * Dato un BankAccount (con accountId1) di cui l'anagrafica esiste sul
 * sistema Core e il cui rifferimento (payPalRefId) esiste sul
 * sistema PayPal
 *
 * Quando il servizio di recupero informazioni
 * viene chiamata con accountId1
 *
 * Allora il risultato atteso è  il BankAccount completa
 *  di informazioni angarifici (valuta, nome conto, accountId,) e di credito (saldo)
 */

public class AccountServiceTest {

  @Test
  public void givenExistingAccount_returnFullDataWithBalance() {
    int accountId = 1000;

    BankAccount bankAccount= new AccountServiceImpl().getBankAccount(accountId);

    assertThat(bankAccount.getCurrency()).isEqualTo("EUR");
    assertThat(bankAccount.getName()).isEqualTo("Primary Account");
    assertThat(bankAccount.getBalance()).isEqualTo(BigDecimal.TEN);

  }

  @Test(expected = InvalidAccountError.class)
  public void givenInvalidAccount_returnInvalidAccountError() {
    int accountId = -1000;

    BankAccount bankAccount= new AccountServiceImpl().getBankAccount(accountId);

  }
}