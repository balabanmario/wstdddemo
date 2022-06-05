package it.wsolutions.demotdd.integration;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class PayPallServiceIT {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8181);



  @Test
  public void changeStateOnEachCallTest() throws IOException {
    stubFor(get(urlPathMatching("/balance/.*"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("\"balance\": \"4321\"")));


    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet request = new HttpGet("http://localhost:8181/balance/123");
    HttpResponse httpResponse = httpClient.execute(request);
    String stringResponse = convertHttpResponseToString(httpResponse);

    verify(getRequestedFor(urlEqualTo("/balance/123")));
    assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    assertEquals("application/json", httpResponse.getFirstHeader("Content-Type").getValue());
    assertEquals("\"balance\": \"4321\"", stringResponse);
  }

  private String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
    InputStream inputStream = httpResponse.getEntity().getContent();
    return convertInputStreamToString(inputStream);
  }

  private String convertInputStreamToString(InputStream inputStream) {
    Scanner scanner = new Scanner(inputStream, "UTF-8");
    String string = scanner.useDelimiter("\\Z").next();
    scanner.close();
    return string;
  }
}
