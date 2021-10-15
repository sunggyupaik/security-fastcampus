package com.sp.fc.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorityBasicTest extends WebIntegrationTest {
    TestRestTemplate client;

    @Test
    void test_1() {
        client = new TestRestTemplate("user1", "1111");
        ResponseEntity<String> response = client.getForEntity(uri("/greeting"), String.class);

        assertEquals("hello", response.getBody());
        System.out.println(response.getBody());
    }
}
