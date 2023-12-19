package kr.co.bigzero.udemy.broker.wallet;

import kr.co.bigzero.udemy.broker.exception.RestApiResponse;
import kr.co.bigzero.udemy.broker.symbol.Symbol;

import java.math.BigDecimal;
import java.util.UUID;

public record Wallet (
  UUID accountId,
  UUID walletId,
  Symbol symbol,
  BigDecimal available,
  BigDecimal locked
) implements RestApiResponse {
  public Wallet addAvailable(BigDecimal amountToAdd) {
    return new Wallet(
        this.accountId,
        this.walletId,
        this.symbol,
        this.available.add(amountToAdd),
        this.locked
    );
  }
}
