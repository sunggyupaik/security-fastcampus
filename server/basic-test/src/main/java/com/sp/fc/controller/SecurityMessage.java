package com.sp.fc.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;

@Data
@Builder
public class SecurityMessage {
    private Authentication auth;
    private String message;
}
