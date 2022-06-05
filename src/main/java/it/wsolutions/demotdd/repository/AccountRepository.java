package it.wsolutions.demotdd.repository;

import it.wsolutions.demotdd.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  @Query("select u from Account u where u.name = :name")
  Account findByNameCustomQuery(@Param("name") String name);

  @Query(
      value = "select * from Account as u where u.name = :name",
      nativeQuery = true)
  Account findByNameNativeQuery(@Param("name") String name);
}
