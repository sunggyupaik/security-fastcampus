package com.sp.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.sp.fc.config",
        "com.sp.fc"
})
public class UserDetailsTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserDetailsTestApplication.class, args);
    }
}
