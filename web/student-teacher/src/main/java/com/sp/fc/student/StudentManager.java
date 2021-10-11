package com.sp.fc.student;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {
    private final Map<String, Student> studentDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if(studentDB.containsKey(token.getName())) {
                Student student = studentDB.get(token.getName());
                return studentAuthenticationToken(student);
            }
            return null;
        }

        if(authentication instanceof StudentAuthenticationToken) {
            StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;
            if(studentDB.containsKey(token.getCredentials())) {
                Student student = studentDB.get(token.getCredentials());
                return studentAuthenticationToken(student);
            }
            return null;
        }

        return null;
    }

    private StudentAuthenticationToken studentAuthenticationToken(Student student) {
        return StudentAuthenticationToken.builder()
                .principal(student)
                .credentials(null)
                .details(student.getUsername())
                .authenticated(true)
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == StudentAuthenticationToken.class ||
                authentication == UsernamePasswordAuthenticationToken.class;
    }

    public List<Student> myStudentList(String teacherId) {
        return studentDB.values().stream().filter(s-> s.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Student("hong", "홍길동", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "paik"),
                new Student("gang", "강호동", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "paik"),
                new Student("bong", "봉태호", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "paik")
        ).forEach(s ->
                studentDB.put(s.getId(), s));
    }
}
