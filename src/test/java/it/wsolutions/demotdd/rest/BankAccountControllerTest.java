package it.wsolutions.demotdd.rest;

import it.wsolutions.demotdd.model.BankAccount;
import it.wsolutions.demotdd.service.AccountNotFoundError;
import it.wsolutions.demotdd.service.InvalidAccountError;
import it.wsolutions.demotdd.service.api.AccountService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@RunWith(SpringRunner.class)
public class BankAccountControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

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
  public void givenExistingAccount_returnFullDataWithBalance() throws Exception {

    //prepare
    BankAccount bankAccount = new BankAccount("CHF", "MyAccount", BigDecimal.ONE);
    when(accountService.getBankAccount(1234)).thenReturn(bankAccount);

    //execute
    mockMvc.perform(MockMvcRequestBuilders.get("/account/1234").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.currency", is("CHF")))
        .andExpect(jsonPath("$.name", is("MyAccount")))
        .andExpect(jsonPath("$.balance", is(1)))
        .andDo(print());
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
  @Test
  public void givenNotExistingAccount_return404AccountNotFoundError() throws Exception {

    //prepare
    int accountId = 1234;
    doThrow(new AccountNotFoundError(accountId)).when(accountService).getBankAccount(accountId);

    //execute
    mockMvc.perform(MockMvcRequestBuilders.get("/account/" + accountId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.ex", is("No Account found with accountId : " + accountId)))
        .andDo(print());
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
  @Test
  public void givenAccountBelow999_return400InvalidAccountError() throws Exception {

    //prepare
    int accountId = 9;
    doThrow(new InvalidAccountError(accountId)).when(accountService).getBankAccount(accountId);

    //execute
    mockMvc.perform(MockMvcRequestBuilders.get("/account/" + accountId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.ex", is("Invalid  accountId : " + accountId)))
        .andDo(print());
  }
}
