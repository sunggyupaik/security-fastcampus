package com.sp.fc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.fc.student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultiChainProxyTest {
    @LocalServerPort
    int port;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    void test_1() throws JsonProcessingException {
        String url = format("http://localhost:%d/api/teacher/students", port);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString(
                "paik:1".getBytes()
        ));

        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

         List<Student> list = new ObjectMapper().readValue(response.getBody(),
                new TypeReference<List<Student>>() {
                });

        assertEquals(3, list.size());
    }

    @Test
    void test_2(){
        TestRestTemplate testClient = new TestRestTemplate("paik", "1");
        ResponseEntity<List<Student>> resp = testClient.exchange("http://localhost:" + port + "/api/teacher/students",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });

        assertNotNull(resp.getBody());
        assertEquals(3, resp.getBody().size());
    }
}
