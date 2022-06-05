package it.wsolutions.demotdd.gateway;

import java.io.IOException;
import java.math.BigDecimal;

public interface PayPallService {
  BigDecimal getBalance(int accountId);
}
