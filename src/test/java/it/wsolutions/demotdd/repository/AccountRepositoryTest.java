package it.wsolutions.demotdd.repository;

import it.wsolutions.demotdd.model.Account;
import it.wsolutions.demotdd.repository.AccountRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountRepositoryTest {

  @Autowired private DataSource dataSource;
  @Autowired private EntityManager entityManager;
  @Autowired private AccountRepository accountRepository;

  @Test
  public void injectedComponentsAreNotNull(){
    assertThat(dataSource).isNotNull();
    assertThat(entityManager).isNotNull();
    assertThat(accountRepository).isNotNull();
  }

  @Test
  public void whenSaved_thenFindByNameCustomQueryIsNotNull() {
    Account entity = new Account("EUR", "custom name");
    accountRepository.save(entity);
    assertThat(accountRepository.findByNameCustomQuery("custom name")).isNotNull();
  }

  @Test
  public void whenSaved_thenFindByNameNativeQueryIsNotNull() {
    Account entity = new Account("EUR", "custom native");
    accountRepository.save(entity);
    assertThat(accountRepository.findByNameNativeQuery("custom native")).isNotNull();
  }


}