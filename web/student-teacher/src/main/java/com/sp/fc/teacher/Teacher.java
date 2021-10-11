package com.sp.fc.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sp.fc.student.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private String id;
    private String username;
    @JsonIgnore
    private Set<GrantedAuthority> role;
    private List<Student> studentList;
}
