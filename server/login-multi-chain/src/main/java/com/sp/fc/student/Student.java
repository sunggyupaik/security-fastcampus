package com.sp.fc.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@Builder
public class Student {
    private String id;
    private String username;
    @JsonIgnore
    private Set<GrantedAuthority> role;
    private String teacherId;
}
