package it.wsolutions.demotdd.integration;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import it.wsolutions.demotdd.model.Account;
import it.wsolutions.demotdd.repository.AccountRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is an Integration Test that should check
 * a Complete UseCase involving all its dependencies.
 * External dependencies and DB data must be mocked.
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayPallServiceIT {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8181);

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void changeStateOnEachCallTest() throws Exception {
    //arrange
    Account entity = accountRepository.save(new Account("EUR", "MyAccount"));
    Long accountId = entity.getId();

    stubFor(get(urlPathMatching("/balance/.*")).willReturn(aResponse().withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"balance\": \"123123\"}")));

    //execute
    ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/account/" + accountId).contentType(MediaType.APPLICATION_JSON));

    //assert
    actions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.currency", is("EUR")))
        .andExpect(jsonPath("$.name", is("MyAccount")))
        .andExpect(jsonPath("$.balance", is(123123)))
        .andDo(print());

    verify(getRequestedFor(urlEqualTo("/balance/" + accountId)));
  }

}
