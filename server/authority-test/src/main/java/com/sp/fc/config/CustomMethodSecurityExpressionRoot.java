package com.sp.fc.config;

import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

@Getter
@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
    implements MethodSecurityExpressionOperations {
    private MethodInvocation invocation;
    public CustomMethodSecurityExpressionRoot(Authentication authentication,
                                              MethodInvocation invocation) {
        super(authentication);
        this.invocation = invocation;
    }

    private Object filterObject;
    private Object returnObject;

    public boolean isStudent(){
        return getAuthentication().getAuthorities().stream()
                .anyMatch(a->a.getAuthority().equals("ROLE_STUDENT"));
    }

    @Override
    public Object getThis() {
        return this;
    }
}
