package it.wsolutions.demotdd.model;


import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
public class Account {
  @TableGenerator(name = "Account_Gen", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "Addr_Gen", initialValue = 10000, allocationSize = 100)
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "Account_Gen")
  @Column(name = "id", nullable = false)
  private Long id;

  String currency;
  String name;

  public Account() {
  }

  public Account(Long id, String currency, String name) {
    this.id = id;
    this.currency = currency;
    this.name = name;
  }

  public Account( String currency, String name) {
    this.currency = currency;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
