package com.sp.fc.teacher;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TeacherManager implements AuthenticationProvider, InitializingBean {
    private final Map<String, Teacher> teacherDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TeacherAuthenticationToken token = (TeacherAuthenticationToken) authentication;
        if(teacherDB.containsKey(token.getCredentials())) {
            Teacher teacher = teacherDB.get(token.getCredentials());
            return TeacherAuthenticationToken.builder()
                    .principal(teacher)
                    .credentials(null)
                    .details(teacher.getUsername())
                    .authenticated(true)
                    .build();
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == TeacherAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Teacher("paik", "백선생", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null),
                new Teacher("kim", "김선생", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null),
                new Teacher("jo", "조선생", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null)
        ).forEach(s ->
                teacherDB.put(s.getId(), s));
    }
}
