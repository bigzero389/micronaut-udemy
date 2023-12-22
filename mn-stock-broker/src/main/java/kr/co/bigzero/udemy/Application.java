package kr.co.bigzero.udemy;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import kr.co.bigzero.udemy.hello.HelloWorldService;

public class Application {

  public static void main(String[] args) {
    final ApplicationContext context = Micronaut.run(Application.class, args);
    final HelloWorldService service = context.getBean(HelloWorldService.class);
    System.out.println(service.helloFromService());
//    context.close();
  }
}
