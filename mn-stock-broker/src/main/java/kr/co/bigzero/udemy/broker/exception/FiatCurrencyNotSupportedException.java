package kr.co.bigzero.udemy.broker.exception;

public class FiatCurrencyNotSupportedException extends RuntimeException {
  public FiatCurrencyNotSupportedException(String message) {
    super(message);
  }
}
