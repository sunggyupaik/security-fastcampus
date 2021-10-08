package com.sp.fc.teacher;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@Builder
public class Teacher {
    private String id;
    private String username;
    private Set<GrantedAuthority> role;
}
