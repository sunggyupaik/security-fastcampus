package com.sp.fc.config;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestInfo {
    private String remoteIp;
    private String sessionId;
    private LocalDateTime loginTime;
}
