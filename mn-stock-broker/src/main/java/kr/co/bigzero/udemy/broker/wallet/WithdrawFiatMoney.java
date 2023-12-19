package kr.co.bigzero.udemy.broker.wallet;

import kr.co.bigzero.udemy.broker.symbol.Symbol;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawFiatMoney(
  UUID accountId,
  UUID walletId,
  Symbol symbol,
  BigDecimal amount
) {}
