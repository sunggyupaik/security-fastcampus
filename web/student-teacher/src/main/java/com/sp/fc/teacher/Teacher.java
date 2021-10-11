package com.sp.fc.teacher;

import com.sp.fc.student.Student;
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
    private List<Student> studentList;
}
