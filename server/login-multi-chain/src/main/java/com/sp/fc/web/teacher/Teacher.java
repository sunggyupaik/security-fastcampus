package com.sp.fc.web.teacher;

import com.sp.fc.web.student.Student;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class Teacher {
    private String id;
    private String username;
    private Set<GrantedAuthority> role;
}
