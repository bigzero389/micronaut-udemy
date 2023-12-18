package kr.co.bigzero.udemy.broker.watchlist;

import kr.co.bigzero.udemy.broker.Symbol;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public record WatchList(List<Symbol> symbols) {
  public WatchList() {
    this(new ArrayList<>());
  }
}
