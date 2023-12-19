package kr.co.bigzero.udemy.broker.exception;

public record CustomError(
    int status,
    String error,
    String message
) implements RestApiResponse {

}
