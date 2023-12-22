package kr.co.bigzero.udemy.hello;

import io.micronaut.context.annotation.Property;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("${hello.controller.path}")
public class HelloWorldController {

  private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

  private final MyService service;
  private final String helloFromConfig;
  private final HelloWorldTranslationConfig translationConfig;

  public HelloWorldController(MyService service, @Property(name="hello.world.message") String helloFromConfig, HelloWorldTranslationConfig translationConfig) {
    this.service = service;
    this.helloFromConfig = helloFromConfig;
    this.translationConfig = translationConfig;
  }

  @Get(produces = MediaType.TEXT_PLAIN)
  public String helloWorld() {
//  return "Hello World!";
    LOG.debug("Called the hello World API!");
    return service.helloFromService();
  }

  @Get("/hello-world")
  public String index() {
    return "Hello World";
  }

  @Get(uri = "/config", produces = MediaType.TEXT_PLAIN)
  public String helloConfig() {
    LOG.debug("return Hello From Config Message: {}", helloFromConfig);
    return helloFromConfig;
  }

  @Get(uri = "/translation", produces = MediaType.APPLICATION_JSON)
  public HelloWorldTranslationConfig helloTranslation() {
    return translationConfig;
  }
}
