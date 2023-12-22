package kr.co.bigzero.udemy.hello;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Singleton
public class HelloWorldService implements MyService {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldService.class);

    @Value("${hello.service.greeting}")
    private String greeting;

    @Override
    public String helloFromService() {
        return greeting;
    }

    @EventListener
    public void onStartup(StartupEvent startupEvent) {
        LOG.debug("Startup HelloWorldService");
    }


}
