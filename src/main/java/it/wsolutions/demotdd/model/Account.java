package it.wsolutions.demotdd.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
  @Id
  @GeneratedValue
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
