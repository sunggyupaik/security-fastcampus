package com.sp.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.sp.fc"
})
public class AuthorityTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorityTestApplication.class, args);
    }
}
