package com.sp.fc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.sp.fc.config",
        "com.sp.fc.site",
        "com.sp.fc"
})
public class OnlinePaperApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlinePaperApplication.class, args);
    }
}
