package kr.co.bigzero.udemy.broker.watchlist;

import kr.co.bigzero.udemy.broker.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

public record WatchList(List<Symbol> symbols) {
  public WatchList() {
    this(new ArrayList<>());
  }
}
