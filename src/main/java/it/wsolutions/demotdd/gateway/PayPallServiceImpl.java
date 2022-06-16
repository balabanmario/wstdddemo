package it.wsolutions.demotdd.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

@Service
public class PayPallServiceImpl implements PayPallService {
  @Override
  public BigDecimal getBalance(long accountId) {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet request = new HttpGet("http://localhost:8181/balance/"+accountId);
    HttpResponse httpResponse;
    try {
      httpResponse = httpClient.execute(request);
      String responseString = convertResponseToString(httpResponse);
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(responseString);
      String balance = node.get("balance").asText();

      return BigDecimal.valueOf(Long.parseLong(balance));
    }
    catch (IOException e) {
      e.printStackTrace();
    }


    return BigDecimal.valueOf(3414);
  }

  private String convertResponseToString(HttpResponse response) throws IOException {
    InputStream responseStream = response.getEntity().getContent();
    Scanner scanner = new Scanner(responseStream, "UTF-8");
    String responseString = scanner.useDelimiter("\\Z").next();
    scanner.close();
    return responseString;
  }
}
