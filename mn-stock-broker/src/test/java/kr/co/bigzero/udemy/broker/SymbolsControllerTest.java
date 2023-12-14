package kr.co.bigzero.udemy.broker;

import com.fasterxml.jackson.databind.JsonNode;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@MicronautTest
public class SymbolsControllerTest {

  @Inject
  @Client("/symbols")
  HttpClient client;

  @Test
  void symbolsEndpointReturnsListOfSymbol() {
    var response = client.toBlocking().exchange("/", JsonNode.class);
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(10, response.getBody().get().size());
  }
}
