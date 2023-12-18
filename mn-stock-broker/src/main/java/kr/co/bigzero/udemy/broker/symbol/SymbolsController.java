package kr.co.bigzero.udemy.broker.symbol;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import kr.co.bigzero.udemy.broker.data.InMemorySymbolStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller("/symbols")
public class SymbolsController {

  private final InMemorySymbolStore inMemorySymbolStore;

  public SymbolsController(InMemorySymbolStore inMemorySymbolStore) {
    this.inMemorySymbolStore = inMemorySymbolStore;
  }

  @Get
  public List<Symbol> getAll() {
    return new ArrayList<>(inMemorySymbolStore.getSymbols().values());
  }

  @Get("{value}")
  public Symbol getSymbolByValue(@PathVariable String value) {
    return inMemorySymbolStore.getSymbols().get(value);
  }

  @Get("/filter{?max,offset}")
  public List<Symbol> getSymbols(@QueryValue Optional<Integer> max, @QueryValue Optional<Integer> offset) {
    return inMemorySymbolStore.getSymbols().values()
        .stream()
        .skip(offset.orElse(0))
        .limit(max.orElse(10))
        .toList();
  }
}

