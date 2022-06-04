package it.wsolutions.demotdd.service;

import it.wsolutions.demotdd.gateway.PayPallService;
import it.wsolutions.demotdd.model.Account;
import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.repository.AccountRepository;
import it.wsolutions.demotdd.service.impl.AccountServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

  private static AccountServiceImpl accountService;
  private static AccountRepository accountRepository;
  private static PayPallService payPallService;

  @BeforeClass
  public static void setUp() {
    accountRepository = Mockito.mock(AccountRepository.class);
    payPallService = Mockito.mock(PayPallService.class);
    accountService = new AccountServiceImpl(payPallService, accountRepository);
  }

  /**
   * UC1:
   * <p>
   * Dato un BankAccount (con accountId1) di cui l'anagrafica esiste sul
   * sistema Core e il cui riferimento (payPalRefId) esiste sul
   * sistema PayPal
   * <p>
   * Quando il servizio di recupero informazioni
   * viene chiamata con accountId1
   * <p>
   * Allora il risultato atteso è  il BankAccount completa
   * d'informazioni anagrafici Core (valuta, nome conto, accountId) e di credito (saldo) PayPall
   */

  @Test
  public void givenExistingAccount_returnFullDataWithBalance() {

    //arrange
    int accountId = 1000;
    Account account = new Account("EUR", "Primary Account");

    when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(account));
    when(payPallService.getBalance(accountId)).thenReturn(BigDecimal.TEN);

    //execute
    BankAccount bankAccount = getAccountService().getBankAccount(accountId);

    //assert
    assertThat(bankAccount.getCurrency()).isEqualTo("EUR");
    assertThat(bankAccount.getName()).isEqualTo("Primary Account");
    assertThat(bankAccount.getBalance()).isEqualTo(BigDecimal.TEN);

  }

  private AccountServiceImpl getAccountService() {
    return accountService;
  }

  /**
   * UC2:
   * <p>
   * Dato un accountId1 che non è valido (minore di 0)
   * <p>
   * Quando il servizio di recupero informazioni
   * viene chiamata con accountId1
   * <p>
   * Allora viene restituito un errore
   */

  @Test(expected = InvalidAccountError.class)
  public void givenInvalidAccount_returnInvalidAccountError() {
    int accountId = -1000;

    BankAccount bankAccount = getAccountService().getBankAccount(accountId);

  }

  /**
   * UC2:
   * <p>
   * Dato un accountId1 che non è valido (minore di 999)
   * <p>
   * Quando il servizio di recupero informazioni
   * viene chiamata con accountId1
   * <p>
   * Allora viene restituito un errore
   */

  @Test(expected = InvalidAccountError.class)
  public void givenAccountBelow999_returnInvalidAccountError() {
    int accountId = 999;

    BankAccount bankAccount = getAccountService().getBankAccount(accountId);

  }

  /**
   * UC3:
   * <p>
   * Dato un accountId1 che non è esiste
   * <p>
   * Quando il servizio di recupero informazioni
   * viene chiamata con accountId1
   * <p>
   * Allora viene restituito un errore
   */

  @Test(expected = AccountNotFoundError.class)
  public void givenNotExistingAccount_returnAccountNotFoundError() {
    int accountId = 1234;

    BankAccount bankAccount = getAccountService().getBankAccount(accountId);

  }


  /**
   * UC4:
   * Dato un BankAccount (con accountId1) di cui l'anagrafica esiste sul
   * sistema Core e il cui riferimento (payPalRefId) NON esiste sul
   * sistema PayPal
   * <p>
   * Quando il servizio di recupero informazioni
   * viene chiamata con accountId1
   * <p>
   * Allora il risultato atteso è  il BankAccount completa
   * d'informazioni anagrafici Core (valuta, nome conto, accountId) e credito ZERO PayPall
   */

  @Test
  public void givenNotExistingPayPallAccount_returnAccountWithBalanceZero() {
    //arrange
    int accountId = 2000;
    Account account = new Account("USD", "Secondary Account");

    when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(account));
    doThrow(new AccountNotFoundError(accountId)).when(payPallService).getBalance(accountId);

    //execute
    BankAccount bankAccount = getAccountService().getBankAccount(accountId);

    //assert
    assertThat(bankAccount.getCurrency()).isEqualTo("USD");
    assertThat(bankAccount.getName()).isEqualTo("Secondary Account");
    assertThat(bankAccount.getBalance()).isEqualTo(BigDecimal.ZERO);

  }

}