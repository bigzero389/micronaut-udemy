package kr.co.bigzero.udemy.broker.watchlist;

import com.fasterxml.jackson.databind.JsonNode;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import kr.co.bigzero.udemy.broker.data.InMemoryAccountStore;
import kr.co.bigzero.udemy.broker.symbol.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.stream.Stream;

import static io.micronaut.http.HttpRequest.GET;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class WatchListControllerTest {
  private static final Logger LOG = LoggerFactory.getLogger(WatchListControllerTest.class);
  private static final UUID TEST_ACCOUNT_ID = WatchListController.ACCOUNT_ID;

  @Inject
//  @Client("/account/watchlist")
  @Client("/")
  HttpClient client;

  @Inject
  InMemoryAccountStore inMemoryAccountStore;

  @BeforeEach
  void setup() {
    inMemoryAccountStore.deleteWatchList(TEST_ACCOUNT_ID);
  }

  @Test
  void returnsEmptyWatchListForTestAccount() {
    final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("my-user", "secret");
    var login = HttpRequest.POST("/login", credentials);
    var response = client.toBlocking().exchange(login, BearerAccessRefreshToken.class);
    assertEquals(HttpStatus.OK, response.getStatus());

    final BearerAccessRefreshToken token = response.body();
    assertNotNull(token);
    assertEquals("my-user", token.getUsername());
    LOG.debug("Login Bearer Token: {} expires in {}",token.getAccessToken(), token.getExpiresIn());

    var request = GET("/")
        .accept(MediaType.APPLICATION_JSON)
        .bearerAuth(token.getAccessToken());
    final WatchList result = client.toBlocking().retrieve(request, WatchList.class);
    assertNull(result.symbols());
    Assertions.assertTrue(inMemoryAccountStore.getWatchList(TEST_ACCOUNT_ID).symbols().isEmpty());
  }

  @Test
  void returnsWatchListForTestAccount() {
    givenWatchListForAccountExist();
    var response = client.toBlocking().exchange("/", JsonNode.class);
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals("""
        {
          "symbols" : [ {
            "value" : "AAPL"
          }, {
            "value" : "GOOGL"
          }, {
            "value" : "MSFT"
          } ]
        }""", response.getBody().get().toPrettyString());
  }


  @Test
  void canUpdateWatchListForTestAccount() {
    var symbols = Stream.of("AAPL", "GOOGL", "MSFT").map(Symbol::new).toList();
    final var request = HttpRequest.PUT("/", new WatchList(symbols))
        .accept(MediaType.APPLICATION_JSON);
    final HttpResponse<Object> added = client.toBlocking().exchange(request);
    assertEquals(HttpStatus.OK, added.getStatus());
    Assertions.assertEquals(symbols, inMemoryAccountStore.getWatchList(TEST_ACCOUNT_ID).symbols());
  }

  @Test
  void canDeleteWatchListForTestAccount() {
    givenWatchListForAccountExist();
    Assertions.assertFalse(inMemoryAccountStore.getWatchList(TEST_ACCOUNT_ID).symbols().isEmpty());

    var deleted = client.toBlocking().exchange(HttpRequest.DELETE("/"));
    assertEquals(HttpStatus.NO_CONTENT, deleted.getStatus());
    Assertions.assertTrue(inMemoryAccountStore.getWatchList(TEST_ACCOUNT_ID).symbols().isEmpty());
  }

  private void givenWatchListForAccountExist() {
    inMemoryAccountStore.updateWatchList(TEST_ACCOUNT_ID, new WatchList(
        Stream.of("AAPL", "GOOGL", "MSFT")
            .map(Symbol::new)
            .toList()
//        Stream.of("AAPL", "GOOGL", "MSFT")
//            .map(s -> new Symbol(s))    // 람다를 메서드 참조를 변환하면 더 코드를 간결하게 할 수 있다.
//            .toList()
    ));
  }
}
