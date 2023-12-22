package kr.co.bigzero.udemy.hello;

import io.micronaut.context.annotation.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ConfigurationProperties("hello.world.translation")
public interface HelloWorldTranslationConfig {
  @NotBlank
  String getEn();

  @NotBlank
  String getKr();

  @NotNull
  String getDe();


}
