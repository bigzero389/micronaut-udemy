package kr.co.bigzero.udemy.broker.data;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import kr.co.bigzero.udemy.broker.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Singleton
public class InMemoryStore {
  private static final Logger LOG = LoggerFactory.getLogger(InMemoryStore.class);

  private final Map<String, Symbol> symbols = new HashMap<>();
  private final Faker faker = new Faker();

  @PostConstruct
  public void initialize() {
    IntStream.range(0, 10).forEach(i -> addNewSymbol());
  }

  private void addNewSymbol() {
    var symbol = new Symbol(faker.stock().nsdqSymbol());
    symbols.put(symbol.value(), symbol);
    LOG.debug("Add Symbol {}", symbol);
  }

  public Map<String, Symbol> getSymbols() {
    return symbols;
  }

}

