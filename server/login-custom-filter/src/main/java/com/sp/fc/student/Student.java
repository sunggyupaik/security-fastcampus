package com.sp.fc.student;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@Builder
public class Student {
    private String id;
    private String username;
    private Set<GrantedAuthority> role;
}
