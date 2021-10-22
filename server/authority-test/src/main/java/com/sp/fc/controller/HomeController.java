package com.sp.fc.controller;

import com.sp.fc.user.service.SecurityMessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final SecurityMessageService securityMessageService;

    public HomeController(SecurityMessageService securityMessageService) {
        this.securityMessageService = securityMessageService;
    }

    @PreAuthorize("@nameCheck.check(#name)")
    @GetMapping("/greeting/{name}")
    public String greeting(@PathVariable String name) {
        return "hello " + securityMessageService.message(name);
    }
}
