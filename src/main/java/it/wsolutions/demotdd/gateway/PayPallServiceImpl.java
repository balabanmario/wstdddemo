package it.wsolutions.demotdd.gateway;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayPallServiceImpl implements PayPallService {
  @Override
  public BigDecimal getBalance(int accountId) {
    //TODO implement this
    return BigDecimal.valueOf(3414);
  }
}
