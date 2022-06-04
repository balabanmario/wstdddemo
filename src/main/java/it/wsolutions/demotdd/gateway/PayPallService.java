package it.wsolutions.demotdd.gateway;

import java.math.BigDecimal;

public interface PayPallService {
  BigDecimal getBalance(int accountId);
}
