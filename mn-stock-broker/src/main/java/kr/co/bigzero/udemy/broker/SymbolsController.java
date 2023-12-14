package kr.co.bigzero.udemy.broker;

import io.micronaut.http.annotation.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller("/symbols")
public class SymbolsController {

  public List<Symbol> getAll() {
    return new ArrayList<>();
  }
}
