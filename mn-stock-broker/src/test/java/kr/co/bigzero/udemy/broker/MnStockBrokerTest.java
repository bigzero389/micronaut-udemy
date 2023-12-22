package kr.co.bigzero.udemy.broker;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class MnStockBrokerTest {

  @Inject
  EmbeddedApplication application;

  @Test
  void testItWorks() {
    Assertions.assertTrue(application.isRunning());
  }
}
