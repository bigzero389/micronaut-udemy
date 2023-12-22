package kr.co.bigzero.udemy.broker.hello;

import com.fasterxml.jackson.databind.JsonNode;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class HelloWorldControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testHelloResponse() {
        final String result = client.toBlocking().retrieve("/hello/hello-world");
        assertEquals(result, "Hello World");
    }

    @Test
    void helloWorldEndpointResponseWithProperContent() {
        var response = client.toBlocking().retrieve("/hello");
        assertEquals("Hello from Service", response);
    }

    @Test
    void helloWorldEndpointResponseWithProperStatusCodeAndContent() {
        var response = client.toBlocking().exchange("/hello", String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Hello from Service", response.getBody().get());
    }

    @Test
    void helloFromConfigEndpointReturnsMessageFromConfigFile() {
        var response = client.toBlocking().exchange("/hello/config", String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Hello from application.yaml", response.getBody().get());
    }
    @Test
    void helloFromTranslationEndpointReturnsContentFromConfigFile() throws IOException {
        var response = client.toBlocking().exchange("/hello/translation", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
//        assertEquals("-", response.getBody().get().toString());
        assertEquals("{\"de\":\"Hallo Welt\",\"en\":\"Hello World\"}", response.getBody().get().toString());
    }
}
