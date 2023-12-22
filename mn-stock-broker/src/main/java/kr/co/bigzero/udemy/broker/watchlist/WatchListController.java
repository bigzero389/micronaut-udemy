package kr.co.bigzero.udemy.broker.watchlist;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import kr.co.bigzero.udemy.broker.data.InMemoryAccountStore;

import java.util.UUID;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/account/watchlist")
public record WatchListController(InMemoryAccountStore store) {
  static final UUID ACCOUNT_ID = UUID.randomUUID();

  @Get(produces = MediaType.APPLICATION_JSON)
  public WatchList get() {
    return store.getWatchList(ACCOUNT_ID);
  }

  @Put(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  public WatchList update(@Body WatchList watchList) {
    return store.updateWatchList(ACCOUNT_ID, watchList);
  }

  //DELETE
//  @Status(HttpStatus.NO_CONTENT)
  @Delete(produces = MediaType.APPLICATION_JSON)
  public HttpResponse<Void> delete() {
    store.deleteWatchList(ACCOUNT_ID);
    return HttpResponse.noContent();
  }

}
